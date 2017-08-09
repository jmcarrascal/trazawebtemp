package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.ITransporteDAO;
import ar.com.cipres.model.Transporte;
import ar.com.cipres.services.ITransporteService;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class TransporteService implements ITransporteService {

	@Autowired
	private ITransporteDAO transporteDAO;
	
	public List<Object> getTransportes() {
		List<Transporte> list = transporteDAO.getTransportes();
		List<Object> objList = new ArrayList<Object>(); 
		for (Transporte item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getId()), 
				item.getDescripcion()
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	

}
