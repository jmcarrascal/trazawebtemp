package ar.com.cipres.model.report;

import java.util.Date;

public class EstadoPedidoDeVenta {
	
	
	private Integer idEstado;
	private String descrip;
	private Date fechaEstado;
	
	public EstadoPedidoDeVenta(Integer idEstado, String descrip, Date fechaEstado){
		this.descrip = descrip;
		this.idEstado = idEstado;
		this.fechaEstado = fechaEstado;		
	}
	
	
	public Integer getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public Date getFechaEstado() {
		return fechaEstado;
	}
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	
	
	

}
