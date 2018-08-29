package ar.com.cipres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trans", schema = "ComunNewDistri.dbo")
public class Transporte implements java.io.Serializable {
	private Integer id;
	private String descripcion;
	private String domicilio;
	private String cuit;
	private String telefono;
	private String observaciones;
	private Integer area1;
	private Integer area2;
	private Integer area3;
	private String observacionEntrega;

	public Transporte() {
	}

	public Transporte(Integer id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "nr")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "descrip", nullable = true, length = 30)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "domi", nullable = true, length = 30)
	public String getDomicilio() {
		return this.domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	@Column(name = "cuit", nullable = true, length = 30)
	public String getCuit() {
		return this.cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	@Column(name = "tel", nullable = true, length = 30)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "obser", nullable = true, length = 30)
	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Column(name = "area1", nullable = true)
	public Integer getArea1() {
		return this.area1;
	}

	public void setArea1(Integer area1) {
		this.area1 = area1;
	}

	@Column(name = "area2", nullable = true)
	public Integer getArea2() {
		return this.area2;
	}

	public void setArea2(Integer area2) {
		this.area2 = area2;
	}

	@Column(name = "area3", nullable = true)
	public Integer getArea3() {
		return this.area3;
	}

	public void setArea3(Integer area3) {
		this.area3 = area3;
	}

	@Column(name = "ObserEntrega", nullable = true, length = 50)
	public String getObservacionEntrega() {
		return this.observacionEntrega;
	}

	public void setObservacionEntrega(String observacionEntrega) {
		this.observacionEntrega = observacionEntrega;
	}

}
