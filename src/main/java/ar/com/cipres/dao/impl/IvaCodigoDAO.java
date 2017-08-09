package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IIvaCodigoDAO;
import ar.com.cipres.model.IvaCodigo;

@Repository
public class IvaCodigoDAO extends GenericDAO<IvaCodigo> implements IIvaCodigoDAO {

	private String[] defaultOrderCriteria = { "id" };

	public IvaCodigoDAO() {
		super(IvaCodigo.class);
	}

	public List<IvaCodigo> getIvaCodigos() {
		List<IvaCodigo> returnList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select g from IvaCodigo g where g.descripcion is not null");
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
