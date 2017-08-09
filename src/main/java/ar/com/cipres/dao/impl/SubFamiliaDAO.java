package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.ISubFamiliaDAO;
import ar.com.cipres.model.SubFamilia;

@Repository
public class SubFamiliaDAO extends GenericDAO<SubFamilia> implements ISubFamiliaDAO {

	private String[] defaultOrderCriteria = { "desubfa" };

	public SubFamiliaDAO() {
		super(SubFamilia.class);
	}

	public List<SubFamilia> getSubFamilias() {
		List<SubFamilia> returnList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select s from SubFamilia as s where s.desubfa <> '.' and s.desubfa is not null order by s.desubfa desc");
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
