package ar.com.cipres.model.report;

public class Header {
	
	private String name;
	private Integer align;
	private Double columnWidths;
	
	public Header(String name, Integer align, Double columnWidths){
		this.setAlign(align);
		this.setColumnWidths(columnWidths);
		this.setName(name);
		
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAlign() {
		return align;
	}
	public void setAlign(Integer align) {
		this.align = align;
	}
	public Double getColumnWidths() {
		return columnWidths;
	}
	public void setColumnWidths(Double columnWidths) {
		this.columnWidths = columnWidths;
	}
	
	

}
