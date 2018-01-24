package ar.com.cipres.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "detaviajes", schema = "Distri.dbo")
public class DetaViajes implements java.io.Serializable {
	
	
	private String id;
	private Integer transacRemito;
	private Integer viajeNr;

	@Id
	@Column(name = "unico")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getTransacRemito() {
		return transacRemito;
	}
	public void setTransacRemito(Integer transacRemito) {
		this.transacRemito = transacRemito;
	}
	public Integer getViajeNr() {
		return viajeNr;
	}
	public void setViajeNr(Integer viajeNr) {
		this.viajeNr = viajeNr;
	}

	
	
}
