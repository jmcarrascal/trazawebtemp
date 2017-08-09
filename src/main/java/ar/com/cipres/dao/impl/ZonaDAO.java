package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IZonaDAO;
import ar.com.cipres.model.Zona;

@Repository
public class ZonaDAO extends GenericDAO<Zona> implements IZonaDAO {

	private String[] defaultOrderCriteria = { "id" };

	public ZonaDAO() {
		super(Zona.class);
	}

	public List<Zona> getZonas() {
		List<Zona> returnList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select g from Zona g where g.descripcion is not null");
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
