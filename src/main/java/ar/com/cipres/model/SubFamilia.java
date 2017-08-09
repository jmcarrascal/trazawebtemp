package ar.com.cipres.model;

// Generated 23/09/2011 12:11:03 by Hibernate Tools 3.2.0.b9

import java.sql.Blob;
import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * SubFam generated by hbm2java
 */
@Entity
@Table(name = "subfam", schema = "ComunSql.dbo")
public class SubFamilia implements java.io.Serializable {

	private Long nrsubfam;
	private String desubfa = "";
	private Short famaso;
	private Blob foto;
	private Clob leyenda;
	private String descripC;
	

	public SubFamilia() {
	}

	public SubFamilia(Long nrsubfam) {
		this.nrsubfam = nrsubfam;
	}

	public SubFamilia(Long nrsubfam, String desubfa, Short famaso, Blob foto,
			Clob leyenda) {
		this.nrsubfam = nrsubfam;
		this.desubfa = desubfa;
		this.famaso = famaso;
		this.foto = foto;
		this.leyenda = leyenda;
	}

	@Id
	@Column(name = "nrsubfam", unique = true, nullable = false)
	public Long getNrsubfam() {
		return this.nrsubfam;
	}

	public void setNrsubfam(Long nrsubfam) {
		this.nrsubfam = nrsubfam;
	}

	@Column(name = "desubfa", length = 50)
	public String getDesubfa() {
		return this.desubfa;
	}

	public void setDesubfa(String desubfa) {
		this.desubfa = desubfa;
	}

	@Column(name = "famaso")
	public Short getFamaso() {
		return this.famaso;
	}

	public void setFamaso(Short famaso) {
		this.famaso = famaso;
	}

	@Column(name = "foto")
	public Blob getFoto() {
		return this.foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
	}

	@Column(name = "leyenda")
	public Clob getLeyenda() {
		return this.leyenda;
	}

	public void setLeyenda(Clob leyenda) {
		this.leyenda = leyenda;
	}

	@Transient
	public String getDescripC() {
		return "["+nrsubfam+"]"+ desubfa;
	}

	public void setDescripC(String descripC) {
		this.descripC = descripC;
	}
	
	


}
