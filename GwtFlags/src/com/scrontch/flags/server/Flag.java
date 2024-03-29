package com.scrontch.flags.server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.HashSet; 
import java.util.Scanner;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.scrontch.flags.client.FlagInfo;

public class Flag {
	public FlagDivision division;
	public FlagColor color1;
	public FlagColor color2;
	public FlagColor color3;
	public FlagOverlay overlay;
	public FlagColor color4;
	public FlagSymbol symbol;
	public FlagColor color5;

	static Random rand = new Random();
	public static List<FlagDivision> divisions = new ArrayList<FlagDivision>();
	public static List<FlagOverlay> overlays = new ArrayList<FlagOverlay>();
	public static List<FlagColor> colors = new ArrayList<FlagColor>();
	public static List<FlagSymbol> symbols = new ArrayList<FlagSymbol>();
	public static FlagDivision fdSolid;
	public static FlagOverlay foNone;

	static Map<FlagDivision, Double> division_weight_sums = new HashMap<FlagDivision, Double>();

	static double division_weight_sum = 0;
	static Map<FlagOverlay, Double> overlay_weights = new HashMap<FlagOverlay, Double>();

	public static void addSymbolsFromFile(List<FlagSymbol> symbols, String filename) throws IOException, XMLStreamException {
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		String xmlString = scanner.useDelimiter("\\Z").next();
		byte[] xmlDATA =  xmlString.getBytes();
		scanner.close();
		ByteArrayInputStream in = new ByteArrayInputStream(xmlDATA);
		
		XMLInputFactory f = XMLInputFactory.newInstance();
		XMLEventReader r = f.createXMLEventReader(in);
		int startOffset = 0;

		FlagSymbol flagSymbol = null;

		while(r.hasNext()) {
			XMLEvent e = r.nextEvent();

			if (e.isStartElement())
			{
				StartElement startElement = e.asStartElement();
				
				if (startElement.getName().getLocalPart() == "symbol")
				{
					flagSymbol = new FlagSymbol();

					Iterator<Attribute> attributes = startElement.getAttributes();
					while (attributes.hasNext()) {
						Attribute attribute = attributes.next();
						if (attribute.getName().toString().equals("name")) {
							flagSymbol.name = attribute.getValue();
						}
						if (attribute.getName().toString().equals("width")) {
							flagSymbol.width = Integer.parseInt( attribute.getValue().toString() );
						}
						if (attribute.getName().toString().equals("height")) {
							flagSymbol.height = Integer.parseInt( attribute.getValue().toString() );
						}
						if (attribute.getName().toString().equals("centerx")) {
							flagSymbol.centerX = Integer.parseInt( attribute.getValue().toString() );
						}
						if (attribute.getName().toString().equals("centery")) {
							flagSymbol.centerY = Integer.parseInt( attribute.getValue().toString() );
						}
					}
				}

				if (startElement.getName().getLocalPart() == "svg")
				{
					startOffset = startElement.getLocation().getCharacterOffset();
				}
			}

			if (e.isEndElement())
			{
				EndElement endElement = e.asEndElement();
				if (endElement.getName().getLocalPart() == "svg")
				{
					int endOffset = endElement.getLocation().getCharacterOffset() - "<\\svg>".length();
					String svg = new String(Arrays.copyOfRange(xmlDATA, startOffset, endOffset));
					flagSymbol.svg = svg;
					//System.out.println(svg);
					symbols.add(flagSymbol);
				}			
			}
		}
	}

