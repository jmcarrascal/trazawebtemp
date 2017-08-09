package ar.com.cipres.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IAlfaBetaActualizacionesDAO;
import ar.com.cipres.model.AlfaBetaActualizaciones;

@Repository
public class AlfaBetaActualizacionesDAO extends GenericDAO<AlfaBetaActualizaciones> implements IAlfaBetaActualizacionesDAO {

	@Override
	public Integer getMaxId() {
		Integer idMax = 0;

		try {
			String queryStr = "";

				queryStr = "select max(id) from AlfaBetaActualizaciones";

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			idMax = (Integer) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return idMax;
	}

	@Override
	protected String[] getDefaultOrderCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

}
