package ar.com.cipres.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "alfabetaactualizaciones", schema = "")
public class AlfaBetaActualizaciones implements java.io.Serializable {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "FechaAlta")
	private Date FechaAlta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaAlta() {
		return FechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		FechaAlta = fechaAlta;
	}
	
	
}
