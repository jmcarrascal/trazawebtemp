package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.CondicionVenta;

public interface ICondicionVentaDAO extends IGenericDAO<CondicionVenta> {
	public List<CondicionVenta> getCondicionVentas();
}
