package ar.com.cipres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trananexo", schema = "")
public class TranAnexo implements java.io.Serializable {
	
	private String id;
	private Integer transacNr;
	private String str01;
	
	public TranAnexo() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "unico")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getTransacNr() {
		return transacNr;
	}

	public void setTransacNr(Integer transacNr) {
		this.transacNr = transacNr;
	}

	public String getStr01() {
		return str01;
	}

	public void setStr01(String str01) {
		this.str01 = str01;
	}

	

	
	

}
