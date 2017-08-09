package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.IFamiliaDAO;
import ar.com.cipres.model.Familia;
import ar.com.cipres.model.Plan;
import ar.com.cipres.services.IFamiliaService;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class FamiliaService implements IFamiliaService {

	@Autowired
	private IFamiliaDAO familiaDAO;
	
	public List<Object> getFamilias() {
		List<Familia> list = familiaDAO.getFamilias();
		List<Object> objList = new ArrayList<Object>(); 
		for (Familia item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getNrfam()), 
				item.getDesfam()
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	

}
