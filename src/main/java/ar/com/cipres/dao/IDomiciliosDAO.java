package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Domicilios;


public interface IDomiciliosDAO extends IGenericDAO<Domicilios> {
	
	public Domicilios getDomicilioPrincipal(Integer genteNr);
	
	public List<Object> getDomicilioList(Integer genteNr);
	public List<Object> getDomicilioList(Integer genteId, String texto, Boolean principal, Boolean entrega, Boolean cobranza, Boolean mailing, int pageNumber, int pageSize, Boolean alternativo);
	public Long getDomicilioListCount(Integer genteId, String texto, Boolean principal, Boolean entrega, Boolean cobranza, Boolean mailing, Boolean alternativo);

	public List<Domicilios> getAgendadoDomicilioTipo(Integer idGente, Boolean principal, Boolean entrega,
			Boolean cobranza, Boolean mailing);

	public List<Domicilios> getAgendadoDomicilioByAlternativo(Integer idGente, Boolean principal, Boolean entrega,
			Boolean cobranza, Boolean mailing, Boolean alternativo);

	public Domicilios getDomicilioAgendadoAfiliado(int obraSocial, String afiliado);		
	
}
