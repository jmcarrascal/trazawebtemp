package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.ICondTributariaAFIPDAO;
import ar.com.cipres.model.CondTributariaAFIP;

@Repository
public class CondTributariaAFIPDAO extends GenericDAO<CondTributariaAFIP> implements ICondTributariaAFIPDAO {

	@Override
	protected String[] getDefaultOrderCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getCondTributaria(String busqueda) {
		List<Object> genteList = null;
		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select c from CondTributariaAFIP c where Denominacion LIKE '%" + busqueda + "' OR CUIT LIKE '%" + busqueda + "' ORDER BY Denominacion");
			genteList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return genteList;
		}
	}
}
