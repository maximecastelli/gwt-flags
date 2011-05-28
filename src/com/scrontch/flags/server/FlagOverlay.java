package com.scrontch.flags.server;


import java.util.ArrayList;

public class FlagOverlay {
	String name;
	String svg;  //SVG source
	double weight;
	ArrayList<FlagDivision> incompatible_divisions = new ArrayList<FlagDivision>(); 

	//Constructor
	FlagOverlay(String name, String svg/*, double weight*/) {
		this.name = name;
		this.svg = svg;
//		this.weight = weight;
	}
}
