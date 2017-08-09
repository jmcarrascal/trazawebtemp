package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.ICondicionVentaDAO;
import ar.com.cipres.model.CondicionVenta;

@Repository
public class CondicionVentaDAO extends GenericDAO<CondicionVenta> implements ICondicionVentaDAO {

	private String[] defaultOrderCriteria = { "id" };

	public CondicionVentaDAO() {
		super(CondicionVenta.class);
	}

	public List<CondicionVenta> getCondicionVentas() {
		List<CondicionVenta> returnList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select g from CondicionVenta g where g.descripcion is not null");
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
