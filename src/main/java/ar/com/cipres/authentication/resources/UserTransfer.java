package ar.com.cipres.authentication.resources;

import java.util.Map;


public class UserTransfer {

	private final String name;
	private final long id;
	private final Map<String, Boolean> roles;
	private String token;
	private Integer rol;
	private Boolean useBulkLoad;
	
	public UserTransfer(String userName, long id, Map<String, Boolean> roles, Integer rol) {
		this.id = id;
		this.name = userName;
		this.roles = roles;
		this.rol = rol;
	}
	
	public UserTransfer(String userName, long id, Map<String, Boolean> roles, String token, Integer rol, Boolean useBulkLoad) {
		this.id = id;
		this.name = userName;
		this.roles = roles;
		this.token = token;
		this.rol = rol;
		this.useBulkLoad = useBulkLoad;
	}
	
	public String getName() {
		return this.name;
	}
	
	public long getId() {
		return this.id;
	}

	public Map<String, Boolean> getRoles() {
		return this.roles;
	}

	public String getToken() {
		return this.token;
	}

	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
		this.rol = rol;
	}

	public Boolean getUseBulkLoad() {
		return useBulkLoad;
	}

	public void setUseBulkLoad(Boolean useBulkLoad) {
		this.useBulkLoad = useBulkLoad;
	}	
	
	
}
