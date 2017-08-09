package ar.com.cipres.dao;

import ar.com.cipres.model.ExiArt;


public interface IExiArtDAO extends IGenericDAO<ExiArt> {
	
	public Double getStockReservadoPVta(String articuloId);
	
}
