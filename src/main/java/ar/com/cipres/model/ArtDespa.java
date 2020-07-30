package ar.com.cipres.model;

// Generated 07-may-2015 12:44:03 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * ArtDespa generated by hbm2java
 */
@Entity
@Table(name = "artdespa", schema = "ComunDistri.dbo")
public class ArtDespa implements java.io.Serializable {

	private ArtDespaId id;
	private Double cantidad;
	private Float vendido;
	private BigDecimal preco;
	private Integer exinr;
	private String descripC;

	public ArtDespa() {
	}

	public ArtDespa(ArtDespaId id) {
		this.id = id;
	}

	public ArtDespa(ArtDespaId id, Double cantidad, Float vendido,
			BigDecimal preco, Integer exinr) {
		this.id = id;
		this.cantidad = cantidad;
		this.vendido = vendido;
		this.preco = preco;
		this.exinr = exinr;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "artic", column = @Column(name = "artic", nullable = false, length = 13)),
			@AttributeOverride(name = "despaNr", column = @Column(name = "despaNr", nullable = false)) })
	public ArtDespaId getId() {
		return this.id;
	}

	public void setId(ArtDespaId id) {
		this.id = id;
	}

	public Double getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Float getVendido() {
		return this.vendido;
	}

	public void setVendido(Float vendido) {
		this.vendido = vendido;
	}

	public BigDecimal getPreco() {
		return this.preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Integer getExinr() {
		return this.exinr;
	}

	public void setExinr(Integer exinr) {
		this.exinr = exinr;
	}

	@Transient
	public String getDescripC() {
		try{
			descripC = "["+ id.getDespachos().getDescrip() +"] - [" + id.getDespachos().getDespaNr() + "]";
		}catch(Exception e){}
		return descripC;
	}

	public void setDescripC(String descripC) {
		this.descripC = descripC;
	}
	
	

}
