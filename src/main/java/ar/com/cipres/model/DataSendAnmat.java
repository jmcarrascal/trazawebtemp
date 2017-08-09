package ar.com.cipres.model;

import com.inssjp.mywebservice.business.IWebServiceStub.TransaccionPlainWS;

public class DataSendAnmat {
	private Integer transacNr;
	private TransaccionPlainWS[] arrayTransac;
	private String nrComprob;
	private Integer existencia;
	
	
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
	public TransaccionPlainWS[] getArrayTransac() {
		return arrayTransac;
	}
	public void setArrayTransac(TransaccionPlainWS[] arrayTransac) {
		this.arrayTransac = arrayTransac;
	}
	public String getNrComprob() {
		return nrComprob;
	}
	public void setNrComprob(String nrComprob) {
		this.nrComprob = nrComprob;
	}
	
	
	
	

}
