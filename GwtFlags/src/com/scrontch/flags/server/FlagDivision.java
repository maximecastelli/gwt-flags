package com.scrontch.flags.server;


public class FlagDivision {
	String name;
	String svg;  //SVG source
	int usedColors;

	//Constructor
	FlagDivision(String name, String svg, int usedColors) {
		this.name = name;
		this.svg = svg;
		this.usedColors = usedColors;
	}
}
