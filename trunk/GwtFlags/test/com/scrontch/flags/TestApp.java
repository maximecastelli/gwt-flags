package com.scrontch.flags;

import com.scrontch.flags.server.*;

public class TestApp {
	public static void main(String[] args) {
//		Flag flag = Flag.getRandomFlag();
		
		Flag flag = new Flag();

		for (int i=0; i<Flag.symbols.size(); i++) {
			flag.division = Flag.divisions.get(0);
			flag.color1 = Flag.colors.get(2);
			flag.color2 = Flag.colors.get(3);
			flag.color3 = Flag.colors.get(4);
			flag.overlay = Flag.overlays.get(0);
			flag.color4 = Flag.colors.get(5);
			flag.symbol = Flag.symbols.get(i);
			flag.color5 = Flag.colors.get(6);
			
			System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
			System.out.println("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.0//EN\" \"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">");
			System.out.println(flag.getSvgString());
		}
		
		//System.out.println(flag.getQueryString());
	}
}