	//Static initialization block
	static {
		fdSolid = new FlagDivision(
				"solid",
				"<rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n",
				1);
		divisions.add(fdSolid);
		
		FlagDivision fdBicolorHorizontal = new FlagDivision(
				"bicolor-horizontal",
				"<rect width=\"360\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"360\" height=\"120\" x=\"0\" y=\"120\" fill=\"%2$s\" />\n",
				2);
		divisions.add(fdBicolorHorizontal);
				
		FlagDivision fdBicolorVertical = new FlagDivision(
				"bicolor-vertical",
				"<rect width=\"180\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"180\" height=\"240\" x=\"180\" y=\"0\" fill=\"%2$s\" />\n",
				2);
		divisions.add(fdBicolorVertical);
				
		FlagDivision fdTricolorHorizontal = new FlagDivision(
				"tricolor-horizontal",
				"<rect width=\"360\" height=\"80\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"360\" height=\"80\" x=\"0\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"80\" x=\"0\" y=\"160\" fill=\"%3$s\" />\n",
				3);
		divisions.add(fdTricolorHorizontal);
				
		FlagDivision fdTricolorVertical = new FlagDivision(
				"tricolor-vertical",
				"<rect width=\"120\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"120\" height=\"240\" x=\"120\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"120\" height=\"240\" x=\"240\" y=\"0\" fill=\"%3$s\" />\n",
				3);
		divisions.add(fdTricolorVertical);
				
		FlagDivision fdBicolor2x2 = new FlagDivision(
				"bicolor-2x2",
				"<rect width=\"180\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"180\" height=\"120\" x=\"180\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"180\" height=\"120\" x=\"0\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"180\" height=\"120\" x=\"180\" y=\"120\" fill=\"%1$s\" />\n",
				2);
		divisions.add(fdBicolor2x2);
				
		FlagDivision fdBicolorStripesHorizontal = new FlagDivision(
				"bicolor-stripes-horizontal",
				"<rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"26\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"133\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"187\" fill=\"%2$s\" />\n",
				2);
		divisions.add(fdBicolorStripesHorizontal);
				
		FlagDivision fdBicolorStripesVertical = new FlagDivision(
				"bicolor-stripes-vertical",
				"<rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"40\" height=\"240\" x=\"40\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"240\" x=\"120\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"240\" x=\"200\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"240\" x=\"280\" y=\"0\" fill=\"%2$s\" />\n",
				2);
		divisions.add(fdBicolorStripesVertical);
				
		FlagDivision fdBicolorCheckered = new FlagDivision(
				"bicolor-checkered",
				"<rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"40\" height=\"40\" x=\"40\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"120\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"200\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"280\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"0\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"80\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"160\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"240\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"320\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"40\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"120\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"200\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"280\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"0\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"80\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"160\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"240\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"320\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"40\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"120\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"200\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"280\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"0\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"80\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"160\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"240\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"320\" y=\"200\" fill=\"%2$s\" />\n",
				2);
		divisions.add(fdBicolorCheckered);
				
		FlagDivision fdBicolorDiagonalDown = new FlagDivision(
				"bicolor-diagonal-down",
				"<rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <polygon points=\"0,0 0,240 360,240\" fill=\"%2$s\" />\n",
				2);
		divisions.add(fdBicolorDiagonalDown);
				
		FlagDivision fdBicolorDiagonalUp = new FlagDivision(
				"bicolor-diagonal-up",
				"<rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <polygon points=\"360,0 0,240 360,240\" fill=\"%2$s\" />\n",
				2);
		divisions.add(fdBicolorDiagonalUp);

		FlagDivision fdBicolorDiagonal2x2 = new FlagDivision(
				"bicolor-diagonal-2x2",
				"<rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <polygon points=\"0,0 180,120 0,240\" fill=\"%2$s\" />  <polygon points=\"360,0 180,120 360,240\" fill=\"%2$s\" /> \n",
				2);
		divisions.add(fdBicolorDiagonal2x2);

		HashMap<FlagDivision, Double> division_weights = new HashMap<FlagDivision, Double>();
		division_weights.put(fdSolid, 3.0);
		division_weights.put(fdBicolorHorizontal, 2.0);
		division_weights.put(fdBicolorVertical, 2.0);
		division_weights.put(fdTricolorHorizontal, 1.0);
		division_weights.put(fdTricolorVertical, 1.0);
		division_weights.put(fdBicolor2x2, 1.0);
		division_weights.put(fdBicolorStripesHorizontal, 1.0);
		division_weights.put(fdBicolorStripesVertical, 1.0);
		division_weights.put(fdBicolorCheckered, 1.0);
		division_weights.put(fdBicolorDiagonalDown, 2.0);
		division_weights.put(fdBicolorDiagonalUp, 2.0);
		division_weights.put(fdBicolorDiagonal2x2, 1.0);

		division_weight_sum = 0;
		//iterate through HashMap keys/values
		for (Map.Entry<FlagDivision, Double> e : division_weights.entrySet()) {
			division_weight_sum += e.getValue().doubleValue();
			division_weight_sums.put(e.getKey(), division_weight_sum);
		}

		// Overlays
		foNone = new FlagOverlay(
				"none",
				"");
		foNone.incompatible_divisions.add(fdSolid);  //Force non-none overlay for solid division!
		foNone.anchors.add(new FlagSymbolAnchor(300,200, 180,120));
		foNone.symbolProbability = 0.5;
		overlays.add(foNone);

		FlagOverlay foBarCross = new FlagOverlay(
				"bar-cross",
				"<rect width=\"360\" height=\"60\" x=\"0\" y=\"90\" fill=\"%1$s\" />\n  <rect width=\"60\" height=\"240\" x=\"150\" y=\"0\" fill=\"%1$s\" />\n");
		foBarCross.incompatible_divisions.add(fdTricolorHorizontal);
		foBarCross.incompatible_divisions.add(fdTricolorVertical);
		foBarCross.incompatible_divisions.add(fdBicolorDiagonalDown);
		foBarCross.incompatible_divisions.add(fdBicolorDiagonalUp);
		foBarCross.incompatible_divisions.add(fdBicolorCheckered);
		foBarCross.incompatible_divisions.add(fdBicolorStripesHorizontal);
		foBarCross.incompatible_divisions.add(fdBicolorStripesVertical);
		foBarCross.anchors.add(new FlagSymbolAnchor(300,200, 180,120));
		foBarCross.symbolProbability = 0.3;
		overlays.add(foBarCross);
		
		FlagOverlay foDiagonalBarDown = new FlagOverlay(
				"diagonal-bar-down",
				"<polygon points=\"0,0 40,0 360,200, 360,240, 320,240, 0,40\" fill=\"%1$s\" />\n");
		foDiagonalBarDown.incompatible_divisions.add(fdTricolorHorizontal);
		foDiagonalBarDown.incompatible_divisions.add(fdTricolorVertical);
		foDiagonalBarDown.incompatible_divisions.add(fdBicolorDiagonalUp);
		foDiagonalBarDown.incompatible_divisions.add(fdBicolorCheckered);
		foDiagonalBarDown.incompatible_divisions.add(fdBicolorStripesHorizontal);
		foDiagonalBarDown.incompatible_divisions.add(fdBicolorStripesVertical);
		foDiagonalBarDown.anchors.add(new FlagSymbolAnchor(300,200, 180,120));
		foDiagonalBarDown.symbolProbability = 0.3;
		overlays.add(foDiagonalBarDown);

		FlagOverlay foDiagonalBarUp = new FlagOverlay(
				"diagonal-bar-up",
				"<polygon points=\"360,0 320,0 0,200, 0,240, 40,240, 360,40\" fill=\"%1$s\" />\n");
		foDiagonalBarUp.incompatible_divisions.add(fdTricolorHorizontal);
		foDiagonalBarUp.incompatible_divisions.add(fdTricolorVertical);
		foDiagonalBarUp.incompatible_divisions.add(fdBicolorDiagonalDown);
		foDiagonalBarUp.incompatible_divisions.add(fdBicolorCheckered);
		foDiagonalBarUp.incompatible_divisions.add(fdBicolorStripesHorizontal);
		foDiagonalBarUp.incompatible_divisions.add(fdBicolorStripesVertical);
		foDiagonalBarUp.anchors.add(new FlagSymbolAnchor(300,200, 180,120));
		foDiagonalBarUp.symbolProbability = 0.3;
		overlays.add(foDiagonalBarUp);

		FlagOverlay foDiagonalCross = new FlagOverlay(
				"diagonal-cross",
				"<polygon points=\"0,0 40,0 360,200, 360,240, 320,240, 0,40\" fill=\"%1$s\" />\n    <polygon points=\"360,0 320,0 0,200, 0,240, 40,240, 360,40\" fill=\"%1$s\" />\n");
		foDiagonalCross.incompatible_divisions.add(fdTricolorHorizontal);
		foDiagonalCross.incompatible_divisions.add(fdTricolorVertical);
		foDiagonalCross.incompatible_divisions.add(fdBicolorCheckered);
		foDiagonalCross.incompatible_divisions.add(fdBicolorStripesHorizontal);
		foDiagonalCross.incompatible_divisions.add(fdBicolorStripesVertical);
		foDiagonalCross.incompatible_divisions.add(fdBicolor2x2);
		foDiagonalCross.anchors.add(new FlagSymbolAnchor(300,200, 180,120));
		foDiagonalCross.symbolProbability = 0.3;
		overlays.add(foDiagonalCross);

		FlagOverlay foTriangleLeft = new FlagOverlay(
				"triangle-left",
				"<polygon points=\"0,0 180,120 0,240\" fill=\"%1$s\" />\n");
		foTriangleLeft.incompatible_divisions.add(fdBicolorDiagonalDown);
		foTriangleLeft.incompatible_divisions.add(fdBicolorDiagonalUp);
		foTriangleLeft.incompatible_divisions.add(fdBicolorDiagonal2x2);
		foTriangleLeft.anchors.add(new FlagSymbolAnchor(100,100, 55,120));
		foTriangleLeft.symbolProbability = 0.5;
		overlays.add(foTriangleLeft);

		FlagOverlay foCircleCenter = new FlagOverlay(
				"circle-center",
				"<circle cx=\"180\" cy=\"120\" r=\"90\" fill=\"%1$s\" />\n");
		foCircleCenter.anchors.add(new FlagSymbolAnchor(150,150, 180,120));
		foCircleCenter.symbolProbability = 0.8;
		overlays.add(foCircleCenter);

		FlagOverlay foDiamondCenter = new FlagOverlay(
				"diamond-center",
				"<polygon points=\"0,120 180,0 360,120 180,240\" fill=\"%1$s\" />\n");
		foDiamondCenter.incompatible_divisions.add(fdBicolorDiagonalDown);
		foDiamondCenter.incompatible_divisions.add(fdBicolorDiagonalUp);
		foDiamondCenter.incompatible_divisions.add(fdBicolorCheckered);
		foDiamondCenter.anchors.add(new FlagSymbolAnchor(240,150, 180,120));
		foDiamondCenter.symbolProbability = 0.9;
		overlays.add(foDiamondCenter);

		FlagOverlay foSquareCanton = new FlagOverlay(
				"square-canton",
				"<rect width=\"120\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n");
		foSquareCanton.incompatible_divisions.add(fdTricolorHorizontal);
		foSquareCanton.incompatible_divisions.add(fdBicolorVertical);
		foSquareCanton.incompatible_divisions.add(fdBicolorDiagonalDown);
		foSquareCanton.incompatible_divisions.add(fdBicolorDiagonalUp);
		foSquareCanton.incompatible_divisions.add(fdBicolor2x2);
		foSquareCanton.incompatible_divisions.add(fdBicolorDiagonal2x2);
		foSquareCanton.anchors.add(new FlagSymbolAnchor(110,110, 60,60));
		foSquareCanton.symbolProbability = 1.0;
		overlays.add(foSquareCanton);

		FlagOverlay foBigSquareCanton = new FlagOverlay(
				"big-square-canton",
				"<rect width=\"180\" height=\"160\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n");
		foBigSquareCanton.incompatible_divisions.add(fdBicolorHorizontal);
		foBigSquareCanton.incompatible_divisions.add(fdTricolorVertical);
		foBigSquareCanton.incompatible_divisions.add(fdBicolorDiagonalDown);
		foBigSquareCanton.incompatible_divisions.add(fdBicolorDiagonalUp);
		foBigSquareCanton.incompatible_divisions.add(fdBicolor2x2);
		foBigSquareCanton.incompatible_divisions.add(fdBicolorDiagonal2x2);
		foBigSquareCanton.anchors.add(new FlagSymbolAnchor(170,150, 90,80));
		foBigSquareCanton.symbolProbability = 1.0;
		overlays.add(foBigSquareCanton);

		FlagOverlay foRectangleCanton = new FlagOverlay(
				"rectangle-canton",
				"<rect width=\"180\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n");
		foRectangleCanton.incompatible_divisions.add(fdTricolorHorizontal);
		foRectangleCanton.incompatible_divisions.add(fdTricolorVertical);
		foRectangleCanton.incompatible_divisions.add(fdBicolorStripesVertical);
		foRectangleCanton.incompatible_divisions.add(fdBicolorDiagonalDown);
		foRectangleCanton.incompatible_divisions.add(fdBicolorDiagonalUp);
		foRectangleCanton.incompatible_divisions.add(fdBicolorCheckered);
		foRectangleCanton.incompatible_divisions.add(fdBicolorDiagonal2x2);
		foRectangleCanton.anchors.add(new FlagSymbolAnchor(170,110, 90,60));
		foRectangleCanton.symbolProbability = 1.0;
		overlays.add(foRectangleCanton);

		FlagOverlay foScandinavian = new FlagOverlay(
				"scandinavian",
				"<rect width=\"360\" height=\"40\" x=\"0\" y=\"100\" fill=\"%1$s\" />\n  <rect width=\"40\" height=\"240\" x=\"100\" y=\"0\" fill=\"%1$s\" />\n");
		foScandinavian.incompatible_divisions.add(fdBicolorDiagonalDown);
		foScandinavian.incompatible_divisions.add(fdBicolorDiagonalUp);
		foScandinavian.incompatible_divisions.add(fdBicolor2x2);
		foScandinavian.incompatible_divisions.add(fdBicolorCheckered);
		foScandinavian.incompatible_divisions.add(fdBicolorDiagonal2x2);
		foScandinavian.incompatible_divisions.add(fdBicolorStripesVertical);
		foScandinavian.anchors.add(new FlagSymbolAnchor(200,200, 120,120));
		foScandinavian.symbolProbability = 0.5;
		overlays.add(foScandinavian);
		
		overlay_weights = new HashMap<FlagOverlay, Double>();
		overlay_weights.put(foNone, 1.0);
		overlay_weights.put(foBarCross, 1.0);
		overlay_weights.put(foDiagonalBarDown, 1.0);
		overlay_weights.put(foDiagonalBarUp, 1.0);
		overlay_weights.put(foDiagonalCross, 1.0);
		overlay_weights.put(foTriangleLeft, 1.0);
		overlay_weights.put(foCircleCenter, 1.0);
		overlay_weights.put(foDiamondCenter, 1.0);
		overlay_weights.put(foSquareCanton, 1.0);
		overlay_weights.put(foBigSquareCanton, 1.0);
		overlay_weights.put(foRectangleCanton, 1.0);
		overlay_weights.put(foScandinavian, 1.0);

		//Colors
		colors.add(new FlagColor("black", "#000000"));
		colors.add(new FlagColor("white", "#ffffff"));
		colors.add(new FlagColor("red", "#ce1126"));
		colors.add(new FlagColor("blue", "#00335b"));
		colors.add(new FlagColor("green", "#108042"));
		colors.add(new FlagColor("yellow", "#fcd116"));
		colors.add(new FlagColor("orange", "#ff8430"));
		colors.add(new FlagColor("lightblue", "#75aadb"));
//		colors.add(new FlagColor("purple", "#8b008b"));
//		colors.add(new FlagColor("lightseagreen", "#48d688"));
		
		//Symbols
		symbols.add(FlagSymbol.NONE);
		try {
			addSymbolsFromFile(symbols, "symbols.xml");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XMLStreamException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
/*
		symbols.add(new FlagSymbol("five-pointed-star",
				"<polygon style=\"fill:%1$s;stroke:#000;stroke-width:4\" points=\"125,5 155,90 245,90 175,145 200,230 125,180 50,230 75,145 5,90 95,90\"/>",
				250, 235, 
				125, 123 
		));		
		symbols.add(new FlagSymbol("eagle",
				"<path style=\"fill:%1$s;stroke:#000;stroke-width:4\" d=\"M 167.06974,386.99218 C 181.05274,382.24789 181.82524,369.94196 186.37554,359.37962 C 193.89234,373.39602 212.23784,374.17721 233.82264,370.99931 C 249.54894,367.10924 254.07554,373.55744 259.96694,378.74577 C 263.20704,368.14289 255.46304,354.49741 239.63244,351.63316 C 223.01114,353.28638 210.02574,352.2127 202.83684,346.79162 C 206.50624,343.34 219.93584,341.66105 231.88604,338.07686 C 215.59634,317.22316 207.34124,326.62299 195.30254,324.89861 C 186.00704,317.28981 182.97294,310.435 179.80954,303.59584 C 183.66314,301.36338 199.22134,306.44633 215.42484,311.93255 C 209.69134,293.53065 201.49204,294.92731 192.18544,287.72486 C 179.47594,283.17254 180.96994,276.25297 176.69244,270.29532 C 185.25824,275.13137 191.52374,280.98973 206.71004,282.88333 C 183.21534,258.63978 179.92864,245.478 180.56574,234.46795 C 196.04784,230.17985 213.33904,282.81189 219.29804,298.37624 C 224.03274,293.56256 228.03904,287.83841 235.75924,286.75655 C 245.02994,296.98948 256.09764,304.82642 267.71344,311.93255 C 259.78594,317.9086 249.69424,304.20913 241.56914,310.96424 C 232.34264,322.7423 236.84594,331.28983 240.60074,340.01347 C 240.27344,331.81213 245.03284,330.60515 250.94164,330.97851 C 257.22814,332.35912 261.67164,329.59307 264.81234,323.89558 C 268.15704,320.19254 271.50174,320.20329 274.84644,320.35412 C 274.60864,331.05799 276.87604,344.62512 271.60014,349.57117 C 266.44044,366.33038 280.63274,360.02783 275.73184,377.01748 C 281.82914,373.45037 287.20524,372.18072 292.55374,358.1297 C 291.86404,347.83709 289.28724,346.78579 286.35624,343.96386 L 285.76594,324.48583 C 292.67434,328.68488 298.97954,333.72822 301.99764,343.37361 C 305.38694,359.89158 320.03664,343.46921 324.72204,360.49066 C 330.35054,343.38492 324.69904,327.42655 313.80254,326.55167 C 301.12904,328.69458 292.56884,319.47891 292.25864,312.97608 C 302.81894,311.75473 315.05274,317.29698 323.54154,304.71268 C 325.58924,302.9684 327.57614,301.1482 332.69034,303.23707 C 326.50824,292.87688 320.60704,289.03784 311.44154,287.59562 C 296.85484,283.90441 299.40004,303.96145 284.29034,307.6639 L 273.66594,305.0078 L 242.67824,279.62733 L 254.77824,275.49564 C 235.33614,260.76463 230.49264,238.0708 218.77334,219.1274 C 224.92514,211.64526 235.27854,255.01325 255.07334,263.39565 C 256.54904,254.0501 241.98964,242.14689 231.46354,210.27375 C 239.22974,206.6382 248.48224,250.0168 283.10984,269.00296 C 290.56574,272.75182 296.90064,270.95392 301.99764,268.41271 C 281.31894,245.127 265.93204,220.56464 253.89284,192.56645 C 254.62604,189.97261 255.39574,187.47007 259.20504,192.56645 C 270.20694,221.08511 289.40544,238.91781 318.22934,254.83712 C 297.35394,234.28706 280.89204,211.53024 266.28794,187.84451 C 265.92014,181.09598 268.13224,179.93736 272.48554,183.41769 C 280.93314,197.87466 289.84114,212.07588 305.24404,222.66886 C 314.89394,226.09527 323.23504,224.94098 329.73914,217.35668 C 307.11804,204.37579 297.56464,179.51521 282.66724,159.51283 C 281.27964,152.16674 284.62434,152.70779 287.83184,153.02015 C 294.31314,170.47938 310.96974,183.57771 328.26344,196.40304 L 333.57564,195.51767 C 316.80544,178.66991 304.02514,159.82712 290.19284,141.51041 C 291.05734,139.27269 289.06774,135.67267 294.32454,138.5592 C 300.47874,145.23486 305.85094,151.81092 314.98304,157.7421 C 321.82164,160.92222 327.71934,159.86783 332.98544,155.97137 C 317.83024,143.1156 306.92314,128.98539 299.63664,113.76898 L 301.70254,108.45679 C 308.05524,119.33373 317.38604,127.97707 330.03424,134.13237 L 301.40744,93.700706 C 301.01254,89.467936 303.05464,90.108936 304.94884,90.454366 C 309.86414,97.137806 315.64874,105.12331 330.91954,100.19339 C 305.83744,83.000536 310.85134,38.217836 296.39034,20.215416 C 282.96744,5.8155262 264.07714,12.573746 256.54894,19.625176 C 247.22864,32.894116 247.38894,50.130116 259.79524,53.859286 C 267.38814,61.389346 265.44204,69.449356 258.90994,77.764146 C 246.25944,95.266486 232.78434,109.88231 217.00264,116.42507 C 198.11584,119.25939 193.05404,113.29602 191.32704,105.21045 C 202.88804,105.71386 210.55514,101.26143 213.46114,90.749496 C 194.98124,92.855096 186.00104,90.020846 187.19534,81.895836 C 201.20714,83.801986 204.98854,79.456216 204.90264,72.747066 C 197.86704,79.542426 195.61274,73.409776 191.62214,68.025126 C 189.44014,64.411186 190.88084,48.172916 194.27824,42.939786 C 196.51274,37.037356 198.39904,31.134916 193.68804,25.232486 C 191.47604,22.281266 190.54164,19.330056 194.57334,16.378836 C 201.82684,17.372446 203.96684,10.695896 206.67334,4.8690962 C 194.47754,9.0878862 179.83884,9.3958762 168.89784,4.2788562 C 156.80524,-0.89749381 141.35374,-1.7401938 135.84414,8.1154362 C 122.35264,10.304106 116.38564,16.513186 118.43204,25.232486 L 121.97344,32.315406 C 122.78394,19.672226 152.46884,29.378666 150.01004,34.086136 L 133.77834,35.561746 C 143.58564,40.805636 157.36284,45.325966 155.91244,58.581236 C 153.71664,76.796646 138.09234,73.153336 126.99054,73.042196 C 125.78364,78.962456 128.41754,84.334016 138.20514,88.683636 C 131.90804,91.460076 127.02114,97.409316 117.84174,93.700706 C 116.72884,101.12181 125.90144,105.50789 137.02464,108.45679 C 127.93134,116.40693 117.68284,118.58173 105.87404,111.46642 C 90.51974,105.57231 87.81964,93.829816 75.16924,76.327476 C 71.51044,68.731016 70.28264,60.671016 74.28384,52.422616 C 86.69024,48.693446 86.85054,31.457456 77.53024,18.188506 C 70.00194,11.137076 51.11174,4.3788562 37.68874,18.778756 C 23.22784,36.781176 28.24164,81.563866 3.1595405,98.756716 C 18.43044,103.68664 24.21494,95.701136 29.13024,89.017696 C 31.02454,88.672266 33.06654,88.031266 32.67174,92.264036 L 4.0449405,132.69571 C 16.69314,126.5404 26.02394,117.89706 32.37664,107.02012 L 34.44244,112.33231 C 27.15604,127.54872 16.24894,141.67893 1.0937405,154.5347 C 6.3598405,158.43116 12.25744,159.48555 19.09614,156.30544 C 28.22824,150.37425 33.60044,143.79819 39.75464,137.12253 C 45.01134,134.236 43.02184,137.83603 43.88634,140.07374 C 30.05394,158.39045 17.27374,177.23325 0.5034405,194.081 L 5.8156405,194.96637 C 23.10934,182.14105 39.76604,169.04271 46.24734,151.58349 C 49.45484,151.27112 52.79954,150.73007 51.41194,158.07617 C 36.51454,178.07854 26.96114,202.93912 4.3400405,215.92001 C 10.84404,223.50431 19.18524,224.6586 28.83514,221.23219 C 44.23794,210.63921 53.14604,196.43799 61.59364,181.98102 C 65.94684,178.50069 68.15904,179.65931 67.79124,186.40784 C 53.18714,210.09357 36.72524,232.85039 15.84974,253.40045 C 44.67364,237.48114 63.87224,219.64844 74.87414,191.12978 C 78.68344,186.03341 79.45314,188.53594 80.18634,191.12978 C 68.14714,219.12797 52.76014,243.69033 32.08144,266.97605 C 37.17854,269.51725 43.51344,271.31515 50.96924,267.56629 C 85.59684,248.58013 94.84944,205.20153 102.61554,208.83709 C 92.08954,240.71023 77.53014,252.61343 79.00584,261.95898 C 98.80064,253.57658 109.15394,210.20859 115.30574,217.69073 C 103.58644,236.63413 98.74294,259.32796 79.30094,274.05897 L 91.40094,278.19067 L 60.41314,303.57114 L 49.78874,306.22723 C 34.67914,302.52478 37.22434,282.46775 22.63754,286.15895 C 13.47214,287.60117 7.5709405,291.44021 1.3888405,301.8004 C 6.5030405,299.71153 8.4899405,301.53173 10.53764,303.27601 C 19.02634,315.86031 31.26024,310.31806 41.82054,311.53941 C 41.51034,318.04224 32.95014,327.25791 20.27664,325.11501 C 9.3796405,325.98988 3.7285405,341.94825 9.3571405,359.05399 C 14.04254,342.03251 28.69214,358.45491 32.08144,341.93695 C 35.09954,332.29155 41.40484,327.24821 48.31314,323.04916 L 47.72294,342.52719 C 44.79194,345.34913 42.21514,346.40042 41.52534,356.69303 C 46.87384,370.74405 52.24994,372.0137 58.34734,375.58081 C 53.44634,358.59116 67.63874,364.89371 62.47904,348.1345 C 57.20304,343.18845 59.47054,329.62132 59.23264,318.91745 C 62.57734,318.76662 65.92214,318.75587 69.26684,322.45891 C 72.40744,328.1564 76.85104,330.92245 83.13754,329.54184 C 89.04634,329.16848 93.80574,330.37547 93.47834,338.5768 C 97.23324,329.85317 101.73644,321.30563 92.51004,309.52757 C 84.38484,302.77246 74.29314,316.47193 66.36574,310.49588 C 77.98154,303.38976 89.04924,295.55281 98.31984,285.31989 C 106.04004,286.40174 110.04644,292.12589 114.78114,296.93958 C 120.74004,281.37522 138.03124,228.74318 153.51344,233.03128 C 154.15044,244.04133 150.86374,257.20311 127.36914,281.44666 C 142.55534,279.55307 148.82094,273.6947 157.38664,268.85865 C 153.10914,274.81631 154.60314,281.73587 141.89374,286.28819 C 132.58704,293.49065 124.38784,292.09398 118.65434,310.49588 C 134.85774,305.00967 150.41604,299.92671 154.26954,302.15918 C 151.10614,308.99833 148.07214,315.85314 138.77664,323.46194 C 126.73784,325.18632 118.48284,315.78649 102.19314,336.64019 C 114.14324,340.22438 127.57294,341.90334 131.24234,345.35495 C 124.05344,350.77603 111.06794,351.84971 94.44664,350.19649 C 78.61604,353.06075 70.87214,366.70622 74.11224,377.3091 C 80.00364,372.12077 84.53014,365.67258 100.25654,369.56264 C 121.84134,372.74054 140.18684,371.95935 147.70354,357.94295 C 152.25394,368.50529 153.02644,380.81122 167.06974,386.99218 L 167.06974,386.99218 z\" />\n  <path d=\"M 139.19644,5.8603562 C 141.76934,32.483346 157.51664,22.683086 173.13544,13.238396 C 172.76524,1.5767262 150.50944,8.3196962 139.19644,5.8603562 z\" style=\"fill:#ffffff;fill-opacity:1;fill-rule:evenodd;stroke:#000000;stroke-width:4px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1\" />  <path d=\"M 551.03699,47.598999 C 551.03699,50.027159 548.74051,51.995572 545.90765,51.995572 C 543.0748,51.995572 540.77832,50.027159 540.77832,47.598999 C 540.77832,45.170839 543.0748,43.202426 545.90765,43.202426 C 548.74051,43.202426 551.03699,45.170839 551.03699,47.598999 z\"  transform=\"matrix(1.00688,0,0,1.00688,-399.169,-28.0062)\"  style=\"fill:#0a0a0a;fill-opacity:1;fill-rule:nonzero;stroke:#000000;stroke-width:3;stroke-linecap:round;stroke-linejoin:round;stroke-miterlimit:4;stroke-dasharray:none;stroke-dashoffset:0;stroke-opacity:1\" />",
				334, 388, 
				167, 194 
		));		
		symbols.add(new FlagSymbol("sun",
				"<path style=\"fill:%1$s;stroke:#000;stroke-width:3\" d=\"M 56.095816,8.1647826 C 53.880946,16.826903 56.322316,23.957673 60.555486,30.676703 C 63.828886,35.872363 65.979646,41.409303 65.877316,47.358313 C 64.388296,48.197953 62.968396,49.103003 61.597776,50.076943 L 27.387186,27.353903 L 50.354436,61.187063 C 49.941786,61.756813 49.539746,62.348993 49.152666,62.934913 C 42.638366,60.000353 36.523196,59.418463 30.696016,60.545673 C 22.458466,62.139143 15.464846,60.413923 8.161986,56.061403 C 12.720886,63.752603 19.522446,67.066733 27.266816,68.824513 C 33.267326,70.186483 38.691196,72.576423 42.830366,76.871763 C 42.379986,78.490303 42.008026,80.127363 41.725196,81.789193 L 1.456986,89.954943 L 41.634416,97.638293 C 41.744846,98.329523 41.877906,99.018733 42.019056,99.708803 C 35.335826,102.2374 30.584216,106.14025 27.260096,111.0589 C 22.562016,118.01047 16.429886,121.73755 8.188306,123.82376 C 16.850416,126.03865 23.969236,123.56843 30.688276,119.33525 C 35.883946,116.06186 41.420886,113.91109 47.369876,114.01342 C 48.209526,115.50244 49.114566,116.92235 50.088506,118.29297 L 27.377416,152.5324 L 61.227476,129.52436 C 61.792816,129.93353 62.365216,130.35407 62.946486,130.73807 C 60.011926,137.25238 59.441976,143.39637 60.569186,149.22356 C 62.162646,157.46113 60.425516,164.42595 56.072976,171.72877 C 63.764186,167.16993 67.078326,160.36834 68.836086,152.62393 C 70.198056,146.62341 72.587996,141.19954 76.883326,137.06037 C 78.505136,137.51204 80.135476,137.88211 81.800756,138.16553 L 89.966516,178.43377 L 97.649866,138.25632 C 98.341106,138.1459 99.030306,138.01284 99.720386,137.87169 C 102.24755,144.55691 106.15297,149.30729 111.07047,152.63066 C 118.02205,157.32873 121.74914,163.46088 123.83535,171.70244 C 126.05024,163.04035 123.58002,155.92151 119.34684,149.20248 C 116.06708,143.99667 113.94035,138.47653 114.05384,132.50891 C 115.53312,131.67352 116.94226,130.77025 118.30455,129.80223 L 152.54397,152.51333 L 129.56477,118.65132 C 129.96853,118.09284 130.37046,117.51825 130.74967,116.94426 C 137.26582,119.88729 143.40498,120.44933 149.23514,119.32155 C 157.47268,117.72802 164.43751,119.46529 171.74034,123.81777 C 167.18145,116.12657 160.39184,112.84128 152.64744,111.0835 C 146.65368,109.72308 141.23598,107.28711 137.1008,102.99546 C 137.55008,101.3797 137.89479,99.748873 138.17712,98.089983 L 178.45728,89.953073 L 138.2679,82.240883 C 138.15748,81.549653 138.02441,80.860443 137.88326,80.170363 C 144.57262,77.644093 149.31763,73.739573 152.64223,68.820283 C 157.3403,61.868713 163.48439,58.170453 171.72596,56.084253 C 163.06384,53.869373 155.93307,56.310733 149.21405,60.543903 C 144.00829,63.823673 138.48813,65.950413 132.52051,65.836913 C 131.6851,64.357633 130.78183,62.948493 129.81381,61.586213 L 152.53686,27.375613 L 118.66291,50.325983 C 118.10441,49.922223 117.52984,49.520303 116.95585,49.141093 C 119.89493,42.628673 120.47229,36.511623 119.34507,30.684443 C 117.75158,22.446883 119.47685,15.453263 123.82936,8.1504126 C 116.13816,12.709303 112.85285,19.498923 111.09508,27.243293 C 109.73159,33.250543 107.29995,38.692033 102.99016,42.830743 C 101.37934,42.383383 99.755196,41.995073 98.101566,41.713633 L 89.964636,1.4334726 L 82.252446,41.622843 C 81.561216,41.733273 80.872016,41.866333 80.181936,42.007493 C 77.653786,35.322493 73.751226,30.573173 68.831846,27.248523 C 61.880266,22.550443 58.182026,16.406373 56.095816,8.1647826 z\" />",
				180, 180, 
				90, 90 
		));		
		symbols.add(new FlagSymbol("four-leaf-clover",
				"<path style=\"fill:%1$s;stroke:#000;stroke-width:5\" d=\"M 206.5,1.5 C 212.16667,1.5 217.16667,2.5 221.5,4.5 C 225.83333,6.5 229.91667,9.25 233.75,12.75 C 237.58333,16.25 241.75,21.41667 246.25,28.25 C 250.75,35.083328 255.16667,40 259.5,43 C 263.83334,46 268.58334,49.083328 273.75,52.25 C 278.91666,55.416672 282.83334,59 285.5,63 C 288.16666,67 290.08334,70.833344 291.25,74.5 C 292.41666,78.166656 292.41666,84.166656 291.25,92.5 C 290.08334,100.83334 288.16666,107.5 285.5,112.5 C 282.83334,117.5 278.58334,122.25 272.75,126.75 C 266.91666,131.25 261.16666,134.83333 255.5,137.5 C 249.83333,140.16667 244.83333,142.16667 240.5,143.5 C 236.16667,144.83333 226.58333,146.25 211.75,147.75 C 196.91667,149.25 189.25,150.25 188.75,150.75 L 188,151.5 L 188.5,152.75 C 188.83333,153.58333 199.5,153.75 220.5,153.25 C 241.5,152.75 256,153.66667 264,156 C 272,158.33333 278.08334,161.08333 282.25,164.25 C 286.41666,167.41667 289.66666,170.83333 292,174.5 C 294.33334,178.16667 296.16666,181.66667 297.5,185 C 298.83334,188.33333 299,194.83333 298,204.5 C 297,214.16667 295.41666,220.75 293.25,224.25 C 291.08334,227.75 289.83334,229.91667 289.5,230.75 L 289,232 L 284.75,234.5 C 281.91666,236.16667 278.5,239.66667 274.5,245 C 270.5,250.33333 267.16666,255.83333 264.5,261.5 C 261.83334,267.16666 259.58334,271.25 257.75,273.75 C 255.91667,276.25 254.16667,278.08334 252.5,279.25 C 250.83333,280.41666 249.5,281.75 248.5,283.25 C 247.5,284.75 244.33333,286.83334 239,289.5 C 233.66667,292.16666 227.5,293 220.5,292 C 213.5,291 207.58333,288.75 202.75,285.25 C 197.91667,281.75 193.83333,278.33334 190.5,275 C 187.16667,271.66666 183.75,267.58334 180.25,262.75 C 176.75,257.91666 174.41667,254.41667 173.25,252.25 C 172.08333,250.08333 170.66667,245 169,237 C 167.33333,229 165.5,222 163.5,216 L 160.5,207 L 159,213.5 C 158,217.83333 158,227.33333 159,242 C 160,256.66666 162.16667,268.33334 165.5,277 C 168.83333,285.66666 171.91667,291.75 174.75,295.25 C 177.58333,298.75 180.91667,302.25 184.75,305.75 C 188.58333,309.25 190.16667,312.83334 189.5,316.5 C 188.83333,320.16666 188.41667,322.08334 188.25,322.25 L 188,322.5 L 181,321 C 176.33333,320 171.75,317.58334 167.25,313.75 C 162.75,309.91666 159,305.83334 156,301.5 C 153,297.16666 150.16667,291.5 147.5,284.5 C 144.83333,277.5 143,270.66666 142,264 C 141,257.33334 140.91667,245.25 141.75,227.75 L 143,201.5 L 139.75,202.25 C 137.58333,202.75 136,204.5 135,207.5 C 134,210.5 132.16667,220.33333 129.5,237 C 126.83334,253.66667 124.16666,264.5 121.5,269.5 C 118.83334,274.5 116.41666,278.08334 114.25,280.25 C 112.08334,282.41666 109.33334,284.66666 106,287 C 102.66666,289.33334 99,291.16666 95,292.5 C 91,293.83334 84.666656,294 76,293 C 67.333344,292 61,290.33334 57,288 C 53,285.66666 48.416672,281.25 43.25,274.75 C 38.083328,268.25 34.25,262.33334 31.75,257 L 28,249 L 26.5,247.75 C 25.5,246.91667 23.58333,245.25 20.75,242.75 C 17.91667,240.25 14.33333,236.16667 10,230.5 C 5.66667,224.83333 3.16667,221 2.5,219 C 1.83333,217 2,211.5 3,202.5 C 4,193.5 6.16667,186.33333 9.5,181 C 12.83333,175.66667 16.91667,171.08333 21.75,167.25 C 26.58333,163.41667 31.66667,160.5 37,158.5 C 42.333328,156.5 54.5,155.33333 73.5,155 C 92.5,154.66667 102.75,153.75 104.25,152.25 L 106.5,150 L 105.25,148.75 C 104.41666,147.91667 94,146.66667 74,145 C 54,143.33333 39.5,140.5 30.5,136.5 C 21.5,132.5 15.25,128.75 11.75,125.25 C 8.25,121.75 5.83333,118.83334 4.5,116.5 C 3.16667,114.16666 2.33333,109.83334 2,103.5 C 1.66667,97.166656 2.75,90.916656 5.25,84.75 C 7.75,78.583344 13.16667,72 21.5,65 C 29.83333,58 34.333328,54.416672 35,54.25 L 36,54 L 37.5,51.25 C 38.5,49.416672 41.416672,45.416672 46.25,39.25 C 51.083328,33.083328 54.75,28.58333 57.25,25.75 C 59.75,22.91667 63.333328,19.66667 68,16 C 72.666656,12.33333 77.666656,9.66667 83,8 C 88.333344,6.33333 93,5.66667 97,6 C 101,6.33333 105.66666,7.66667 111,10 C 116.33334,12.33333 121.08334,15.41667 125.25,19.25 C 129.41667,23.08333 132.5,27.16667 134.5,31.5 C 136.5,35.833328 138,41 139,47 C 140,53 141.41667,64.333344 143.25,81 C 145.08333,97.666656 146.33333,106 147,106 L 148,106 L 148.75,103.5 C 149.25,101.83334 150.16667,91.333344 151.5,72 C 152.83333,52.666672 154,41.833328 155,39.5 C 156,37.166672 158.41667,33.25 162.25,27.75 C 166.08333,22.25 170.66667,17.5 176,13.5 C 181.33333,9.5 186.33333,6.5 191,4.5 C 195.66667,2.5 200.83333,1.5 206.5,1.5 z\" />",
				310, 340, 
				149, 161 
		));		
		symbols.add(new FlagSymbol("rose",
				"<path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 39.826803,47.103134 L 25.046555,39.496442 L 9.165333,46.351449 L 11.023823,30.021445 L 0.49999995,18.580642 L 16.869364,15.478122 L 24.442888,0.49999995 L 31.392558,17.182371 L 49.356953,18.412977 L 36.757149,30.884233 L 39.826803,47.103134 z\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 25.011141,28.067971 C 20.312897,3.6312615 30.317313,3.4032515 37.744707,7.9610615 C 45.235998,12.558081 47.720069,21.017521 25.011141,28.067971 z\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 24.896964,24.830161 C 0.66126718,23.937011 5.0676872,12.771851 10.488454,8.1514115 C 15.858368,3.5743215 26.56127,1.1008915 24.896964,24.830161 z\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 24.729941,29.081071 C 43.16758,16.417011 48.56516,22.802601 45.80926,32.522351 C 43.076473,42.160591 38.712191,46.591971 24.729941,29.081071 z\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 23.664307,27.833981 C 41.523625,42.913161 30.261036,50.121091 23.457484,50.043691 C 16.654364,49.966291 6.8811522,42.891791 23.664307,27.833981 z\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 23.379563,26.074841 C 13.169281,45.976471 4.3108162,41.298791 2.4432192,32.963631 C 0.57980418,24.647131 2.7580782,17.306181 23.379563,26.074841 z\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 16.826925,16.502686 L 26.689879,21.858016 L 32.552936,16.20107 L 30.182279,25.68874 L 36.643725,30.666149 L 27.712109,31.25466 L 23.622109,39.114672 L 21.622987,30.00855 L 12.338898,29.728647 L 20.358619,23.931896 L 16.826925,16.502686 z\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 32.067272,26.656313 C 32.067956,30.573692 28.892486,33.749713 24.975109,33.749713 C 21.057731,33.749713 17.882261,30.573692 17.882945,26.656313 C 17.882261,22.738934 21.057731,19.562914 24.975109,19.562914 C 28.892486,19.562914 32.067956,22.738934 32.067272,26.656313 L 32.067272,26.656313 z\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 17.008638,43.779761 C 19.326894,46.277451 23.032327,43.898211 24.527036,43.842301 C 26.052504,43.785241 28.345776,46.255251 30.616781,43.449891\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 39.047634,38.653971 C 41.264515,37.165891 40.488416,32.986761 40.996356,31.579891 C 41.514751,30.144071 44.355397,28.187991 42.957111,25.207831\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 40.318857,16.839181 C 39.78938,12.672931 36.614182,13.448321 35.282087,12.768031 C 33.922579,12.073731 32.15809,7.6503215 28.278559,9.5201315\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 19.473773,8.4478015 C 15.355215,7.6259715 14.160777,11.689441 13.092453,12.736321 C 12.002142,13.804751 9.4201202,14.242871 9.6270202,17.846301\" /> <path style=\"fill:%1$s;stroke:#000;stroke-width:0.8;\" d=\"M 6.1713272,24.782861 C 3.8292382,28.268911 7.1211862,30.933791 7.6768792,32.322491 C 8.2440062,33.739761 7.6557742,36.291781 11.061796,37.486151\" />",
				50, 51, 
				25, 26 
		));		
		symbols.add(new FlagSymbol("cross-bottony",
				"<path transform=\"translate(15,15)\" style=\"fill:%1$s;stroke:#000;stroke-width:5;\"  d=\"M436.75,202c-6.465,0-12.487,1.881-17.564,5.115 c2.121-4.337,3.314-9.211,3.314-14.365c0-18.088-14.662-32.75-32.75-32.75 c-18.087,0-32.75,14.662-32.75,32.75c0,6.225,1.737,12.044,4.753,17  H259.75  V107.747c4.956,3.016,10.775,4.753,17,4.753c18.088,0,32.75-14.663,32.75-32.75 c0-18.087-14.662-32.75-32.75-32.75c-5.153,0-10.027,1.194-14.365,3.315 c3.234-5.077,5.115-11.1,5.115-17.565  C267.5,14.663,252.838,0,234.75,0  C216.663,0,202,14.663,202,32.75c0,6.465,1.881,12.488,5.115,17.564  C202.777,48.194,197.904,47,192.75,47  C174.663,47,160,61.663,160,79.75c0,18.087,14.663,32.75,32.75,32.75 c6.225,0,12.044-1.737,17-4.753  V209.75  H107.747c3.016-4.956,4.753-10.775,4.753-17c0-18.088-14.663-32.75-32.75-32.75  C61.663,160,47,174.662,47,192.75c0,5.154,1.194,10.027,3.315,14.365  C45.238,203.881,39.215,202,32.75,202  C14.663,202,0,216.662,0,234.75c0,18.087,14.663,32.75,32.75,32.75 c6.465,0,12.488-1.881,17.564-5.115  C48.194,266.723,47,271.596,47,276.75c0,18.087,14.663,32.75,32.75,32.75 c18.087,0,32.75-14.663,32.75-32.75c0-6.225-1.737-12.044-4.753-17  H209.75v102.003c-4.956-3.016-10.775-4.753-17-4.753 c-18.087,0-32.75,14.663-32.75,32.75c0,18.088,14.663,32.75,32.75,32.75 c5.154,0,10.027-1.193,14.365-3.314c-3.234,5.077-5.115,11.1-5.115,17.564 c0,18.088,14.663,32.75,32.75,32.75c18.088,0,32.75-14.662,32.75-32.75 c0-6.465-1.881-12.487-5.115-17.564c4.338,2.121,9.212,3.314,14.365,3.314 c18.088,0,32.75-14.662,32.75-32.75c0-18.087-14.662-32.75-32.75-32.75 c-6.225,0-12.044,1.737-17,4.753  V259.75h102.003c-3.016,4.956-4.753,10.775-4.753,17 c0,18.087,14.663,32.75,32.75,32.75c18.088,0,32.75-14.663,32.75-32.75 c0-5.154-1.193-10.027-3.314-14.365c5.077,3.234,11.1,5.115,17.564,5.115 c18.088,0,32.75-14.663,32.75-32.75  C469.5,216.662,454.838,202,436.75,202z\" />",
				500, 500, 
				250, 250 
		));		
		symbols.add(new FlagSymbol("tree",
				"<path fill=\"%1$s\" stroke=\"#000000\" stroke-width=\"2\" marker-start=\"none\" marker-mid=\"none\" marker-end=\"none\" stroke-miterlimit=\"4\" stroke-dashoffset=\"0\" stroke-opacity=\"0.597122\" id=\"path3229\" d=\"m125.505959,8.908875c1.778427,4.897034 10.109993,7.750732 11.533089,15.963379c1.437927,8.298218 -4.956924,12.284363 -9.522133,10.707092l0.088104,16.826233c2.337013,-5.990417 6.442497,-10.124451 12.143547,-12.252441c-1.028564,-3.643799 -2.196396,-7.75061 2.101654,-12.520935c4.29805,-4.770325 14.806686,0.263916 20.228836,-0.636108c-1.871964,4.018372 -1.43013,11.547302 -5.248123,16.429993c-3.925751,5.020508 -13.353348,3.878052 -15.41011,-1.412842c-5.548615,2.583618 -9.868195,9.262756 -10.677872,10.255249c-0.852066,1.044434 -2.797859,3.930298 -2.797859,6.832764l0,22.113403c1.380257,-1.996948 3.553398,-4.258179 5.286064,-5.638428c0.029373,1.174622 0.196884,5.558105 0.176193,6.695679c-1.606339,1.898865 -2.813751,3.764526 -3.87645,6.078979c0.058731,1.130615 -0.352402,18.031372 -0.52861,27.047058c4.023285,-14.184326 12.242249,-34.197815 12.950882,-35.769104c0.675446,-1.497742 1.627975,-2.537231 1.409622,-3.876465c-0.232346,-1.424927 -1.663498,-3.497314 -1.746399,-5.73053c-3.783844,1.500427 -8.754105,-0.350342 -9.951935,-5.262695c-1.193069,-4.892761 5.76088,-12.5354 6.878342,-17.449951c2.815781,3.877808 9.410202,4.18335 12.036041,10.291687c2.601639,6.052002 -1.43399,8.749878 -5.05368,11.037292c0.483246,2.136475 0.349808,4.096741 0.392563,6.145081c3.905823,-5.256653 10.632156,-14.242981 11.674698,-15.417664c0.999557,-1.126282 1.099625,-2.253418 1.218292,-3.736755c0.132355,-1.654541 -0.330154,-5.014648 -1.269897,-7.892639c1.204041,-0.234924 2.67865,-0.646057 3.882706,-0.880981c1.292145,2.554932 1.086578,6.431396 1.145325,9.25061c7.710663,-7.225647 8.923645,-11.653381 8.804214,-16.863342c-3.983124,-0.838806 -8.591064,-5.205994 -6.954834,-11.59967c1.638062,-6.40094 13.159241,-8.512024 16.895081,-11.593384c0.938202,10.617065 7.177612,11.011292 5.993271,17.553772c-1.173401,6.482178 -7.410339,7.248535 -12.355042,6.945374c-0.251129,2.630859 -0.85463,5.437866 -2.339157,7.716248c1.931091,-0.150574 4.287277,-0.240601 6.342819,-0.096619c1.908463,-4.246277 4.719284,-8.30481 11.0439,-6.781433c6.34523,1.52832 9.684647,14.430786 13.944031,15.662109c-6.671112,1.797485 -10.846878,6.160767 -17.495026,6.254822c-6.586884,0.09314 -10.050079,-4.193481 -8.64772,-11.432861c-4.4832,0.270447 -5.622147,-1.346008 -8.447754,1.151428c-2.868881,2.535706 -11.163361,10.347839 -15.946304,17.00354c2.891876,-1.38385 5.523712,-1.520264 8.193054,-2.155762c0.177658,-3.78241 2.206192,-7.792236 8.791977,-8.221497c6.593216,-0.42981 11.332153,11.344788 15.468338,12.236145c-6.343246,1.142151 -7.72084,7.610718 -15.729202,9.121216c-8.023956,1.513428 -10.166656,-5.982422 -9.440598,-9.993774c-8.601227,1.276978 -12.304947,4.299744 -16.397675,14.557922c-4.123016,10.334167 -10.142181,24.979797 -14.676025,43.540588c2.408813,-4.526917 5.108353,-9.386108 7.931259,-13.228333c2.792343,-3.800659 5.049255,-9.007874 5.394028,-10.97052l4.075104,-1.488892l0.249191,2.118103c-0.776123,1.155884 -1.412079,3.820862 -2.118103,5.731323c5.773026,-4.385742 6.612396,-5.687927 8.068466,-11.835693c-4.07045,-0.014832 -8.063049,-1.708984 -7.755417,-8.301575c0.307968,-6.600037 10.348969,-9.708679 12.073563,-13.741211c1.425217,4.516418 9.160065,8.209656 9.325089,14.856384c0.163528,6.585571 -4.355713,7.645447 -9.52681,8.057312l-2.092789,4.962891c12.589111,-6.309448 18.39444,-1.518188 27.459534,-11.88031c-3.985809,-1.559387 -7.943604,-6.773376 -3.487915,-12.024597c4.499298,-5.302673 13.964783,-2.946289 18.984573,-4.853455c-0.580704,5.651306 1.981644,14.335754 -0.691818,18.018616c-2.631409,3.624878 -9.357513,3.699829 -12.52803,0.271667c-1.335892,2.395935 -3.357071,3.795044 -5.502838,5.318787c8.525146,-0.823853 16.365036,0.781799 21.837631,4.443298c0.979507,-3.950867 3.497391,-7.387268 9.91716,-6.670715c6.430084,0.717773 10.091248,12.842834 11.792603,17.547607c-5.236771,0.864502 -10.651047,5.554565 -17.532867,3.907654c-6.965118,-1.666809 -7.502914,-8.666077 -5.433899,-12.177429c-6.465668,-3.367615 -15.885666,-4.105408 -24.629929,-4.496216c2.907349,1.850098 4.236191,2.990234 5.99437,5.647949c3.516266,-1.35907 9.093506,-1.897888 12.638184,2.028931c3.588699,3.975647 -0.166565,15.885925 -1.706757,20.527771c-7.184708,-1.839233 -12.237762,-4.132507 -15.626907,-8.126282c-3.34549,-3.942322 -2.472031,-11.361816 1.366119,-13.400879c-4.430115,-4.823181 -6.2314,-5.015137 -9.331696,-4.744202c-3.105087,0.271362 -14.443787,4.572815 -17.729706,6.472351c1.947006,0.342102 4.185349,1.317078 5.45813,3.057007c2.899551,-2.856812 8.300613,-5.333862 12.726288,-0.437927c4.430664,4.901489 0.362045,15.181152 0.231461,19.999146c-7.184708,-1.839172 -11.532394,-0.617432 -16.331711,-5.218872c-4.755096,-4.559082 -2.658905,-10.863464 1.047073,-12.726318c0.040405,-0.003723 -4.012253,-1.589539 -6.655289,-1.41333c-2.643036,0.176208 -7.400497,6.695679 -10.043533,11.453186c-2.643036,4.757446 -4.581268,11.276917 -4.757462,16.739197c-0.176208,5.46228 0.793076,37.083313 1.719284,45.856873c0.926208,8.77356 9.51265,18.00354 29.7229,11.773804c20.175491,-6.219055 27.47612,1.661438 31.176361,4.128235c3.700256,2.466858 3.14769,9.771973 2.466843,12.069885c-0.704803,2.378723 -1.233414,3.083496 -1.409622,-1.497742c-0.176193,-4.581238 -5.352798,-9.603027 -6.762421,-10.484009c-1.409607,-0.881042 -3.435944,-1.762024 -3.435944,0.176147c0,1.938232 1.850128,9.162537 3.083542,12.157959c1.233429,2.995483 -2.820541,0.615356 -4.228851,-0.880981c-1.409622,-1.497742 -3.171646,-8.105286 -3.700256,-11.100769c-0.528595,-2.995422 -7.641342,-1.108765 -9.691116,-0.880981c-2.378738,0.264282 -8.81012,2.466797 -8.81012,2.466797c0,0 3.803452,2.415222 6.094086,3.82489c2.290634,1.409607 4.122864,5.295227 4.40506,7.929077c0.264297,2.466858 1.16011,4.854614 2.378738,7.048096c1.218613,2.193542 -0.881012,0.969116 -2.819244,-0.264282c-1.938217,-1.233459 -3.788345,-5.726624 -4.669357,-8.545837c-0.881012,-2.819214 -3.891571,-5.234436 -6.358398,-5.93927c-2.466827,-0.704773 -9.426819,-1.05719 -9.426819,-1.05719c0,0 4.228851,2.554932 6.519485,4.316956c2.290634,1.762024 3.238373,7.892578 2.885971,9.654602c-0.352417,1.762024 -0.440506,8.281555 0.088104,10.748352c0.528595,2.466797 -0.352417,2.995422 -2.466843,-0.528625c-2.114426,-3.523987 -2.113281,-8.310791 -2.20253,-12.774658c-0.089249,-4.463867 -3.062164,-4.36853 -5.7052,-6.65918c-2.643036,-2.290649 -3.87645,-1.585815 -5.990891,-2.290649c-2.114426,-0.704773 -6.343277,-3.171631 -8.633911,-4.757446c-2.290619,-1.585815 -5.462265,-4.052673 -5.462265,-4.052673c0,0 2.114426,3.083557 5.286057,6.607605c3.171646,3.524048 1.145325,8.898193 -0.969101,11.717468c-2.114441,2.819214 -3.171646,8.369629 -2.290634,10.307861c0.881012,1.938171 1.289825,5.957275 0.792908,8.19342c-0.52861,2.378662 -1.321503,0.704773 -1.585815,-1.762024c-0.282211,-2.633911 -1.67392,-4.581299 -2.554932,-7.576721c-0.881027,-2.995483 -0.704803,-6.69574 1.938217,-11.188904c2.639648,-4.487366 0.792908,-6.783752 -0.616699,-7.664795c-1.409622,-0.880981 -1.456772,-1.673889 -5.963173,-7.048096c-4.506424,5.726624 -4.553574,6.519531 -5.963196,7.400513c-1.409607,0.881042 -3.256348,3.177429 -0.616699,7.664795c2.643021,4.493164 2.819244,8.19342 1.938217,11.188843c-0.881012,2.995483 -2.27272,4.942871 -2.554932,7.576782c-0.264313,2.466797 -1.057205,4.140686 -1.585815,1.761963c-0.496918,-2.236084 -0.088104,-6.255188 0.792908,-8.19342c0.881012,-1.938232 -0.176193,-7.488586 -2.290634,-10.3078c-2.114426,-2.819275 -4.140747,-8.19342 -0.969101,-11.717468c3.171631,-3.524048 5.286057,-6.607605 5.286057,-6.607605c0,0 -3.171646,2.466858 -5.462265,4.052673c-2.290634,1.585815 -6.519485,4.052673 -8.633919,4.757446c-2.114426,0.704834 -3.347847,0 -5.990875,2.290649c-2.643044,2.290588 -5.989746,2.319885 -6.078995,6.783752c-0.089241,4.463867 -0.088097,9.250671 -2.202515,12.774658c-2.114433,3.524048 -2.995445,2.995422 -2.466843,0.528625c0.52861,-2.466797 0.440506,-8.986328 0.088097,-10.748352c-0.352394,-1.762024 0.969124,-8.017212 3.25975,-9.779175c2.290634,-1.762085 6.519493,-4.317017 6.519493,-4.317017c0,0 -6.959999,0.352417 -9.426834,1.057251c-2.466827,0.704834 -5.72657,2.995422 -6.607574,5.814697c-0.881012,2.819214 -2.73114,7.312378 -4.669365,8.545776c-1.93824,1.233398 -4.037857,2.457825 -2.819244,0.264282c1.21862,-2.193481 2.114441,-4.581238 2.378738,-7.048035c0.282196,-2.63385 2.114426,-6.519531 4.40506,-7.929138c2.290627,-1.409607 6.343277,-3.700256 6.343277,-3.700256c0,0 -6.431381,-2.202515 -8.81012,-2.466797c-2.049782,-0.227783 -9.162521,-2.114441 -9.691132,0.880981c-0.528595,2.995422 -2.290619,9.603027 -3.700233,11.100769c-1.408325,1.496338 -5.46228,3.876465 -4.228867,0.880981c1.233421,-2.995422 3.083542,-10.219727 3.083542,-12.157959c0,-1.938232 -2.026321,-1.05719 -3.435951,-0.176208c-1.409607,0.881042 -6.959984,5.902771 -7.136185,10.48407c-0.176201,4.581238 -0.704811,3.876465 -1.409622,1.497681c-0.680847,-2.297852 -1.233414,-9.603027 2.466843,-12.069824c3.700233,-2.466858 11.62384,-10.596497 31.799324,-4.377441c20.210258,6.229736 28.7967,-3.000244 29.722908,-11.773804c0.926208,-8.77356 1.646301,-40.145386 1.470093,-45.607666c-0.176193,-5.46228 -2.114426,-11.98175 -4.757462,-16.739258c-2.643036,-4.757446 -7.400497,-11.276917 -10.043533,-11.453125c-2.643036,-0.176208 -6.695694,1.409607 -6.655296,1.41333c3.705994,1.862793 5.802185,8.167236 1.047081,12.726257c-4.799316,4.60144 -9.147011,3.379761 -16.331718,5.218933c-0.130577,-4.817993 -4.199196,-15.097717 0.231468,-19.999146c4.425682,-4.895935 9.826729,-2.418884 12.726295,0.437927c1.272766,-1.739929 3.511116,-2.714966 5.45813,-3.057068c-3.285934,-1.899536 -14.624634,-6.200928 -17.729713,-6.47229c-3.100296,-0.270935 -4.901581,-0.079041 -9.331696,4.744202c3.83815,2.039063 4.711609,9.458557 1.366127,13.400879c-3.389153,3.993774 -8.4422,6.287048 -15.626907,8.126221c-1.540207,-4.641785 -5.295471,-16.552124 -1.706764,-20.52771c3.54467,-3.926819 9.121918,-3.388 12.638191,-2.028931c1.758171,-2.657715 3.087021,-3.797852 5.994362,-5.647949c-8.744263,0.390747 -18.164261,1.12854 -24.629929,4.496216c2.069016,3.511353 1.531219,10.510559 -5.433907,12.177429c-6.881805,1.646851 -12.296089,-3.043152 -17.532858,-3.907654c1.701347,-4.704834 5.362516,-16.829834 11.792601,-17.547607c6.419777,-0.716614 8.937653,2.719849 9.91716,6.670715c5.472603,-3.661499 13.312477,-5.267212 21.837631,-4.443298c-2.145775,-1.523743 -4.166939,-2.922913 -5.502838,-5.318787c-3.170525,3.428162 -9.896622,3.35321 -12.52803,-0.271729c-2.673462,-3.6828 -0.111115,-12.367249 -0.691826,-18.018555c5.019806,1.907166 14.485283,-0.449219 18.984581,4.853394c4.455681,5.251221 0.497902,10.46521 -3.487907,12.024658c9.065071,10.362122 14.870407,5.570801 27.459526,11.88031l-2.092789,-4.962891c-5.171097,-0.411926 -9.690338,-1.471802 -9.52681,-8.057312c0.165024,-6.646729 7.899872,-10.339966 9.325081,-14.856384c1.724602,4.032532 11.765602,7.141174 12.07357,13.741211c0.307632,6.59259 -3.684959,8.286682 -7.755409,8.301575c1.45607,6.147766 2.295433,7.449951 8.068459,11.835693c-0.706032,-1.910461 -1.855019,-4.75 -2.118095,-5.731323l0.249184,-2.491882l4.075104,1.86261c0.138443,1.621338 2.601685,7.169922 5.394028,10.97052c2.822906,3.842285 4.817627,9.053833 7.226456,13.58075c-4.533844,-18.560791 -10.553009,-33.558838 -14.676033,-43.892944c-4.092728,-10.25824 -7.963799,-13.031738 -16.565018,-14.308777c0.726044,4.011353 -1.416656,11.507263 -9.440605,9.993835c-8.008354,-1.510498 -9.385948,-7.979065 -15.729195,-9.121277c4.136177,-0.891357 8.875114,-12.665894 15.46833,-12.236145c6.585785,0.429321 8.614326,4.439087 8.791977,8.221558c2.669342,0.635498 5.116127,0.522644 8.008011,1.906555c-4.782944,-6.655701 -12.372612,-13.763 -15.241501,-16.298706c-2.8256,-2.497437 -4.316963,-1.233459 -8.800163,-1.503845c1.402359,7.239319 -2.060837,11.52594 -8.64772,11.4328c-6.648148,-0.093994 -10.823906,-4.457336 -17.495018,-6.254761c4.259369,-1.231384 7.598793,-14.13385 13.944023,-15.66217c6.324631,-1.523376 9.135437,2.535156 11.0439,6.781433c2.055542,-0.143982 4.235527,-0.58252 6.166626,-0.431946c-1.484528,-2.278442 -2.088051,-4.733032 -2.339157,-7.363831c-4.94471,0.303101 -11.181648,-0.463196 -12.355049,-6.945374c-1.184319,-6.54248 5.055069,-6.936707 5.993279,-17.553772c3.73584,3.081299 15.257019,5.192383 16.895081,11.593323c1.636223,6.393738 -2.971718,10.760864 -6.954842,11.59967c-0.119431,5.210022 1.974571,9.461487 9.685234,16.687195c0.058739,-2.819275 -0.146835,-6.695679 1.145317,-9.25061c1.204048,0.234924 2.055687,0.646057 3.259743,0.880981c-0.939743,2.877991 -1.277672,6.362671 -1.145309,8.017212c0.11866,1.483337 -0.030441,2.485901 0.969109,3.612122c1.042542,1.174683 7.81163,10.513428 11.717445,15.770142c0.042755,-2.04834 -0.090691,-4.008606 0.392563,-6.145081c-3.61969,-2.287415 -7.655319,-4.985291 -5.05368,-11.037354c2.625839,-6.108337 9.220253,-6.413818 12.036034,-10.291626c1.117462,4.91449 8.071426,12.557129 6.878357,17.449951c-1.197845,4.912354 -6.168106,6.763123 -9.951935,5.262634c-0.082901,2.233215 -0.80925,3.953186 -1.041595,5.378174c-0.218353,1.339233 0.029373,2.73114 0.704819,4.228882c0.708618,1.571228 9.632401,21.232361 13.655685,35.416626c-0.176208,-9.015686 -0.587341,-25.916382 -0.52861,-27.047058c-1.062698,-2.314392 -2.270111,-4.180054 -3.87645,-6.078979c-0.020691,-1.137512 0.14682,-5.520996 0.176193,-6.695679c1.732666,1.380249 3.905807,3.641541 5.286072,5.638489l0,-22.113403c0,-2.902466 -2.31958,-4.417847 -3.171646,-5.46228c-0.809677,-0.992493 -6.873566,-7.173218 -10.927063,-11.376526c-2.056763,5.290894 -12.252937,5.589661 -14.78714,1.163635c-2.512741,-4.388489 -1.881027,-10.418152 -2.257874,-18.548096c5.42215,0.900024 13.563515,0.351135 17.487778,4.249329c3.968384,3.941956 2.881042,7.382019 1.852463,11.025818c4.081329,4.4953 8.062225,7.757141 12.143555,12.252441l0.088104,-16.826233c-3.942245,0.954285 -11.83223,-2.533508 -10.394302,-10.831726c1.423096,-8.212646 10.626831,-11.294128 12.405273,-16.191162z\"/>",
				250, 250, 
				125, 125 
		));		
		symbols.add(new FlagSymbol("crescent",
				"<path d=\"M4,62A299,298 0 1,1 4,538A238,240 0 1,0 4,62z\" fill=\"%1$s\" stroke=\"#000\" stroke-width=\"7\"/>",
				484, 620, 
				206, 308 
		));		
		symbols.add(new FlagSymbol("tower",
				"<rect width=\"69.3\" height=\"229.4\" x=\"69.8\" y=\"88.5\" style=\"fill:%1$s;stroke:#000;stroke-width:4\" />  <path d=\"M 165.85927,1.5210564 C 162.78469,1.5619064 159.71809,1.6551664 156.86667,1.6188064 C 156.53249,7.6960764 156.99713,14.178116 156.86667,20.337096 C 149.34514,20.337096 141.90671,20.337096 134.38518,20.337096 C 134.25417,14.178326 134.76978,7.7931664 134.43405,1.7165564 C 127.66134,1.7958564 120.30496,1.5370464 113.61423,1.8142964 C 113.5536,7.9502364 113.66562,14.152206 113.61423,20.288216 L 91.52372,20.288216 L 91.13274,1.8142964 C 84.14227,1.8935264 75.80408,1.5371064 68.89561,1.8142964 L 68.89561,20.239346 L 47.14721,20.239346 L 47.14721,1.7165564 C 41.76835,1.7968764 34.84956,1.6343164 29.55299,1.9120464 C 29.33575,16.691486 29.57264,31.907396 29.01539,46.679536 C 77.98361,46.953796 126.95665,46.826156 175.92707,46.826156 L 174.80299,1.6188064 C 171.98685,1.4917664 168.93384,1.4802064 165.85927,1.5210564 z M 29.11314,47.461506 C 30.07419,49.712086 32.80289,52.739786 34.14704,54.890176 C 36.32125,57.892566 38.38347,60.860186 40.69599,63.736146 C 40.64633,64.465626 40.59502,65.202266 40.54938,65.935426 C 40.49082,66.845936 40.45261,67.757556 40.40276,68.672306 C 40.37995,69.081296 40.37566,69.484206 40.35388,69.894126 C 40.26073,71.733866 40.17803,73.565656 40.10952,75.416756 C 39.73841,84.001446 39.48149,92.714586 39.18094,101.31935 C 39.17252,101.48074 39.1896,101.64682 39.18094,101.80808 C 39.15708,102.48266 39.10835,103.13892 39.08319,103.81186 C 38.30696,120.29587 37.39278,137.04209 36.49293,153.80875 C 35.48775,170.97189 34.45617,188.12101 33.80493,205.17407 C 33.85342,205.17446 33.90305,205.17368 33.95155,205.17407 C 33.94199,205.40061 33.91216,205.63188 33.90267,205.85829 C 27.23905,213.44342 14.50413,227.81096 10.34598,232.59172 C 7.3645,260.45898 4.34876,290.00889 1.5,317.92365 L 82.9221,317.92365 L 83.41083,274.18248 C 83.51585,265.00275 91.3893,256.39278 102.22687,256.39278 C 113.2598,256.39278 120.9738,264.93594 120.99403,274.18248 L 121.09177,317.92365 L 203.05148,317.92365 C 203.19445,314.10582 201.68701,309.74194 201.43868,305.85207 C 198.85136,281.63316 196.46868,256.98747 193.71677,232.78721 C 151.85479,233.54805 53.67835,233.08766 11.81216,232.78721 C 53.63895,232.79117 151.89333,232.59635 193.47241,232.64059 C 191.93641,230.10268 188.55716,227.15376 186.63021,224.67433 C 181.38529,218.52161 176.09336,211.89603 170.79542,205.80942 L 167.9608,148.18837 C 167.90859,146.98143 167.86585,145.77952 167.81418,144.57179 C 166.72373,118.19925 165.74036,91.808596 164.34421,65.446696 C 164.36672,65.078896 164.32666,64.800676 164.24646,64.566986 C 164.2352,64.355956 164.2089,64.142636 164.19759,63.931646 C 164.05154,63.933906 163.90382,63.929406 163.75774,63.931646 C 163.44292,63.758346 163.02611,63.700896 162.48704,63.687276 L 162.43817,63.687276 C 161.64312,63.672486 160.61557,63.768826 159.40806,63.785026 C 130.06249,63.785026 100.8396,63.804036 71.58361,63.833896 C 68.75838,63.815306 44.05486,63.800196 41.2336,63.785026 C 74.9549,63.762206 130.74854,63.732906 164.34421,63.638406 C 167.96942,58.526846 172.09197,52.659786 175.58496,47.461506 L 29.11314,47.461506 z M 96.21551,100.63513 L 111.17059,100.63513 L 111.17059,141.00407 L 96.21551,141.00407 L 96.21551,100.63513 z M 170.71149,205.6628 C 156.07252,205.62463 48.70616,205.62064 34.04929,205.61393 C 56.77607,205.39155 147.98542,205.51432 170.71149,205.6628 z\" style=\"fill:%1$s;stroke:#000;stroke-width:4;display:inline\" />",
				204, 340, 
				102, 166 
		));		
		symbols.add(new FlagSymbol("flame",
				"<path d=\"M 1.3218161,109.08665 C -0.014383884,97.302823 5.1731801,92.460153 1.1646161,81.079921 C 4.5443681,82.774844 7.4525801,86.003289 8.9459081,88.747489 C 11.225272,76.479425 12.640072,63.404258 11.539696,51.781923 C 16.570024,53.073259 20.028436,62.112879 22.150576,66.39058 C 27.809728,48.957003 29.303104,33.218388 25.29454,23.855903 C 30.403504,24.420918 33.390292,30.554913 36.848632,37.496045 C 40.228408,32.895526 39.678232,23.371666 39.678232,13.524891 C 40.228408,8.8436634 41.014384,3.5974634 43.843972,1.0954452 C 46.594936,23.1295 57.127252,21.273165 63.022168,45.809277 C 65.144356,40.966633 65.537392,35.720418 64.987156,29.102146 C 71.90392,33.218388 77.956024,48.63417 81.1786,70.668252 C 84.243976,69.054027 85.658752,62.839269 86.837764,55.81747 C 93.51868,65.583454 95.876656,77.770831 92.496916,92.298763 C 90.76774,96.415003 89.667328,100.61196 90.217528,105.29322 C 93.047068,103.51758 93.990292,99.239933 95.640868,95.688593 C 96.81982,100.93483 97.998844,107.14954 96.977092,111.66938 C 93.59728,137.81971 64.422772,142.24846 45.990256,142.11632 C 35.669956,142.03934 5.0299001,136.12401 1.3218161,109.08665 z\" style=\"fill:%1$s;stroke:#000;stroke-width:2\" />",
				99, 144, 
				50, 76 
		));		
		symbols.add(new FlagSymbol("crossed-swords",
				"<use transform=\"matrix(-1,0,0,1,220.31844,-2.1921088e-6)\" id=\"Sword-trang-2\" x=\"0\" y=\"0\" width=\"301\" height=\"805.92999\" xlink:href=\"#Sword-trang\" /> <g transform=\"matrix(0.292869,-0.273105,-0.273105,-0.292869,192.41822,350.77341)\" id=\"Sword-trang\" style=\"fill:%1$s;stroke:#000;stroke-width:4\"> <path d=\"M 254.81293,227.21933 C 265.02722,185.61219 277.95578,185.61219 287.67007,227.21933 C 297.38436,268.82647 300.38436,320.04076 289.67007,361.6479 C 278.95578,403.25504 261.52722,403.25504 250.81293,361.6479 C 240.09864,320.04076 244.59864,268.82647 254.81293,227.21933 z\" id=\"path6679\" /> <path d=\"M 250.00148,371.74275 L 291.09286,371.74273 L 285.13975,897.26199 L 270.54697,942.69037 L 255.95424,897.262 L 250.00148,371.74275 z\" id=\"path6681\" /> <path d=\"M 268.89157,376.48534 L 270.54698,938.18018 L 272.20277,376.48534 L 268.89157,376.48534 z\" id=\"path6683\" style=\"fill:#000;stroke:none\" /> <rect width=\"161.86885\" height=\"21.377804\" x=\"189.61275\" y=\"358.07336\" id=\"rect6685\" /> <path d=\"M 299.33652,233.12339 C 299.34245,249.0275 286.45128,261.92347 270.54718,261.92347 C 254.64307,261.92347 241.7519,249.0275 241.75783,233.12339 C 241.7519,217.21928 254.64307,204.32331 270.54718,204.32331 C 286.45128,204.32331 299.34245,217.21928 299.33652,233.12339 L 299.33652,233.12339 z\" id=\"path6687\" /> <path d=\"M 282.69807,233.12339 C 282.7006,239.83592 277.25972,245.27885 270.54717,245.27885 C 263.83464,245.27885 258.39376,239.83592 258.39628,233.12339 C 258.39376,226.41085 263.83464,220.96792 270.54717,220.96792 C 277.25972,220.96792 282.7006,226.41085 282.69807,233.12339 L 282.69807,233.12339 z\" id=\"path6689\" /> </g>",
				260, 260, 
				110, 115 
		));
*/
	}

	public static FlagDivision getRandomDivision() {
		double x = rand.nextDouble() * division_weight_sum;
		//iterate through HashMap keys/values
		for (Map.Entry<FlagDivision, Double> e : division_weight_sums.entrySet()) {
			if (x < e.getValue().doubleValue()) {
				return e.getKey();
			}
		}
		return fdSolid;
	}

	public static FlagOverlay getRandomOverlay(FlagDivision division) {
		HashMap<FlagOverlay, Double> overlay_weight_sums = new HashMap<FlagOverlay, Double>();
		double overlay_weight_sum = 0;
		//iterate through HashMap keys/values
		for (Map.Entry<FlagOverlay, Double> e : overlay_weights.entrySet()) {
			if (!(e.getKey().incompatible_divisions.contains(division))) {
				overlay_weight_sum += e.getValue().doubleValue();
				overlay_weight_sums.put(e.getKey(), overlay_weight_sum);
			}
		}
		
		double x = rand.nextDouble() * overlay_weight_sum;
		//iterate through HashMap keys/values
		for (Map.Entry<FlagOverlay, Double> e : overlay_weight_sums.entrySet()) {
			if (x < e.getValue().doubleValue()) {
				return e.getKey();
			}
		}
		return foNone;
	}

	public static FlagSymbol getRandomSymbol(FlagOverlay overlay) {
		if (overlay.anchors.size()>0) {
			if (rand.nextDouble() < overlay.symbolProbability)
				return symbols.get(rand.nextInt(symbols.size()-1)+1);
		}
		return FlagSymbol.NONE;
	}

	public static FlagColor getRandomColor(List<FlagColor> colors) {
		return colors.get(rand.nextInt(colors.size()));
	}

	public static Flag getRandomFlag() {
		Flag flag = new Flag(); 
		flag.division = getRandomDivision();
		flag.overlay = getRandomOverlay(flag.division);
		flag.symbol = getRandomSymbol(flag.overlay);

		do {
			flag.color1 = colors.get(rand.nextInt(colors.size()));
			flag.color2 = colors.get(rand.nextInt(colors.size()));
			flag.color3 = colors.get(rand.nextInt(colors.size()));
			flag.color4 = colors.get(rand.nextInt(colors.size()));
			
		    List<FlagColor> colors_for_symbol = new ArrayList<FlagColor>(colors);
		    if (flag.symbol != FlagSymbol.NONE)
		    	colors_for_symbol.remove(flag.color4);
			flag.color5 = getRandomColor(colors_for_symbol);
		} while (flag.countDifferentColors() < 2);

		if (flag.countDifferentColors() > 4)
			flag.color5 = flag.color2;
		return flag;
	}
	
	public int countDifferentColors() {
		HashSet<FlagColor> different_colors = new HashSet<FlagColor>();
		different_colors.add(color1);
		if (division.usedColors>1)
			different_colors.add(color2);
		if (division.usedColors>2)
			different_colors.add(color3);
		if (overlay!=foNone)
			different_colors.add(color4);
		if (symbol!=FlagSymbol.NONE)
			different_colors.add(color5);
		return different_colors.size();
	}
	
	public String getSvgString() {
		String svgString =
			"<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.0\" x=\"0\" y=\"0\" width=\"360\" height=\"240\">\n";
		svgString += String.format(division.svg, color1.code, color2.code, color3.code);
		if (overlay != foNone)
			svgString += String.format(overlay.svg, color4.code);
		if (symbol != FlagSymbol.NONE) {
			FlagSymbolAnchor target = overlay.anchors.get(0);
			double scale = Math.min(target.width / symbol.width, target.height / symbol.height);

			//NOTE: transformations from last to first
			svgString += String.format(Locale.US, "<g transform=\"translate(%1$f,%2$f)\">\n",
					target.centerX, target.centerY);
			svgString += String.format(Locale.US, "<g transform=\"scale(%1$f)\">\n", scale);
			svgString += String.format(Locale.US, "<g transform=\"translate(%1$f,%2$f)\">\n",
					-symbol.centerX, -symbol.centerY);
			svgString += String.format(symbol.svg, color5.code, "#000");
			svgString += "</g>\n</g>\n</g>\n";
		}
		svgString +=  "</svg>\n";
		
		return svgString;
	}
	
	public String getQueryString() {
		return String.format("d=%1$d&c1=%2$d&c2=%3$d&c3=%4$d&o=%5$d&c4=%6$d&s=%7$d&c5=%8$d",
				divisions.indexOf(division),
				colors.indexOf(color1),
				colors.indexOf(color2),
				colors.indexOf(color3),
				overlays.indexOf(overlay),
				colors.indexOf(color4),
				symbols.indexOf(symbol),
				colors.indexOf(color5)
			);
	}

	public FlagInfo getFlagInfo() {
		FlagInfo result = new FlagInfo();
		result.divIdx = divisions.indexOf(division);
		result.col1 = colors.indexOf(color1);
		result.col2 = colors.indexOf(color2);
		result.col3 = colors.indexOf(color3);
		result.ovlIdx = overlays.indexOf(overlay);
		result.col4 = colors.indexOf(color4);
		result.symIdx = symbols.indexOf(symbol);
		result.col5 = colors.indexOf(color5);
		return result;
	}
}
