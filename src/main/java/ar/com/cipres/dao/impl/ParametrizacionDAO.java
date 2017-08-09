package ar.com.cipres.dao.impl;

import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IParametrizacionDAO;
import ar.com.cipres.model.Parametrizacion;

@Repository
public class ParametrizacionDAO extends GenericDAO<Parametrizacion> implements IParametrizacionDAO {

	private String[] defaultOrderCriteria = { "idParametrizacion" };

	public ParametrizacionDAO() {
		super(Parametrizacion.class);
	}

	@Override
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}

	
}
