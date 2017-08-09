package ar.com.cipres.model;


public class JsonResultIngresoMercaderia {
	
	private Boolean success;
	private String msg;
	private Boolean printLabel;
	private PrintLabel labels = new PrintLabel();
	private String b64Report ;
	
	public JsonResultIngresoMercaderia(Boolean success, String msg){
		this.success = success;
		this.msg = msg;
	}
	
	public JsonResultIngresoMercaderia(boolean success, String msg, boolean printLabel,
			PrintLabel labels) {
		this.success = success;
		this.msg = msg;
		this.printLabel = printLabel;
		this.labels =labels;
	}
	
	public JsonResultIngresoMercaderia(boolean success, String msg, boolean printLabel,
			PrintLabel labels, String b64Report ) {
		this.success = success;
		this.msg = msg;
		this.printLabel = printLabel;
		this.labels =labels;
		this.b64Report=b64Report;
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

	public Boolean getPrintLabel() {
		return printLabel;
	}

	public void setPrintLabel(Boolean printLabel) {
		this.printLabel = printLabel;
	}

	public PrintLabel getLabels() {
		return labels;
	}

	public void setLabels(PrintLabel labels) {
		this.labels = labels;
	}

	public String getB64Report() {
		return b64Report;
	}

	public void setB64Report(String b64Report) {
		this.b64Report = b64Report;
	}

	
}
