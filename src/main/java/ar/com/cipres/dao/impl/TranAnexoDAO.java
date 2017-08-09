package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.ITranAnexoDAO;
import ar.com.cipres.model.TranAnexo;
import ar.com.cipres.model.Transac;

@Repository
public class TranAnexoDAO extends GenericDAO<TranAnexo> implements ITranAnexoDAO {

	public TranAnexoDAO() {
		super(TranAnexo.class);
	}

	protected String[] getDefaultOrderCriteria() {
		// TODO
		return null;
	}

	@Override
	public List<TranAnexo> getTranAnexoFecha1(Integer fromTransac) {
		List<TranAnexo> transacList = null;

		try {
			String queryStr = "select ta from TranAnexo ta "
					+ "where ta.transacNr > :fromTransac and ta.str01 is not null and ta.str01 != 'NULL' and ta.str01 != '.' and  ta.str01 != '00        '";

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr).setInteger("fromTransac", fromTransac);
			
			transacList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return transacList;
	}

	
}
