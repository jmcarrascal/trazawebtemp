package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Items;


public interface IItemsDAO extends IGenericDAO<Items> {
	
	public List<Items> getByTransacArticulo(Integer transacNr, String articulo) ;

	public List<Items> getOcByTransac(Integer transac_oc_long, boolean validGtin);
	
	public List<Items> getTransacByIdTransac(Integer transac_oc_long);
}
