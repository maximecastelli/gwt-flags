package com.scrontch.flags.server;


public class FlagDesign {
	String name;
	String svg;  //SVG source
	double weight;

	//Constructor
	FlagDesign(String name, String svg) {
		this.name = name;
		this.svg = svg;
	}
}
