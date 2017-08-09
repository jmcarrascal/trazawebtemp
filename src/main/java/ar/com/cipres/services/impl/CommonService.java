package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ar.com.cipres.dao.IParametrizacionDAO;
import ar.com.cipres.model.Parametrizacion;
import ar.com.cipres.services.ICommonService;

@Component
@Transactional
public class CommonService implements ICommonService {

	@Autowired(required=true)
	private IParametrizacionDAO parametrizacionDAO;

	private List<Parametrizacion> parametrizacionList = new ArrayList<Parametrizacion>();
	
	
	@PostConstruct
//	@Transactional
	public void CommonServicea() {		
		//parametrizacionList = parametrizacionDAO.getAll();				
	}

	public List<Parametrizacion> getParametrizacion(){
//		return parametrizacionList;
		return null;
	}
	

}