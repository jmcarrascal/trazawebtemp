package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.cipres.dao.IPlanDAO;
import ar.com.cipres.model.Plan;
import ar.com.cipres.services.IPlanService;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class PlanService implements IPlanService {

	@Autowired
	private IPlanDAO planDAO;
	
	public List<Object> getPlanes() {
		List<Plan> list = planDAO.getPlanes();
		//List<Plan> list = new ArrayList<Plan>();
		List<Object> objList = new ArrayList<Object>(); 
		for (Plan item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(
				String.valueOf(item.getId()), 
				item.getDescripcion()
			);
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	

}
