package ar.com.cipres.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ar.com.cipres.dao.IAlfaBetaActualizacionesDAO;
import ar.com.cipres.dao.IArtDespaDAO;
import ar.com.cipres.dao.IArticuloMadreDAO;
import ar.com.cipres.dao.IArticulosHistoricoPreciosDAO;
import ar.com.cipres.dao.IClieArticPrecioDAO;
import ar.com.cipres.dao.IDespachosDAO;
import ar.com.cipres.dao.IDomiciliosDAO;
import ar.com.cipres.dao.IExiArtDAO;
import ar.com.cipres.dao.IExistenciasDAO;
import ar.com.cipres.dao.IExistenciasIndicadoresDAO;
import ar.com.cipres.dao.IGenteDAO;
import ar.com.cipres.dao.IItemsDAO;
import ar.com.cipres.dao.IKardexDAO;
import ar.com.cipres.dao.INumerKarDAO;
import ar.com.cipres.dao.IOperadorDAO;
import ar.com.cipres.dao.IParametrizacionDAO;
import ar.com.cipres.dao.IPrefijoExistenciaDAO;
import ar.com.cipres.dao.IStockDAO;
import ar.com.cipres.dao.ISubFamiliaDAO;
import ar.com.cipres.dao.ITransacDAO;
import ar.com.cipres.dao.IUsuarioDAO;
import ar.com.cipres.dao.impl.ArtDespaDAO;
import ar.com.cipres.dao.impl.StockDAO;
import ar.com.cipres.model.AlfaBetaActualizaciones;
import ar.com.cipres.model.ArtDespa;
import ar.com.cipres.model.ArtDespaId;
import ar.com.cipres.model.ArticuloMadre;
import ar.com.cipres.model.ArticulosHistoricoPrecios;
import ar.com.cipres.model.ChartBasic;
import ar.com.cipres.model.ClieArticPrecio;
import ar.com.cipres.model.ClieArticPrecioId;
import ar.com.cipres.model.DataSendMercaderia;
import ar.com.cipres.model.Despachos;
import ar.com.cipres.model.ExiArt;
import ar.com.cipres.model.ExiArtId;
import ar.com.cipres.model.Existencias;
import ar.com.cipres.model.ExistenciasIndicadores;
import ar.com.cipres.model.Gente;
import ar.com.cipres.model.Indicador;
import ar.com.cipres.model.IngresoMercaderia;
import ar.com.cipres.model.Items;
import ar.com.cipres.model.JsonResultIngresoMercaderia;
import ar.com.cipres.model.Kardex;
import ar.com.cipres.model.KardexId;
import ar.com.cipres.model.NumerKar;
import ar.com.cipres.model.ObjectPaging;
import ar.com.cipres.model.Operadores;
import ar.com.cipres.model.PrintLabel;
import ar.com.cipres.model.Stock;
import ar.com.cipres.model.SubFamilia;
import ar.com.cipres.model.Transac;
import ar.com.cipres.model.Trazabi;
import ar.com.cipres.model.TrazabiShort;
import ar.com.cipres.model.Usuario;
import ar.com.cipres.services.IReportService;
import ar.com.cipres.services.IStockService;
import ar.com.cipres.services.ITrazaService;
import ar.com.cipres.util.Configuration;
import ar.com.cipres.util.Constants;
import ar.com.cipres.util.DateUtil;
import ar.com.cipres.util.FilterCriteria;
import ar.com.cipres.util.FormatUtil;
import ar.com.cipres.util.MathUtil;
import ar.com.cipres.util.ObjCodigoDescrip;
import ar.com.cipres.util.ZipUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class StockService implements IStockService {

	@Autowired
	private IParametrizacionDAO parametrizacionDAO;

	@Autowired
	private IClieArticPrecioDAO clieArticPrecioDAO;
	
	@Autowired
	private IArticulosHistoricoPreciosDAO artHistPreciDAO;

	@Autowired
	private IExistenciasIndicadoresDAO existenciasIndicadoresDAO;

	@Autowired
	private IArticuloMadreDAO articuloMadreDAO;

	@Autowired
	private IOperadorDAO operadorDAO;

	@Autowired
	private IGenteDAO genteDAO;

	@Autowired
	private IAlfaBetaActualizacionesDAO alfaBetaActualizacionesDAO;

	@Autowired
	private IDespachosDAO despachosDAO;

	@Autowired
	private IStockDAO stockDAO;

	@Autowired
	private IExiArtDAO exiArtDAO;

	@Autowired
	private ITransacDAO transacDAO;

	@Autowired
	private IItemsDAO itemsDAO;

	@Autowired
	private IKardexDAO kardexDAO;

	@Autowired
	private IArtDespaDAO artDespaDAO;

	@Autowired
	private INumerKarDAO numerKarDAO;

	@Autowired
	private IReportService reportService;

	@Autowired
	private IExistenciasIndicadoresDAO existenciaIndicadoresDAO;

	@Autowired
	private IExistenciasDAO existenciasDAO;

	@Autowired
	private ITrazaService trazaService;

	@Autowired
	private IUsuarioDAO usuarioDAO;

	@Autowired
	private IPrefijoExistenciaDAO prefijoExistenciaDAO;

	@Autowired
	protected ISubFamiliaDAO subFamiliaDAO;

	private final static Logger LOGGER = Logger.getLogger(StockService.class.getName());

	public Despachos generateDespachos(String vencimiento, String lote) {
		// Me fijo si el despacho existe
		String date_short = DateUtil.CastStringToDateShort(vencimiento);
		String filterTextLote = "L=" + lote + " V=" + date_short;
		FilterCriteria filterdescrip = new FilterCriteria("descrip", filterTextLote, new Short("0"));
		List<FilterCriteria> filterCriteriaList = new ArrayList<FilterCriteria>();
		filterCriteriaList.add(filterdescrip);
		List<Despachos> despachosList = despachosDAO.getAll(filterCriteriaList);
		Despachos despachos_ = new Despachos();
		if (despachosList != null && despachosList.size() > 0) {
			// Hay que tomar el despanr

			despachos_ = despachosList.get(0);
		} else {
			// Hay que crear un nuevo despacho
			Despachos despachos = new Despachos();
			despachos.setFechaIng(DateUtil.getDateShortDateStr(vencimiento));
			despachos.setDescrip(filterTextLote);
			despachos.setSoloLote(lote);
			despachos.setTrazable(0);
			despachosDAO.save(despachos);
			despachos_ = despachos;
		}
		return despachos_;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public JsonResultIngresoMercaderia ingresoMercaderia(DataSendMercaderia dataSendMercaderia) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		Usuario usuario = usuarioDAO.getByUsername(name);
		Integer genteNr = 0;
		try {
			if (dataSendMercaderia.getGenteNr() != null) {
				genteNr = dataSendMercaderia.getGenteNr();
			}
		} catch (Exception e) {
		}
		boolean validoTransac = true;
		boolean useOC = true;
		// Pregunto si la transac es valida
		Transac transacSk = null;
		List<TrazabiShort> trazabiPrintLabelList = new ArrayList<TrazabiShort>();
		List<Kardex> kardexList = new ArrayList<Kardex>();
		try {
			transacSk = transacDAO.getByPrimaryKey(dataSendMercaderia.getTransacNr());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Integer sucursalNr = 1;
			Existencias existencias = new Existencias();
			if (parametrizacionDAO.getByPrimaryKey(Constants.PARAM_USE_EXIT_DEFAULT).getValor().equals("true")) {
				existencias.setNr(
						Integer.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_DEFAULT).getValor()));
			} else {

				if (useOC) {
					try {
						existencias.setNr(prefijoExistenciaDAO.getPrefijoExistencia(transacSk.getPrefijo()).get(0)
								.getNrExistencia());
					} catch (Exception e) {
						existencias.setNr(Integer
								.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_DEFAULT).getValor()));
					}
				} else {
					existencias.setNr(Integer
							.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_DEFAULT).getValor()));
				}
			}
			if (dataSendMercaderia.getTransacNr() == 0) {
				useOC = false;
				try {
					existencias.setNr(
							Integer.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_ZERO).getValor()));
				} catch (Exception e) {
				}
			} else {

				if (transacSk == null) {
					return new JsonResultIngresoMercaderia(false, "El número de transacción ingresado no se es válido");
				} else {
					if (transacSk.getTipoComprob().getNr() != 15 && transacSk.getDestino() == -1) {
						return new JsonResultIngresoMercaderia(false,
								"El tipo de comprobante ingresado no se es válido");
					}
				}
			}

			for (IngresoMercaderia ingresoMercaderia : dataSendMercaderia.getArrayIngresoMercaderia()) {

				try {
					Despachos despachos = null;
					if (ingresoMercaderia.getLote() != null && !ingresoMercaderia.getLote().trim().equals("")) {
						despachos = generateDespachos(ingresoMercaderia.getVencimiento(), ingresoMercaderia.getLote());

						if (ingresoMercaderia.getArtDespa() != null) {
							ArtDespaId artDespaId = new ArtDespaId();
							artDespaId.setArtic(ingresoMercaderia.getArtDespa().getId().getArtic());
							artDespaId.setDespachos(despachos);
							ArtDespa artDespa = artDespaDAO.getByPrimaryKey(artDespaId);
							if (artDespa == null) {
								artDespa = new ArtDespa();
								artDespa.setCantidad(Double.valueOf(ingresoMercaderia.getCantidad()));
								artDespa.setExinr(existencias.getNr());
								artDespa.setId(artDespaId);
								artDespa.setPreco(BigDecimal.ZERO);
								artDespa.setVendido(0f);
								artDespaDAO.save(artDespa);
							} else {
								artDespa.setCantidad(
										artDespa.getCantidad() + Double.valueOf(ingresoMercaderia.getCantidad()));
								artDespaDAO.update(artDespa);
							}
						} else {
							// Agrego ArtDespa
							ArtDespa artDespa = null;
							ArtDespaId artDespaId = new ArtDespaId();
							artDespaId.setArtic(ingresoMercaderia.getArticulo().getId());
							artDespaId.setDespachos(despachos);
							artDespa = artDespaDAO.getByPrimaryKey(artDespaId);
							if (artDespa == null) {
								artDespa = new ArtDespa();
								artDespa.setCantidad(Double.valueOf(ingresoMercaderia.getCantidad()));
								artDespa.setExinr(existencias.getNr());
								artDespa.setId(artDespaId);
								artDespa.setPreco(BigDecimal.ZERO);
								artDespa.setVendido(0f);
								artDespaDAO.save(artDespa);
							} else {
								artDespa.setCantidad(
										artDespa.getCantidad() + Double.valueOf(ingresoMercaderia.getCantidad()));
								artDespaDAO.update(artDespa);
							}
						}
					}

					// Creación del kardex
					Kardex kardex = new Kardex();
					KardexId kardexId = new KardexId();
					kardexId.setTransacNr(generateTransacNrKar(sucursalNr));
					// Consultar el articulo
					Stock stock = stockDAO.getByPrimaryKey(ingresoMercaderia.getArticulo().getId());
					kardexId.setStock(stock);
					kardex.setId(kardexId);
					kardex.setArticNr(0);
					if (useOC) {
						kardex.setAgendadoNr(transacSk.getGente().getId());
					} else {
						kardex.setAgendadoNr(genteNr);
					}
					kardex.setCantidad1(Float.parseFloat(String.valueOf(ingresoMercaderia.getCantidad())));
					kardex.setCantidad2(0f);
					kardex.setDespachos(despachos);
					try {
						kardex.setSubtotal(Double.valueOf(String.valueOf(despachos.getDespaNr())));
					} catch (Exception e) {
						kardex.setSubtotal(0d);
					}
					if (useOC) {
						kardex.setObser(dataSendMercaderia.getObservaciones());
					} else {
						if (dataSendMercaderia.getObservaciones() != null
								&& !dataSendMercaderia.getObservaciones().equals("")) {
							kardex.setObser(dataSendMercaderia.getObservaciones());
						} else {
							kardex.setObser("Ingreso Sin Orden de Compra ");
						}
					}
					kardex.setExistenciaNr(existencias.getNr());
					kardex.setFechaComprob(DateUtil.getDateSkString(new Date(System.currentTimeMillis())));
					kardex.setFechaTransac(new Date(System.currentTimeMillis()));

					kardex.setOperadorNr(usuario.getUsersk());
					if (useOC) {
						List<Items> itemsList = itemsDAO.getByTransacArticulo(transacSk.getTransacNr(),
								ingresoMercaderia.getArticulo().getId());
						kardex.setPreCosto(itemsList.get(0).getPreCosto());
						kardex.setPreVta(itemsList.get(0).getPrecio());
					} else {
						kardex.setPreCosto(BigDecimal.ZERO);
						kardex.setPreVta(BigDecimal.ZERO);
					}
					kardex.setSucursalNr(sucursalNr);
					kardex.setPreFob(FormatUtil.castRemitoAnmat(dataSendMercaderia.getNrComprob()));

					kardex.setTalle(".");
					kardex.setVendedorNr(1);

					kardex.setTipoComprobante(20);
					if (useOC) {
						kardex.setTransacOrigen(transacSk.getTransacNr());
					} else {
						kardex.setTransacOrigen(0);
					}
					kardexList.add(kardex);
					kardexDAO.save(kardex);
					// Preguntar si tiene que generar trazabilidad interna
					if (ingresoMercaderia.getTraza_int() != null && ingresoMercaderia.getTraza_int()) {
						// Generar en Trazabi el movimiento
						for (int j = 0; j < ingresoMercaderia.getCantidad(); j++) {
							Trazabi trazabiTmp = new Trazabi();
							trazabiTmp = trazaService.addTrazabi(ingresoMercaderia, dataSendMercaderia, transacSk);
							TrazabiShort trazabiShort = new TrazabiShort();
							trazabiShort.setNr(trazabiTmp.getNr());
							trazabiShort.setNrlote(trazabiTmp.getNrlote());
							trazabiShort.setGlndestinoIng(trazabiTmp.getGlndestinoIng());
							trazabiPrintLabelList.add(trazabiShort);
						}
					}

					// Validar con la Orden de compra si entra en el pedido
					// Completar Orden de compra
					// Ingresar a ExiArt
					ExiArtId exiArtId = new ExiArtId();
					exiArtId.setClave(ingresoMercaderia.getArticulo().getId());
					exiArtId.setExistencias(existencias);

					ExiArt exiArt = exiArtDAO.getByPrimaryKey(exiArtId);
					if (exiArt == null) {
						exiArt = new ExiArt();
						exiArt.setId(exiArtId);
						exiArt.setCantidad1(ingresoMercaderia.getCantidad().doubleValue());
						exiArt.setCantidad2(0d);
						exiArtDAO.save(exiArt);
					} else {
						exiArt.setCantidad1(exiArt.getCantidad1() + ingresoMercaderia.getCantidad());
						exiArtDAO.update(exiArt);

					}

					System.out.println("Update  ExiArt clave: " + exiArt.getId().getClave() + " Existencia: "
							+ exiArt.getId().getExistencias().getNr() + " Cant: " + exiArt.getCantidad1());

					if (useOC) {
						// Dar de baja articulos en la orden de compra
						// Encontrar Item
						List<Items> itemsList = itemsDAO.getByTransacArticulo(transacSk.getTransacNr(),
								ingresoMercaderia.getArticulo().getId());
						if (itemsList.size() > 0) {
							itemsList.get(0).setCant1entregado(
									(itemsList.get(0).getCant1entregado() + ingresoMercaderia.getCantidad()));
							System.out.println("Update  items itemnr: " + itemsList.get(0).getId().getItemNr());
							itemsDAO.update(itemsList.get(0));
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("Error al ingresar : " + ingresoMercaderia.getArticulo().getId() + " ");
					return new JsonResultIngresoMercaderia(false, "Error al ingresar : "
							+ ingresoMercaderia.getArticulo().getId() + " " + ex.getCause().getMessage(), false, null);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error Gereral en remito: " + dataSendMercaderia.getNrComprob() + " ");
			return new JsonResultIngresoMercaderia(false,
					"Error Gereral en remito: " + dataSendMercaderia.getNrComprob() + " " + ex.getCause().getMessage(),
					false, null);
		}
		PrintLabel printLabel = null;
		String reportStr = reportService.createTablePDF(FormatUtil.convertKardexJsonReporteIngreso(kardexList));
		if (trazabiPrintLabelList.size() > 0) {
			printLabel = new PrintLabel(usuario.getPrintLabel(), trazabiPrintLabelList);
			return new JsonResultIngresoMercaderia(true, "Se ha ingresado la mercaderia con éxito", true, printLabel,
					reportStr);
		} else {
			return new JsonResultIngresoMercaderia(true, "Se ha ingresado la mercaderia con éxito", false, printLabel,
					reportStr);
		}

	}

	private Integer generateTransacNrKar(Integer sucursalNr) {
		Integer result = 0;
		NumerKar numerKar = numerKarDAO.getByPrimaryKey(10);
		result = numerKar.getLetraA() + 1;
		numerKar.setLetraA(result);
		numerKarDAO.update(numerKar);
		result = Integer.parseInt((String.valueOf(result) + String.valueOf(sucursalNr)));
		return result;
	}

	public List<ArtDespa> get_artdespa_art_lote(String art, String lote) {
		return artDespaDAO.getArtDespaByArtLote(art, lote);
	}

	@Override
	public ChartBasic getIndicadoresIngresos(Integer anio) {

		List<Indicador> indicardorSinTraza = kardexDAO.getIndicadoresIngresos(anio, 0);

		List<Indicador> indicardorConTraza = kardexDAO.getIndicadoresIngresos(anio, -1);

		ChartBasic chartBasic = new ChartBasic();
		List<String> series = new ArrayList<String>();
		series.add("No traza");
		ChartBasic chartBasicSTRaza = indicadorToChartBasic(indicardorSinTraza);
		chartBasic.setLabels(chartBasicSTRaza.getLabels());
		List<List<Double>> data = new ArrayList<List<Double>>();
		List<Double> dataSTraza = chartBasicSTRaza.getData().get(0);

		series.add("Traza");
		ChartBasic chartBasicCTRaza = indicadorToChartBasic(indicardorConTraza);

		List<Double> dataCTraza = chartBasicCTRaza.getData().get(0);

		data.add(dataSTraza);
		data.add(dataCTraza);

		chartBasic.setSeries(series);
		chartBasic.setData(data);

		return chartBasic;
	}

	private ChartBasic indicadorToChartBasic(List<Indicador> indicador_list) {
		ChartBasic chartBasic = new ChartBasic();
		List<String> labels = new ArrayList<String>();

		List<List<Double>> data = new ArrayList<List<Double>>();
		List<Double> data_unit = new ArrayList<Double>();
		for (Indicador indicador : indicador_list) {
			labels.add(convertMonth(indicador.getEjeX()));
			data_unit.add(indicador.getEjeY());
		}
		chartBasic.setLabels(labels);
		data.add(data_unit);
		chartBasic.setData(data);
		return chartBasic;

	}

	private String convertMonth(Integer month) {
		switch (month) {
		case 1:
			return "Enero";
		case 2:
			return "Febrero";
		case 3:
			return "Marzo";
		case 4:
			return "Abril";
		case 5:
			return "Mayo";
		case 6:
			return "Junio";
		case 7:
			return "Julio";
		case 8:
			return "Agosto";
		case 9:
			return "Septiembre";
		case 10:
			return "Octubre";
		case 11:
			return "Noviembre";
		case 12:
			return "Diciembre";
		default:
			return "";
		}
	}

	public ChartBasic getIndicadoresIngresosVencidos(Integer anio) {
		try {
			ArrayList<String> listLabel = new ArrayList<String>() {
				{
					add("Enero");
					add("Febrero");
					add("Marzo");
					add("Abril");
					add("Mayo");
					add("Junio");
					add("Julio");
					add("Agosto");
					add("Septiembre");
					add("Octubre");
					add("Noviembre");
					add("Diciembre");
				}
			};
			ChartBasic chartBasic = new ChartBasic();
			List<String> series = new ArrayList<String>();
			List<List<Double>> data = new ArrayList<List<Double>>();

			List<ExistenciasIndicadores> list = existenciasIndicadoresDAO
					.getExistenciaIndicadores(Constants.IDINDICESALDOVENCIDOS);

			List<Integer> listExistencia = new ArrayList<Integer>();

			for (ExistenciasIndicadores exi : list) {
				listExistencia.add(exi.getNrExistencia());
			}
			for (Integer ExiNr : listExistencia) {
				Existencias existencias = existenciasDAO.getByPrimaryKey(ExiNr);
				List<Indicador> indicardorExi = kardexDAO.getIndicadoresIngresosExistencia(anio, existencias.getNr());
				List<Double> data_unit = new ArrayList<Double>();
				for (int i = 1; i < 13; i++) {
					Indicador indicador_new = new Indicador(0d, i);
					for (Indicador indicador : indicardorExi) {
						if (indicador.getEjeX().equals(i)) {
							indicador_new = indicador;
						}
					}
					data_unit.add(indicador_new.getEjeY());
				}
				data.add(data_unit);
				series.add(existencias.getDescrip());

			}
			chartBasic.setSeries(series);
			chartBasic.setData(data);
			chartBasic.setLabels(listLabel);
			return chartBasic;
		} catch (Exception e) {
			return null;
		}

	}

	public ChartBasic getIndicadoresIngresosEgresos(Integer anio) {

		List<Indicador> indicardorIngresos = kardexDAO.getIndicadoresIngresosEgresos(anio, "20,2,24");

		List<Indicador> indicardorEgresos = kardexDAO.getIndicadoresIngresosEgresos(anio, "1,3,21");

		ChartBasic chartBasic = new ChartBasic();
		List<String> series = new ArrayList<String>();
		series.add("Ingresos");
		ChartBasic chartBasicIngresos = indicadorToChartBasic(indicardorIngresos);
		chartBasic.setLabels(chartBasicIngresos.getLabels());
		List<List<Double>> data = new ArrayList<List<Double>>();
		List<Double> dataIngresos = chartBasicIngresos.getData().get(0);

		series.add("Egresos");
		ChartBasic chartBasicEgresos = indicadorToChartBasic(indicardorEgresos);

		List<Double> dataEgresos = chartBasicEgresos.getData().get(0);

		data.add(dataIngresos);
		data.add(dataEgresos);

		chartBasic.setSeries(series);
		chartBasic.setData(data);

		return chartBasic;
	}

	public ChartBasic getIndicadoresIngresosSaldo(Integer anio, List<Integer> listExistencia) {

		try {
			ArrayList<String> listLabel = new ArrayList<String>() {
				{
					add("Enero");
					add("Febrero");
					add("Marzo");
					add("Abril");
					add("Mayo");
					add("Junio");
					add("Julio");
					add("Agosto");
					add("Septiembre");
					add("Octubre");
					add("Noviembre");
					add("Diciembre");
				}
			};
			ChartBasic chartBasic = new ChartBasic();
			List<String> series = new ArrayList<String>();
			List<List<Double>> data = new ArrayList<List<Double>>();
			for (Integer ExiNr : listExistencia) {
				Existencias existencias = existenciasDAO.getByPrimaryKey(ExiNr);
				List<Indicador> indicardorIngresosHistorico = kardexDAO
						.getIndicadoresIngresosEgresosAnioMenorIgual(anio - 1, "20,2,24", ExiNr);
				indicardorIngresosHistorico = validateMesIndicadorList(indicardorIngresosHistorico);
				Double totalHistorico = getTotal(indicardorIngresosHistorico);
				List<Indicador> indicardorIngresos = kardexDAO.getIndicadoresIngresosEgresos(anio, "20,2,24", ExiNr);

				List<Indicador> indicardorEgresosHistorico = kardexDAO
						.getIndicadoresIngresosEgresosAnioMenorIgual(anio - 1, "1,3,21", ExiNr);
				indicardorEgresosHistorico = validateMesIndicadorList(indicardorEgresosHistorico);
				Double totalHistoricoEgreso = getTotal(indicardorEgresosHistorico);
				List<Indicador> indicardorEgresos = kardexDAO.getIndicadoresIngresosEgresos(anio, "1,3,21", ExiNr);
				indicardorEgresos = addMesIndicadorList(indicardorEgresos);
				Double saldoHistorico = totalHistorico - totalHistoricoEgreso;
				indicardorIngresos = addMesIndicadorList(indicardorIngresos, saldoHistorico);
				Indicador indicadorEgresoActual = null;
				List<Indicador> saldo = new ArrayList<Indicador>();
				for (Indicador indicador : indicardorIngresos) {
					Indicador indicadorEgreso = null;
					for (Indicador indicadorE : indicardorEgresos) {
						if (indicadorE.getEjeX() == indicador.getEjeX()) {
							indicadorEgreso = indicadorE;
							indicadorEgresoActual = indicadorE;
						}

					}
					Indicador indicadorSaldo = null;
					if (indicadorEgreso == null) {
						if (indicadorEgresoActual == null) {
							indicadorSaldo = new Indicador(indicador.getEjeY(), indicador.getEjeX());
						} else {
							indicadorSaldo = new Indicador(indicador.getEjeY() - indicadorEgresoActual.getEjeY(),
									indicador.getEjeX());
						}
					} else {
						indicadorSaldo = new Indicador(indicador.getEjeY() - indicadorEgreso.getEjeY(),
								indicador.getEjeX());
					}

					saldo.add(indicadorSaldo);
				}
				// List<Indicador> indicardorExi =
				// kardexDAO.getIndicadoresIngresosExistencia(anio,
				// existencias.getNr());
				List<Double> data_unit = new ArrayList<Double>();
				for (int i = 1; i < 13; i++) {
					Indicador indicador_new = new Indicador(0d, i);
					for (Indicador indicador : saldo) {
						if (indicador.getEjeX().equals(i)) {
							indicador_new = indicador;
						}
					}
					data_unit.add(indicador_new.getEjeY());
				}
				data.add(data_unit);
				series.add(existencias.getDescrip());

			}
			chartBasic.setSeries(series);
			chartBasic.setData(data);
			chartBasic.setLabels(listLabel);
			return chartBasic;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Double getTotal(List<Indicador> indicardorIngresosHistorico) {
		Double result = 0d;
		for (Indicador tmp : indicardorIngresosHistorico) {
			result = result + tmp.getEjeY();
		}
		return result;
	}

	private List<Indicador> validateMesIndicadorList(List<Indicador> indicadores) {
		List<Indicador> result = new ArrayList<Indicador>();
		for (int i = 1; i < 13; i++) {
			Indicador indicador = null;
			for (Indicador tmp : indicadores) {
				if (tmp.getEjeX() == i) {
					indicador = tmp;
				}
			}
			if (indicador == null) {
				indicador = new Indicador(0d, i);
			}
			result.add(indicador);

		}
		return result;

	}

	private List<Indicador> addMesIndicadorList(List<Indicador> indicadores, Double totalHistorico) {
		List<Indicador> result = new ArrayList<Indicador>();
		Double count = totalHistorico;
		for (int i = 1; i < 13; i++) {
			Indicador indicador = null;
			for (Indicador tmp : indicadores) {
				if (tmp.getEjeX() == i) {
					indicador = tmp;
				}
			}

			if (indicador != null) {
				count = count + indicador.getEjeY();
				indicador.setEjeY(count);
				result.add(indicador);
			}

		}

		// for (Indicador indicador : indicadores) {
		// indicador.setEjeY(indicador.getEjeY() + totalHistorico);
		// result.add(indicador);
		// }
		return result;
	}

	private List<Indicador> addMesIndicadorList(List<Indicador> indicadores) {
		List<Indicador> result = new ArrayList<Indicador>();
		Double count = 0d;
		for (int i = 1; i < 13; i++) {
			Indicador indicador = null;
			for (Indicador tmp : indicadores) {
				if (tmp.getEjeX() == i) {
					indicador = tmp;
				}
			}

			if (indicador != null) {
				count = count + indicador.getEjeY();
				indicador.setEjeY(count);
				result.add(indicador);
			}

		}

		// for (Indicador indicador : indicadores) {
		// indicador.setEjeY(indicador.getEjeY() + totalHistorico);
		// result.add(indicador);
		// }
		return result;
	}

	public ChartBasic getIndicadoresIngresosCuarentena(Integer anio) {
		try {
			ArrayList<String> listLabel = new ArrayList<String>() {
				{
					add("Enero");
					add("Febrero");
					add("Marzo");
					add("Abril");
					add("Mayo");
					add("Junio");
					add("Julio");
					add("Agosto");
					add("Septiembre");
					add("Octubre");
					add("Noviembre");
					add("Diciembre");
				}
			};
			ChartBasic chartBasic = new ChartBasic();
			List<String> series = new ArrayList<String>();
			List<List<Double>> data = new ArrayList<List<Double>>();
			List<ExistenciasIndicadores> list = existenciasIndicadoresDAO
					.getExistenciaIndicadores(Constants.IDINDICESALDOCUARENTENA);
			List<Integer> listExistencia = new ArrayList<Integer>();

			for (ExistenciasIndicadores exi : list) {
				listExistencia.add(exi.getNrExistencia());
			}
			for (Integer ExiNr : listExistencia) {
				Existencias existencias = existenciasDAO.getByPrimaryKey(ExiNr);
				List<Indicador> indicardorExi = kardexDAO.getIndicadoresIngresosExistencia(anio, existencias.getNr());
				List<Double> data_unit = new ArrayList<Double>();
				for (int i = 1; i < 13; i++) {
					Indicador indicador_new = new Indicador(0d, i);
					for (Indicador indicador : indicardorExi) {
						if (indicador.getEjeX().equals(i)) {
							indicador_new = indicador;
						}
					}
					data_unit.add(indicador_new.getEjeY());
				}
				data.add(data_unit);
				series.add(existencias.getDescrip());

			}
			chartBasic.setSeries(series);
			chartBasic.setData(data);
			chartBasic.setLabels(listLabel);
			return chartBasic;
		} catch (Exception e) {
			return null;
		}
	}

	public ChartBasic getIndicadoresIngresosOperador(Integer anio, List<Integer> listOper) {
		try {
			ArrayList<String> listLabel = new ArrayList<String>() {
				{
					add("Enero");
					add("Febrero");
					add("Marzo");
					add("Abril");
					add("Mayo");
					add("Junio");
					add("Julio");
					add("Agosto");
					add("Septiembre");
					add("Octubre");
					add("Noviembre");
					add("Diciembre");
				}
			};
			ChartBasic chartBasic = new ChartBasic();
			List<String> series = new ArrayList<String>();
			List<List<Double>> data = new ArrayList<List<Double>>();
			List<Integer> listOperadores = kardexDAO.getOperadoresIngresos(anio);

			for (Integer operNr : listOperadores) {
				if (listOper.contains(operNr)) {
					Operadores operador = operadorDAO.getByPrimaryKey(operNr);
					try {
						List<Indicador> indicardorOper = kardexDAO.getIndicadoresIngresosOperador(anio,
								operador.getOperNr());
						List<Double> data_unit = new ArrayList<Double>();
						for (int i = 1; i < 13; i++) {
							Indicador indicador_new = new Indicador(0d, i);
							for (Indicador indicador : indicardorOper) {
								if (indicador.getEjeX().equals(i)) {
									indicador_new = indicador;
								}
							}
							data_unit.add(indicador_new.getEjeY());
						}
						data.add(data_unit);
						series.add("[" + operador.getOperNr() + "] " + operador.getOperNombre());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			}
			chartBasic.setSeries(series);
			chartBasic.setData(data);
			chartBasic.setLabels(listLabel);
			return chartBasic;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ChartBasic getIndicadoresIngresoPorExistencia(Integer anio, List<Integer> exiListInt,
			Boolean joinExistencias) {
		try {
			ArrayList<String> listLabel = new ArrayList<String>() {
				{
					add("Enero");
					add("Febrero");
					add("Marzo");
					add("Abril");
					add("Mayo");
					add("Junio");
					add("Julio");
					add("Agosto");
					add("Septiembre");
					add("Octubre");
					add("Noviembre");
					add("Diciembre");
				}
			};
			ChartBasic chartBasic = new ChartBasic();
			List<String> series = new ArrayList<String>();
			List<List<Double>> data = new ArrayList<List<Double>>();

			if (!joinExistencias) {
				for (Integer exiNr : exiListInt) {

					Existencias exi = existenciasDAO.getByPrimaryKey(exiNr);
					try {
						List<Indicador> indicardorExi = kardexDAO.getIndicadoresIngresosExistencia(anio, exi.getNr());
						List<Double> data_unit = new ArrayList<Double>();
						for (int i = 1; i < 13; i++) {
							Indicador indicador_new = new Indicador(0d, i);
							for (Indicador indicador : indicardorExi) {
								if (indicador.getEjeX().equals(i)) {
									indicador_new = indicador;
								}
							}
							data_unit.add(indicador_new.getEjeY());
						}
						data.add(data_unit);
						series.add("[" + exi.getNr() + "] " + exi.getDescrip());
					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			} else {
				try {
					List<Indicador> indicardorExi = kardexDAO.getIndicadoresIngresosExistencia(anio, exiListInt);
					List<Double> data_unit = new ArrayList<Double>();
					for (int i = 1; i < 13; i++) {
						Indicador indicador_new = new Indicador(0d, i);
						for (Indicador indicador : indicardorExi) {
							if (indicador.getEjeX().equals(i)) {
								indicador_new = indicador;
							}
						}
						data_unit.add(indicador_new.getEjeY());
					}
					data.add(data_unit);
					series.add("[" + exiListInt + "] ");
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
			chartBasic.setSeries(series);
			chartBasic.setData(data);
			chartBasic.setLabels(listLabel);
			return chartBasic;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Existencias> getExistenciaAll() {
		return existenciasDAO.getAll();
	}

	public ChartBasic getIndicadoresIngresoPorExistenciaPorMes(Integer anio, List<Integer> exiListInt, Integer mes) {
		try {
			ArrayList<String> listLabel = new ArrayList<String>();
			for (int j = 1; j < 32; j++) {
				listLabel.add(String.valueOf(j));
			}
			ChartBasic chartBasic = new ChartBasic();
			List<String> series = new ArrayList<String>();
			List<List<Double>> data = new ArrayList<List<Double>>();

			for (Integer exiNr : exiListInt) {

				Existencias exi = existenciasDAO.getByPrimaryKey(exiNr);
				try {
					List<Indicador> indicardorExi = kardexDAO.getIndicadoresIngresosMesExistencia(anio, exi.getNr(),
							mes);
					List<Double> data_unit = new ArrayList<Double>();
					for (int i = 1; i < 32; i++) {
						Indicador indicador_new = new Indicador(0d, i);
						for (Indicador indicador : indicardorExi) {
							if (indicador.getEjeX().equals(i)) {
								indicador_new = indicador;
							}
						}
						data_unit.add(indicador_new.getEjeY());
					}
					data.add(data_unit);
					series.add("[" + exi.getNr() + "] " + exi.getDescrip());
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
			chartBasic.setSeries(series);
			chartBasic.setData(data);
			chartBasic.setLabels(listLabel);
			return chartBasic;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ObjectPaging findArticulosUsuariosExistencias(JSONObject jsonObject) {
		ObjectPaging objectPaging = new ObjectPaging();
		List<Object[]> objectList = new ArrayList<Object[]>();
		List<Object> resultList = new ArrayList<>();
		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
			String search = gsonObject.has("search") ? gsonObject.get("search").getAsString() : "";
			int idUsuario = gsonObject.has("userId") ? gsonObject.get("userId").getAsInt() : 0;
			int pageNumber = gsonObject.has("pageNumber") ? gsonObject.get("pageNumber").getAsInt() : 0;
			int pageSize = gsonObject.has("pageSize") ? gsonObject.get("pageSize").getAsInt() : 0;
			Long total_items = 0l;
			objectList = stockDAO.findArticulosUsuariosExistencias(search, idUsuario, pageNumber, pageSize);
			Gente gente = null;

			String formulaPrecios = null;
			if (objectList != null) {

				try {
					formulaPrecios = parametrizacionDAO.getByPrimaryKey(Constants.ID_FORMULA_GET_PRECIOS).getValor();
				} catch (Exception e) {
				}

				// CARGO LISTA STOCK
				List<Stock> tempStock = new ArrayList<>();
				Stock auxStock;
				Object auxObject;
				for (Object[] row : objectList) {
					auxStock = new Stock();
					auxStock.setDescripcion(row[0].toString());
					auxStock.setId(row[1].toString());
					auxStock.setNrArt(Integer.parseInt(row[2].toString()));
					auxStock.setPrecio1(new BigDecimal(row[3].toString()));
					auxObject = auxStock;
					resultList.add(auxObject);
					tempStock.add(auxStock);
				}

				// List<Stock> tempStock = (List<Stock>) (List) objectList;
				Integer exi = 0;
				try {
					exi = Integer.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_DEFAULT).getValor());
				} catch (Exception e) {
				}
				Existencias exiDefault = existenciasDAO.getByPrimaryKey(exi);
				for (Stock stock : tempStock) {
					stock = findArticuloCalculo(stock, exiDefault, gente, formulaPrecios);
				}
				// objectList = (List<Object>) (List) objectList;

				if (objectList != null && objectList.size() > 0) {
					total_items = stockDAO.findArticulosUsuariosExistenciasCount(search, idUsuario, pageNumber,
							pageSize);
				}
			}
			objectPaging = new ObjectPaging(resultList, total_items);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectPaging;
	}

	public ObjectPaging findArticulo(JSONObject jsonObject) {
		ObjectPaging objectPaging = new ObjectPaging();
		List<Object> objectList = new ArrayList<Object>();

		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
			JsonObject gsonObjectResult = gsonObject.get("busqueda").getAsJsonObject();
			String nombreNumero = gsonObjectResult.has("nombreNumero")
					? gsonObjectResult.get("nombreNumero").getAsString() : "";
			int familiaId = gsonObjectResult.has("familiaId") ? gsonObjectResult.get("familiaId").getAsInt() : 0;
			int genteId = gsonObjectResult.has("genteId") ? gsonObjectResult.get("genteId").getAsInt() : 0;
			int subFamiliaId = gsonObjectResult.has("subFamiliaId") ? gsonObjectResult.get("subFamiliaId").getAsInt()
					: 0;
			int pageNumber = gsonObject.has("pageNumber") ? gsonObject.get("pageNumber").getAsInt() : 0;
			int pageSize = gsonObject.has("pageSize") ? gsonObject.get("pageSize").getAsInt() : 0;
			Long total_items = 0l;
			objectList = stockDAO.findArticulo(nombreNumero, familiaId, subFamiliaId, pageNumber, pageSize);
			Gente gente = null;

			if (genteId != 0) {
				gente = genteDAO.getByPrimaryKey(genteId);
			}
			String formulaPrecios = null;
			if (objectList != null) {

				try {
					formulaPrecios = parametrizacionDAO.getByPrimaryKey(Constants.ID_FORMULA_GET_PRECIOS).getValor();
				} catch (Exception e) {
				}
				List<Stock> tempStock = (List<Stock>) (List) objectList;
				Integer exi = 0;
				try {
					exi = Integer.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_DEFAULT).getValor());
				} catch (Exception e) {
				}
				Existencias exiDefault = existenciasDAO.getByPrimaryKey(exi);
				for (Stock stock : tempStock) {
					stock = findArticuloCalculo(stock, exiDefault, gente, formulaPrecios);
				}
				objectList = (List<Object>) (List) objectList;

				if (objectList != null && objectList.size() > 0) {
					total_items = stockDAO.findArticuloCount(nombreNumero, familiaId, subFamiliaId);
				}
			}
			objectPaging = new ObjectPaging(objectList, total_items);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectPaging;
	}

	public Stock findArticuloCalculo(Stock stock, Existencias exiDefault, Gente gente, String formulaPrecios) {
		try {
			ExiArtId exiArtId = new ExiArtId();
			exiArtId.setClave(stock.getId());
			exiArtId.setExistencias(exiDefault);
			Double stockReservadoPVta = exiArtDAO.getStockReservadoPVta(stock.getId());

			// Cargo la Fecha de Vencimiento más próxima de cada
			// artículo
			stock.setFechaVencimientoMasProxima(artDespaDAO.getFechaVencimientoMasProxima(stock.getId()));

			ExiArt exiArt = exiArtDAO.getByPrimaryKey(exiArtId);
			stock.setStockReal(exiArt.getCantidad1());
			stock.setStock(exiArt.getCantidad1() - stockReservadoPVta);
			if (gente != null) {
				Method method = stock.getClass().getMethod("getPrecio" + gente.getListaPrecio());
				BigDecimal precio = (BigDecimal) method.invoke(stock);
				ClieArticPrecioId id = new ClieArticPrecioId();
				id.setGente(gente);
				id.setStock(stock);
				if (formulaPrecios != null) {
					ClieArticPrecio clieArticPrecio = clieArticPrecioDAO.getByPrimaryKey(id);
					if (clieArticPrecio != null) {
						switch (formulaPrecios) {
						case "PRE":
							Method methodPrecio = clieArticPrecio.getClass().getMethod("getP" + gente.getListaPrecio());
							precio = (BigDecimal) methodPrecio.invoke(clieArticPrecio);
							break;
						case "POR":
							Method methodProcentaje = clieArticPrecio.getClass()
									.getMethod("getP" + gente.getListaPrecio());
							BigDecimal porcentaje = (BigDecimal) methodProcentaje.invoke(clieArticPrecio);
							precio = precio.subtract((precio.multiply(porcentaje).divide(BigDecimal.valueOf(100l))));
							break;
						default:
							break;
						}
					}
				}
				stock.setPrecio1(precio);
			}

			// stock.setPrecio1(BigDecimal.ZERO);
			stock.setPrecio2(BigDecimal.ZERO);
			stock.setPrecio3(BigDecimal.ZERO);
			stock.setPrecio4(BigDecimal.ZERO);
			stock.setPrecio5(BigDecimal.ZERO);
			stock.setPrecio6(BigDecimal.ZERO);

			return stock;
		} catch (Exception ex) {
			return null;
		}
	}

	public Object findArticuloByKey(String id) {
		Object obj = stockDAO.getByPrimaryKey(id);
		return obj;
	}

	public List<Object> getArticuloMadreAll() {
		List<ArticuloMadre> list = articuloMadreDAO.getAll();
		List<Object> objList = new ArrayList<Object>();
		for (ArticuloMadre item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(String.valueOf(item.getCodArtMad()), item.getDescArtMad());
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	@Transactional
	public void saveArticulo(Stock stock) throws Exception {
		stockDAO.saveOrUpdate(stock);

	}

	public void updateAlfaBeta() throws Exception {
		try {
			Integer maxId = alfaBetaActualizacionesDAO.getMaxId();

			String URL = "http://web.alfabeta.net/update?usr=jortiguela&pw=drofar&src=MD&id=" + maxId.toString();

			String zipName = "UpdateAlfaBeta.zip";
			// COMENTADO HASTA TENER USER

			File file = new File(Configuration.getInstance().getZipFolder() + "/" + zipName);
			if (file.exists()) {
				file.delete();
			}

			FileUtils.copyURLToFile(new URL(URL), new File(Configuration.getInstance().getZipFolder() + "/" + zipName));

			file = new File(Configuration.getInstance().getZipFolder() + "/" + zipName);
			if (file.length() > 0) {
				ZipUtil unZip = new ZipUtil();
				unZip.unZipIt(Configuration.getInstance().getZipFolder() + "/" + zipName, Configuration.getInstance().getZipFolder() + "/ZipOutput/");
				try (BufferedReader br = new BufferedReader(new FileReader(Configuration.getInstance().getZipFolder() + "/ZipOutput/manual.dat"))) {
					String line = br.readLine();
					
					while (line != null) {
						// while (line != null) {
						String troquel = line.substring(0, 7);
						String nombre = line.substring(7, 51);
						String presentacion = line.substring(51, 75);
						String iOMA = line.substring(75, 83);
						String iOMA2 = line.substring(83, 84);
						String iOMA3 = line.substring(84, 85);
						String laboratorio = line.substring(85, 101);
						String precio = line.substring(101, 110);
						String fecha = line.substring(110, 118);
						String marca = line.substring(118, 119);
						String importado = line.substring(119, 120);
						String tipoVenta = line.substring(120, 121);
						String IVA = line.substring(121, 122);
						String descuentoPami = line.substring(122, 123);
						String codigoLaboratorio = line.substring(123, 126);
						String numeroRegistro = line.substring(126, 131);
						String baja = line.substring(131, 132);
						String codBarra = line.substring(132, 145);
						String unidades = line.substring(145, 149);
						String tamano = line.substring(149, 150);
						String heladera = line.substring(150, 151);
						String SIFAR = line.substring(151, 152);
						String blanco = line.substring(152, 153);
						String gravamen = line.substring(153, 154);

						// GET ARTICULO
						// Stock stock = stockDAO.getStockByGtin("AB" +
						// numeroRegistro);
						Stock stock = stockDAO.getStockByCodBarra(codBarra);

						if (stock != null) {
							// SI EXISTE UPDATEO EL PRECIO
							BigDecimal precioSugerido = new BigDecimal(precio).divide(new BigDecimal("100"));
							stock.setPrecio1(precioSugerido);
							stockDAO.update(stock);

							//ACTUALIZO HISTORICO
							ArticulosHistoricoPrecios oHistorico = new ArticulosHistoricoPrecios();
							oHistorico.setClave(stock.getId());
							oHistorico.setFechaActualizacion(new Date(System.currentTimeMillis()));
							oHistorico.setPrecio(precioSugerido);
							artHistPreciDAO.save(oHistorico);
							
						} else {
							// SI ES UN NUEVO ARTICULO INSERTO
							/*
							 * stock = new Stock();
							 * //stock.setSubFamilia(subFamiliaDAO.
							 * getByPrimaryKey( 2l)); stock.setId("AB" +
							 * numeroRegistro.trim()); stock.setEsAlfaBeta(1);
							 * stock.setTroquel(troquel.trim()); nombre =
							 * nombre.trim() + " " + presentacion.trim();
							 * if(nombre.length() > 50) nombre =
							 * nombre.substring(0, 50);
							 * stock.setDescripcion(nombre); BigDecimal
							 * precioSugerido = new
							 * BigDecimal(precio).divide(new BigDecimal("100"));
							 * stock.setPrecio1(precioSugerido);
							 * 
							 * stockDAO.save(stock);
							 */
						}
						line = br.readLine();
					}
					stockDAO.flush();
					artHistPreciDAO.flush();

					// ACTUALIZO REGISTRO DE ALFA BETA
					maxId = maxId + 1;
					AlfaBetaActualizaciones oAlfa = new AlfaBetaActualizaciones();
					oAlfa.setId(maxId);
					oAlfa.setFechaAlta(new Date(System.currentTimeMillis()));
					alfaBetaActualizacionesDAO.save(oAlfa);

					alfaBetaActualizacionesDAO.flush();
				}
			}
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "excepcion", ex);
		}
	}

	public ChartBasic getIndicadoresPorcentajeFiltroDisponible(Integer anio, Integer indiceFiltro) {
		List<ExistenciasIndicadores> list = existenciaIndicadoresDAO
				.getExistenciaIndicadores(Constants.IDINDICESALDODISPONIBLE);
		List<Integer> listExistencia = new ArrayList<Integer>();

		for (ExistenciasIndicadores exi : list) {
			listExistencia.add(exi.getNrExistencia());
		}

		ChartBasic disponible = getIndicadoresIngresosSaldo(anio, listExistencia);

		listExistencia = new ArrayList<Integer>();

		list = existenciaIndicadoresDAO.getExistenciaIndicadores(indiceFiltro);

		for (ExistenciasIndicadores exi : list) {
			listExistencia.add(exi.getNrExistencia());
		}

		ChartBasic filtros = getIndicadoresIngresosSaldo(anio, listExistencia);

		ChartBasic result = new ChartBasic();

		List<Double> sumDisponibleList = new ArrayList<Double>();
		for (int j = 0; j < disponible.getLabels().size(); j++) {
			sumDisponibleList.add(0d);
		}

		for (List<Double> disponibleList : disponible.getData()) {
			for (int j = 0; j < disponible.getLabels().size(); j++) {
				if (disponibleList.get(j) == null) {
					disponibleList.set(j, 0d);
				}
				sumDisponibleList.set(j, sumDisponibleList.get(j) + disponibleList.get(j));
			}

		}

		List<Double> sumfiltrosList = new ArrayList<Double>();
		for (int j = 0; j < filtros.getLabels().size(); j++) {
			sumfiltrosList.add(0d);
		}

		for (List<Double> filtrosList : filtros.getData()) {
			for (int j = 0; j < disponible.getLabels().size(); j++) {
				if (filtrosList.get(j) == null) {
					filtrosList.set(j, 0d);
				}
				sumfiltrosList.set(j, sumfiltrosList.get(j) + filtrosList.get(j));
			}

		}

		List<Double> porcentajefiltrosList = new ArrayList<Double>();
		for (int j = 0; j < disponible.getLabels().size(); j++) {
			porcentajefiltrosList.add(0d);
		}

		for (List<Double> filtrosList : filtros.getData()) {
			for (int j = 0; j < disponible.getLabels().size(); j++) {
				if (sumDisponibleList.get(j) == 0d) {
					porcentajefiltrosList.set(j, 0d);
				} else {
					try {
						porcentajefiltrosList.set(j,
								MathUtil.redondearEn2(sumfiltrosList.get(j) * 100 / sumDisponibleList.get(j)));
					} catch (Exception e) {
						porcentajefiltrosList.set(j, 0d);
					}
				}

			}

		}

		List<String> series = new ArrayList<String>();

		series.add("Porcentaje saldo filtros sobre diponible");

		ChartBasic resultChartBasic = new ChartBasic();

		List<List<Double>> datarResultChartBasic = new ArrayList<List<Double>>();

		datarResultChartBasic.add(porcentajefiltrosList);

		resultChartBasic.setData(datarResultChartBasic);

		resultChartBasic.setSeries(series);

		resultChartBasic.setLabels(disponible.getLabels());

		return resultChartBasic;
	}

}
