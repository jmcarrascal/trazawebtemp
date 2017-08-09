package ar.com.cipres.model;

import java.util.ArrayList;
import java.util.List;

public class JsonResultRomaneo {
	
	private Boolean success;
	private String msg;
	private String observaciones;
	private String existencia;
	
	public String getExistencia() {
		return existencia;
	}

	public void setExistencia(String existencia) {
		this.existencia = existencia;
	}

	private List<RomaneoMedicamento> romaneoList = new ArrayList<RomaneoMedicamento>();
	
	public JsonResultRomaneo(Boolean success, String msg, List<RomaneoMedicamento> romaneoList, String observaciones){
		this.success = success;
		this.msg = msg;
		this.romaneoList = romaneoList;
		this.observaciones = observaciones;
	}
	
	public JsonResultRomaneo(Boolean success, String msg, List<RomaneoMedicamento> romaneoList, String observaciones, String existencia) {
		this.success = success;
		this.msg = msg;
		this.romaneoList = romaneoList;
		this.observaciones = observaciones;
		this.existencia = existencia;
	}

	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<RomaneoMedicamento> getRomaneoList() {
		return romaneoList;
	}

	public void setRomaneoList(List<RomaneoMedicamento> romaneoList) {
		this.romaneoList = romaneoList;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	
	
	
	

}
