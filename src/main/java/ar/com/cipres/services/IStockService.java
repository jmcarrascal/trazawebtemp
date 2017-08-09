package ar.com.cipres.services;



import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import ar.com.cipres.model.ArtDespa;
import ar.com.cipres.model.ChartBasic;
import ar.com.cipres.model.DataSendMercaderia;
import ar.com.cipres.model.Existencias;
import ar.com.cipres.model.Gente;
import ar.com.cipres.model.JsonResultIngresoMercaderia;
import ar.com.cipres.model.ObjectPaging;
import ar.com.cipres.model.Stock;



/**
 * 
 * @author Juan Manuel Carrascal
 */
public interface IStockService {
	
	public JsonResultIngresoMercaderia ingresoMercaderia(DataSendMercaderia dataSendMercaderia);

	public List<ArtDespa> get_artdespa_art_lote(String art, String lote);

	public ChartBasic getIndicadoresIngresos(Integer anio);

	public ChartBasic getIndicadoresIngresosVencidos(Integer anio);

	public ChartBasic getIndicadoresIngresosEgresos(Integer anio);

	public ChartBasic getIndicadoresIngresosCuarentena(Integer anio);

	public ChartBasic getIndicadoresIngresosOperador(Integer anio, List<Integer> operListInt);
	
	public ChartBasic getIndicadoresIngresosSaldo(Integer anio,List<Integer> operListInt);

	public ChartBasic getIndicadoresIngresoPorExistencia(Integer anio, List<Integer> exiListInt, Boolean joinExistencias);

	public List<Existencias> getExistenciaAll();

	public ChartBasic getIndicadoresIngresoPorExistenciaPorMes(Integer anio, List<Integer> exiListInt, Integer mes);

	public ObjectPaging findArticulo(JSONObject jsonObject);

	public Stock findArticuloCalculo(Stock stock, Existencias exiDefault, Gente gente, String formulaPrecios);
	
	public Object findArticuloByKey(String id);

	public List<Object> getArticuloMadreAll();

	public void saveArticulo(Stock stock) throws Exception;
	
	public void updateAlfaBeta() throws Exception;

	public ChartBasic getIndicadoresPorcentajeFiltroDisponible(Integer anio, Integer indiceFiltro);

	public ObjectPaging findArticulosUsuariosExistencias(JSONObject jsonObject);
	
	
	
	
}
