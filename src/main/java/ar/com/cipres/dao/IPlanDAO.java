package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Plan;

public interface IPlanDAO extends IGenericDAO<Plan> {
	public List<Plan> getPlanes();
}
