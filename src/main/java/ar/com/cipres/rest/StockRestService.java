package ar.com.cipres.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.dao.IExistenciasDAO;
import ar.com.cipres.dao.IExistenciasIndicadoresDAO;
import ar.com.cipres.dao.IParametrizacionDAO;
import ar.com.cipres.dao.IStockBasicDAO;
import ar.com.cipres.model.ArtDespa;
import ar.com.cipres.model.ChartBasic;
import ar.com.cipres.model.DataSendMercaderia;
import ar.com.cipres.model.Existencias;
import ar.com.cipres.model.ExistenciasIndicadores;
import ar.com.cipres.model.JsonResultIngresoMercaderia;
import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.model.StockBasic;
import ar.com.cipres.services.IStockService;
import ar.com.cipres.util.Constants;
import ar.com.cipres.util.FormatUtil;

@Component
@Path("/stock")
public class StockRestService {

	@Autowired
	private IParametrizacionDAO parametrizacionDAO;

	@Autowired
	private IStockBasicDAO stockBasicDAO;

	@Autowired
	private IExistenciasIndicadoresDAO existenciaIndicadoresDAO;

	@Autowired
	private IStockService stockService;

	@GET
	@Path("/get_by_descrip/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StockBasic> get_by_descrip(@PathParam("name") String name) {
		return stockBasicDAO.get_stock_by_descrip(name);

	}

	@GET
	@Path("/get_artdespa_art_lote/{art}/{lote}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArtDespa> get_artdespa_art_lote(@PathParam("art") String art, @PathParam("lote") String lote) {
		return stockService.get_artdespa_art_lote(art, lote);

	}

	@GET
	@Path("/get_indicadores_ingresos/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic get_indicadores_ingresos(@PathParam("anio") Integer anio) {
		return stockService.getIndicadoresIngresos(anio);

	}

	@GET
	@Path("/get_indicadores_ingresos_egresos/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic get_indicadores_ingresos_egresos(@PathParam("anio") Integer anio) {
		return stockService.getIndicadoresIngresosEgresos(anio);

	}

	@GET
	@Path("/get_indicadores_ingresos_saldo_vencidos/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic getIndicadoresIngresosSaldo(@PathParam("anio") Integer anio) {

		List<ExistenciasIndicadores> list = existenciaIndicadoresDAO
				.getExistenciaIndicadores(Constants.IDINDICESALDOVENCIDOS);

		List<Integer> listExistencia = new ArrayList<Integer>();

		for (ExistenciasIndicadores exi : list) {
			listExistencia.add(exi.getNrExistencia());
		}
		return stockService.getIndicadoresIngresosSaldo(anio, listExistencia);
	}

	@GET
	@Path("/get_indicadores_ingresos_saldo_cuarentena/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic getIndicadoresIngresosCuarentena(@PathParam("anio") Integer anio) {
		List<ExistenciasIndicadores> list = existenciaIndicadoresDAO
				.getExistenciaIndicadores(Constants.IDINDICESALDOCUARENTENA);
		List<Integer> listExistencia = new ArrayList<Integer>();

		for (ExistenciasIndicadores exi : list) {
			listExistencia.add(exi.getNrExistencia());
		}
		return stockService.getIndicadoresIngresosSaldo(anio, listExistencia);
	}

	@GET
	@Path("/get_indicadores_ingresos_saldo_recupero/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic getIndicadoresIngresosRecupero(@PathParam("anio") Integer anio) {
		List<Integer> listExistencia = new ArrayList<Integer>() {
			{
				add(25);
			}
		};
		return stockService.getIndicadoresIngresosSaldo(anio, listExistencia);
	}

	@GET
	@Path("/get_indicadores_ingresos_saldo_disponible/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic getIndicadoresIngresosDisponible(@PathParam("anio") Integer anio) {
		List<ExistenciasIndicadores> list = existenciaIndicadoresDAO
				.getExistenciaIndicadores(Constants.IDINDICESALDODISPONIBLE);
		List<Integer> listExistencia = new ArrayList<Integer>();

		for (ExistenciasIndicadores exi : list) {
			listExistencia.add(exi.getNrExistencia());
		}
		return stockService.getIndicadoresIngresosSaldo(anio, listExistencia);
	}
	
	@GET
	@Path("/get_indicadores_porcentaje_vencidos/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic getIndicadoresPorcentajeVencidos(@PathParam("anio") Integer anio) {
		return stockService.getIndicadoresPorcentajeFiltroDisponible(anio, Constants.IDINDICESALDOVENCIDOS);
	}
	
	@GET
	@Path("/get_indicadores_porcentaje_cuarentena/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic getIndicadoresPorcentajeCuarentena(@PathParam("anio") Integer anio) {
		return stockService.getIndicadoresPorcentajeFiltroDisponible(anio, Constants.IDINDICESALDOCUARENTENA);
	}
	
	
	@GET
	@Path("/get_indicadores_porcentaje_recupero/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic getIndicadoresPorcentajeRecupero(@PathParam("anio") Integer anio) {
		return stockService.getIndicadoresPorcentajeFiltroDisponible(anio, Constants.IDINDICESALDORECUPERO);
	}

