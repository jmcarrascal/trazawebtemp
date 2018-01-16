package ar.com.cipres.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tracking", schema = "TransacSql.dbo")
public class Tracking implements java.io.Serializable {
	private Integer id;
	private Integer genteNr;
	private Integer transac;
	private Integer tipoComprob ;
	private Integer madre;
	private Integer hijo;
	private Integer nivel;
	private String obser;
	private Date fechaEntrega;
	
	@Id
	@Column(name = "PK")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGenteNr() {
		return genteNr;
	}
	public void setGenteNr(Integer genteNr) {
		this.genteNr = genteNr;
	}
	public Integer getTransac() {
		return transac;
	}
	public void setTransac(Integer transac) {
		this.transac = transac;
	}
	public Integer getTipoComprob() {
		return tipoComprob;
	}
	public void setTipoComprob(Integer tipoComprob) {
		this.tipoComprob = tipoComprob;
	}
	public Integer getMadre() {
		return madre;
	}
	public void setMadre(Integer madre) {
		this.madre = madre;
	}
	public Integer getHijo() {
		return hijo;
	}
	public void setHijo(Integer hijo) {
		this.hijo = hijo;
	}
	public Integer getNivel() {
		return nivel;
	}
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	public String getObser() {
		return obser;
	}
	public void setObser(String obser) {
		this.obser = obser;
	}
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	

	
	
	





}
