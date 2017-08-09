package ar.com.cipres.dao.impl;

import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.INumerKarDAO;
import ar.com.cipres.model.NumerKar;

@Repository
public class NumerKarDAO extends GenericDAO<NumerKar> implements INumerKarDAO {
	
	public NumerKarDAO() {
		super(NumerKar.class);
	}
	
	protected String[] getDefaultOrderCriteria() {
		// TODO 
		return null;
	}
	
	
	
}
 