	@GET
	@Path("/get_indicadores_porcentaje_desvio/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic getIndicadoresPorcentajeDesvio(@PathParam("anio") Integer anio) {
		return stockService.getIndicadoresPorcentajeFiltroDisponible(anio, Constants.IDINDICESALDODESVIO);
	}
	
	@GET
	@Path("/get_indicadores_ingresos_vencidos/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic get_indicadores_ingresos_vencidos(@PathParam("anio") Integer anio) {
		return stockService.getIndicadoresIngresosVencidos(anio);

	}

	@GET
	@Path("/get_indicadores_cuarentena/{anio}")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic get_indicadores_ingresos_cuarentena(@PathParam("anio") Integer anio) {
		return stockService.getIndicadoresIngresosCuarentena(anio);

	}

	@POST
	@Path("/get_indicadores_operadores")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic get_indicadores_ingresos_operador(JSONObject json) {
		Integer anio = 1900;

		try {
			anio = json.optInt("anio");
		} catch (Exception ne) {
			return new ChartBasic(false, "El año ingresado es invalido ");
		}
		List<Integer> operListInt = new ArrayList<Integer>();
		try {
			String operadores = json.optString("operadores");
			String[] operList = operadores.split(",");

			for (String oper : operList) {
				operListInt.add(Integer.parseInt(oper));
			}
		} catch (Exception ne) {
			return new ChartBasic(false,
					"El valor ingresado para operadores es invalido debe ingresar por ejemplo: 10,23,137 ");
		}

		return stockService.getIndicadoresIngresosOperador(anio, operListInt);

	}

	@POST
	@Path("/get_indicadores_por_existencia")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic get_indicadores_por_existencia(JSONObject json) {
		Integer anio = 1900;

		try {
			anio = json.optInt("anio");
		} catch (Exception ne) {
			return new ChartBasic(false, "El año ingresado es invalido ");
		}
		List<Integer> exiListInt = new ArrayList<Integer>();
		try {
			String existencias = json.optString("existencias");
			String[] exiList = existencias.split(",");

			for (String exi : exiList) {
				exiListInt.add(Integer.parseInt(exi));
			}
		} catch (Exception ne) {
			return new ChartBasic(false,
					"El valor ingresado para operadores es invalido debe ingresar por ejemplo: 10,23,137 ");
		}

		return stockService.getIndicadoresIngresoPorExistencia(anio, exiListInt, false);

	}

	@POST
	@Path("/get_indicadores_por_existencia_por_mes")
	@Produces(MediaType.APPLICATION_JSON)
	public ChartBasic get_indicadores_por_existencia_por_mes(JSONObject json) {
		Integer anio = 1900;
		Integer mes = 1;
		try {
			anio = json.optInt("anio");
		} catch (Exception ne) {
			return new ChartBasic(false, "El año ingresado es invalido ");
		}
		try {
			mes = FormatUtil.getIntMes(json.optString("mes"));
		} catch (Exception ne) {
			return new ChartBasic(false, "El mes ingresado es invalido ");
		}
		List<Integer> exiListInt = new ArrayList<Integer>();
		try {
			String existencias = json.optString("existencias");
			String[] exiList = existencias.split(",");

			for (String exi : exiList) {
				exiListInt.add(Integer.parseInt(exi));
			}
		} catch (Exception ne) {
			return new ChartBasic(false,
					"El valor ingresado para operadores es invalido debe ingresar por ejemplo: 10,23,137 ");
		}

		return stockService.getIndicadoresIngresoPorExistenciaPorMes(anio, exiListInt, mes);

	}

	@GET
	@Path("/findStockByCodBarras/{cod_barras}")
	@Produces(MediaType.APPLICATION_JSON)
	public StockBasic get_by_cod_barras(@PathParam("cod_barras") String cod_barras) {
		return stockBasicDAO.get_stock_by_cod_barras(cod_barras);

	}
	
	
	
	@GET
	@Path("/findStockByCodBarrasAll/{cod_barras}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList get_by_cod_barras_all(@PathParam("cod_barras") String cod_barras) {
		try{					
			return new JsonResultList(true, "OK", stockBasicDAO.get_stock_by_cod_barras_all(cod_barras));
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}


	@GET
	@Path("/findStockByGtinAll/{gtin}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList findStockByGtinAll(@PathParam("gtin") String gtin) {
		try{					
			return new JsonResultList(true, "OK", stockBasicDAO.get_stock_by_gtin_all(gtin));
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}

	@GET
	@Path("/getAllExistencias")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Existencias> get_all_existencias() {
		return stockService.getExistenciaAll();

	}

	@POST
	@Path("/ingresarMercaderia")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultIngresoMercaderia ingresarMercaderia(DataSendMercaderia dataSendMercaderia) {
		JsonResultIngresoMercaderia result = null;
		try {
			result = stockService.ingresoMercaderia(dataSendMercaderia);
			// result = trazaService.confirmTransacAnmat(dataSendAnmat,
			// "DROGUERIAORIEN",
			// "ORIEN1152","https://trazabilidad.pami.org.ar:9050/trazamed.WebService");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResultIngresoMercaderia(false,
					"No se ha podido ingresar los medicamentos consulte el siguiente error con el administrador: "
							+ e.getMessage());
		}
	}

}
