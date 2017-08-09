package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IDetaViajesDAO;
import ar.com.cipres.model.DetaViajes;
import ar.com.cipres.model.Stock;
import ar.com.cipres.model.Viaje;

@Repository
public class DetaViajesDAO extends GenericDAO<DetaViajes> implements IDetaViajesDAO {

	private String[] defaultOrderCriteria = { "id" };

	public DetaViajesDAO() {
		super(DetaViajes.class);
	}

	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}

	public DetaViajes getByRemito(Integer transacNr) {
		DetaViajes detaViajes = null;

		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select dv from DetaViajes dv where dv.transacRemito = :transacNr").setInteger("transacNr", transacNr);
			List<DetaViajes> detaViajesList = query.list();
			if (detaViajesList != null && detaViajesList.size() > 0) {
				detaViajes = detaViajesList.get(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return detaViajes;
		}
	}

	public Viaje getViaje(Integer viajeNr) {
		Viaje viaje = null;

		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select v from Viaje v where v.id =" + viajeNr);
			List<Viaje> viajeList = query.list();
			if (viajeList != null && viajeList.size() > 0) {
				viaje = viajeList.get(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return viaje;
		}
	}

}
