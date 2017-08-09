package ar.com.cipres.model.report;

import java.math.BigDecimal;
import java.util.Date;


public class ExportTransacAfiliados {
	
	
	private Integer tipoComprob;
	private String letra;
	private String prefijo;
	private String nrComprob;
	private Date fecha;
	private BigDecimal netoGrav;
	private BigDecimal netoNoGrav;
	private Float alicuotaIva;
	private BigDecimal retIb;
	private String nrAfiliado;
	private String apellidoAfiliado;
	private String nombreAfiliado;
	private Integer transacNr;
	private Double cant1;
	private BigDecimal precio;
	private String troquel;
	private Integer ivaGravado;
	private String receta;
	private String nombreMedicamento;
	private String claveMedicamento;
	private String total;
	
	
	public String getReceta() {
		return receta;
	}

	public void setReceta(String receta) {
		this.receta = receta;
	}

	public ExportTransacAfiliados(Integer tipoComprob, String letra, String prefijo,  String nrComprob, Date fecha, BigDecimal netoGrav,
					BigDecimal netoNoGrav, Float alicuotaIva, BigDecimal retIb, String nrAfiliado, String apellidoAfiliado,
					String nombreAfiliado, Integer transacNr) {
				super();
				this.tipoComprob = tipoComprob;
				this.prefijo = prefijo;
				this.letra = letra;
				this.nrComprob = nrComprob;
				this.fecha = fecha;
				this.netoGrav = netoGrav;
				this.netoNoGrav = netoNoGrav;
				this.alicuotaIva = alicuotaIva;
				this.retIb = retIb;
				this.nrAfiliado = nrAfiliado;
				this.apellidoAfiliado = apellidoAfiliado;
				this.nombreAfiliado = nombreAfiliado;
				this.transacNr = transacNr;
				
			}
	
	public ExportTransacAfiliados(Integer tipoComprob, String letra, String prefijo,  String nrComprob, Date fecha, BigDecimal netoGrav,
			BigDecimal netoNoGrav, Float alicuotaIva, BigDecimal retIb, String nrAfiliado, String apellidoAfiliado,
			String nombreAfiliado, Integer transacNr, Double cant1, BigDecimal precio, String troquel, Integer ivaGravado) {
		super();
		this.tipoComprob = tipoComprob;
		this.prefijo = prefijo;
		this.letra = letra;
		this.nrComprob = nrComprob;
		this.fecha = fecha;
		this.netoGrav = netoGrav;
		this.netoNoGrav = netoNoGrav;
		this.alicuotaIva = alicuotaIva;
		this.retIb = retIb;
		this.nrAfiliado = nrAfiliado;
		this.apellidoAfiliado = apellidoAfiliado;
		this.nombreAfiliado = nombreAfiliado;
		this.transacNr = transacNr;
		this.cant1 = cant1;
		this.precio = precio;
		this.troquel = troquel;
		this.ivaGravado = ivaGravado;
	}
	
	
	public ExportTransacAfiliados(Integer tipoComprob, String letra, String prefijo,  String nrComprob, Date fecha, BigDecimal netoGrav,
			BigDecimal netoNoGrav, Float alicuotaIva, BigDecimal retIb, String nrAfiliado, String apellidoAfiliado,
			String nombreAfiliado, Integer transacNr, Double cant1, BigDecimal precio, String troquel, Integer ivaGravado, String receta, String nombreMedicamento, String claveMedicamento, String total) {
		super();
		this.tipoComprob = tipoComprob;
		this.prefijo = prefijo;
		this.letra = letra;
		this.nrComprob = nrComprob;
		this.fecha = fecha;
		this.netoGrav = netoGrav;
		this.netoNoGrav = netoNoGrav;
		this.alicuotaIva = alicuotaIva;
		this.retIb = retIb;
		this.nrAfiliado = nrAfiliado;
		this.apellidoAfiliado = apellidoAfiliado;
		this.nombreAfiliado = nombreAfiliado;
		this.transacNr = transacNr;
		this.cant1 = cant1;
		this.precio = precio;
		this.troquel = troquel;
		this.ivaGravado = ivaGravado;
		this.receta = receta;
		this.nombreMedicamento =nombreMedicamento;
		this.claveMedicamento = claveMedicamento;
		this.total = total;
	}
	
	public Integer getTipoComprob() {
		return tipoComprob;
	}
	public void setTipoComprob(Integer tipoComprob) {
		this.tipoComprob = tipoComprob;
	}
	public String getLetra() {
		return letra;
	}
	public void setLetra(String letra) {
		this.letra = letra;
	}
	public String getNrComprob() {
		return nrComprob;
	}
	public void setNrComprob(String nrComprob) {
		this.nrComprob = nrComprob;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public BigDecimal getNetoGrav() {
		return netoGrav;
	}
	public void setNetoGrav(BigDecimal netoGrav) {
		this.netoGrav = netoGrav;
	}
	public BigDecimal getNetoNoGrav() {
		return netoNoGrav;
	}
	public void setNetoNoGrav(BigDecimal netoNoGrav) {
		this.netoNoGrav = netoNoGrav;
	}
	public Float getAlicuotaIva() {
		return alicuotaIva;
	}
	public void setAlicuotaIva(Float alicuotaIva) {
		this.alicuotaIva = alicuotaIva;
	}
	public BigDecimal getRetIb() {
		return retIb;
	}
	public void setRetIb(BigDecimal retIb) {
		this.retIb = retIb;
	}
	public String getNrAfiliado() {
		return nrAfiliado;
	}
	public void setNrAfiliado(String nrAfiliado) {
		this.nrAfiliado = nrAfiliado;
	}
	public String getApellidoAfiliado() {
		return apellidoAfiliado;
	}
	public void setApellidoAfiliado(String apellidoAfiliado) {
		this.apellidoAfiliado = apellidoAfiliado;
	}
	public String getNombreAfiliado() {
		return nombreAfiliado;
	}
	public void setNombreAfiliado(String nombreAfiliado) {
		this.nombreAfiliado = nombreAfiliado;
	}
	public Integer getTransacNr() {
		return transacNr;
	}
	public void setTransacNr(Integer transacNr) {
		this.transacNr = transacNr;
	}
	public Double getCant1() {
		return cant1;
	}
	public void setCant1(Double cant1) {
		this.cant1 = cant1;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public String getTroquel() {
		return troquel;
	}
	public void setTroquel(String troquel) {
		this.troquel = troquel;
	}
	public Integer getIvaGravado() {
		return ivaGravado;
	}
	public void setIvaGravado(Integer ivaGravado) {
		this.ivaGravado = ivaGravado;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public String getNombreMedicamento() {
		return nombreMedicamento;
	}

	public void setNombreMedicamento(String nombreMedicamento) {
		this.nombreMedicamento = nombreMedicamento;
	}

	public String getClaveMedicamento() {
		return claveMedicamento;
	}

	public void setClaveMedicamento(String claveMedicamento) {
		this.claveMedicamento = claveMedicamento;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	
	
	
	

}
