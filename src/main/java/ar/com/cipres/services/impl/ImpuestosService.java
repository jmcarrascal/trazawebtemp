package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.IImpuestosDAO;
import ar.com.cipres.model.Impuestos;
import ar.com.cipres.services.IImpuestosService;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class ImpuestosService implements IImpuestosService {

	@Autowired
	private IImpuestosDAO impuestosDAO;
	
	public List<Object> getImpuestosCombo() {
		List<Impuestos> list = impuestosDAO.getAll();
		List<Object> objList = new ArrayList<Object>(); 
		for (Impuestos item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getId()), 
				item.getDescripcion() 
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	

}
