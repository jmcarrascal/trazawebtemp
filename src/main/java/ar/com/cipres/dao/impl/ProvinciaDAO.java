package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IProvinciaDAO;
import ar.com.cipres.model.Provincia;

@Repository
public class ProvinciaDAO extends GenericDAO<Provincia> implements IProvinciaDAO {

	private String[] defaultOrderCriteria = { "id" };

	public ProvinciaDAO() {
		super(Provincia.class);
	}

	public List<Provincia> getProvincias() {
		List<Provincia> returnList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select g from Provincia g where g.descripcion is not null");
			returnList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return returnList;
		}
	}

	@Override
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
}
