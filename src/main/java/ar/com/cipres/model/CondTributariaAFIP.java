package ar.com.cipres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "condtributariaafip", schema = "")
public class CondTributariaAFIP {
	@Id
	@Column(name = "CUIT")
	private String CUIT;
	
	@Column(name = "Denominacion")
	private String Denominacion;
	
	@Column(name = "ImpGanancias")
	private String ImpGanancias;
	
	@Column(name = "ImpIVA")
	private String ImpIVA;
	
	@Column(name = "Monotributo")
	private String Monotributo;
	
	@Column(name = "IntegranteSoc")
	private String IntegranteSoc;
	
	@Column(name = "Empleador")
	private String Empleador;
	
	@Column(name = "ActividadMonotributo")
	private String ActividadMonotributo;

	public String getCUIT() {
		return CUIT;
	}

	public void setCUIT(String cUIT) {
		CUIT = cUIT;
	}

	public String getDenominacion() {
		return Denominacion;
	}

	public void setDenominacion(String denominacion) {
		Denominacion = denominacion;
	}

	public String getImpGanancias() {
		return ImpGanancias;
	}

	public void setImpGanancias(String impGanancias) {
		ImpGanancias = impGanancias;
	}

	public String getImpIVA() {
		return ImpIVA;
	}

	public void setImpIVA(String impIVA) {
		ImpIVA = impIVA;
	}

	public String getMonotributo() {
		return Monotributo;
	}

	public void setMonotributo(String monotributo) {
		Monotributo = monotributo;
	}

	public String getIntegranteSoc() {
		return IntegranteSoc;
	}

	public void setIntegranteSoc(String integranteSoc) {
		IntegranteSoc = integranteSoc;
	}

	public String getEmpleador() {
		return Empleador;
	}

	public void setEmpleador(String empleador) {
		Empleador = empleador;
	}

	public String getActividadMonotributo() {
		return ActividadMonotributo;
	}

	public void setActividadMonotributo(String actividadMonotributo) {
		ActividadMonotributo = actividadMonotributo;
	}
	
}
