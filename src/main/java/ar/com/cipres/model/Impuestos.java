package ar.com.cipres.model;

// Generated 22/09/2011 00:18:30 by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Impuestos generated by hbm2java
 */
@Entity
@Table(name = "impuestos", schema = "NewDistri.dbo")
public class Impuestos implements java.io.Serializable {

	private Integer id;
	private String descripcion;
	private Float alicuota;
	private Float sobreAlicuota;
	private Float percepcion;
	private Short uso;
	private BigDecimal minimo;
 
	public Impuestos() {
	}

	public Impuestos(Integer id) {
		this.id = id;
	}

	public Impuestos(Integer id, String descripcion, Float alicuota,
			Float sobreAlicuota, Float percepcion, Short uso, BigDecimal minimo) {
		this.id = id;
		this.descripcion = descripcion;
		this.alicuota = alicuota;
		this.sobreAlicuota = sobreAlicuota;
		this.percepcion = percepcion;
		this.uso = uso;
		this.minimo = minimo;
	}

	@Id
	@Column(name = "Nr", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "Descripcion", length = 50)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "Alicuota", precision = 24, scale = 0)
	public Float getAlicuota() {
		return this.alicuota;
	}

	public void setAlicuota(Float alicuota) {
		this.alicuota = alicuota;
	}

	@Column(name = "SobreAlicuota", precision = 24, scale = 0)
	public Float getSobreAlicuota() {
		return this.sobreAlicuota;
	}

	public void setSobreAlicuota(Float sobreAlicuota) {
		this.sobreAlicuota = sobreAlicuota;
	}

	@Column(name = "Percepcion", precision = 24, scale = 0)
	public Float getPercepcion() {
		return this.percepcion;
	}

	public void setPercepcion(Float percepcion) {
		this.percepcion = percepcion;
	}

	@Column(name = "Uso")
	public Short getUso() {
		return this.uso;
	}

	public void setUso(Short uso) {
		this.uso = uso;
	}

	@Column(name = "Minimo", scale = 4)
	public BigDecimal getMinimo() {
		return this.minimo;
	}

	public void setMinimo(BigDecimal minimo) {
		this.minimo = minimo;
	}

}
