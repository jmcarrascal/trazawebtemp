package ar.com.cipres.model;

// Generated 23/09/2011 12:11:03 by Hibernate Tools 3.2.0.b9

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Fam generated by hbm2java
 */
@Entity
@Table(name = "fam", schema = "ComunSql.dbo")
public class Familia implements java.io.Serializable {

	private Long nrfam;
	private String desfam;
	private String descripC;
	
	
	
	

	public Familia() {
	}

	public Familia(Long nrfam) {
		this.nrfam = nrfam;
	}

	public Familia(Long nrfam, String desfam) {
		this.nrfam = nrfam;
		this.desfam = desfam;
	}

	@Id
	@Column(name = "nrfam", unique = true, nullable = false)
	public Long getNrfam() {
		return this.nrfam;
	}

	public void setNrfam(Long nrfam) {
		this.nrfam = nrfam;
	}

	@Column(name = "desfam", length = 50)
	public String getDesfam() {
		return this.desfam;
	}

	public void setDesfam(String desfam) {
		this.desfam = desfam;
	}

	@Transient
	public String getDescripC() {
		return "["+nrfam+"]"+ desfam;
	}

	public void setDescripC(String descripC) {
		this.descripC = descripC;
	}


}
