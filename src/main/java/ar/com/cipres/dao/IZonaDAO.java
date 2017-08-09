package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Zona;

public interface IZonaDAO extends IGenericDAO<Zona> {
	public List<Zona> getZonas();
}
