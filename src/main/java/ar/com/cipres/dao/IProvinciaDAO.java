package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Provincia;

public interface IProvinciaDAO extends IGenericDAO<Provincia> {
	public List<Provincia> getProvincias();
}
