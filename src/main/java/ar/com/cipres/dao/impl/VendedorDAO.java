package ar.com.cipres.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IVendedorDAO;
import ar.com.cipres.model.Vendedor;

@Repository
public class VendedorDAO extends GenericDAO<Vendedor> implements IVendedorDAO {

	private String[] defaultOrderCriteria = { "id" };

	public VendedorDAO() {
		super(Vendedor.class);
	}

	public List<Vendedor> getVendedores() {
		List<Vendedor> returnList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select g from Vendedor g where g.descripcion is not null");
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
