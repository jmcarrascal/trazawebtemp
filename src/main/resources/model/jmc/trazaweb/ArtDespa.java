package jmc.trazaweb;

// Generated 07-may-2015 12:44:03 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

/**
 * ArtDespa generated by hbm2java
 */
public class ArtDespa implements java.io.Serializable {

	private ArtDespaId id;
	private Float cantidad;
	private Float vendido;
	private BigDecimal preco;
	private Integer exinr;

	public ArtDespa() {
	}

	public ArtDespa(ArtDespaId id) {
		this.id = id;
	}

	public ArtDespa(ArtDespaId id, Float cantidad, Float vendido,
			BigDecimal preco, Integer exinr) {
		this.id = id;
		this.cantidad = cantidad;
		this.vendido = vendido;
		this.preco = preco;
		this.exinr = exinr;
	}

	public ArtDespaId getId() {
		return this.id;
	}

	public void setId(ArtDespaId id) {
		this.id = id;
	}

	public Float getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Float cantidad) {
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

}
