package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IGenteDAO;
import ar.com.cipres.model.Gente;
import ar.com.cipres.util.ObjCodigoDescrip;

@Repository
public class GenteDAO extends GenericDAO<Gente> implements IGenteDAO {

	private String[] defaultOrderCriteria = { "razonSocial" };

	public GenteDAO() {
		super(Gente.class);
	}

	@Override
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}

	public List<Object> findAgendado(ObjCodigoDescrip objCodigoDescrip) {		
		List<Object> genteList = null;

		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select g from Gente g where g.razonSocial like " + "'%"
							+ objCodigoDescrip.getDescrip() + "%'");
			genteList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return genteList;
		}
	}

	public List<Object> findAgendado(String razonSocial, String cuit, int relacionId, int pageNumber, int pageSize) {		
		List<Object> genteList = null;

		try {
			Boolean first = true;
			String queryStr = "select g from Gente g ";
			if (razonSocial != "") {
				queryStr += (first ? "where " : "and ");
				queryStr += "(g.razonSocial like '%" + razonSocial + "%' or g.id like '%" + razonSocial + "%') ";
				first = false;
			}
			if (cuit != "") {
				queryStr += (first ? "where " : "and ");
				queryStr += "g.cuit like '%" + cuit + "%' ";
				first = false;
			}
			if (relacionId != 0) {
				queryStr += (first ? "where " : "and ");
				queryStr += "g.relacionId = '" + relacionId + "' ";
				first = false;
			}
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			query.setFirstResult((pageNumber * pageSize) - pageSize);
			query.setMaxResults(pageSize);
			genteList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		return genteList;
	}
	
	public List<Object> findPersons(String data, int relacionId, int pageNumber, int pageSize) {		
		List<Object> genteList = null;

		try {
			Boolean first = true;
			String queryStr = "select g from Gente g ";
			if (data != "") {
				queryStr += (first ? "where " : "and ");
				queryStr += "(g.razonSocial like '%" + data + "%' or g.id like '%" + data + "%' OR g.cuit like '%" + data + "%') ";
				first = false;
			}
			if (relacionId != 0) {
				queryStr += (first ? "where " : "and ");
				queryStr += "g.relacionId = '" + relacionId + "' ";
				first = false;
			}
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			query.setFirstResult((pageNumber * pageSize) - pageSize);
			query.setMaxResults(pageSize);
			genteList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		return genteList;
	}
	
	public Long findPersonsCount(String data, int relacionId) {
		Long count = 0l;
		
		try {
			Boolean first = true;
			String queryStr = "select count(g) from Gente g ";
			if (data != "") {
				queryStr += (first ? "where " : "and ");
				queryStr += "(g.razonSocial like '%" + data + "%' or g.id like '%" + data + "%' OR g.cuit like '%" + data + "%') ";
				first = false;
			}
			if (relacionId != 0) {
				queryStr += (first ? "where " : "and ");
				queryStr += "g.relacionId = '" + relacionId + "' ";
				first = false;
			}
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			count = (Long) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		return count;
	}
	
	public List<Object> findAgendado(String searchString) {		
		List<Object> genteList = null;

		try {
			String queryStr = "select g from Gente g ";
			if (searchString != "") {
				queryStr += "where";
				queryStr += "(g.razonSocial like '%" + searchString + "%' or g.id like '%" + searchString + "%') ";
				queryStr += "or ";
				queryStr += "g.cuit like '%" + searchString + "%' ";
				queryStr += "or ";
				queryStr += "g.relacionId = '" + searchString + "' ";
			}
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			genteList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		return genteList;
	}

	public Long findAgendadoCount(String razonSocial, String cuit, int relacionId) {
		Long count = 0l;
		
		try {
			Boolean first = true;
			String queryStr = "select count(g) from Gente g ";
			if (razonSocial != "") {
				queryStr += (first ? "where " : "and ");
				queryStr += "(g.razonSocial like '%" + razonSocial + "%' or g.id like '%" + razonSocial + "%') ";
				first = false;
			}
			if (cuit != "") {
				queryStr += (first ? "where " : "and ");
				queryStr += "g.cuit like '%" + cuit + "%' ";
				first = false;
			}
			if (relacionId != 0) {
				queryStr += (first ? "where " : "and ");
				queryStr += "g.relacionId = '" + relacionId + "' ";
				first = false;
			}
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			count = (Long) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		return count;
	}

	public List<Object> findAgendadoShort(ObjCodigoDescrip objCodigoDescrip) {		
		List<Object> genteList = null;

		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select g from GenteShort g where g.razonSocial like " + "'%"
							+ objCodigoDescrip.getDescrip() + "%'");
			genteList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return genteList;
		}
	}

	public List<Object> getAgendadoProveedorAll() {
		List<Object> genteList = null;
		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select g from GenteShort g where g.proveedor = -1");
			genteList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return genteList;
		}
	}
	
	
	public List<Object> getObraSocialAll() {
		List<Object> genteList = null;
		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select g from GenteShort g where g.nrObraSocial is not null and  g.nrObraSocial <> 0");
			genteList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return genteList;
		}
	}
	

}
