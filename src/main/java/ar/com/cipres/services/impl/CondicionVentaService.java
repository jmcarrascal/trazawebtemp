package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.ICondicionVentaDAO;
import ar.com.cipres.model.CondicionVenta;
import ar.com.cipres.services.ICondicionVentaService;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class CondicionVentaService implements ICondicionVentaService {

	@Autowired
	private ICondicionVentaDAO condicionVentaDAO;
	
	public List<Object> getCondicionVentas() {
		List<CondicionVenta> list = condicionVentaDAO.getCondicionVentas();
		List<Object> objList = new ArrayList<Object>(); 
		for (CondicionVenta item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getId()), 
				item.getDescripcion()
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	

}
