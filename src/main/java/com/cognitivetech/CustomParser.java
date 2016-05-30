package com.cognitivetech;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;

public class CustomParser {
	
	private static Set<String> excludeList = new HashSet<String>();

	static {
		excludeList.add("x");
		excludeList.add("y");
		excludeList.add("lineColor");
		excludeList.add("startX");
		excludeList.add("startY");
	}
	
	public ArrayList<String> parseFile(Path fileName){
		ArrayList<String> list = new ArrayList<String>();
		XMLInputFactory xmlif = null;

			xmlif = XMLInputFactory.newInstance();
			xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
					Boolean.TRUE);
			xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,
					Boolean.FALSE);
			// set the IS_COALESCING property to true , if application desires
			// to
			// get whole text data as one event.
			xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
			long starttime = System.currentTimeMillis();

			try {
				
					// pass the file name.. all relative entity refernces will be
					// resolved against this as
					// base URI.
					XMLStreamReader xmlr = xmlif.createXMLStreamReader(fileName.toString(),
							new FileInputStream(fileName.toFile()));

					// when XMLStreamReader is created, it is positioned at
					// START_DOCUMENT event.
					int eventType = xmlr.getEventType();
					// printEventType(eventType);
					printStartDocument(xmlr, list);

					// check if there are more events in the input stream
					while (xmlr.hasNext()) {
						eventType = xmlr.next();
						// printEventType(eventType);

						// these functions prints the information about the
						// particular event by calling relevant function
						if (eventType == XMLEvent.START_ELEMENT
								&& excludeList.contains(xmlr.getLocalName()
										.toString())) {
							// Skip its text element
							xmlr.next();
							continue;
						}

						printStartElement(xmlr,list);
						// printEndElement(xmlr);
						printText(xmlr);

						printPIData(xmlr);
						printComment(xmlr,list);
					}
				
			} catch (XMLStreamException ex) {
				System.out.println(ex.getMessage());

				if (ex.getNestedException() != null) {
					ex.getNestedException().printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
return list;
	}
	private static void printStartDocument(XMLStreamReader xmlr , ArrayList<String> list) {
		if (xmlr.START_DOCUMENT == xmlr.getEventType()) {
			String str = "<?xml version=\"" + xmlr.getVersion() + "\""
					+ " encoding=\"" + xmlr.getCharacterEncodingScheme() + "\""
					+ "?>";
			list.add( str);
		}
	}
	
	private static void printComment(XMLStreamReader xmlr, ArrayList<String> list) {
		if (xmlr.getEventType() == xmlr.COMMENT) {
			String str = "<!--" + xmlr.getText() + "-->";
			list.add( str);
		}
	}
	private static void printStartElement(XMLStreamReader xmlr,ArrayList<String> list) {
		if (xmlr.isStartElement()) {

			String str = xmlr.getLocalName().toString() + " = ";
			list.add( str);
			printAttributes(xmlr, list);
			// System.out.print(">");
		}
	}

	private static void printText(XMLStreamReader xmlr) {
		if (xmlr.hasText()) {
			System.out.print(xmlr.getText());
		}
	}

	private static void printPIData(XMLStreamReader xmlr) {
		if (xmlr.getEventType() == XMLEvent.PROCESSING_INSTRUCTION) {
			System.out.print("<?" + xmlr.getPITarget() + " " + xmlr.getPIData()
					+ "?>");
		}
	}
	private static void printAttributes(XMLStreamReader xmlr, ArrayList<String> list) {
		int count = xmlr.getAttributeCount();
		StringBuilder builder = new StringBuilder();
		

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				builder.append(" ");
				builder.append(xmlr.getAttributeName(i).toString());
				builder.append("=");
				builder.append("\"");
				builder.append(xmlr.getAttributeValue(i));
				builder.append("\"");
			}
		}

		count = xmlr.getNamespaceCount();

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				builder.append(" ");
				builder.append("xmlns");

				if (xmlr.getNamespacePrefix(i) != null) {
					builder.append(":" + xmlr.getNamespacePrefix(i));
				}

				builder.append("=");
				builder.append("\"");
				builder.append(xmlr.getNamespaceURI(i));
				builder.append("\"");
			}
		}
		
		list.add(builder.toString())
;
		}
}
