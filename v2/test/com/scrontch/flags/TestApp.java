package com.scrontch.flags;

import com.scrontch.flags.server.*;

public class TestApp {
	public static void main(String[] args) {
		Flag flag = Flag.getRandomFlag();
		System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
		System.out.println("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.0//EN\" \"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">");
		System.out.println(flag.getSvgString());
		
		System.out.println(flag.getQueryString());
	}
}
