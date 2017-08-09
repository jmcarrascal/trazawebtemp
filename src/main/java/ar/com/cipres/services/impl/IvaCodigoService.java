package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.IIvaCodigoDAO;
import ar.com.cipres.model.IvaCodigo;
import ar.com.cipres.services.IIvaCodigoService;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class IvaCodigoService implements IIvaCodigoService {

	@Autowired
	private IIvaCodigoDAO ivaCodigoDAO;
	
	public List<Object> getIvaCodigos() {
		List<IvaCodigo> list = ivaCodigoDAO.getIvaCodigos();
		List<Object> objList = new ArrayList<Object>(); 
		for (IvaCodigo item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getId()), 
				item.getDescripcion()
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	

}
