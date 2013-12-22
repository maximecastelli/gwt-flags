import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import com.scrontch.flags.server.Flag;
import com.scrontch.flags.server.FlagColor;
import com.scrontch.flags.server.FlagSymbol;

public class SymbolTool {

	public static void main(String[] args) throws IOException, XMLStreamException {
		System.out.println("SymbolTool. Read symbol data from XML file and generate SVG files.");

		List<FlagSymbol> symbols = new ArrayList<FlagSymbol>();
		symbols.add(FlagSymbol.NONE);
		Flag.addSymbolsFromFile(symbols, "../GwtFlags/war/symbols.xml");

		Integer i=0;
	    for (Iterator<FlagSymbol> iter = symbols.iterator(); iter.hasNext(); )
	    {
	    	FlagSymbol flagSymbol = iter.next();
	    	System.out.println(i.toString() + ": " + flagSymbol.name);
	    	
	    	Flag flag = new Flag();
	    	flag.division = Flag.fdSolid;
	    	flag.color1 = new FlagColor("lightgray", "#ccc");
	    	flag.color2 = new FlagColor("dummy", "#000");
	    	flag.color3 = new FlagColor("dummy", "#000");
	    	flag.overlay = Flag.foNone;
	    	flag.symbol = flagSymbol;
	    	flag.color5 = new FlagColor("darkgray", "#444");
	    	
	    	String svgString = flag.getSvgString();
	    	
	    	PrintWriter out = new PrintWriter("sym" + i.toString() + ".svg");
	    	out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    	out.println("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.0//EN\" \"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">");
	    	out.println(svgString);
	    	out.close();
	    	
	    	i++;
	    }
	}

}
