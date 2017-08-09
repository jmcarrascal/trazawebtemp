package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Vendedor;

public interface IVendedorDAO extends IGenericDAO<Vendedor> {
	public List<Vendedor> getVendedores();
}
