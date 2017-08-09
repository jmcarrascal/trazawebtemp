package ar.com.cipres.dao;

import ar.com.cipres.model.Despachos;


public interface IDespachosDAO extends IGenericDAO<Despachos> {
	
	public Integer getCountDescpachobyLote(String descrip);
	
}
