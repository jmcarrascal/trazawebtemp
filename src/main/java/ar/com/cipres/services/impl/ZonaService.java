package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.IZonaDAO;
import ar.com.cipres.model.Zona;
import ar.com.cipres.services.IZonaService;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class ZonaService implements IZonaService {

	@Autowired
	private IZonaDAO zonaDAO;
	
	public List<Object> getZonas() {
		List<Zona> list = zonaDAO.getZonas();
		List<Object> objList = new ArrayList<Object>(); 
		for (Zona item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getId()), 
				item.getDescripcion()
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	

}
