package ar.com.cipres.model;

import java.util.List;

public class ChartBasic {
	
	private List<String> labels;
	private List<String> series;
	private List<List<Double>> data;
	private Boolean success = true;
	private String msg;
	
	public ChartBasic(){
		
	}
	
	public ChartBasic(Boolean success,String msg){
		this.setMsg(msg);
		this.setSuccess(success);
	}
	
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<String> getSeries() {
		return series;
	}
	public void setSeries(List<String> series) {
		this.series = series;
	}
	public List<List<Double>> getData() {
		return data;
	}
	public void setData(List<List<Double>> data) {
		this.data = data;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
