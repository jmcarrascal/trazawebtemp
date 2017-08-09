package ar.com.cipres.model;

import java.util.ArrayList;
import java.util.List;

public class FilterTrazabi {
	
	private String gtin;
	private List<String> series = new ArrayList<String>();
	
	
	public String getGtin() {
		return gtin;
	}
	public void setGtin(String gtin) {
		this.gtin = gtin;
	}
	public List<String> getSeries() {
		return series;
	}
	public void setSeries(List<String> series) {
		this.series = series;
	}
	
	

}
