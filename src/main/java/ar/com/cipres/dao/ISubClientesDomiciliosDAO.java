package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.SubClientesDomicilios;

public interface ISubClientesDomiciliosDAO extends IGenericDAO<SubClientesDomicilios> {

	public List<SubClientesDomicilios> getByidSubCliente (Integer idSubCliente);
	
}
