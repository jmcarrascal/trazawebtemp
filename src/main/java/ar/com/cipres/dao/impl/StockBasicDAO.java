package ar.com.cipres.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IStockBasicDAO;
import ar.com.cipres.model.Stock;
import ar.com.cipres.model.StockBasic;

@Repository
public class StockBasicDAO extends GenericDAO<StockBasic> implements IStockBasicDAO {

	public StockBasicDAO() {
		super(StockBasic.class);
	}

	public StockBasic getStockByGtin(String gtin) {
		// TODO Auto-generated method stub
		StockBasic stock = null;

		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select s from StockBasic s where s.gtin =" + "'" + gtin + "'");
			List<StockBasic> stockList = query.list();
			if (stockList != null && stockList.size() > 0) {
				stock = stockList.get(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return stock;
		}
	}

	public List<StockBasic> get_stock_by_descrip(String name) {
		List<StockBasic> result = new ArrayList<StockBasic>();
		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select s from StockBasic s where s.descripcion like '" + name.toUpperCase() + "%'");

			result = (List<StockBasic>) query.list();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return result;
		}
	}

	protected String[] getDefaultOrderCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockBasic get_stock_by_cod_barras(String cod_barras) {
		// TODO Auto-generated method stub
		StockBasic stock = null;

		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select s from StockBasic s where s.codBarras =" + "'" + cod_barras + "'");
			List<StockBasic> stockList = query.list();
			if (stockList != null && stockList.size() > 0) {
				stock = stockList.get(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return stock;
		}
	}

	public List<Object> get_stock_by_cod_barras_all(String cod_barras) {
		List<Object> list = new ArrayList<Object>();

		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select s from StockBasic s where s.codBarras =" + "'" + cod_barras + "'");
			list = query.list();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return list;
		}
	}

	public List<Object> get_stock_by_gtin_all(String gtin) {
		List<Object> list = new ArrayList<Object>();


		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select s from StockBasic s where s.gtin =" + "'" + gtin + "'");
			list = query.list();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return list;
		}
	}

}
