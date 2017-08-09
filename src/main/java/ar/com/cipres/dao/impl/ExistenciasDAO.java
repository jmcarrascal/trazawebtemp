package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IExistenciasDAO;
import ar.com.cipres.model.Existencias;

@Repository
public class ExistenciasDAO extends GenericDAO<Existencias> implements IExistenciasDAO {
	
	private String[] defaultOrderCriteria = { "nr" };
	
	public ExistenciasDAO() {
		super(Existencias.class);
	}
	
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}

	public List<Existencias> getExistenacias() {
		List<Existencias> returnList = null;
		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select e from Existencias e");
			returnList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return returnList;
	}
}
 