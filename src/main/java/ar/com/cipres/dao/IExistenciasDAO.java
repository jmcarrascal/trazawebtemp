package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Existencias;


public interface IExistenciasDAO extends IGenericDAO<Existencias> {
	
	public List<Existencias> getExistenacias();
	
}
