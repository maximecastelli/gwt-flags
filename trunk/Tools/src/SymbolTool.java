import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import java.nio.file.*;

//import com.scrontch.flags.server.FlagSymbol;

public class SymbolTool {

	public static void main(String[] args) throws IOException, XMLStreamException {
		System.out.println ("Hello World!");

		String yourXMLAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
+ "<symbols> <symbol id=\"2\" name=\"eagle\" width=\"360\" height=\"240\" centerx=\"0\" centery=\"0\">"
+ "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\" x=\"0\" y=\"0\" width=\"360\" height=\"240\">"
+ "<rect width=\"360\" height=\"240\" x=\"0\" y=\"0\" fill=\"#cccccc\" />"
+ "</svg> </symbol> </symbols>";

//		byte[] xmlDATA = yourXMLAsString.getBytes();
		
		Path file = Paths.get("../gfx/symbols/symbols.xml");
		byte[] xmlDATA =  Files.readAllBytes(file);		
		
		ByteArrayInputStream in = new ByteArrayInputStream(xmlDATA);
		
		XMLInputFactory f = XMLInputFactory.newInstance();
		XMLEventReader r = f.createXMLEventReader(in);
		int startOffset = 0;
		
		while(r.hasNext()) {
			XMLEvent e = r.nextEvent();

			if (e.isStartElement())
			{
				StartElement startElement = e.asStartElement();
				if (startElement.getName().getLocalPart() == "symbol")
				{
					startOffset = startElement.getLocation().getCharacterOffset();
					Iterator<Attribute> attributes = startElement.getAttributes();
					while (attributes.hasNext()) {
						Attribute attribute = attributes.next();
						if (attribute.getName().toString().equals("id")) {
//							item.setDate(attribute.getValue();
						}
					}
				}
			}

			if (e.isEndElement())
			{
				EndElement endElement = e.asEndElement();
				if (endElement.getName().getLocalPart() == "svg")
				{
					int endOffset = endElement.getLocation().getCharacterOffset();
					String s = new String(Arrays.copyOfRange(xmlDATA, startOffset, endOffset));
					System.out.println(s);
				}			
			}
		}		
	}

}
