package ar.com.cipres.model;


public class DataSendMercaderia {
	private Integer transacNr;
	private IngresoMercaderia[] arrayIngresoMercaderia;
	private String nrComprob;
	private Integer existencia;
	private String observaciones;
	private Integer genteNr;
	
	
	
	public Integer getExistencia() {
		return existencia;
	}
	public void setExistencia(Integer existencia) {
		this.existencia = existencia;
	}
	public Integer getTransacNr() {
		return transacNr;
	}
	public void setTransacNr(Integer transacNr) {
		this.transacNr = transacNr;
	}
	
	
	
	public IngresoMercaderia[] getArrayIngresoMercaderia() {
		return arrayIngresoMercaderia;
	}
	public void setArrayIngresoMercaderia(IngresoMercaderia[] arrayIngresoMercaderia) {
		this.arrayIngresoMercaderia = arrayIngresoMercaderia;
	}
	public String getNrComprob() {
		return nrComprob;
	}
	public void setNrComprob(String nrComprob) {
		this.nrComprob = nrComprob;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Integer getGenteNr() {
		return genteNr;
	}
	public void setGenteNr(Integer genteNr) {
		this.genteNr = genteNr;
	}
	
	
	
	
	

}
