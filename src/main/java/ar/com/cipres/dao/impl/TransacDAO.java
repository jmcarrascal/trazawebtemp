package ar.com.cipres.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IDomiciliosDAO;
import ar.com.cipres.dao.ITransacDAO;
import ar.com.cipres.model.Domicilios;
import ar.com.cipres.model.ObjDescripCant;
import ar.com.cipres.model.Transac;
import ar.com.cipres.model.report.ExportTransacAfiliados;

@Repository
public class TransacDAO extends GenericDAO<Transac> implements ITransacDAO {

	@Autowired
	private IDomiciliosDAO domiciliosDAO;

	public TransacDAO() {
		super(Transac.class);
	}

	protected String[] getDefaultOrderCriteria() {
		// TODO
		return null;
	}

	public List<Transac> getCuentaCorrienteVenta(Integer[] listTipoComprVentas, Integer genteNr) {
		List<Transac> transacList = null;
		Boolean primerReg = true;
		String whereTipoComprSql = "";
		for (Integer tipoCompr : listTipoComprVentas) {
			if (primerReg) {
				whereTipoComprSql = "(t.tipoComprob = " + tipoCompr;
				primerReg = false;
			} else {
				whereTipoComprSql = whereTipoComprSql + " or t.tipoComprob = " + tipoCompr;
			}

		}
		whereTipoComprSql = whereTipoComprSql + ")";
		try {

			String sql = "select t from Transac t where t.gente.id = " + genteNr + " and t.saldo <> 0 and "
					+ whereTipoComprSql + " order by t.fecha, t.transacNr asc";
			Query query = sessionFactory.getCurrentSession().createQuery(sql);

			transacList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
		}
		return transacList;

	}

