package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.IProvinciaDAO;
import ar.com.cipres.model.Provincia;
import ar.com.cipres.services.IProvinciaService;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class ProvinciaService implements IProvinciaService {

	@Autowired
	private IProvinciaDAO provinciaDAO;
	
	public List<Object> getProvincias() {
		List<Provincia> list = provinciaDAO.getProvincias();
		List<Object> objList = new ArrayList<Object>(); 
		for (Provincia item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getId()), 
				item.getDescripcion()
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	

}
