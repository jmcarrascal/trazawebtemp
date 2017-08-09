package ar.com.cipres.model;

import java.util.ArrayList;
import java.util.List;

public class PrintLabel {
	
	private String printName;
	private List<String> printCommandList = new ArrayList<String>();
	
	public PrintLabel(String printName, List<String> printCommandList){
		this.printCommandList = printCommandList;
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

	public List<String> getPrintCommandList() {
		return printCommandList;
	}

	public void setPrintCommandList(List<String> printCommandList) {
		this.printCommandList = printCommandList;
	}

}
