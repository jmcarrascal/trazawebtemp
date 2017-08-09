package ar.com.cipres.dao;

import ar.com.cipres.model.DetaViajes;
import ar.com.cipres.model.Viaje;


public interface IDetaViajesDAO extends IGenericDAO<DetaViajes> {

	DetaViajes getByRemito(Integer transacNr);

	Viaje getViaje(Integer viajeNr);
	
	
	
}
