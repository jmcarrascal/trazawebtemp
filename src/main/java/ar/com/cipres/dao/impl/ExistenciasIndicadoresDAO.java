package ar.com.cipres.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IExistenciasIndicadoresDAO;
import ar.com.cipres.model.ExistenciasIndicadores;

@Repository
public class ExistenciasIndicadoresDAO extends GenericDAO<ExistenciasIndicadores> implements IExistenciasIndicadoresDAO {
	
	private String[] defaultOrderCriteria = { "id" };
	
	public ExistenciasIndicadoresDAO() {
		super(ExistenciasIndicadores.class);
	}
	
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
	
	
	public List<ExistenciasIndicadores> getExistenciaIndicadores(Integer indicador) {
		List<ExistenciasIndicadores> list = new ArrayList<ExistenciasIndicadores>();
		try{								
			Query query =sessionFactory.
				      getCurrentSession().
				      createQuery("select e from ExistenciasIndicadores e where e.nrIndicador =" + indicador);
			list =query.list();    
										
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return list;
			}
	}
	
}
 