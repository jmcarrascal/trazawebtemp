package ar.com.cipres.model;

import java.util.ArrayList;
import java.util.List;

public class PrintLabel {
	
	private String printName;
	private List<TrazabiShort> trazabiList = new ArrayList<TrazabiShort>();
	
	public PrintLabel(String printName, List<TrazabiShort> trazabiList){
		this.trazabiList = trazabiList;
		this.printName = printName;
	}

	public PrintLabel() {
		// TODO Auto-generated constructor stub
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public List<TrazabiShort> getTrazabiList() {
		return trazabiList;
	}

	public void setTrazabiList(List<TrazabiShort> trazabiList) {
		this.trazabiList = trazabiList;
	}

	

}
