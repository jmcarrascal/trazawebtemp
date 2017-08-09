package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Transporte;

public interface ITransporteDAO extends IGenericDAO<Transporte> {
	public List<Transporte> getTransportes();
}
