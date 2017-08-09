package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.IVendedorDAO;
import ar.com.cipres.model.Vendedor;
import ar.com.cipres.services.IVendedorService;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class VendedorService implements IVendedorService {

	@Autowired
	private IVendedorDAO vendedorDAO;
	
	public List<Object> getVendedores() {
		List<Vendedor> list = vendedorDAO.getVendedores();
		List<Object> objList = new ArrayList<Object>(); 
		for (Vendedor item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getId()), 
				item.getDescripcion()
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	

}
