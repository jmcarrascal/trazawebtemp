package ar.com.cipres.services.impl.test;

import java.io.FileOutputStream;
import java.text.DecimalFormat;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PrintReportReporteIngreso {
	public static void main(String[] args) {
		String spec = "{'title':'Hola como va?','headers':[{'name':'Orden','align':0, 'columnWidths':1.5 }, {'name':'Orden1','align':0 , 'columnWidths':2 }, {'name':'Orden2','align':0, 'columnWidths':5 }, {'name':'Orden2','align':2 , 'columnWidths':2 }],"
				+ "'data':[{'value':'10010','align':0}, {'value':'ABC00','align':0 }, {'value':'This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00This is Customer Number ABC00','align':0 }, {'value':'12.34','align':2}]}";
		PrintReportReporteIngreso printReport = new PrintReportReporteIngreso();

		printReport.createPDF("/Users/juanmanuelcarrascal/Downloads/testtable1.pdf", spec);

	}

	private void createPDF(String pdfFilename, String jsonSpec) {

		Document doc = new Document(PageSize.A4.rotate());

		PdfWriter docWriter = null;

		DecimalFormat df = new DecimalFormat("0.00");

		try {

			// special font sizes
			Font bfBold12 = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(0, 0, 0));
			Font bf12 = new Font(FontFamily.HELVETICA, 8);

			// file path
			String path = pdfFilename;
			docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
			
			

			// document header attributes
			doc.addAuthor("Cipres Server");
			doc.addCreationDate();
			doc.addProducer();
			doc.addCreator("");
			doc.addTitle("");

			// open document
			doc.open();
			Image img = Image.getInstance("/Users/juanmanuelcarrascal/projects/ERP/trazaweb/src/main/resources/reports/images/logo.png");
			img.scalePercent(50f);
			doc.add(img);
			
			JsonParser parser = new JsonParser();
			JsonObject spec = parser.parse(jsonSpec).getAsJsonObject();

			JsonArray jsonHeaders = spec.getAsJsonArray("headers");
			JsonArray jsonData = spec.getAsJsonArray("data");
			
			String title = spec.getAsJsonObject().get("title").getAsString();
			
			// create a paragraph
			Paragraph paragraph = new Paragraph(title);
			
			float[] columnWidths = new float[jsonHeaders.size()];
			// specify column widths
			int i = 0;
			for (JsonElement header : jsonHeaders) {
				JsonObject jsonHeader = header.getAsJsonObject();
				columnWidths[i]=jsonHeader.get("columnWidths").getAsFloat();
				i++;
				
			}
			//float[] columnWidths = { 1.5f, 2f, 5f, 2f };
			// create PDF table with the given widths
			PdfPTable table = new PdfPTable(columnWidths);
			// set table width a percentage of the page width
			table.setWidthPercentage(100f);

			

			for (JsonElement header : jsonHeaders) {
				JsonObject jsonHeader = header.getAsJsonObject();
				insertCell(table, jsonHeader.get("name").getAsString(), jsonHeader.get("align").getAsInt(), 1, bfBold12);
			}
			

			table.setHeaderRows(1);
			
			for (JsonElement data : jsonData) {
				JsonObject jsonRow = data.getAsJsonObject();
				insertCell(table, jsonRow.get("value").getAsString(), jsonRow.get("align").getAsInt(), 1, bf12);
			}
			
			// add the PDF table to the paragraph
			paragraph.add(table);
			// add the paragraph to the document
			doc.add(paragraph);

		} catch (DocumentException dex) {
			dex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (doc != null) {
				// close the document
				doc.close();
			}
			if (docWriter != null) {
				// close the writer
				docWriter.close();
			}
		}
	}

	private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

		// create a new cell with the specified Text and Font
		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		// set the cell alignment
		cell.setBorder(Rectangle.NO_BORDER);

		cell.setHorizontalAlignment(align);
		// set the cell column span in case you want to merge two or more cells
		cell.setColspan(colspan);
		// in case there is no text and you wan to create an empty row
		if (text.trim().equalsIgnoreCase("")) {
			cell.setMinimumHeight(10f);
		}
		// add the call to the table
		table.addCell(cell);

	}

}
