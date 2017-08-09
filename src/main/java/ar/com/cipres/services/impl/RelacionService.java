package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.IRelacionDAO;
import ar.com.cipres.model.Relacion;
import ar.com.cipres.services.IRelacionService;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class RelacionService implements IRelacionService {

	@Autowired
	private IRelacionDAO relacionDAO;
	
	public List<Object> getRelaciones() {
		List<Relacion> list = relacionDAO.getRelaciones();
		List<Object> objList = new ArrayList<Object>(); 
		for (Relacion item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getId()), 
				item.getDescripcion()
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	

}
