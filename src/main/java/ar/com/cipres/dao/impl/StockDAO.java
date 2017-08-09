package ar.com.cipres.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IStockDAO;
import ar.com.cipres.model.Stock;

@Repository
public class StockDAO extends GenericDAO<Stock> implements IStockDAO {
	
	
	public StockDAO() {
		super(Stock.class);
	}

	public Integer getMaxTrazabi() {
		Integer max = 0;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
				      createQuery("select MAX(t.nr) from Trazabi t");
				    
			max=(Integer) query.list().get(0);
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return max;
			}
	}

	
	public Stock getStockByCodBarra(String codBarra) {
		Stock stock = null;
		try{								
			Query query =sessionFactory.
				      getCurrentSession().
				      createQuery("select s from Stock s where s.codBarras = '"+ codBarra + "'");
			Stock auxResult = (Stock) query.uniqueResult();    
			if (auxResult != null) {
				stock = auxResult;
			}
		}catch(Exception e){
			stock = null;
			System.out.println(e.getMessage());
		}
		return stock;
	}
	
	public Stock getStockByGtin(String gtin) {
		Stock stock = null;
			
		try{								
			Query query =sessionFactory.
				      getCurrentSession().
				      createQuery("select s from Stock s where s.gtin =" + "'"+ gtin+"'");
			List<Stock> stockList =query.list();    
			if (stockList!= null && stockList.size() > 0){
				stock = stockList.get(0);
			}									
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return stock;
			}
	}

	
	public List<Stock> get_stock_by_descrip(String name) {
		List<Stock> result = new ArrayList<Stock>();
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
				      createQuery("select s from Stock s where s.descripcion like '" + name.toUpperCase() + "%'");
				    
			result =(List<Stock>) query.list();
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return result;
			}
	}
	
	protected String[] getDefaultOrderCriteria() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Stock findArticuloById(String id){
		Stock articulo = new Stock();

		try {
			String queryStr = "select a from Stock a where a.id = '" + id + "'";
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			articulo = (Stock)query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		return articulo;	
	}
	
	public List<Object> findArticulo(String nombreNumero, int familiaId, int subFamiliaId, int pageNumber,
			int pageSize) {
		List<Object> articuloList = new ArrayList<Object>();

		try {
			Boolean first = true;
			String queryStr = "select a from Stock a ";
			if (nombreNumero != "") {
				queryStr += (first ? "where " : "and ");
				queryStr += "(a.descripcion like '%" + nombreNumero + "%' or a.id like '%" + nombreNumero + "%') ";
				first = false;
			}			
			if (familiaId != 0) {
				queryStr += (first ? "where " : "and ");
				queryStr += "a.familia = " + familiaId + " ";
				first = false;
			}
			if (subFamiliaId != 0) {
				queryStr += (first ? "where " : "and ");
				queryStr += "a.subFamilia = " + subFamiliaId + " ";
				first = false;
			}
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			if (pageSize > 0) {
				query.setFirstResult((pageNumber * pageSize) - pageSize);
				query.setMaxResults(pageSize);
			}
			articuloList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		return articuloList;
	}
	
	public List<Object[]> findArticulosUsuariosExistencias(String search, int idUsuario, int pageNumber,
			int pageSize) {
		List<Object[]> articuloList = new ArrayList<Object[]>();

		try {
			Boolean first = true;
//			String queryStr = "select DISTINCT a from Stock a, ExiArt e, UsuariosExistencias u where a.nrArt = e.nrArt AND e.id.clave = u.nrExistencia "
//					+ "AND u.idUsuario = " + idUsuario;
			String queryStr =  "select DISTINCT a.Descripcion, a.clave, a.NrArt, a.Precio1 "
							+ "FROM comunsql.Stock a " 
							+ "INNER JOIN comunsql.ExiArt e ON a.nrArt = e.nrArt "
							+ "INNER JOIN trazaweb.usuariosExistencias u ON e.NrExist = u.nrExistencia "
							+ "WHERE u.idUsuario = " + idUsuario;
							if(search != "")
								queryStr += " AND a.Descripcion LIKE '%" + search + "%'";
							queryStr += " GROUP BY a.clave ORDER BY SUM(Cantidad1) DESC";

			Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
			if (pageSize > 0) {
				query.setFirstResult((pageNumber * pageSize) - pageSize);
				query.setMaxResults(pageSize);
			}
			articuloList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		return articuloList;
	}

	public Long findArticulosUsuariosExistenciasCount(String search, int idUsuario, int pageNumber,
			int pageSize) {
		Long count = 0l;
		try {
			String queryStr =  "SELECT COUNT(*) FROM(select DISTINCT a.Descripcion, a.clave, a.NrArt, a.Precio1 "
					+ "FROM comunsql.Stock a " 
					+ "INNER JOIN comunsql.ExiArt e ON a.nrArt = e.nrArt "
					+ "INNER JOIN trazaweb.usuariosExistencias u ON e.NrExist = u.nrExistencia "
					+ "WHERE u.idUsuario = " + idUsuario;
					if(search != "")
						queryStr += " AND a.Descripcion LIKE '%" + search + "%'";
					queryStr += " GROUP BY a.clave ORDER BY SUM(Cantidad1) DESC)a";

			Query query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
			count = new BigInteger(query.list().get(0).toString()).longValue();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return count;
		}
	}
	
	public Long findArticuloCount(String nombreNumero, int pageNumber,
			int pageSize) {
		Long count = 0l;
		try {
			String queryStr = "select count(a) from Stock a ";

			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			count = (Long) query.list().get(0);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return count;
		}
	}
	
	

	
	
}
 