package ar.com.cipres.model;

public class IngresoMercaderia {

	private Stock articulo;
	private Integer cantidad;
	private Boolean useDespacho;
	private String lote;
	private String vencimiento;
	private ArtDespa artDespa;
	private String observacion_kardex;
	private Boolean traza_int;
	
	public Boolean getTraza_int() {
		return traza_int;
	}
	public void setTraza_int(Boolean traza_int) {
		this.traza_int = traza_int;
	}
	public String getObservacion_kardex() {
		return observacion_kardex;
	}
	public void setObservacion_kardex(String observacion_kardex) {
		this.observacion_kardex = observacion_kardex;
	}
	public ArtDespa getArtDespa() {
		return artDespa;
	}
	public void setArtDespa(ArtDespa artDespa) {
		this.artDespa = artDespa;
	}
	public Boolean getUseDespacho() {
		return useDespacho;
	}
	public void setUseDespacho(Boolean useDespacho) {
		this.useDespacho = useDespacho;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	
	public String getVencimiento() {
		return vencimiento;
	}
	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}
	public Stock getArticulo() {
		return articulo;
	}
	public void setArticulo(Stock articulo) {
		this.articulo = articulo;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
}
