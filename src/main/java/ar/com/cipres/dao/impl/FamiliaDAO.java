package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IFamiliaDAO;
import ar.com.cipres.model.Familia;

@Repository
public class FamiliaDAO extends GenericDAO<Familia> implements IFamiliaDAO {

	private String[] defaultOrderCriteria = { "desfam" };

	public FamiliaDAO() {
		super(Familia.class);
	}

	public List<Familia> getFamilias() {
		List<Familia> returnList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select f from Familia as f where f.desfam <> '.' and f.desfam is not null order by f.desfam desc");
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
