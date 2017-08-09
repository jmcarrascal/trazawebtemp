package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.IvaCodigo;

public interface IIvaCodigoDAO extends IGenericDAO<IvaCodigo> {
	public List<IvaCodigo> getIvaCodigos();
}