	public List<Object> getAgendadoTransacList(int genteId, String texto, int tipoComprobante, int Estado,
			int pageNumber, int pageSize) {
		List<Object> transacList = null;

		try {
			String queryStr = "";
			if (Estado == 1) {
				queryStr += "select distinct t from Transac t, Items i where t.transacNr = i.id.transacNr and Cant1Entregado < Cant1 and t.destino = -1"
						+ " and t.gente.id = " + genteId + " and (t.observaciones like '%" + texto + "%') ";
			} else if (Estado == 2) {
				queryStr += "select distinct t from Transac t, Items i where t.transacNr = i.id.transacNr and Cant1Entregado = Cant1 and t.destino = -1"
						+ " and t.gente.id = " + genteId + " and (t.observaciones like '%" + texto + "%') ";
			} else {
				queryStr += "select t from Transac t where t.gente.id = " + genteId + " and (t.observaciones like '%"
						+ texto + "%') ";
			}
			if (tipoComprobante != 0) {
				queryStr += "and t.tipoComprob.nr = " + tipoComprobante;
			}
			queryStr += " order by t.transacNr desc";
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			query.setFirstResult((pageNumber * pageSize) - pageSize);
			query.setMaxResults(pageSize);
			transacList = query.list();
			for (Object object : transacList) {
				Domicilios domicilioPrincipal = domiciliosDAO
						.getDomicilioPrincipal(((Transac) object).getGente().getId());
				Domicilios domicilioEntrega = domiciliosDAO.getByPrimaryKey(((Transac) object).getNrDomicilioEnt());
				((Transac) object).setDomicilioPrincipal(domicilioPrincipal);
				((Transac) object).setDomicilioEntrega(domicilioEntrega);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return transacList;
	}

	public Long getAgendadoTransacListCount(int genteId, String texto, int tipoComprobante, int Estado, int pageNumber,
			int pageSize) {
		Long count = 0l;

		try {
			String queryStr = "";

			if (Estado == 1) {
				queryStr = "select count(distinct t) from Transac t, Items i where t.transacNr = i.id.transacNr and Cant1Entregado < Cant1 and t.destino = -1"
						+ " and t.gente.id = " + genteId + " and (t.observaciones like '%" + texto + "%') ";
			} else if (Estado == 2) {
				queryStr = "select count(distinct t) from Transac t, Items i where t.transacNr = i.id.transacNr and Cant1Entregado = Cant1 and t.destino = -1"
						+ " and t.gente.id = " + genteId + " and (t.observaciones like '%" + texto + "%') ";
			} else {
				queryStr = "select count(t) from Transac t where t.gente.id = " + genteId
						+ " and (t.observaciones like '%" + texto + "%') ";
			}
			if (tipoComprobante != 0) {
				queryStr += "and t.tipoComprob.nr = " + tipoComprobante;
			}
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			count = (Long) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}

	public List<Object> getTransacAfiliados(int genteId, Date fechaDesde, Date fechaHasta) {
		List<Object> exportTransacAfiliadosList = new ArrayList<Object>();

		try {

			String queryStr = "select new ar.com.cipres.model.report.ExportTransacAfiliados(t.tipoComprob.nr, t.letra, t.prefijo, t.nrComprob, t.fecha, t.netoGrav,t.netoNoGrav,"
					+ " t.alicuotaIva, t.retIb, d.nrAfiliado, d.apellidoAfiliado, d.nombreAfiliado, t.transacNr) "
					+ " from Transac t, Domicilios d where "
					+ "d.id = t.benef and t.fecha >= :fechaDesde and t.fecha <= :fechaHasta and t.gente.id = :genteId and t.tipoComprob.nr = 1";

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr).setDate("fechaDesde", fechaDesde)
					.setDate("fechaHasta", fechaHasta).setInteger("genteId", genteId);

			exportTransacAfiliadosList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return exportTransacAfiliadosList;
	}

	public List<Object> getRemitosAfiliados(int genteId, Date fechaDesde, Date fechaHasta) {
		List<Object> exportTransacAfiliadosList = new ArrayList<Object>();

		try {

			String queryStr = "select new ar.com.cipres.model.report.ExportTransacAfiliados(t.tipoComprob.nr, t.letra, t.prefijo, t.nrComprob, t.fecha, t.netoGrav,t.netoNoGrav,"
					+ " t.alicuotaIva, t.retIb, d.nrAfiliado, d.apellidoAfiliado, d.nombreAfiliado, t.transacNr) "
					+ " from Transac t, Domicilios d where "
					+ "d.id = t.nrDomicilioEnt and t.fecha >= :fechaDesde and t.fecha <= :fechaHasta and t.gente.id = :genteId and t.tipoComprob.nr = 3";

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr).setDate("fechaDesde", fechaDesde)
					.setDate("fechaHasta", fechaHasta).setInteger("genteId", genteId);

			exportTransacAfiliadosList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return exportTransacAfiliadosList;
	}

	public List<ExportTransacAfiliados> getTransacAfiliadosItems(int genteId, Date fechaDesde, Date fechaHasta,
			String list, Integer tipoComprob) {
		List<ExportTransacAfiliados> exportTransacAfiliadosList = new ArrayList<ExportTransacAfiliados>();

		try {

			String queryStr = "select new ar.com.cipres.model.report.ExportTransacAfiliados(t.tipoComprob.nr, t.letra, t.prefijo, t.nrComprob, t.fecha, t.netoGrav,t.netoNoGrav, t.alicuotaIva,"
					+ " t.retIb, d.nrAfiliado, d.apellidoAfiliado, d.nombreAfiliado, t.transacNr, i.cant1, i.precio, s.troquel, s.impuestos.id, t.ordenCompra, i.descrip, i.stock.id, i.nrCompInt) "
					+ " from Transac t, Domicilios d , Items i, Stock s where i.stock.id = s.id and i.id.transacNr = t.transacNr and "
					+ "d.id = t.benef and t.fecha >= :fechaDesde and t.fecha <= :fechaHasta and t.gente.id = :genteId and t.tipoComprob.nr = :tipoComprob and i.id.transacNr in "
					+ list;

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr).setDate("fechaDesde", fechaDesde)
					.setDate("fechaHasta", fechaHasta).setInteger("genteId", genteId)
					.setInteger("tipoComprob", tipoComprob);

			exportTransacAfiliadosList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return exportTransacAfiliadosList;
	}

	public List<Transac> getPedidoPendienteRemitir(java.util.Date currentDateTime0, String prefijo) {
		List<Transac> transacList = null;

		try {
			String queryStr = "select distinct t from Transac t, Items i where i.id.transacNr = t.transacNr and t.tipoComprob.nr = 8 and t.fechaEntrega <= :fechaEntrega and t.prefijo = :prefijo and ((i.cant1 - i.cant1entregado) > 0 ) and (t.destino = -1 or t.destino = -4)";

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr)
					.setDate("fechaEntrega", currentDateTime0).setString("prefijo", prefijo);
			transacList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return transacList;
	}

	public Long getPedidoPendienteRemitirSize(java.util.Date currentDateTime0, String prefijo) {
		Long result = 0l;

		try {
			String queryStr = "select count(distinct t.transacNr) from Transac t, Items i where i.id.transacNr = t.transacNr and t.tipoComprob.nr = 8 and t.fechaEntrega <= :fechaEntrega and t.prefijo = :prefijo and ((i.cant1 - i.cant1entregado) > 0 ) and (t.destino = -1 or t.destino = -4)";

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr)
					.setDate("fechaEntrega", currentDateTime0).setString("prefijo", prefijo);
			result = (Long) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;
	}

	public List<ObjDescripCant> getPedidoPendienteViajesSize() {
		List<ObjDescripCant> result = new ArrayList<ObjDescripCant>();

		try {
			String queryStr = "	select new ar.com.cipres.model.ObjDescripCant(count(tr_general.transacNr), lugares.descripcion) from Transac tr_general, Domicilios domi, Lugares lugares where tr_general.transacNr in("
					+ "select distinct(tr.transacNr) from Transac tr, Tracking trak, Transac trPed, Domicilios d where tr.transacNr = trak.madre and trPed.transacNr = trak.hijo and "
					+ "trPed.tipoComprob.nr = 8 and (tr.destino != -2 and tr.destino != -3) and tr.nrDomicilioEnt = d.id  and tr.tipoComprob.nr = 3 and tr.transacNr not in ( "
					+ "select deta.transacRemito from DetaViajes deta where deta.transacRemito in( "
					+ "select distinct(tr.transacNr) from Transac tr, Tracking trak, Transac trPed, Domicilios d where tr.transacNr = trak.madre and trPed.transacNr = trak.hijo and "
					+ "trPed.tipoComprob.nr = 8 and (tr.destino != -2 and tr.destino != -3) and tr.nrDomicilioEnt = d.id  and tr.tipoComprob.nr = 3) )) and tr_general.nrDomicilioEnt = domi.id and domi.otroLugar = lugares.id "
					+ "and domi.otroLugar <> 0 group by lugares.descripcion";

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			result = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;
	}
	/*
	 * String queryStr =
	 * "	select count(tr_general.transacNr) from Transac tr_general where tr_general.transacNr in("
	 * +
	 * "select distinct(tr.transacNr) from Transac tr, Tracking trak, Transac trPed, Domicilios d where tr.transacNr = trak.madre and trPed.transacNr = trak.hijo and "
	 * +
	 * "trPed.tipoComprob.nr = 8 and (tr.destino != -2 and tr.destino != -3) and tr.nrDomicilioEnt = d.id  and tr.tipoComprob.nr = 3 and tr.transacNr not in ( "
	 * +
	 * "select deta.transacRemito from DetaViajes deta where deta.transacRemito in( "
	 * +
	 * "select distinct(tr.transacNr) from Transac tr, Tracking trak, Transac trPed, Domicilios d where tr.transacNr = trak.madre and trPed.transacNr = trak.hijo and "
	 * +
	 * "trPed.tipoComprob.nr = 8 and (tr.destino != -2 and tr.destino != -3) and tr.nrDomicilioEnt = d.id  and tr.tipoComprob.nr = 3) ))"
	 * ;
	 */

	/*
	 * select * from Transac where TransacNr in( select distinct(tr.TransacNr)
	 * from Transac tr, Tracking trak, Transac trPed, ComunSQL.dbo.Domicilios d
	 * where tr.TransacNr = trak.Madre and trPed.TransacNr = trak.Hijo and
	 * trPed.TipoComprob =8 and trPed.FechaEntrega >= '01-08-2016' and
	 * (tr.Destino != -2 and tr.Destino != -3) and tr.NrDomicilioEnt = d.DomiNr
	 * and trPed.FechaEntrega < '10-08-2016' and tr.TipoComprob = 3 and
	 * tr.TransacNr not in ( select TransacRemito from DetaViajes where
	 * TransacRemito in( select distinct(tr.TransacNr) from Transac tr, Tracking
	 * trak, Transac trPed, ComunSQL.dbo.Domicilios d where tr.TransacNr =
	 * trak.Madre and trPed.TransacNr = trak.Hijo and trPed.TipoComprob =8 and
	 * trPed.FechaEntrega >= '01-08-2016' and (tr.Destino != -2 and tr.Destino
	 * != -3) and tr.NrDomicilioEnt = d.DomiNr and trPed.FechaEntrega <=
	 * '10-08-2016' and tr.TipoComprob = 3) ))
	 */

	public Long getPedidoPendienteRemitirPickSize(java.util.Date currentDateTime0, String prefijo) {
		Long result = 0l;

		try {
			String queryStr = "select count(distinct t.transacNr) from Transac t, Items i where t.tranReciboFact = 0 and i.id.transacNr = t.transacNr and t.tipoComprob.nr = 8 and t.fechaEntrega <= :fechaEntrega and t.prefijo = :prefijo and ((i.cant1 - i.cant1entregado) > 0 ) and (t.destino = -1 or t.destino = -4)";

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr)
					.setDate("fechaEntrega", currentDateTime0).setString("prefijo", prefijo);
			result = (Long) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;
	}

	public List<Object> getArticuloTransacList(String articuloId, String texto, int tipoComprobante, int pageNumber,
			int pageSize) {
		List<Object> transacList = null;

		try {
			String queryStr = "select tr from Transac tr where tr.transacNr in (select distinct t.transacNr from Transac t, Items i where t.transacNr = i.id.transacNr and i.stock.id = '"
					+ articuloId + "' and (t.observaciones like '%" + texto + "%')";
			if (tipoComprobante != 0) {
				queryStr += "and t.tipoComprob.nr = " + tipoComprobante;
			}
			queryStr += " ) order by tr.transacNr desc";
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			query.setFirstResult((pageNumber * pageSize) - pageSize);
			query.setMaxResults(pageSize);
			transacList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return transacList;
	}

	public Long getArticuloTransacListCount(String articuloId, String texto, int tipoComprobante, int pageNumber,
			int pageSize) {
		Long count = 0l;

		try {
			String queryStr = "select count(distinct t.transacNr) from Transac t, Items i where t.transacNr = i.id.transacNr and i.stock.id = '"
					+ articuloId + "' and (t.observaciones like '%" + texto + "%') ";
			if (tipoComprobante != 0) {
				queryStr += "and t.tipoComprob.nr = " + tipoComprobante;
			}
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			count = (Long) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}

	public List<Object> getDomiciliosTransacList(int domicilioId, String texto, int tipoComprobante, int pageNumber,
			int pageSize) {
		List<Object> transacList = null;

		try {
			String queryStr = "select t from Transac t where t.benef = " + domicilioId;
			if (!texto.equals(""))
				queryStr += " and t.observaciones like '%" + texto + "%' ";
			if (tipoComprobante != 0) {
				queryStr += " and t.tipoComprob.nr = " + tipoComprobante;
			}
			queryStr += " order by t.fecha desc)";
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			query.setFirstResult((pageNumber * pageSize) - pageSize);
			query.setMaxResults(pageSize);
			transacList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return transacList;
	}

	public Long getDomiciliosTransacListCount(int domicilioId, String texto, int tipoComprobante, int pageNumber,
			int pageSize) {
		Long count = 0l;

		try {
			String queryStr = "select count(t.transacNr) from Transac t where t.benef = " + domicilioId;
			if (!texto.equals(""))
				queryStr += " and t.observaciones like '%" + texto + "%' ";
			if (tipoComprobante != 0) {
				queryStr += "and t.tipoComprob.nr = " + tipoComprobante;
			}
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			count = (Long) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}

	public Transac getByTransacAnterior(Integer idMadre) {
		Transac transac = null;
		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select t from Transac t where t.tranFactCred =" + idMadre);
			List<Transac> transacList = query.list();
			if (transacList != null && transacList.size() > 0) {
				transac = transacList.get(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return transac;
	}

	public void updateFechaEntrega(String nrRemito, Integer tipoComprob, java.util.Date fechaEntrega) {
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(
					"update Transac t set t.fechaEntrega = :fechaEntrega where t.nrComprob = :nrRemito and t.tipoComprob.nr = :tipoComprob and t.fechaEntrega is null");
			query.setDate("fechaEntrega", fechaEntrega);
			query.setString("nrRemito", nrRemito);
			query.setInteger("tipoComprob", tipoComprob);
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateFechaTransac(Integer transacNr, Integer tipoComprob, java.util.Date fechaEntrega) {
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(
					"update Transac t set t.fechaEntrega = :fechaEntrega where t.transacNr = :transacNr and t.tipoComprob.nr = :tipoComprob and t.fechaEntrega is null");
			query.setDate("fechaEntrega", fechaEntrega);
			query.setInteger("transacNr", transacNr);
			query.setInteger("tipoComprob", tipoComprob);
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Transac> getTransacSinEstadoViajes(Integer transporte, java.util.Date fechaDesde) {
		List<Transac> transacList = null;
		try {
			String queryStr = "select t from Transac t, DetaViajes d, Viaje v "
					+ "where d.transacRemito = t.transacNr and d.viajeNr = v.id and v.choferNr = :transporte and v.fecha >= :fechaDesde and t.fechaEntrega is null and t.tipoComprob.nr = 3";

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr).setInteger("transporte", transporte)
					.setDate("fechaDesde", fechaDesde);
			transacList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return transacList;
	}

	@Override
	public boolean ordenCompraRepetida(String ordenCompra, Integer idGente) {
		boolean repetida = false;

		try {
			String queryStr = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END " + "FROM Transac t "
					+ "WHERE t.ordenCompra = '" + ordenCompra + "' " + "AND t.gente.id = " + idGente;
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			repetida = ((Integer) query.list().get(0) != 0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return repetida;
	}

}
