package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Stock;


public interface IStockDAO extends IGenericDAO<Stock> {
	
	public Stock getStockByGtin(String gtin);

	public Stock getStockByCodBarra(String codBarra);
	
	public Stock findArticuloById(String id);
	
	public List<Stock> get_stock_by_descrip(String name);

	public List<Object> findArticulo(String nombreNumero, int familiaId, int subFamiliaId, int pageNumber, int pageSize);
	
	public Long findArticuloCount(String nombreNumero, int familiaId, int subFamiliaId) ;
	
	public List<Object[]> findArticulosUsuariosExistencias(String search, int idUsuario, int pageNumber, int pageSize);
	
	public Long findArticulosUsuariosExistenciasCount(String search, int idUsuario, int pageNumber, int pageSize);
}
