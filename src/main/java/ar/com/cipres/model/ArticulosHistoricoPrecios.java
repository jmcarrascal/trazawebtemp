package ar.com.cipres.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "articuloshistoricoprecios", schema = "")
public class ArticulosHistoricoPrecios {
	@Id
	@Column(name = "Clave")
	private String Clave;

	@Column(name = "FechaActualizacion")
	private Date FechaActualizacion;
	
	@Column(name = "Precio")
	private BigDecimal Precio;

	public String getClave() {
		return Clave;
	}

	public void setClave(String clave) {
		Clave = clave;
	}

	public Date getFechaActualizacion() {
		return FechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		FechaActualizacion = fechaActualizacion;
	}

	public BigDecimal getPrecio() {
		return Precio;
	}

	public void setPrecio(BigDecimal precio) {
		Precio = precio;
	}
}
