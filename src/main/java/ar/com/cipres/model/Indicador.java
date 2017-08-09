package ar.com.cipres.model;

public class Indicador {

	private Integer ejeX;
	private Double ejeY;

	public Indicador(Double ejeY, Integer ejeX) {
		this.ejeY = ejeY;
		this.ejeX = ejeX;
	}

	public Integer getEjeX() {
		return ejeX;
	}

	public void setEjeX(Integer ejeX) {
		this.ejeX = ejeX;
	}

	public Double getEjeY() {
		return ejeY;
	}

	public void setEjeY(Double ejeY) {
		this.ejeY = ejeY;
	}

}
