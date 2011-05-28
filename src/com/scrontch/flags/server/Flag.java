package com.scrontch.flags.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Flag {
	FlagDivision division;
	FlagColor color1;
	FlagColor color2;
	FlagColor color3;
	FlagOverlay overlay;
	FlagColor color4;
	FlagSymbol symbol;
	FlagColor color5;

	static Random rand = new Random();
	static ArrayList<FlagDivision> divisions = new ArrayList<FlagDivision>();
	static ArrayList<FlagOverlay> overlays = new ArrayList<FlagOverlay>();
	static ArrayList<FlagColor> colors = new ArrayList<FlagColor>();
	static ArrayList<FlagSymbol> symbols = new ArrayList<FlagSymbol>();
	static FlagDivision fdSolid;
	static FlagOverlay foNone;
	static FlagSymbol fsNone;

	static HashMap<FlagDivision, Double> division_weight_sums = new HashMap<FlagDivision, Double>();

	static double division_weight_sum = 0;
	static HashMap<FlagOverlay, Double> overlay_weights = new HashMap<FlagOverlay, Double>();

	//Static initialization block
	static {
		fdSolid = new FlagDivision(
				"solid",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n");
		divisions.add(fdSolid);
		
		FlagDivision fdBicolorHorizontal = new FlagDivision(
				"bicolor-horizontal",
				"  <rect width=\"360\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"360\" height=\"120\" x=\"0\" y=\"120\" fill=\"%2$s\" />\n");
		divisions.add(fdBicolorHorizontal);
				
		FlagDivision fdBicolorVertical = new FlagDivision(
				"bicolor-vertical",
				"  <rect width=\"180\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"180\" height=\"240\" x=\"180\" y=\"0\" fill=\"%2$s\" />\n");
		divisions.add(fdBicolorVertical);
				
		FlagDivision fdTricolorHorizontal = new FlagDivision(
				"tricolor-horizontal",
				"  <rect width=\"360\" height=\"80\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"360\" height=\"80\" x=\"0\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"80\" x=\"0\" y=\"160\" fill=\"%3$s\" />\n");
		divisions.add(fdTricolorHorizontal);
				
		FlagDivision fdTricolorVertical = new FlagDivision(
				"tricolor-vertical",
				"  <rect width=\"120\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"120\" height=\"240\" x=\"120\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"120\" height=\"240\" x=\"240\" y=\"0\" fill=\"%3$s\" />\n");
		divisions.add(fdTricolorVertical);
				
		FlagDivision fdBicolor2x2 = new FlagDivision(
				"bicolor-2x2",
				"  <rect width=\"180\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"180\" height=\"120\" x=\"180\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"180\" height=\"120\" x=\"0\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"180\" height=\"120\" x=\"180\" y=\"120\" fill=\"%1$s\" />\n");
		divisions.add(fdBicolor2x2);
				
		FlagDivision fdBicolorStripesHorizontal = new FlagDivision(
				"bicolor-stripes-horizontal",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"26\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"133\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"187\" fill=\"%2$s\" />\n");
		divisions.add(fdBicolorStripesHorizontal);
				
		FlagDivision fdBicolorStripesVertical = new FlagDivision(
				"bicolor-stripes-vertical",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"40\" height=\"240\" x=\"40\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"240\" x=\"120\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"240\" x=\"200\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"240\" x=\"280\" y=\"0\" fill=\"%2$s\" />\n");
		divisions.add(fdBicolorStripesVertical);
				
		FlagDivision fdBicolorCheckered = new FlagDivision(
				"bicolor-checkered",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"40\" height=\"40\" x=\"40\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"120\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"200\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"280\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"0\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"80\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"160\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"240\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"320\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"40\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"120\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"200\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"280\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"0\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"80\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"160\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"240\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"320\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"40\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"120\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"200\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"280\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"0\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"80\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"160\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"240\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"320\" y=\"200\" fill=\"%2$s\" />\n");
		divisions.add(fdBicolorCheckered);
				
		FlagDivision fdBicolorDiagonalDown = new FlagDivision(
				"bicolor-diagonal-down",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <polygon points=\"0,0 0,240 360,240\" fill=\"%2$s\" />\n");
		divisions.add(fdBicolorDiagonalDown);
				
		FlagDivision fdBicolorDiagonalUp = new FlagDivision(
				"bicolor-diagonal-up",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <polygon points=\"360,0 0,240 360,240\" fill=\"%2$s\" />\n");
		divisions.add(fdBicolorDiagonalUp);

		HashMap<FlagDivision, Double> division_weights = new HashMap<FlagDivision, Double>();
		division_weights.put(fdSolid, 1.0);
		division_weights.put(fdBicolorHorizontal, 1.0);
		division_weights.put(fdBicolorVertical, 1.0);
		division_weights.put(fdTricolorHorizontal, 1.0);
		division_weights.put(fdTricolorVertical, 1.0);
		division_weights.put(fdBicolor2x2, 1.0);
		division_weights.put(fdBicolorStripesHorizontal, 1.0);
		division_weights.put(fdBicolorStripesVertical, 1.0);
		division_weights.put(fdBicolorCheckered, 1.0);
		division_weights.put(fdBicolorDiagonalDown, 1.0);
		division_weights.put(fdBicolorDiagonalUp, 1.0);

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
		foNone.anchors.add(new FlagSymbolAnchor(340,220, 180,120));
		overlays.add(foNone);

		FlagOverlay foBarCross = new FlagOverlay(
				"bar-cross",
				"  <rect width=\"360\" height=\"60\" x=\"0\" y=\"90\" fill=\"%1$s\" />\n  <rect width=\"60\" height=\"240\" x=\"150\" y=\"0\" fill=\"%1$s\" />\n");
		foBarCross.incompatible_divisions.add(fdTricolorHorizontal);
		foBarCross.incompatible_divisions.add(fdTricolorVertical);
		foBarCross.incompatible_divisions.add(fdBicolorDiagonalDown);
		foBarCross.incompatible_divisions.add(fdBicolorDiagonalUp);
		foBarCross.incompatible_divisions.add(fdBicolorCheckered);
		foBarCross.anchors.add(new FlagSymbolAnchor(340,220, 180,120));
		overlays.add(foBarCross);
		
		FlagOverlay foDiagonalBarDown = new FlagOverlay(
				"diagonal-bar-down",
				"  <polygon points=\"0,0 40,0 360,200, 360,240, 320,240, 0,40\" fill=\"%1$s\" />\n");
		foDiagonalBarDown.incompatible_divisions.add(fdBicolorDiagonalUp);
		foDiagonalBarDown.incompatible_divisions.add(fdBicolorCheckered);
		foDiagonalBarDown.anchors.add(new FlagSymbolAnchor(340,220, 180,120));
		overlays.add(foDiagonalBarDown);

		FlagOverlay foDiagonalBarUp = new FlagOverlay(
				"diagonal-bar-up",
				"  <polygon points=\"360,0 320,0 0,200, 0,240, 40,240, 360,40\" fill=\"%1$s\" />\n");
		foDiagonalBarUp.incompatible_divisions.add(fdBicolorDiagonalDown);
		foDiagonalBarUp.incompatible_divisions.add(fdBicolorCheckered);
		foDiagonalBarUp.anchors.add(new FlagSymbolAnchor(340,220, 180,120));
		overlays.add(foDiagonalBarUp);

		FlagOverlay foDiagonalCross = new FlagOverlay(
				"diagonal-cross",
				"  <polygon points=\"0,0 40,0 360,200, 360,240, 320,240, 0,40\" fill=\"%1$s\" />\n    <polygon points=\"360,0 320,0 0,200, 0,240, 40,240, 360,40\" fill=\"%1$s\" />\n");
		foDiagonalCross.incompatible_divisions.add(fdTricolorHorizontal);
		foDiagonalCross.incompatible_divisions.add(fdTricolorVertical);
		foDiagonalCross.incompatible_divisions.add(fdBicolorCheckered);
		foDiagonalCross.anchors.add(new FlagSymbolAnchor(340,220, 180,120));
		overlays.add(foDiagonalCross);

		FlagOverlay foTriangleLeft = new FlagOverlay(
				"triangle-left",
				"  <polygon points=\"0,0 180,120 0,240\" fill=\"%1$s\" />\n");
		foTriangleLeft.incompatible_divisions.add(fdBicolorDiagonalDown);
		foTriangleLeft.incompatible_divisions.add(fdBicolorDiagonalUp);
		foTriangleLeft.anchors.add(new FlagSymbolAnchor(120,120, 70,120));
		overlays.add(foTriangleLeft);

		FlagOverlay foCircleCenter = new FlagOverlay(
				"circle-center",
				"  <circle cx=\"180\" cy=\"120\" r=\"90\" fill=\"%1$s\" />\n");
		foCircleCenter.anchors.add(new FlagSymbolAnchor(150,150, 180,120));
		overlays.add(foCircleCenter);

		FlagOverlay foDiamondCenter = new FlagOverlay(
				"diamond-center",
				"  <polygon points=\"0,120 180,0 360,120 180,240\" fill=\"%1$s\" />\n");
		foDiamondCenter.incompatible_divisions.add(fdBicolorDiagonalDown);
		foDiamondCenter.incompatible_divisions.add(fdBicolorDiagonalUp);
		foDiamondCenter.incompatible_divisions.add(fdBicolorCheckered);
		foDiamondCenter.anchors.add(new FlagSymbolAnchor(300,200, 180,120));
		overlays.add(foDiamondCenter);

		FlagOverlay foSquareCanton = new FlagOverlay(
				"square-canton",
				"  <rect width=\"120\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n");
		foSquareCanton.incompatible_divisions.add(fdTricolorHorizontal);
		foSquareCanton.incompatible_divisions.add(fdBicolorVertical);
		foSquareCanton.incompatible_divisions.add(fdBicolorDiagonalDown);
		foSquareCanton.incompatible_divisions.add(fdBicolorDiagonalUp);
		foSquareCanton.incompatible_divisions.add(fdBicolor2x2);
		foSquareCanton.anchors.add(new FlagSymbolAnchor(120,120, 60,60));
		overlays.add(foSquareCanton);

		FlagOverlay foBigSquareCanton = new FlagOverlay(
				"big-square-canton",
				"  <rect width=\"180\" height=\"160\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n");
		foBigSquareCanton.incompatible_divisions.add(fdBicolorHorizontal);
		foBigSquareCanton.incompatible_divisions.add(fdBicolorDiagonalDown);
		foBigSquareCanton.incompatible_divisions.add(fdBicolorDiagonalUp);
		foBigSquareCanton.incompatible_divisions.add(fdBicolor2x2);
		foBigSquareCanton.anchors.add(new FlagSymbolAnchor(180,160, 90,80));
		overlays.add(foBigSquareCanton);

		FlagOverlay foRectangleCanton = new FlagOverlay(
				"rectangle-canton",
				"  <rect width=\"180\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n");
		foRectangleCanton.incompatible_divisions.add(fdTricolorHorizontal);
		foRectangleCanton.incompatible_divisions.add(fdBicolorStripesVertical);
		foRectangleCanton.incompatible_divisions.add(fdBicolorDiagonalDown);
		foRectangleCanton.incompatible_divisions.add(fdBicolorDiagonalUp);
		foRectangleCanton.incompatible_divisions.add(fdBicolorCheckered);
		foRectangleCanton.anchors.add(new FlagSymbolAnchor(180,120, 90,60));
		overlays.add(foRectangleCanton);

		FlagOverlay foScandinavian = new FlagOverlay(
				"scandinavian",
				"  <rect width=\"360\" height=\"40\" x=\"0\" y=\"100\" fill=\"%1$s\" />\n  <rect width=\"40\" height=\"240\" x=\"100\" y=\"0\" fill=\"%1$s\" />\n");
		foScandinavian.incompatible_divisions.add(fdBicolorDiagonalDown);
		foScandinavian.incompatible_divisions.add(fdBicolorDiagonalUp);
		foScandinavian.incompatible_divisions.add(fdBicolor2x2);
		foScandinavian.anchors.add(new FlagSymbolAnchor(200,200, 120,120));
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
		colors.add(new FlagColor("green", "#006b3f"));
		colors.add(new FlagColor("yellow", "#fcd116"));
		colors.add(new FlagColor("orange", "#ff8400"));
		colors.add(new FlagColor("lightblue", "#3a75c4"));
		
		//Symbols
		fsNone = new FlagSymbol("none", "", 0, 0, 0, 0);
		symbols.add(fsNone);
		symbols.add(new FlagSymbol("five-pointed-star",
				"<polygon fill=\"%1$s\" stroke=\"#000\" stroke-width=\"5\" points=\"125,5 155,90 245,90 175,145 200,230 125,180 50,230 75,145 5,90 95,90\"/>",
				250, 235, 
				125, 125 
		));		
	
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
			return symbols.get(1);  //TODO
		}
		return fsNone;
	}

	public static Flag getRandomFlag() {
		Flag flag = new Flag(); 
		flag.division = getRandomDivision();
		flag.overlay = getRandomOverlay(flag.division);
		flag.symbol = getRandomSymbol(flag.overlay);
		flag.color1 = colors.get(rand.nextInt(colors.size()));
		flag.color2 = colors.get(rand.nextInt(colors.size()));
		flag.color3 = colors.get(rand.nextInt(colors.size()));
		flag.color4 = colors.get(rand.nextInt(colors.size()));
		flag.color5 = colors.get(rand.nextInt(colors.size()));
		return flag;
	}
	
	public String getSvgString() {
		String svgString =
			"<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\" x=\"0\" y=\"0\" width=\"360\" height=\"240\">";
		svgString += String.format(division.svg, color1.code, color2.code, color3.code);
		if (overlay != foNone)
			svgString += String.format(overlay.svg, color4.code);
		if (symbol != fsNone) {
			FlagSymbolAnchor target = overlay.anchors.get(0);
			double scale = Math.min(target.width / symbol.width, target.height / symbol.height);

			//NOTE: transformations from last to first
			svgString += String.format(Locale.US, "<g transform=\"translate(%1$f,%2$f)\">",
					target.centerX, target.centerY);
			svgString += String.format(Locale.US, "<g transform=\"scale(%1$f)\">", scale);
			svgString += String.format(Locale.US, "<g transform=\"translate(%1$f,%2$f)\">",
					-symbol.centerX, -symbol.centerY);
			svgString += String.format(symbol.svg, color5.code);
			svgString += "</g></g></g>";
		}
		svgString +=  "</svg>";
		
		return svgString;
	}
}