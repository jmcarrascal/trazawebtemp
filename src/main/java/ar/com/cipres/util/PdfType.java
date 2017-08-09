package ar.com.cipres.util;

public enum PdfType {
	PEDIDO_VENTA_SIN_ALTERNATIVO("pedidoVentaSin"), PEDIDO_VENTA_CON_ALTERNATIVO("pedidoVentaCon"), PROFORMA("proforma");

	private String method;

	PdfType(String method) {
		this.method = method;
	}
	
	public String GetMethod(){
		return method;
	}
}
