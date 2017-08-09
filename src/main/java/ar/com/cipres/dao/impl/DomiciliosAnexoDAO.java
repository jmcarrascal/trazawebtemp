package ar.com.cipres.dao.impl;

import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IDomiciliosAnexoDAO;
import ar.com.cipres.model.DomiciliosAnexo;

@Repository
public class DomiciliosAnexoDAO extends GenericDAO<DomiciliosAnexo> implements IDomiciliosAnexoDAO {

	private String[] defaultOrderCriteria = { "id" };

	public DomiciliosAnexoDAO() {
		super(DomiciliosAnexo.class);
	}

	@Override
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
}
