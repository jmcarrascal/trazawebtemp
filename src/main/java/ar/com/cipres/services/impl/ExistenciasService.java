package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.IExistenciasDAO;
import ar.com.cipres.model.Existencias;
import ar.com.cipres.services.IExistenciasService;
import ar.com.cipres.util.ObjCodigoDescrip;

@Service
public class ExistenciasService implements IExistenciasService {

	@Autowired
	private IExistenciasDAO existenciasDAO;
	
	public List<Object> getExistencias() {
		List<Existencias> list = existenciasDAO.getExistenacias();
		List<Object> objList = new ArrayList<Object>(); 
		for (Existencias item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getNr()), 
				item.getDescrip()
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}
}
