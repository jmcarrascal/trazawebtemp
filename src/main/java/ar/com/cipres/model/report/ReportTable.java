package ar.com.cipres.model.report;

import java.util.ArrayList;
import java.util.List;

public class ReportTable {
	
	private List<Header> headers = new ArrayList<Header>();
	private String title;
	private List<Data> data = new ArrayList<Data>();
	
	public ReportTable(List<Header> listHeader,String title, List<Data> listData ){
		this.setTitle(title);
		this.setData(listData);
		this.setHeaders(listHeader);
		
	}

	public List<Header> getHeaders() {
		return headers;
	}

	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}
	
	
	
	

}
