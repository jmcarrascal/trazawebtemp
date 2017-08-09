package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.ExistenciasIndicadores;


public interface IExistenciasIndicadoresDAO extends IGenericDAO<ExistenciasIndicadores> {
	
	
	public List<ExistenciasIndicadores> getExistenciaIndicadores(Integer indicador);
	
	
}
