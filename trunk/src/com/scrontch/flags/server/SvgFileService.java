package com.scrontch.flags.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SvgFileService extends RemoteServiceServlet  {
	
	//Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
     throws ServletException, IOException {
		Map<String, String[]> parameterMap = req.getParameterMap();
		Flag flag = new Flag();
		flag.division = Flag.divisions.get(Integer.parseInt(parameterMap.get("d")[0]));
		flag.color1 = Flag.colors.get(Integer.parseInt(parameterMap.get("c1")[0]));
		flag.color2 = Flag.colors.get(Integer.parseInt(parameterMap.get("c2")[0]));
		flag.color3 = Flag.colors.get(Integer.parseInt(parameterMap.get("c3")[0]));
		flag.overlay = Flag.overlays.get(Integer.parseInt(parameterMap.get("o")[0]));
		flag.color4 = Flag.colors.get(Integer.parseInt(parameterMap.get("c4")[0]));
		flag.symbol = Flag.symbols.get(Integer.parseInt(parameterMap.get("s")[0]));
		flag.color5 = Flag.colors.get(Integer.parseInt(parameterMap.get("c5")[0]));
		
		PrintWriter out = resp.getWriter();
		//Write the SVG to the response
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
		out.println("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.0//EN\" \"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">");
		out.println(flag.getSvgString());
		out.close();
    }
}
