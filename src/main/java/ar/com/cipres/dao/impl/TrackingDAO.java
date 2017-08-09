package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.ITrackingDAO;
import ar.com.cipres.model.Tracking;
import ar.com.cipres.model.Transac;

@Repository
public class TrackingDAO extends GenericDAO<Tracking> implements ITrackingDAO {

	private String[] defaultOrderCriteria = { "id" };

	public TrackingDAO() {
		super(Tracking.class);
	}

		

	@Override
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}

	
	public List<Tracking> getTrackingByHijo(Integer transacNr) {
		List<Tracking> trackingList = null; 
		try{			
			
			String sql ="select t from Tracking t where t.hijo = " + transacNr ;				 
			Query query = sessionFactory.getCurrentSession().createQuery(
					sql);
  			
			trackingList = query.list();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			}
		return trackingList;
	}

	public List<Tracking> getTrackingMadre(Integer transacNr) {
		List<Tracking> trackingList = null; 
		try{			
			
			String sql ="select t from Tracking t where t.madre = " + transacNr + " order by t.id asc";				 
			Query query = sessionFactory.getCurrentSession().createQuery(
					sql);
  			
			trackingList = query.list();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			}
		return trackingList;
	}
}
