package ar.com.cipres.dao;

import ar.com.cipres.model.AlfaBetaActualizaciones;

public interface IAlfaBetaActualizacionesDAO extends IGenericDAO<AlfaBetaActualizaciones> {
	public Integer getMaxId();
}
