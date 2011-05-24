package com.scrontch.flags.server;

import com.scrontch.flags.client.FlagService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("serial")
public class FlagServiceImpl extends RemoteServiceServlet implements FlagService {

	Random rand = new Random();
	ArrayList<FlagDesign> designs = new ArrayList<FlagDesign>();
	ArrayList<FlagOverlay> overlays = new ArrayList<FlagOverlay>();
	ArrayList<FlagColor> colors = new ArrayList<FlagColor>();
	FlagDesign fdSolid;
	FlagOverlay foNone;
	
	HashMap<FlagDesign, Double> design_weight_sums = new HashMap<FlagDesign, Double>();
	double design_weight_sum = 0;
	HashMap<FlagOverlay, Double> overlay_weights = new HashMap<FlagOverlay, Double>();

	void Init() {
		fdSolid = new FlagDesign(
				"solid",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n");
		designs.add(fdSolid);
		
		FlagDesign fdBicolorHorizontal = new FlagDesign(
				"bicolor-horizontal",
				"  <rect width=\"360\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"360\" height=\"120\" x=\"0\" y=\"120\" fill=\"%2$s\" />\n");
		designs.add(fdBicolorHorizontal);
				
		FlagDesign fdBicolorVertical = new FlagDesign(
				"bicolor-vertical",
				"  <rect width=\"180\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"180\" height=\"240\" x=\"180\" y=\"0\" fill=\"%2$s\" />\n");
		designs.add(fdBicolorVertical);
				
		FlagDesign fdTricolorHorizontal = new FlagDesign(
				"tricolor-horizontal",
				"  <rect width=\"360\" height=\"80\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"360\" height=\"80\" x=\"0\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"80\" x=\"0\" y=\"160\" fill=\"%3$s\" />\n");
		designs.add(fdTricolorHorizontal);
				
		FlagDesign fdTricolorVertical = new FlagDesign(
				"tricolor-vertical",
				"  <rect width=\"120\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"120\" height=\"240\" x=\"120\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"120\" height=\"240\" x=\"240\" y=\"0\" fill=\"%3$s\" />\n");
		designs.add(fdTricolorVertical);
				
		FlagDesign fdBicolor2x2 = new FlagDesign(
				"bicolor-2x2",
				"  <rect width=\"180\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"180\" height=\"120\" x=\"180\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"180\" height=\"120\" x=\"0\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"180\" height=\"120\" x=\"180\" y=\"120\" fill=\"%1$s\" />\n");
		designs.add(fdBicolor2x2);
				
		FlagDesign fdBicolorStripesHorizontal = new FlagDesign(
				"bicolor-stripes-horizontal",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"26\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"133\" fill=\"%2$s\" />\n  <rect width=\"360\" height=\"26\" x=\"0\" y=\"187\" fill=\"%2$s\" />\n");
		designs.add(fdBicolorStripesHorizontal);
				
		FlagDesign fdBicolorStripesVertical = new FlagDesign(
				"bicolor-stripes-vertical",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"40\" height=\"240\" x=\"40\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"240\" x=\"120\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"240\" x=\"200\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"240\" x=\"280\" y=\"0\" fill=\"%2$s\" />\n");
		designs.add(fdBicolorStripesVertical);
				
		FlagDesign fdBicolorCheckered = new FlagDesign(
				"bicolor-checkered",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <rect width=\"40\" height=\"40\" x=\"40\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"120\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"200\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"280\" y=\"0\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"0\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"80\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"160\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"240\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"320\" y=\"40\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"40\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"120\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"200\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"280\" y=\"80\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"0\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"80\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"160\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"240\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"320\" y=\"120\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"40\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"120\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"200\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"280\" y=\"160\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"0\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"80\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"160\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"240\" y=\"200\" fill=\"%2$s\" />\n  <rect width=\"40\" height=\"40\" x=\"320\" y=\"200\" fill=\"%2$s\" />\n");
		designs.add(fdBicolorCheckered);
				
		FlagDesign fdBicolorDiagonalDown = new FlagDesign(
				"bicolor-diagonal-down",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <polygon points=\"0,0 0,240 360,240\" fill=\"%2$s\" />\n");
		designs.add(fdBicolorDiagonalDown);
				
		FlagDesign fdBicolorDiagonalUp = new FlagDesign(
				"bicolor-diagonal-up",
				"  <rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n  <polygon points=\"360,0 0,240 360,240\" fill=\"%2$s\" />\n");
		designs.add(fdBicolorDiagonalUp);

		HashMap<FlagDesign, Double> design_weights = new HashMap<FlagDesign, Double>();
		design_weights.put(fdSolid, 1.0);
		design_weights.put(fdBicolorHorizontal, 1.0);
		design_weights.put(fdBicolorVertical, 1.0);
		design_weights.put(fdTricolorHorizontal, 1.0);
		design_weights.put(fdTricolorVertical, 1.0);
		design_weights.put(fdBicolor2x2, 1.0);
		design_weights.put(fdBicolorStripesHorizontal, 1.0);
		design_weights.put(fdBicolorStripesVertical, 1.0);
		design_weights.put(fdBicolorCheckered, 1.0);
		design_weights.put(fdBicolorDiagonalDown, 1.0);
		design_weights.put(fdBicolorDiagonalUp, 1.0);

		design_weight_sum = 0;
		//iterate through HashMap keys/values
		for (Map.Entry<FlagDesign, Double> e : design_weights.entrySet()) {
			design_weight_sum += e.getValue().doubleValue();
			design_weight_sums.put(e.getKey(), design_weight_sum);
		}

		// Overlays
		foNone = new FlagOverlay(
				"none",
				"");
		overlays.add(foNone);

		FlagOverlay foBarCross = new FlagOverlay(
				"bar-cross",
				"  <rect width=\"360\" height=\"60\" x=\"0\" y=\"90\" fill=\"%1$s\" />\n  <rect width=\"60\" height=\"240\" x=\"150\" y=\"0\" fill=\"%1$s\" />\n");
		foBarCross.incompatible_designs.add(fdTricolorHorizontal);
		foBarCross.incompatible_designs.add(fdTricolorVertical);
		foBarCross.incompatible_designs.add(fdBicolorDiagonalDown);
		foBarCross.incompatible_designs.add(fdBicolorDiagonalUp);
		foBarCross.incompatible_designs.add(fdBicolorCheckered);
		overlays.add(foBarCross);
		
		FlagOverlay foDiagonalBarDown = new FlagOverlay(
				"diagonal-bar-down",
				"  <polygon points=\"0,0 40,0 360,200, 360,240, 320,240, 0,40\" fill=\"%1$s\" />\n");
		foDiagonalBarDown.incompatible_designs.add(fdBicolorDiagonalUp);
		foDiagonalBarDown.incompatible_designs.add(fdBicolorCheckered);
		overlays.add(foDiagonalBarDown);

		FlagOverlay foDiagonalBarUp = new FlagOverlay(
				"diagonal-bar-up",
				"  <polygon points=\"360,0 320,0 0,200, 0,240, 40,240, 360,40\" fill=\"%1$s\" />\n");
		foDiagonalBarUp.incompatible_designs.add(fdBicolorDiagonalDown);
		foDiagonalBarUp.incompatible_designs.add(fdBicolorCheckered);
		overlays.add(foDiagonalBarUp);

		FlagOverlay foDiagonalCross = new FlagOverlay(
				"diagonal-cross",
				"  <polygon points=\"0,0 40,0 360,200, 360,240, 320,240, 0,40\" fill=\"%1$s\" />\n    <polygon points=\"360,0 320,0 0,200, 0,240, 40,240, 360,40\" fill=\"%1$s\" />\n");
		foDiagonalCross.incompatible_designs.add(fdTricolorHorizontal);
		foDiagonalCross.incompatible_designs.add(fdTricolorVertical);
		foDiagonalCross.incompatible_designs.add(fdBicolorCheckered);
		overlays.add(foDiagonalCross);

		FlagOverlay foTriangleLeft = new FlagOverlay(
				"triangle-left",
				"  <polygon points=\"0,0 180,120 0,240\" fill=\"%1$s\" />\n");
		foTriangleLeft.incompatible_designs.add(fdBicolorDiagonalDown);
		foTriangleLeft.incompatible_designs.add(fdBicolorDiagonalUp);
		overlays.add(foTriangleLeft);

		FlagOverlay foCircleCenter = new FlagOverlay(
				"circle-center",
				"  <circle cx=\"180\" cy=\"120\" r=\"80\" fill=\"%1$s\" />\n");
		overlays.add(foCircleCenter);

		FlagOverlay foDiamondCenter = new FlagOverlay(
				"diamond-center",
				"  <polygon points=\"0,120 180,0 360,120 180,240\" fill=\"%1$s\" />\n");
		foDiamondCenter.incompatible_designs.add(fdBicolorDiagonalDown);
		foDiamondCenter.incompatible_designs.add(fdBicolorDiagonalUp);
		foDiamondCenter.incompatible_designs.add(fdBicolorCheckered);
		overlays.add(foDiamondCenter);

		FlagOverlay foSquareCanton = new FlagOverlay(
				"square-canton",
				"  <rect width=\"120\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n");
		foSquareCanton.incompatible_designs.add(fdTricolorHorizontal);
		foSquareCanton.incompatible_designs.add(fdBicolorVertical);
		foSquareCanton.incompatible_designs.add(fdBicolorDiagonalDown);
		foSquareCanton.incompatible_designs.add(fdBicolorDiagonalUp);
		foSquareCanton.incompatible_designs.add(fdBicolor2x2);
		overlays.add(foSquareCanton);

		FlagOverlay foBigSquareCanton = new FlagOverlay(
				"big-square-canton",
				"  <rect width=\"180\" height=\"160\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n");
		foBigSquareCanton.incompatible_designs.add(fdBicolorHorizontal);
		foBigSquareCanton.incompatible_designs.add(fdBicolorDiagonalDown);
		foBigSquareCanton.incompatible_designs.add(fdBicolorDiagonalUp);
		foBigSquareCanton.incompatible_designs.add(fdBicolor2x2);
		overlays.add(foBigSquareCanton);

		FlagOverlay foRectangleCanton = new FlagOverlay(
				"rectangle-canton",
				"  <rect width=\"180\" height=\"120\" x=\"0\" y=\"0\" fill=\"%1$s\" />\n");
		foRectangleCanton.incompatible_designs.add(fdTricolorHorizontal);
		foRectangleCanton.incompatible_designs.add(fdBicolorStripesVertical);
		foRectangleCanton.incompatible_designs.add(fdBicolorDiagonalDown);
		foRectangleCanton.incompatible_designs.add(fdBicolorDiagonalUp);
		foRectangleCanton.incompatible_designs.add(fdBicolorCheckered);
		overlays.add(foRectangleCanton);

		FlagOverlay foScandinavian = new FlagOverlay(
				"scandinavian",
				"  <rect width=\"360\" height=\"40\" x=\"0\" y=\"100\" fill=\"%1$s\" />\n  <rect width=\"40\" height=\"240\" x=\"100\" y=\"0\" fill=\"%1$s\" />\n");
		foScandinavian.incompatible_designs.add(fdBicolorDiagonalDown);
		foScandinavian.incompatible_designs.add(fdBicolorDiagonalUp);
		foScandinavian.incompatible_designs.add(fdBicolor2x2);
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
	}

	public FlagDesign getRandomDesign() {
		double x = rand.nextDouble() * design_weight_sum;
		//iterate through HashMap keys/values
		for (Map.Entry<FlagDesign, Double> e : design_weight_sums.entrySet()) {
			if (x < e.getValue().doubleValue()) {
				return e.getKey();
			}
		}
		return fdSolid;
	}

	public FlagOverlay getRandomOverlay(FlagDesign design) {
		HashMap<FlagOverlay, Double> overlay_weight_sums = new HashMap<FlagOverlay, Double>();
		double overlay_weight_sum = 0;
		//iterate through HashMap keys/values
		for (Map.Entry<FlagOverlay, Double> e : overlay_weights.entrySet()) {
			if (!(e.getKey().incompatible_designs.contains(design))) {
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

	/**
	* The default constructor.
	*/
	public FlagServiceImpl() {
		Init();
	  }
	
	@Override
	public String getRandomFlag() {
		FlagDesign design = getRandomDesign();
		FlagOverlay overlay = getRandomOverlay(design);
		FlagColor color1 = colors.get(rand.nextInt(colors.size()));
		FlagColor color2 = colors.get(rand.nextInt(colors.size()));
		FlagColor color3 = colors.get(rand.nextInt(colors.size()));
		FlagColor color4 = colors.get(rand.nextInt(colors.size()));
		String svgString =
			"<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\" x=\"0\" y=\"0\" width=\"360\" height=\"240\">";
		svgString += String.format(design.svg, color1.code, color2.code, color3.code);
		if (overlay != foNone)
			svgString += String.format(overlay.svg, color4.code);
		svgString +=  "</svg>";
		
		return svgString;
	}
}
