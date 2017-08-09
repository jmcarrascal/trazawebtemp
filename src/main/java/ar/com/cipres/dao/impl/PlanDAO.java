package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IPlanDAO;
import ar.com.cipres.model.Plan;
import ar.com.cipres.model.Provincia;

@Repository
public class PlanDAO extends GenericDAO<Plan> implements IPlanDAO {

	private String[] defaultOrderCriteria = { "id" };

	public PlanDAO() {
		super(Plan.class);
	}

	public List<Plan> getPlanes() {
		List<Plan> returnList = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select g from Plan as g WHERE descrip is not null");
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
