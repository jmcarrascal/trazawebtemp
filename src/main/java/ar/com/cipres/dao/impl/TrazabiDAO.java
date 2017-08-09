package ar.com.cipres.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.ITrazabiDAO;
import ar.com.cipres.model.FilterTrazabi;
import ar.com.cipres.model.Trazabi;

@Repository
public class TrazabiDAO extends GenericDAO<Trazabi> implements ITrazabiDAO {

	public TrazabiDAO() {
		super(Trazabi.class);
	}

	protected String[] getDefaultOrderCriteria() {
		// TODO
		return null;
	}

	public Integer getMaxTrazabi() {
		Integer max = 0;
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(
					"select MAX(t.nr) from Trazabi t");

			max = (Integer) query.list().get(0);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return max;
		}
	}

	public Integer getNrCaja(FilterTrazabi filterTrazabi) {
		Integer nr = 0;
		String queryParcial = "";
		if (filterTrazabi == null || filterTrazabi.getSeries() == null
				|| filterTrazabi.getSeries().size() == 0) {
			return 0;
		} else {
			for (String serie : filterTrazabi.getSeries()) {
				if (queryParcial.equals("")) {
					queryParcial = " serieGtin = '" + serie +"' ";
				} else {
					queryParcial = queryParcial + " or serieGtin = '" + serie +"' ";
				}
			}
		}
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(
					"select t.nr from Trazabi t where respuestaSalida is null and gtin = '"
							+ filterTrazabi.getGtin() + "' and (" + queryParcial +" )");

			nr = (Integer) query.list().get(0);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return nr;
		}
	}

	
	public List<Trazabi> getTransacSalidaMEdicamento(Integer transacNr, String claveMedicamento) {
		List<Trazabi> trazabiList = new ArrayList<Trazabi>();

		try {
			
			Query query = sessionFactory.getCurrentSession().createQuery(
					"select t from Trazabi t where t.nrTransacSalida = :transacNr and t.articulo = :claveMedicamento");
			query.setInteger("transacNr", transacNr)
					.setString("claveMedicamento", claveMedicamento);
			trazabiList = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return trazabiList;

	}

}
