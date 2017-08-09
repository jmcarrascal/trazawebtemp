package ar.com.cipres.dao.impl;

import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.INumeracionesDAO;
import ar.com.cipres.model.Numeraciones;

@Repository
public class NumeracionesDAO extends GenericDAO<Numeraciones> implements INumeracionesDAO {

	private String[] defaultOrderCriteria = { "id" };

	public NumeracionesDAO() {
		super(Numeraciones.class);
	}

	

	@Override
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
}
