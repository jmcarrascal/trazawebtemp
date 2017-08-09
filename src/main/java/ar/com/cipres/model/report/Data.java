package ar.com.cipres.model.report;

public class Data {
	
	private String value;
	private Integer align;
	
	public Data(String value, Integer align){
		this.setAlign(align);
		this.setValue(value);
	}
	
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getAlign() {
		return align;
	}
	public void setAlign(Integer align) {
		this.align = align;
	}

}
