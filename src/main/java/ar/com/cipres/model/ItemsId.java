   package ar.com.cipres.model;

// Generated 23/09/2011 12:11:03 by Hibernate Tools 3.2.0.b9

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * ExiArtId generated by hbm2java
 */
@Embeddable
public class ItemsId implements java.io.Serializable {

	private Integer transacNr;
	private Integer itemNr;

	public ItemsId() {
	}

	public ItemsId(Transac transac, Integer itemNr) {
		this.itemNr = itemNr;
		this.transacNr = transac.getTransacNr();
	}

		
	

	public Integer getTransacNr() {
		return transacNr;
	}

	public void setTransacNr(Integer transacNr) {
		this.transacNr = transacNr;
	}

	@Column(name = "ItemNr", nullable = false)
	public Integer getItemNr() {
		return itemNr;
	}

	public void setItemNr(Integer itemNr) {
		this.itemNr = itemNr;
	}


	
	
	

}
