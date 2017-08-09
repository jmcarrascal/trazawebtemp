package ar.com.cipres.dao;

import java.sql.Date;
import java.util.List;

import ar.com.cipres.model.ArtDespa;


public interface IArtDespaDAO extends IGenericDAO<ArtDespa> {
	
	public List<ArtDespa> getArtDespaByArtLote(String art, String lote);
	
	public Date getFechaVencimientoMasProxima(String art);
	
}
