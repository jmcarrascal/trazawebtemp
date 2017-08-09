package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Tracking;

public interface ITrackingDAO extends IGenericDAO<Tracking> {

	List<Tracking> getTrackingByHijo(Integer transacNr);

	List<Tracking> getTrackingMadre(Integer transacNr);
	
}
