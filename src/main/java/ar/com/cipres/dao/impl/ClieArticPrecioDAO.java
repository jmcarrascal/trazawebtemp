package ar.com.cipres.dao.impl;

import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IClieArticPrecioDAO;
import ar.com.cipres.model.ClieArticPrecio;

@Repository
public class ClieArticPrecioDAO extends GenericDAO<ClieArticPrecio> implements IClieArticPrecioDAO {

	private String[] defaultOrderCriteria = { "genteNr" };

	public ClieArticPrecioDAO() {
		super(ClieArticPrecio.class);
	}

	

	@Override
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
}
