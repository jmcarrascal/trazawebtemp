package ar.com.cipres.model;



// Generated 30/11/2014 21:27:08 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name = "usuario", schema = "trazawebDistri.dbo")
public class Usuario implements java.io.Serializable {

	private Integer id;
	private Rol rol;
	private String nombre;
	private String apellido;
	private String username;
	private String password;
	private String salt;
	private Date lastLogin;
	private Boolean locked;
	private Integer usersk;
	private String printLabel;
	private Boolean useBulkLoad;
	private Boolean vendedor;
	private String descripC;
	
	private List<Existencias> lExistencias;
	
	public Usuario() {
	}

	public Usuario(Rol rol, String nombre, String apellido, String username,
			String password, Boolean locked) {
		this.rol = rol;
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.password = password;
		this.locked = locked;
	}

	public Usuario(Rol rol, String nombre, String apellido, String username,
			String password, Date lastLogin, Boolean locked ) {
		this.rol = rol;
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.password = password;
		this.lastLogin = lastLogin;
		this.locked = locked;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idrol", nullable = false)
	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Column(name = "nombre", nullable = false, length = 50)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "apellido", nullable = false, length = 50)
	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Column(name = "username", unique = true, nullable = false, length = 10)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login", length = 19)
	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Column(name = "locked", nullable = false, columnDefinition = "BIT", length = 1)	
	public Boolean isLocked() {
		return this.locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	
	
	@Column(name = "salt", nullable = false)
	@JsonIgnore
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getUsersk() {
		return usersk;
	}

	public void setUsersk(Integer usersk) {
		this.usersk = usersk;
	}

	public String getPrintLabel() {
		return printLabel;
	}

	public void setPrintLabel(String printLabel) {
		this.printLabel = printLabel;
	}

	
	@Column(name = "useBulkLoad", nullable = true, columnDefinition = "BIT", length = 1)
	public Boolean getUseBulkLoad() {
		return useBulkLoad;
	}
	
	public void setUseBulkLoad(Boolean useBulkLoad) {
		this.useBulkLoad = useBulkLoad;
	}
	
	@Transient
	//@Column(name = "vendedor", nullable = true, columnDefinition = "BIT", length = 1)
	public Boolean getVendedor() {
		return vendedor;
	}

	public void setVendedor(Boolean vendedor) {
		this.vendedor = vendedor;
	}
	
	@Transient
	public String getDescripC() {
		descripC = "[" +this.getId()+"] " + this.getApellido() + ", " + this.getNombre() ;
		return descripC;
	}

	public void setDescripC(String descripC) {
		this.descripC = descripC;
	}

	@Transient
	public List<Existencias> getlExistencias() {
		return lExistencias;
	}

	public void setlExistencias(List<Existencias> lExistencias) {
		this.lExistencias = lExistencias;
	}
	

}
