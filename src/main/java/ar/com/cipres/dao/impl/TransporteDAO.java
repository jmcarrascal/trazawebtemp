package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.ITransporteDAO;
import ar.com.cipres.model.Transporte;

@Repository
public class TransporteDAO extends GenericDAO<Transporte> implements ITransporteDAO {

	private String[] defaultOrderCriteria = { "id" };

	public TransporteDAO() {
		super(Transporte.class);
	}

	public List<Transporte> getTransportes() {
		List<Transporte> returnList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select g from Transporte g where g.descripcion is not null");
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
