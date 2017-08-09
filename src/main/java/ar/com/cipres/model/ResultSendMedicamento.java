package ar.com.cipres.model;

public class ResultSendMedicamento {

	private String errores;
	private boolean exito;
	private String transacNr;
	public String getErrores() {
		return errores;
	}
	public void setErrores(String errores) {
		this.errores = errores;
	}
	public boolean isExito() {
		return exito;
	}
	public void setExito(boolean exito) {
		this.exito = exito;
	}
	public String getTransacNr() {
		return transacNr;
	}
	public void setTransacNr(String transacNr) {
		this.transacNr = transacNr;
	}
	
	
}
