package ar.com.cipres.dao.impl;

import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.ISubClientesDAO;
import ar.com.cipres.model.SubClientes;

@Repository
public class SubClientesDAO  extends GenericDAO<SubClientes> implements ISubClientesDAO {

	@Override
	protected String[] getDefaultOrderCriteria() {
		// TODO Auto-generated method stub
		return null;
	}
}
