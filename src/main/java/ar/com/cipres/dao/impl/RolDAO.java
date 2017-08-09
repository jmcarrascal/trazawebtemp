package ar.com.cipres.dao.impl;

import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IRolDAO;
import ar.com.cipres.model.Rol;

@Repository
public class RolDAO extends GenericDAO<Rol> implements IRolDAO {

	private String[] defaultOrderCriteria = { "nombre" };

	public RolDAO() {
		super(Rol.class);
	}

	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
}
