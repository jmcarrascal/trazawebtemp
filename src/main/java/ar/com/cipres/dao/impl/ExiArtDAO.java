package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IExiArtDAO;
import ar.com.cipres.model.ExiArt;
import ar.com.cipres.model.Transac;

@Repository
public class ExiArtDAO extends GenericDAO<ExiArt> implements IExiArtDAO {

	private String[] defaultOrderCriteria = { "nrArt" };

	public ExiArtDAO() {
		super(ExiArt.class);
	}

	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}

	public Double getStockReservadoPVta(String articuloId) {
		Double count = 0d;

		try {
			String queryStr = "select sum(Cant1 - Cant1Entregado)"
					+ " from Transac t, Items i where t.transacNr = i.id.transacNr and i.stock.id = '" + articuloId
					+ "' and Cant1Entregado < Cant1 and t.destino = -1 and t.tipoComprob.nr = 8";

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			count = (Double) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (count == null)
			count = 0d;
		return count;
	}
}
