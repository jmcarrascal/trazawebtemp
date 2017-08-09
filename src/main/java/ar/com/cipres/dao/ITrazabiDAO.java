package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.FilterTrazabi;
import ar.com.cipres.model.Trazabi;


public interface ITrazabiDAO extends IGenericDAO<Trazabi> {
	
	public Integer getMaxTrazabi();
	
	public Integer getNrCaja(FilterTrazabi filterTrazabi);

	public List<Trazabi> getTransacSalidaMEdicamento(Integer transacNr, String claveMedicamento);
	
	
}
