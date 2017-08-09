package ar.com.cipres.dao.impl;

import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IImpuestosDAO;
import ar.com.cipres.model.Impuestos;

@Repository
public class ImpuestosDAO extends GenericDAO<Impuestos> implements IImpuestosDAO {

	private String[] defaultOrderCriteria = { "id" };

	public ImpuestosDAO() {
		super(Impuestos.class);
	}

	
	@Override
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
}
