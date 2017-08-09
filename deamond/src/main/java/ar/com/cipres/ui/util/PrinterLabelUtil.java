package ar.com.cipres.ui.util;

import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterName;

import ar.com.cipres.model.PrintLabel;
import ar.com.cipres.model.TrazabiShort;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class PrinterLabelUtil {

	public static void printer(JsonArray printLabels, String printerName) throws PrintException {
		System.out.println("Comienzo");
		PrintService psZebra = null;
		String sPrinterName = null;
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null,
				null);
		for (int i = 0; i < services.length; i++) {
			
			PrintServiceAttribute attr = services[i]
					.getAttribute(PrinterName.class);
			sPrinterName = ((PrinterName) attr).getValue();
			if (sPrinterName.toLowerCase().indexOf(printerName.toLowerCase()) >= 0) {
				System.out.println("Imprime en " + sPrinterName);
				psZebra = services[i];				
				for(final JsonElement labelElement : printLabels) {	
					Gson gson = new Gson();
					TrazabiShort trazabi = gson.fromJson(labelElement, TrazabiShort.class);
					String command = convertPrintCommmand(trazabi);

				    //String printCommand = labelElement.getAsString();
				    //String command = Base64.decode(printCommand);
				    DocPrintJob job = psZebra.createPrintJob();
					DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
					Doc doc = new SimpleDoc(command.getBytes(), flavor, null);
					job.print(doc, null);
					System.out.println(command);
					System.out.println("Imprimió");
				}					
			}
		}

		if (psZebra == null) {
			System.out.println("Zebra printer is not found.");
			return;
		}

	}
	
	public static PrintLabel castTrazabiPrintCommmand(
			List<TrazabiShort> trazabiPrintLabelList, String printName) {
		List<String> printLabelCommand = new ArrayList<String>();
		for (TrazabiShort trazabi : trazabiPrintLabelList) {
			String codUnico = "414" + trazabi.getGlndestinoIng() + "21"
					+ trazabi.getNr();
			String command = "R9,0\n"
					+ // Set Reference Point
					"N\n"
					+ // Clear Image Buffer
					"ZB\n"
					+ // Print direction (from Bottom of buffer)
					"Q223,23\n"
					+ // Set label Length and gap
					"B160,1,0,0,0,7,50,N,\"" + codUnico + "\"\n"
					+ "A160,60,0,1,0,3,N,\"" + codUnico + "\"\n"
					+ "A160,100,0,2,0,1,N,\"(414) " + codUnico + "\"\n"
					+ "A160,130,0,2,0,1,N,\"(21) " + trazabi.getNr() + "\"\n"
					+ "B410,130,0,1,2,2,50,N,\"" + trazabi.getNr() + "\"\n"
					+ "A460,185,0,1,0,3,N,\"" + trazabi.getNr() + "\"\n"
					+ "A160,170,0,2,0,1,N,\"" + trazabi.getNrlote() + "\"\n"
					+ "P1\n"; // Print content of buffer, 1 label
			printLabelCommand.add(command);
		}
		PrintLabel printLabel = new PrintLabel(printName, printLabelCommand);
		return printLabel;
	}
	
	public static String convertPrintCommmand(
			TrazabiShort trazabi) {
	
			String codUnico = "414" + trazabi.getGlndestinoIng() + "21"
					+ trazabi.getNr();
			String command = "R9,0\n"
					+ // Set Reference Point
					"N\n"
					+ // Clear Image Buffer
					"ZB\n"
					+ // Print direction (from Bottom of buffer)
					"Q223,123\n"
					+ // Set label Length and gap
					"B160,1,0,0,0,7,50,N,\"" + codUnico + "\"\n"
					+ "A160,60,0,1,0,3,N,\"" + codUnico + "\"\n"
					+ "A160,100,0,2,0,1,N,\"(414) " + codUnico + "\"\n"
					+ "A160,130,0,2,0,1,N,\"(21) " + trazabi.getNr() + "\"\n"
					+ "B410,130,0,1,2,2,50,N,\"" + trazabi.getNr() + "\"\n"
					+ "A460,185,0,1,0,3,N,\"" + trazabi.getNr() + "\"\n"
					+ "A160,170,0,2,0,1,N,\"" + trazabi.getNrlote() + "\"\n"
					+ "P1\n"; // Print content of buffer, 1 label
			
		
		return command;
	}

}
