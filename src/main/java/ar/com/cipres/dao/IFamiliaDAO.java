package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Familia;

public interface IFamiliaDAO extends IGenericDAO<Familia> {
	public List<Familia> getFamilias();
}
