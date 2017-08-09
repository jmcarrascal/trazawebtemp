package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Gente;
import ar.com.cipres.util.ObjCodigoDescrip;

public interface IGenteDAO extends IGenericDAO<Gente> {
	
	public List<Object> findAgendado(ObjCodigoDescrip objCodigoDescrip);
	public List<Object> findAgendado(String razonSocial, String cuit, int relacionId, int pageNumber, int pageSize);
	public List<Object> findAgendado(String searchString);
	public List<Object> findPersons(String data, int relacionId, int pageNumber, int pageSize);
	public Long findAgendadoCount(String razonSocial, String cuit, int relacionId);	
	public Long findPersonsCount(String data, int relacionId);	
	public List<Object> findAgendadoShort(ObjCodigoDescrip objCodigoDescrip);
	public List<Object> getAgendadoProveedorAll();
	public List<Object> getObraSocialAll();
}
