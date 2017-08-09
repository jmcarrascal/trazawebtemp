package ar.com.cipres.services;



import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import ar.com.cipres.model.Domicilios;
import ar.com.cipres.model.Gente;
import ar.com.cipres.model.ObjectPaging;
import ar.com.cipres.util.ObjCodigoDescrip;



/**
 * 
 * @author Juan Manuel Carrascal
 */
public interface IAgendadoService {
	
	public List<Object> findAgendado(ObjCodigoDescrip objCodigoDescrip);
	
	public List<Object> findAgendado(String searchString);

	public List<Object> findAgendadoShort(ObjCodigoDescrip objCodigoDescrip);

	public void saveAgendado(Gente gente);
	
	public void updateCondTributariaAFIP();
	
	public Object getAgendado(Integer id);
	
	public ObjectPaging findPersons(JSONObject jsonObject);
	
	public List<Object> getCondTributariaAFIP(JSONObject jsonObject);
	
	public Object getAgendadoDomicilio(Integer id);

	public void saveAgendadoDomicilio(Domicilios domicilio);

	public ObjectPaging getAgendadoDomicilios(JSONObject jsonObject);

	public ObjectPaging findAgendado(JSONObject jsonObject);

	public List<Object> getAgendadoProveedorAll();

	public ObjectPaging getAgendadoTransacciones(JSONObject json);

	public List<Object> getAgendadoDomicilioTipo(Integer idGente, Integer tipoDomicilio);
	
	public List<Object> getAgendadoDomicilioTipo(Integer idGente, Integer tipoDomicilio, Boolean alternativo);

	public Object getGoogleAddresses(String address);

	public List<Object> getObraSocialAll();
	
}
