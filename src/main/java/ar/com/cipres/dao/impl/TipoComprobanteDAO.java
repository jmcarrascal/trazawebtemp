package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.ITipoComprobanteDAO;
import ar.com.cipres.model.TipoComprob;
import ar.com.cipres.model.Zona;

@Repository
public class TipoComprobanteDAO extends GenericDAO<TipoComprob> implements ITipoComprobanteDAO {

	private String[] defaultOrderCriteria = { "nr" };

	public TipoComprobanteDAO() {
		super(TipoComprob.class);
	}

	@Override
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
}
