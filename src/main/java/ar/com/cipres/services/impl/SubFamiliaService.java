package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.ISubFamiliaDAO;
import ar.com.cipres.model.Familia;
import ar.com.cipres.model.SubFamilia;
import ar.com.cipres.services.ISubFamiliaService;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class SubFamiliaService implements ISubFamiliaService {

	@Autowired
	private ISubFamiliaDAO subFamiliaDAO;
	
	public List<Object> getSubFamilias() {
		List<SubFamilia> list = subFamiliaDAO.getSubFamilias();
		List<Object> objList = new ArrayList<Object>(); 
		for (SubFamilia item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getNrsubfam()), 
				item.getDesubfa()
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	

}
