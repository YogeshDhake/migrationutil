package com.cognitivetech;


import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class PDFWriter {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
//	public static void main(String[] args) throws FileNotFoundException {
//
//		System.out.println(" Starting main process");
//
//		PDFWriter writer = new PDFWriter();
//		writer.createDocument();
//
//	}

	public void createDocument() throws FileNotFoundException {
		OutputStream fos = new FileOutputStream("TibcoConf.pdf");
		PdfWriter writer = new PdfWriter(fos);
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);
		// Add paragraph to the document
		document.add(new Paragraph("Parsed Result Start !"));
		List list = getList();
document.add(list);
		// Close document
		document.close();

	}

	public List getList() {
		List list  = new List();
		
		CustomParser parser = new CustomParser();
	 ArrayList< String > parsedList=  parser.parseFile( java.nio.file.Paths.get ("./src/main/resources/Process Order.process"));
	for(String str : parsedList){
		list.add(new ListItem(str));
	}
		
		return list;

	}

}
