package ar.com.cipres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "relacion", schema = "ComunDistri.dbo")
public class Relacion implements java.io.Serializable {
	private Integer id;
	private String descripcion;

	public Relacion() {
	}

	public Relacion(Integer id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "nr")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "descrip", nullable = true, length = 30)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
