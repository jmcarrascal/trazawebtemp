package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.TranAnexo;


public interface ITranAnexoDAO extends IGenericDAO<TranAnexo> {

	List<TranAnexo> getTranAnexoFecha1(Integer fromTransac);
	
	
	
}
