package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.ExistenciasIndicadores;
import ar.com.cipres.model.PrefijoExistencia;


public interface IPrefijoExistenciaDAO extends IGenericDAO<PrefijoExistencia> {
	
	
	public List<PrefijoExistencia> getPrefijoExistencia(String nrPrefijo);
	
	
}
