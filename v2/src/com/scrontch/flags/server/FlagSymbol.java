package com.scrontch.flags.server;

public class FlagSymbol {
	String name;
	String svg;  //SVG source
	double width;
	double height;
	double centerX;
	double centerY;

	//Constructor
	FlagSymbol(String name, String svg, double width, double height, double centerX, double centerY) {
		this.name = name;
		this.svg = svg;
		this.width = width;
		this.height = height;
		this.centerX = centerX;
		this.centerY = centerY;
	}
}
