package ar.com.cipres.dao.impl;

import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IArticuloMadreDAO;
import ar.com.cipres.model.ArticuloMadre;

@Repository
public class ArticuloMadreDAO extends GenericDAO<ArticuloMadre> implements IArticuloMadreDAO {

	private String[] defaultOrderCriteria = { "codArtMad" };

	public ArticuloMadreDAO() {
		super(ArticuloMadre.class);
	}

	
	@Override
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
}
