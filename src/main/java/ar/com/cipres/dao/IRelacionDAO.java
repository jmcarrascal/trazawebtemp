package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Relacion;

public interface IRelacionDAO extends IGenericDAO<Relacion> {
	public List<Relacion> getRelaciones();
}
