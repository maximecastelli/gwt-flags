package com.scrontch.flags.server;

public class FlagSymbol {
	public String name;
	public String svg;  //SVG source
	public double width;
	public double height;
	public double centerX;
	public double centerY;

	public static final FlagSymbol NONE = new FlagSymbol("none", "", 0, 0, 0, 0);
	
	//Constructor
	public FlagSymbol() {
		// TODO Auto-generated constructor stub
	}
	
	FlagSymbol(String name, String svg, double width, double height, double centerX, double centerY) {
		this.name = name;
		this.svg = svg;
		this.width = width;
		this.height = height;
		this.centerX = centerX;
		this.centerY = centerY;
	}
}
