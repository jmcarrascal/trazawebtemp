package ar.com.cipres.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IExistenciasIndicadoresDAO;
import ar.com.cipres.dao.IPrefijoExistenciaDAO;
import ar.com.cipres.model.ExistenciasIndicadores;
import ar.com.cipres.model.PrefijoExistencia;

@Repository
public class PrefijoExistenciaDAO extends GenericDAO<PrefijoExistencia> implements IPrefijoExistenciaDAO {
	
	private String[] defaultOrderCriteria = { "id" };
	
	public PrefijoExistenciaDAO() {
		super(PrefijoExistencia.class);
	}
	
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
	
	public List<PrefijoExistencia> getPrefijoExistencia(String nrPrefijo) {
		List<PrefijoExistencia> list = new ArrayList<PrefijoExistencia>();
		try{								
			Query query =sessionFactory.
				      getCurrentSession().
				      createQuery("select e from PrefijoExistencia e where e.nrprefijo = '" + nrPrefijo + "'");
			list =query.list();    
										
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return list;
			}
	}
	
}
 