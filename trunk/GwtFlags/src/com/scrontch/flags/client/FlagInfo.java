package com.scrontch.flags.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FlagInfo  implements Serializable {
	
	public Integer divIdx;
	public Integer col1;
	public Integer col2;
	public Integer col3;
	public Integer ovlIdx;
	public Integer col4;
	public Integer symIdx;
	public Integer col5;
	
	public String getQueryString() {
		return "d=" + divIdx.toString()
				+ "&c1=" + col1.toString()
				+ "&c2=" + col2.toString()
				+ "&c3=" + col3.toString()
				+ "&o=" + ovlIdx.toString()
				+ "&c4=" + col4.toString()
				+ "&s=" + symIdx.toString()
				+ "&c5=" + col5.toString();
	}
}
