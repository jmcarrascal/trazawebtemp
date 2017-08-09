package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.CondTributariaAFIP;

public interface ICondTributariaAFIPDAO extends IGenericDAO<CondTributariaAFIP> {
	public List<Object> getCondTributaria(String busqueda);
}
