package ar.com.cipres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subclientesdomicilios", schema = "")
public class SubClientesDomicilios implements java.io.Serializable {

	@Id
	@Column(name = "idSubCliente")
	private Integer idSubCliente;

	@Id
	@Column(name = "idDomicilio")
	private Integer idDomicilio;

	public Integer getIdSubCliente() {
		return idSubCliente;
	}

	public void setIdSubCliente(Integer idSubCliente) {
		this.idSubCliente = idSubCliente;
	}

	public Integer getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(Integer idDomicilio) {
		this.idDomicilio = idDomicilio;
	}
	
	
}
