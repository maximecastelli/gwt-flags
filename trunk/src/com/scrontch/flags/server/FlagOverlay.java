package com.scrontch.flags.server;


import java.util.ArrayList;

public class FlagOverlay {
	String name;
	String svg;  //SVG source
	double weight;
	ArrayList<FlagDesign> incompatible_designs = new ArrayList<FlagDesign>(); 

	//Constructor
	FlagOverlay(String name, String svg/*, double weight*/) {
		this.name = name;
		this.svg = svg;
//		this.weight = weight;
	}
}
