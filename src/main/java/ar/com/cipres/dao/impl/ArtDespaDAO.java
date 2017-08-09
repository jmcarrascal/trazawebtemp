package ar.com.cipres.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.type.descriptor.java.JdbcTimestampTypeDescriptor.TimestampMutabilityPlan;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IArtDespaDAO;
import ar.com.cipres.model.ArtDespa;

@Repository
public class ArtDespaDAO extends GenericDAO<ArtDespa> implements IArtDespaDAO {
	
	public ArtDespaDAO() {
		super(ArtDespa.class);
	}

	@Override
	protected String[] getDefaultOrderCriteria() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<ArtDespa> getArtDespaByArtLote(String art, String lote) {
		// TODO Auto-generated method stub
		List<ArtDespa> artDespaList = new ArrayList<ArtDespa>();
			
		try{								
			Query query =sessionFactory.
				      getCurrentSession().
				      createQuery("select a from ArtDespa a where a.id.despachos.soloLote like" + "'%"+ lote+"%' and a.id.artic="+ "'"+ art+"'");
			artDespaList =query.list();    
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return artDespaList;
			}
		
	}
	
	public Date getFechaVencimientoMasProxima(String art) {
		// TODO Auto-generated method stub
		Date fechaMin = new Date(System.currentTimeMillis());
			
		try{						
			String qString = "SELECT min(ad.id.despachos.fechaIng) as fechaIng"
					+ " FROM ArtDespa ad"
					+ " WHERE ad.id.artic = :art AND ad.vendido < ad.cantidad";
					//+ " AND ad.id.despachos.fechaIng > :fechaMin";
			Query query =sessionFactory.
				      getCurrentSession().
				      createQuery(qString);
			query.setInteger("art", Integer.valueOf(art));
			//query.setDate("fechaMin", fechaMin);
			try{
				fechaMin = new Date(((Timestamp) query.list().get(0)).getTime());
			}
			catch(Exception e){
				fechaMin = null;
				//e.printStackTrace();
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return fechaMin;
			}
		
	}
	
}
 