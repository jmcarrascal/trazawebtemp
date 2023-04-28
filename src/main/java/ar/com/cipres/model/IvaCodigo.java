package ar.com.cipres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ivacod", schema = "ComunNewDistri.dbo")
public class IvaCodigo implements java.io.Serializable {
	private Integer id;
	private String descripcion;
	private String letra;
	private String observacion;
	private Integer impuestoId;

	public IvaCodigo() {
	}

	public IvaCodigo(Integer id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Nr")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "Descrip", nullable = true, length = 30)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "Letra", nullable = true, length = 30)
	public String getLetra() {
		return this.letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	@Column(name = "Obser", nullable = true, length = 30)
	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@Column(name = "NrAlicuota", nullable = true)
	public Integer getImpuestoId() {
		return this.impuestoId;
	}

	public void setImpuestoId(Integer impuestoId) {
		this.impuestoId = impuestoId;
	}

}
