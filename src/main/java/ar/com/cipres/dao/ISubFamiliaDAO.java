package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.SubFamilia;

public interface ISubFamiliaDAO extends IGenericDAO<SubFamilia> {
	public List<SubFamilia> getSubFamilias();
}
