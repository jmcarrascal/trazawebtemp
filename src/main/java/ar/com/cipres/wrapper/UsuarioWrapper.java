package ar.com.cipres.wrapper;


import ar.com.cipres.model.Rol;

public class UsuarioWrapper {
	private Integer id;
	private Rol rol;
	private String nombre;
	private String apellido;
	private String username;
	private Integer usersk;
	private String printLabel;
	private boolean useBulkLoad;
	private boolean vendedor;
	
	public boolean isVendedor() {
		return vendedor;
	}
	public void setVendedor(boolean vendedor) {
		this.vendedor = vendedor;
	}
	public Integer getUsersk() {
		return usersk;
	}
	public void setUsersk(Integer usersk) {
		this.usersk = usersk;
	}
	private boolean locked;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getLocked() {
		return this.locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public String getPrintLabel() {
		return printLabel;
	}
	public void setPrintLabel(String printLabel) {
		this.printLabel = printLabel;
	}
	public boolean isUseBulkLoad() {
		return useBulkLoad;
	}
	public void setUseBulkLoad(boolean useBulkLoad) {
		this.useBulkLoad = useBulkLoad;
	}
	

	
}
