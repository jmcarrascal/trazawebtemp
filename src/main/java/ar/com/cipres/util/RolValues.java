package ar.com.cipres.util;

public enum RolValues {

	ADMIN(1),
	OPERADOR(2);
	Integer id;
	
	private RolValues(Integer id) {
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}
}
