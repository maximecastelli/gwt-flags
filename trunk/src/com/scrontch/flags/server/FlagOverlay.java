package com.scrontch.flags.server;


import java.util.ArrayList;

public class FlagOverlay {
	String name;
	String svg;  //SVG source
	ArrayList<FlagDivision> incompatible_divisions = new ArrayList<FlagDivision>();
	ArrayList<FlagSymbolAnchor> anchors = new ArrayList<FlagSymbolAnchor>();

	//Constructor
	FlagOverlay(String name, String svg) {
		this.name = name;
		this.svg = svg;
	}
}
