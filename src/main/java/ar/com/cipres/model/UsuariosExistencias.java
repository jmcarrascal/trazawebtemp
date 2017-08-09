package ar.com.cipres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuariosexistencias", schema = "")
public class UsuariosExistencias implements java.io.Serializable {

	@Id
	@Column(name = "idUsuario")
	private Integer idUsuario;
	@Id
	@Column(name = "nrExistencia")
	private Integer nrExistencia;


	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Integer getNrExistencia() {
		return nrExistencia;
	}

	public void setNrExistencia(Integer nrExistencia) {
		this.nrExistencia = nrExistencia;
	}
}
