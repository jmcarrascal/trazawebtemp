package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IItemsDAO;
import ar.com.cipres.model.Items;

@Repository
public class ItemsDAO extends GenericDAO<Items> implements IItemsDAO {
	
	public ItemsDAO() {
		super(Items.class);
	}
	
	protected String[] getDefaultOrderCriteria() {
		// TODO 
		return null;
	}
	
	public List<Items> getByTransacArticulo(Integer transacNr, String articulo) {
		List<Items> listItems = null;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
				      createQuery("select i from Items i where i.id.transacNr = " + transacNr + " and i.stock.id='" + articulo + "'");
				    
			listItems=query.list();
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return listItems;
			}
	}

	public List<Items> getOcByTransac(Integer transac_oc_long, boolean validGtin) {
		List<Items> listItems = null;
		try {
			String select = "select i from Items i where i.id.transacNr = " + transac_oc_long
					+ " and i.tipoComprob.nr=15";
			if (validGtin) {
				select = select + " and i.stock.gtin<>'.'";
			}
			Query query = sessionFactory.getCurrentSession().createQuery(select);

			listItems = query.list();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return listItems;
		}
	}
	
	public List<Items> getTransacByIdTransac(Integer transac_oc_long) {
		List<Items> listItems = null;
		try {
			String select = "select i from Items i where i.id.transacNr = " + transac_oc_long;
			Query query = sessionFactory.getCurrentSession().createQuery(select);
			listItems = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return listItems;
		}
	}
	
}
 