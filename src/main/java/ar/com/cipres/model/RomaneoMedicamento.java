package ar.com.cipres.model;

public class RomaneoMedicamento {
	
	private String gtin;
	private String descrip;
	private Double cant1;
	private Double cant1Entregado;
	private String id;
	private Integer ingresado = 0;
	
	public RomaneoMedicamento(String gtin, String id, Double cant1,
			Double cant1Entregado, String descrip) {
		this.gtin = gtin;
		this.id = id;
		this.cant1 = cant1;
		this.cant1Entregado = cant1Entregado;
		this.descrip = descrip;
	}
	public String getGtin() {
		return gtin;
	}
	public void setGtin(String gtin) {
		this.gtin = gtin;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public Double getCant1() {
		return cant1;
	}
	public void setCant1(Double cant1) {
		this.cant1 = cant1;
	}
	public Double getCant1Entregado() {
		return cant1Entregado;
	}
	public void setCant1Entregado(Double cant1Entregado) {
		this.cant1Entregado = cant1Entregado;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getIngresado() {
		return ingresado;
	}
	public void setIngresado(Integer ingresado) {
		this.ingresado = ingresado;
	}
	
	
	

}
