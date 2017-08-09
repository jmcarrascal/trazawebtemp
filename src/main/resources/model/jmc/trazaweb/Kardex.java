package jmc.trazaweb;

// Generated 04-may-2015 1:14:29 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * Kardex generated by hbm2java
 */
public class Kardex implements java.io.Serializable {

	private KardexId id;
	private int existenciaNr;
	private Integer articNr;
	private Date fechaComprob;
	private int tipoComprobante;
	private int transacOrigen;
	private float cantidad1;
	private float cantidad2;
	private BigDecimal subtotal;
	private BigDecimal preVta;
	private BigDecimal preCosto;
	private BigDecimal preFob;
	private BigDecimal importeDescuento;
	private float porcentDesc;
	private BigDecimal valorMonExt;
	private int operadorNr;
	private int sucursalNr;
	private int vendedorNr;
	private Date fechaTransac;
	private int agendadoNr;
	private String obser;
	private String talle;
	private Integer colo;
	private byte[] unico;

	public Kardex() {
	}

	public Kardex(KardexId id, int existenciaNr, Date fechaComprob,
			int tipoComprobante, int transacOrigen, float cantidad1,
			float cantidad2, BigDecimal subtotal, BigDecimal preVta,
			BigDecimal preCosto, BigDecimal preFob,
			BigDecimal importeDescuento, float porcentDesc,
			BigDecimal valorMonExt, int operadorNr, int sucursalNr,
			int vendedorNr, Date fechaTransac, int agendadoNr, byte[] unico) {
		this.id = id;
		this.existenciaNr = existenciaNr;
		this.fechaComprob = fechaComprob;
		this.tipoComprobante = tipoComprobante;
		this.transacOrigen = transacOrigen;
		this.cantidad1 = cantidad1;
		this.cantidad2 = cantidad2;
		this.subtotal = subtotal;
		this.preVta = preVta;
		this.preCosto = preCosto;
		this.preFob = preFob;
		this.importeDescuento = importeDescuento;
		this.porcentDesc = porcentDesc;
		this.valorMonExt = valorMonExt;
		this.operadorNr = operadorNr;
		this.sucursalNr = sucursalNr;
		this.vendedorNr = vendedorNr;
		this.fechaTransac = fechaTransac;
		this.agendadoNr = agendadoNr;
		this.unico = unico;
	}

	public Kardex(KardexId id, int existenciaNr, Integer articNr,
			Date fechaComprob, int tipoComprobante, int transacOrigen,
			float cantidad1, float cantidad2, BigDecimal subtotal,
			BigDecimal preVta, BigDecimal preCosto, BigDecimal preFob,
			BigDecimal importeDescuento, float porcentDesc,
			BigDecimal valorMonExt, int operadorNr, int sucursalNr,
			int vendedorNr, Date fechaTransac, int agendadoNr, String obser,
			String talle, Integer colo, byte[] unico) {
		this.id = id;
		this.existenciaNr = existenciaNr;
		this.articNr = articNr;
		this.fechaComprob = fechaComprob;
		this.tipoComprobante = tipoComprobante;
		this.transacOrigen = transacOrigen;
		this.cantidad1 = cantidad1;
		this.cantidad2 = cantidad2;
		this.subtotal = subtotal;
		this.preVta = preVta;
		this.preCosto = preCosto;
		this.preFob = preFob;
		this.importeDescuento = importeDescuento;
		this.porcentDesc = porcentDesc;
		this.valorMonExt = valorMonExt;
		this.operadorNr = operadorNr;
		this.sucursalNr = sucursalNr;
		this.vendedorNr = vendedorNr;
		this.fechaTransac = fechaTransac;
		this.agendadoNr = agendadoNr;
		this.obser = obser;
		this.talle = talle;
		this.colo = colo;
		this.unico = unico;
	}

	public KardexId getId() {
		return this.id;
	}

	public void setId(KardexId id) {
		this.id = id;
	}

	public int getExistenciaNr() {
		return this.existenciaNr;
	}

	public void setExistenciaNr(int existenciaNr) {
		this.existenciaNr = existenciaNr;
	}

	public Integer getArticNr() {
		return this.articNr;
	}

	public void setArticNr(Integer articNr) {
		this.articNr = articNr;
	}

	public Date getFechaComprob() {
		return this.fechaComprob;
	}

	public void setFechaComprob(Date fechaComprob) {
		this.fechaComprob = fechaComprob;
	}

	public int getTipoComprobante() {
		return this.tipoComprobante;
	}

	public void setTipoComprobante(int tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public int getTransacOrigen() {
		return this.transacOrigen;
	}

	public void setTransacOrigen(int transacOrigen) {
		this.transacOrigen = transacOrigen;
	}

	public float getCantidad1() {
		return this.cantidad1;
	}

	public void setCantidad1(float cantidad1) {
		this.cantidad1 = cantidad1;
	}

	public float getCantidad2() {
		return this.cantidad2;
	}

	public void setCantidad2(float cantidad2) {
		this.cantidad2 = cantidad2;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getPreVta() {
		return this.preVta;
	}

	public void setPreVta(BigDecimal preVta) {
		this.preVta = preVta;
	}

	public BigDecimal getPreCosto() {
		return this.preCosto;
	}

	public void setPreCosto(BigDecimal preCosto) {
		this.preCosto = preCosto;
	}

	public BigDecimal getPreFob() {
		return this.preFob;
	}

	public void setPreFob(BigDecimal preFob) {
		this.preFob = preFob;
	}

	public BigDecimal getImporteDescuento() {
		return this.importeDescuento;
	}

	public void setImporteDescuento(BigDecimal importeDescuento) {
		this.importeDescuento = importeDescuento;
	}

	public float getPorcentDesc() {
		return this.porcentDesc;
	}

	public void setPorcentDesc(float porcentDesc) {
		this.porcentDesc = porcentDesc;
	}

	public BigDecimal getValorMonExt() {
		return this.valorMonExt;
	}

	public void setValorMonExt(BigDecimal valorMonExt) {
		this.valorMonExt = valorMonExt;
	}

	public int getOperadorNr() {
		return this.operadorNr;
	}

	public void setOperadorNr(int operadorNr) {
		this.operadorNr = operadorNr;
	}

	public int getSucursalNr() {
		return this.sucursalNr;
	}

	public void setSucursalNr(int sucursalNr) {
		this.sucursalNr = sucursalNr;
	}

	public int getVendedorNr() {
		return this.vendedorNr;
	}

	public void setVendedorNr(int vendedorNr) {
		this.vendedorNr = vendedorNr;
	}

	public Date getFechaTransac() {
		return this.fechaTransac;
	}

	public void setFechaTransac(Date fechaTransac) {
		this.fechaTransac = fechaTransac;
	}

	public int getAgendadoNr() {
		return this.agendadoNr;
	}

	public void setAgendadoNr(int agendadoNr) {
		this.agendadoNr = agendadoNr;
	}

	public String getObser() {
		return this.obser;
	}

	public void setObser(String obser) {
		this.obser = obser;
	}

	public String getTalle() {
		return this.talle;
	}

	public void setTalle(String talle) {
		this.talle = talle;
	}

	public Integer getColo() {
		return this.colo;
	}

	public void setColo(Integer colo) {
		this.colo = colo;
	}

	public byte[] getUnico() {
		return this.unico;
	}

	public void setUnico(byte[] unico) {
		this.unico = unico;
	}

}
