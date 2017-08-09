package ar.com.cipres.dao.impl;

import org.springframework.stereotype.Repository;
import ar.com.cipres.dao.IArticulosHistoricoPreciosDAO;
import ar.com.cipres.model.ArticulosHistoricoPrecios;

@Repository
public class ArticulosHistoricoPreciosDAO extends GenericDAO<ArticulosHistoricoPrecios> implements IArticulosHistoricoPreciosDAO {

	@Override
	protected String[] getDefaultOrderCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

}
