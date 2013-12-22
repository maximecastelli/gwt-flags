package com.scrontch.flags.server;


public class FlagDivision {
	public String name;
	public String svg;  //SVG source
	public int usedColors;

	//Constructor
	FlagDivision(String name, String svg, int usedColors) {
		this.name = name;
		this.svg = svg;
		this.usedColors = usedColors;
	}
}
