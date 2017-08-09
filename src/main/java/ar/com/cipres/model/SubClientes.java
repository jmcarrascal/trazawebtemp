package ar.com.cipres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subclientes", schema = "")
public class SubClientes implements java.io.Serializable {
	
	@Id
	@Column(name = "idSubCliente")
	private Integer idSubCliente;

	@Column(name = "idCliente")
	private Integer idCliente;
	
	@Column(name = "Nombre")
	private String Nombre;
	
	@Column(name = "Apellido")
	private String Apellido;

	@Column(name = "TipoDocumento")
	private Integer TipoDocumento;
	
	@Column(name = "Documento")
	private String Documento;
	
	@Column(name = "CUIL")
	private String CUIL;
	
	@Column(name = "NroAfiliado")
	private String NroAfiliado;
	
	@Column(name = "Telefono")
	private String Telefono;
	
	@Column(name = "Email")
	private String Email;

	public Integer getIdSubCliente() {
		return idSubCliente;
	}

	public void setIdSubCliente(Integer idSubCliente) {
		this.idSubCliente = idSubCliente;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	public Integer getTipoDocumento() {
		return TipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		TipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return Documento;
	}

	public void setDocumento(String documento) {
		Documento = documento;
	}

	public String getCUIL() {
		return CUIL;
	}

	public void setCUIL(String cUIL) {
		CUIL = cUIL;
	}

	public String getNroAfiliado() {
		return NroAfiliado;
	}

	public void setNroAfiliado(String nroAfiliado) {
		NroAfiliado = nroAfiliado;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
	
}
