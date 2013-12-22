package com.scrontch.flags.server;


import java.util.ArrayList;

public class FlagOverlay {
	public String name;
	public String svg;  //SVG source
	double symbolProbability = 0;
	ArrayList<FlagDivision> incompatible_divisions = new ArrayList<FlagDivision>();
	public ArrayList<FlagSymbolAnchor> anchors = new ArrayList<FlagSymbolAnchor>();

	//Constructor
	FlagOverlay(String name, String svg) {
		this.name = name;
		this.svg = svg;
	}
}
