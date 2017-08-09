package ar.com.cipres.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IDespachosDAO;
import ar.com.cipres.model.Despachos;
import ar.com.cipres.model.Usuario;

@Repository
public class DespachosDAO extends GenericDAO<Despachos> implements IDespachosDAO {

	private String[] defaultOrderCriteria = { "despaNr" };
	
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
	
	public DespachosDAO() {
		super(Despachos.class);
	}

	
	public Integer getCountDescpachobyLote(String descrip) {
		Integer count = 0;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
				      createQuery("select count(d.despanr) from Despachos d where d.descrip='" + descrip + "'");
				    
			count=(Integer) query.list().get(0);
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return count;
			}
	}
	
		
}
 