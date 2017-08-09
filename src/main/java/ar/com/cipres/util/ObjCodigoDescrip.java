package ar.com.cipres.util;

public class ObjCodigoDescrip {
	
	private String codigo;
	private String descrip;
	
	public ObjCodigoDescrip(){}
	
	public ObjCodigoDescrip(String codigo, String descrip){
		this.codigo=codigo;
		this.descrip=descrip;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	
	

}
