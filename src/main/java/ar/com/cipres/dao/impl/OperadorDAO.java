package ar.com.cipres.dao.impl;

import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IOperadorDAO;
import ar.com.cipres.model.Operadores;

@Repository
public class OperadorDAO extends GenericDAO<Operadores> implements IOperadorDAO {

	private String[] defaultOrderCriteria = { "operNombre" };

	public OperadorDAO() {
		super(Operadores.class);
	}

	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
}
