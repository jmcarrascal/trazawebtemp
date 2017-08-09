package ar.com.cipres.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.ISubClientesDomiciliosDAO;
import ar.com.cipres.model.SubClientesDomicilios;

@Repository
public class SubClientesDomiciliosDAO extends GenericDAO<SubClientesDomicilios> implements ISubClientesDomiciliosDAO {

	@Override
	protected String[] getDefaultOrderCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubClientesDomicilios> getByidSubCliente(Integer idSubCliente) {
		List<SubClientesDomicilios> list = new ArrayList<SubClientesDomicilios>();

		try {
			String queryStr = "select a from SubClientesDomicilios where idSubCliente = " + idSubCliente;

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);

			list = (List<SubClientesDomicilios>)query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
		return list;
	}

}
