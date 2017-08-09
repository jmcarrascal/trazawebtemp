package ar.com.cipres.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "viajes", schema = "Empresa1.dbo")
public class Viaje implements java.io.Serializable {
	
	
	private Integer id;
	private Date fecha;
	private Integer choferNr;
	private Integer zonaNr;
	private Double km;
	private Double precio;
	private Integer modalidad;
	private String obser;
	private Double valoriz;
	private Integer viajeChof;

	@Id
	@Column(name = "viajeNr")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getChoferNr() {
		return choferNr;
	}
	public void setChoferNr(Integer choferNr) {
		this.choferNr = choferNr;
	}
	public Integer getZonaNr() {
		return zonaNr;
	}
	public void setZonaNr(Integer zonaNr) {
		this.zonaNr = zonaNr;
	}
	public Double getKm() {
		return km;
	}
	public void setKm(Double km) {
		this.km = km;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Integer getModalidad() {
		return modalidad;
	}
	public void setModalidad(Integer modalidad) {
		this.modalidad = modalidad;
	}
	public String getObser() {
		return obser;
	}
	public void setObser(String obser) {
		this.obser = obser;
	}
	public Double getValoriz() {
		return valoriz;
	}
	public void setValoriz(Double valoriz) {
		this.valoriz = valoriz;
	}
	public Integer getViajeChof() {
		return viajeChof;
	}
	public void setViajeChof(Integer viajeChof) {
		this.viajeChof = viajeChof;
	}

	
	
	
}
