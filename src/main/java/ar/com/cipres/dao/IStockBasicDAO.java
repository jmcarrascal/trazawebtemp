package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.StockBasic;


public interface IStockBasicDAO extends IGenericDAO<StockBasic> {
	
	public StockBasic getStockByGtin(String gtin);
	
	public List<StockBasic> get_stock_by_descrip(String name);

	public StockBasic get_stock_by_cod_barras(String cod_barras);

	public List<Object> get_stock_by_cod_barras_all(String cod_barras);

	public List<Object> get_stock_by_gtin_all(String gtin);

	
	
}
