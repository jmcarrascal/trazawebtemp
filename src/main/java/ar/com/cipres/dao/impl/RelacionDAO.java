package ar.com.cipres.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IRelacionDAO;
import ar.com.cipres.model.Relacion;

@Repository
public class RelacionDAO extends GenericDAO<Relacion> implements IRelacionDAO {

	private String[] defaultOrderCriteria = { "id" };

	public RelacionDAO() {
		super(Relacion.class);
	}

	public List<Relacion> getRelaciones() {
		List<Relacion> returnList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select g from Relacion g where g.descripcion is not null");
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
