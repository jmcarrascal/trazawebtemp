package ar.com.cipres.services.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ar.com.cipres.dao.IDespachosDAO;
import ar.com.cipres.dao.IDetaViajesDAO;
import ar.com.cipres.dao.IDomiciliosDAO;
import ar.com.cipres.dao.IExistenciasDAO;
import ar.com.cipres.dao.IGenteDAO;
import ar.com.cipres.dao.IItemsDAO;
import ar.com.cipres.dao.IKardexDAO;
import ar.com.cipres.dao.INumeracionesDAO;
import ar.com.cipres.dao.IParametrizacionDAO;
import ar.com.cipres.dao.IPrefijoExistenciaDAO;
import ar.com.cipres.dao.IStockDAO;
import ar.com.cipres.dao.ITipoComprobanteDAO;
import ar.com.cipres.dao.ITrackingDAO;
import ar.com.cipres.dao.ITranAnexoDAO;
import ar.com.cipres.dao.ITransacDAO;
import ar.com.cipres.dao.ITransporteDAO;
import ar.com.cipres.dao.ITrazabiDAO;
import ar.com.cipres.dao.impl.StockDAO;
import ar.com.cipres.model.ChartBasic;
import ar.com.cipres.model.Despachos;
import ar.com.cipres.model.DetaViajes;
import ar.com.cipres.model.Domicilios;
import ar.com.cipres.model.Existencias;
import ar.com.cipres.model.Gente;
import ar.com.cipres.model.Impuestos;
import ar.com.cipres.model.Items;
import ar.com.cipres.model.ItemsId;
import ar.com.cipres.model.JsonResultRomaneo;
import ar.com.cipres.model.Kardex;
import ar.com.cipres.model.Numeraciones;
import ar.com.cipres.model.ObjDescripCant;
import ar.com.cipres.model.ObjectPaging;
import ar.com.cipres.model.PrefijoExistencia;
import ar.com.cipres.model.RomaneoMedicamento;
import ar.com.cipres.model.Stock;
import ar.com.cipres.model.TipoComprob;
import ar.com.cipres.model.Tracking;
import ar.com.cipres.model.TranAnexo;
import ar.com.cipres.model.Transac;
import ar.com.cipres.model.Transporte;
import ar.com.cipres.model.Trazabi;
import ar.com.cipres.model.Viaje;
import ar.com.cipres.model.report.EstadoPedidoDeVenta;
import ar.com.cipres.model.report.ExportTransacAfiliados;
import ar.com.cipres.services.IStockService;
import ar.com.cipres.services.ITransacService;
import ar.com.cipres.util.Constants;
import ar.com.cipres.util.DateUtil;
import ar.com.cipres.util.FormatUtil;
import ar.com.cipres.util.MathUtil;
import ar.com.cipres.util.ObjCodigoDescrip;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
@Transactional
public class TransacService implements ITransacService {

	@Autowired
	private IStockService stockService;
	
	@Autowired
	private IParametrizacionDAO parametrizacionDAO;

	@Autowired
	private ITransporteDAO transporteDAO;

	@Autowired
	private IDomiciliosDAO domiciliosDAO;

	@Autowired
	private ITranAnexoDAO tranAnexoDAO;

	@Autowired
	private IKardexDAO kardexDAO;

	@Autowired
	private ITransacDAO transacDAO;

	@Autowired
	private IExistenciasDAO existenciasDAO;

	@Autowired
	private IItemsDAO itemsDAO;

	@Autowired
	private ITrazabiDAO trazabiDAO;

	@Autowired
	private ITipoComprobanteDAO tipoComprobanteDAO;

	@Autowired
	private ITrackingDAO trackingDAO;

	@Autowired
	private IPrefijoExistenciaDAO prefijoExistenciaDAO;

	@Autowired
	private IDespachosDAO despachosDAO;

	@Autowired
	private IGenteDAO genteDAO;

	@Autowired
	private IStockDAO stockDAO;

	@Autowired
	private IDetaViajesDAO detaViajesDAO;

	@Autowired
	private INumeracionesDAO numeracionesDAO;

	public JsonResultRomaneo getRomaneoOc(Integer transac_oc_long) {
		Transac transac = transacDAO.getByPrimaryKey(transac_oc_long);
		List<Items> itemsList = itemsDAO.getOcByTransac(transac_oc_long, false);
		if (transac_oc_long == 0) {
			return new JsonResultRomaneo(true, "Romaneo no calculado por no tener OC", null, "");
		}
		if (itemsList == null || itemsList.size() == 0) {
			return new JsonResultRomaneo(false, "La orden de compra ingresada no es valida", null, "");
		}
		List<RomaneoMedicamento> romaneoMedicamentoList = new ArrayList<RomaneoMedicamento>();
		for (Items items : itemsList) {

			RomaneoMedicamento romaneoMedicamento = new RomaneoMedicamento(items.getStock().getGtin(),
					items.getStock().getId(), items.getCant1(), items.getCant1entregado(), items.getDescrip());
			romaneoMedicamentoList.add(romaneoMedicamento);
		}
		Existencias existencias = new Existencias();
		if (parametrizacionDAO.getByPrimaryKey(Constants.PARAM_USE_EXIT_DEFAULT).getValor().equals("true")) {
			existencias.setNr(
					Integer.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_DEFAULT).getValor()));
		} else {

			switch (transac.getPrefijo()) {
			case "0033":
				existencias.setNr(15);
				break;
			case "0024":
				existencias.setNr(1);
				break;
			case "0022":
				existencias.setNr(2);
				break;
			case "0019":
				existencias.setNr(19);
				break;
			default:
				existencias.setNr(2);
				break;
			}

		}
		existencias = existenciasDAO.getByPrimaryKey(existencias.getNr());
		return new JsonResultRomaneo(true, "Ok", romaneoMedicamentoList, transac.getObservaciones(),
				"[" + existencias.getNr() + "] - " + existencias.getDescrip());
	}

	public List<Object> getSaldoActualByAgendados(List<Object> objectList) {

		for (Object genteObj : objectList) {
			Gente gente = (Gente) genteObj;
			Integer[] listTipoComprVentas = { 1, 2, 4, 5, 6, 7 };
			List<Transac> transacVentasList = transacDAO.getCuentaCorrienteVenta(listTipoComprVentas, gente.getId());
			gente.setSaldo(getTotalSaldoVentasPorGente(transacVentasList));
		}

		return objectList;
	}

	public Double getTotalSaldoVentasPorGente(List<Transac> transacVentasList) {
		Double totalSaldo = 0d;

		for (Transac transac : transacVentasList) {
			totalSaldo = totalSaldo + transac.getSaldoCalculado();
		}

		return MathUtil.redondear(totalSaldo);
	}

	public String exportTransacMedifeInterno(JSONObject jsonObject) {
		JsonParser jsonParser = new JsonParser();
		JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
		String fechaDesde = gsonObject.has("fechaDesde") ? gsonObject.get("fechaDesde").getAsString() : "";
		String fechaHasta = gsonObject.has("fechaHasta") ? gsonObject.get("fechaHasta").getAsString() : "";
		Integer genteNr = gsonObject.has("genteNr") ? gsonObject.get("genteNr").getAsInt() : 0;
		JsonArray selectedList = gsonObject.get("selectedList").getAsJsonArray();
		String list = "";
		for (final JsonElement obj : selectedList) {
			if (list == "") {
				list = list + obj.getAsInt();
			} else {
				list = list + ", " + obj.getAsInt();
			}

		}
		list = "(" + list + ")";

		List<ExportTransacAfiliados> exportTransacAfiliadosList = transacDAO.getTransacAfiliadosItems(genteNr,
				new java.sql.Date(DateUtil.getDate(fechaDesde).getTime()),
				new java.sql.Date(DateUtil.getDate(fechaHasta).getTime()), list, 1);
		/*
		 * Tipo de Comprobante Char(3) 1 3 FAC Letra del Comprobante Char(1) 4 4
		 * Punto de Emision Num(3) 5 7 Numero de Comprobante Num(8) 8 15 Fecha
		 * Emisión Comprobante Num(8) 16 23 AAAAMMDD Monto TOTAL Gravado
		 * Comprobante Num(11) 24 34 Monto TOTAL No Gravado Comprobante Num(11)
		 * 35 45 Porcentaje IVA Comprobante Num(6) 46 51 Monto Percepción
		 * Ingresos Brutos Comprobante Num(11) 52 62 Credencial(1) Char(14) 63
		 * 76 Apellido del Asociado y Nombre del Asociado Char(40) 77 116 Fecha
		 * de Prescripción Num(8) 117 124 AAAAMMDD Cantidad del medicamento
		 * Num(8) 125 132 Monto unitario gravado del medicamento Num(11) 133 143
		 * Monto unitario no gravado del medicamento Num(11) 144 154 Código
		 * Medicamento(2) Char(9) 155 163 Tipo Código Medicamento(3) Char(1) 164
		 * 164 K, T, A
		 */
		String resultFinal = "";
		for (ExportTransacAfiliados exportTransacAfiliados : exportTransacAfiliadosList) {
			Transac remito = transacDAO.getByTransacAnterior(exportTransacAfiliados.getTransacNr());
			String result = "";
			result = result + "FAC";
			result = result + exportTransacAfiliados.getLetra();
			result = result + exportTransacAfiliados.getPrefijo().substring(1);
			result = result + exportTransacAfiliados.getNrComprob();
			try {
				result = result + DateUtil.getDate(remito.getFecha(), "yyyyMMdd");
			} catch (Exception e) {
				result = result + DateUtil.getDate(exportTransacAfiliados.getFecha(), "yyyyMMdd");
			}
			result = result + FormatUtil.getNumberString(exportTransacAfiliados.getNetoGrav().doubleValue(), 11);
			result = result + FormatUtil.getNumberString(exportTransacAfiliados.getNetoNoGrav().doubleValue(), 11);
			if (exportTransacAfiliados.getIvaGravado() == 3) {
				result = result + FormatUtil.getNumberString(0d, 6);
			} else {
				result = result + FormatUtil.getNumberString(exportTransacAfiliados.getAlicuotaIva().doubleValue(), 6);
			}
			result = result + FormatUtil.getNumberString(exportTransacAfiliados.getRetIb().doubleValue(), 11);
			if (exportTransacAfiliados.getNrAfiliado() == null) {
				result = result + FormatUtil.llenoConEspaciosDerecha("", 14);
			} else {
				if (exportTransacAfiliados.getNrAfiliado().trim().length() > 14) {
					exportTransacAfiliados
							.setNrAfiliado(exportTransacAfiliados.getNrAfiliado().trim().substring(0, 14));
				}
				result = result + FormatUtil.llenoConEspaciosDerecha(exportTransacAfiliados.getNrAfiliado().trim(), 14);
			}
			if (exportTransacAfiliados.getApellidoAfiliado() == null) {
				result = result + FormatUtil.llenoConEspaciosDerecha("", 40);
			} else {
				String nombreA = "";
				String nombreTotal = "";
				if (exportTransacAfiliados.getNombreAfiliado() == null) {
					nombreA = "";
				}else{
					nombreA = exportTransacAfiliados.getNombreAfiliado();
				}
				nombreTotal = exportTransacAfiliados.getApellidoAfiliado().trim() + " " + nombreA; 
				if (nombreTotal.trim().length() > 40) {
					exportTransacAfiliados
							.setApellidoAfiliado(nombreTotal.trim().substring(0, 40));
				}
				result = result
						+ FormatUtil.llenoConEspaciosDerecha(nombreTotal.trim(), 40);
			}
			try {
				result = result + DateUtil.getDate(remito.getFecha(), "yyyyMMdd");
			} catch (Exception e) {
				result = result + DateUtil.getDate(exportTransacAfiliados.getFecha(), "yyyyMMdd");
			}
			result = result + FormatUtil.getNumberString(exportTransacAfiliados.getCant1(), 8);
			if (exportTransacAfiliados.getIvaGravado() == 3) {
				result = result + FormatUtil.getNumberString(0d, 11);
				result = result + FormatUtil.getNumberString(exportTransacAfiliados.getPrecio().doubleValue(), 11);
			} else {
				result = result + FormatUtil.getNumberString(exportTransacAfiliados.getPrecio().doubleValue(), 11);
				result = result + FormatUtil.getNumberString(0d, 11);
			}
			if (exportTransacAfiliados.getTroquel() == null) {
				result = result + FormatUtil.llenoConEspaciosDerecha("", 9);
			} else {
				if (exportTransacAfiliados.getTroquel().trim().length() > 9) {
					exportTransacAfiliados.setTroquel(exportTransacAfiliados.getTroquel().trim().substring(0, 9));
				}
				result = result + FormatUtil.llenoConEspaciosDerecha(exportTransacAfiliados.getTroquel().trim(), 9);
			}
			result = result + "T";
			System.out.println(result);
			resultFinal = resultFinal + result + "\n";
		}
		return resultFinal;
	}

	public List<Object> getTransacMedifeInterno(JSONObject jsonObject) {
		JsonParser jsonParser = new JsonParser();
		JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
		String fechaDesde = gsonObject.has("fechaDesde") ? gsonObject.get("fechaDesde").getAsString() : "";
		String fechaHasta = gsonObject.has("fechaHasta") ? gsonObject.get("fechaHasta").getAsString() : "";
		// Medife Interno id 3922
		Integer genteNr = gsonObject.has("genteNr") ? gsonObject.get("genteNr").getAsInt() : 0;
		List<Object> exportTransacAfiliadosList = transacDAO.getTransacAfiliados(genteNr,
				new java.sql.Date(DateUtil.getDate(fechaDesde).getTime()),
				new java.sql.Date(DateUtil.getDate(fechaHasta).getTime()));
		return exportTransacAfiliadosList;
	}

	public List<Object> getTransacReport(Integer transac_oc_long) {
		Transac transac = transacDAO.getByPrimaryKey(transac_oc_long);
		Domicilios domicilioPrincipal = domiciliosDAO.getDomicilioPrincipal(transac.getGente().getId());
		Domicilios domicilioEntrega = domiciliosDAO.getByPrimaryKey(transac.getNrDomicilioEnt());
		transac.setDomicilioPrincipal(domicilioPrincipal);
		transac.setDomicilioEntrega(domicilioEntrega);
		List<Items> itemsList = itemsDAO.getTransacByIdTransac(transac_oc_long);
		List<Object> objList = new ArrayList<Object>();
		for (Items item : itemsList) {
			item.setTransac(transac);
			item.getTransac().setFechaTransacLong(transac.getFechaTransac().getTime());
			BigDecimal bonif = MathUtil.redondearEn4BD(item.getBonif().divide(new BigDecimal(100)));
			BigDecimal bruto = (item.getPrecio().multiply(new BigDecimal(item.getCant1())));
			BigDecimal totalItem = bruto.subtract(bruto.multiply(bonif));
			item.setNrCompInt(String.valueOf(MathUtil.redondearEn2BD(totalItem)));
			totalItem = MathUtil.redondearEn2BD(totalItem);
			item.setTotalItem(totalItem);
			objList.add((Object) item);
		}
		return objList;
	}

	public List<Object> getTiposComprobante() {
		List<TipoComprob> list = tipoComprobanteDAO.getAll();
		List<Object> objList = new ArrayList<Object>();
		for (TipoComprob item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(String.valueOf(item.getNr()), item.getDescripcion());
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	public ChartBasic getIndicadoresPedidoSRemitar() {
		ChartBasic chartBasic = new ChartBasic();
		List<String> series = new ArrayList<String>();
		List<List<Double>> data = new ArrayList<List<Double>>();

		for (PrefijoExistencia prefijoExistencia : prefijoExistenciaDAO.getAll()) {
			Existencias existencia = existenciasDAO.getByPrimaryKey(prefijoExistencia.getNrExistencia());
			series.add(existencia.getDescrip());
			Long transacResult = transacDAO.getPedidoPendienteRemitirSize(DateUtil.getCurrentDateTime0(),
					prefijoExistencia.getNrprefijo());
			List<Double> dataPedido = new ArrayList<Double>();
			dataPedido.add(Double.parseDouble(String.valueOf(transacResult)));
			data.add(dataPedido);
		}

		List<String> labels = new ArrayList<String>();
		labels.add("Pedidos Pendientes al " + DateUtil.getDateSkTime(new Date(System.currentTimeMillis())));
		chartBasic.setSeries(series);
		chartBasic.setData(data);
		chartBasic.setLabels(labels);
		return chartBasic;
	}

	public ChartBasic getIndicadoresPedidoSRemitarPick() {
		ChartBasic chartBasic = new ChartBasic();
		List<String> series = new ArrayList<String>();
		List<List<Double>> data = new ArrayList<List<Double>>();

		for (PrefijoExistencia prefijoExistencia : prefijoExistenciaDAO.getAll()) {
			Existencias existencia = existenciasDAO.getByPrimaryKey(prefijoExistencia.getNrExistencia());
			series.add(existencia.getDescrip());
			Long transacResult = transacDAO.getPedidoPendienteRemitirPickSize(DateUtil.getCurrentDateTime0(),
					prefijoExistencia.getNrprefijo());
			List<Double> dataPedido = new ArrayList<Double>();
			dataPedido.add(Double.parseDouble(String.valueOf(transacResult)));
			data.add(dataPedido);
		}

		List<String> labels = new ArrayList<String>();
		labels.add("Pedidos Pendientes de picking al " + DateUtil.getDateSkTime(new Date(System.currentTimeMillis())));
		chartBasic.setSeries(series);
		chartBasic.setData(data);
		chartBasic.setLabels(labels);
		return chartBasic;
	}

	public ChartBasic getIndicadoresRemitoSinViaje() {
		ChartBasic chartBasic = new ChartBasic();
		List<String> series = new ArrayList<String>();
		List<List<Double>> data = new ArrayList<List<Double>>();

		List<ObjDescripCant> listaOtroLugar = transacDAO.getPedidoPendienteViajesSize();
		List<Double> dataPedido = new ArrayList<Double>();

		List<String> labels = new ArrayList<String>();
		for (ObjDescripCant objDescripCant : listaOtroLugar) {
			labels.add(objDescripCant.getDescripcion());
			dataPedido.add(objDescripCant.getCantidad().doubleValue());
		}

		data.add(dataPedido);

		series.add("Pedidos Pendientes de asignación de viaje al "
				+ DateUtil.getDateSkTime(new Date(System.currentTimeMillis())));
		chartBasic.setSeries(series);
		chartBasic.setData(data);
		chartBasic.setLabels(labels);
		return chartBasic;
	}

	public Transac buildTransac(List<Items> itemsSessionList, Integer transacNr, Integer genteNr, int tipoComprobNr,
			Transac transacOrig) {

		Transac transac = calculoTotales(itemsSessionList, transacNr, transacOrig);
		transac.setTransacNr(transacNr);
		// Set agendado
		Gente gente = genteDAO.getByPrimaryKey(genteNr);
		transac.setGente(gente);
		// Set Fecha Transac
		transac.setFecha(DateUtil.getCurrentDateTime0());
		transac.setFechaTransac(new Timestamp(System.currentTimeMillis()));
		// Set tipo de comprobante
		TipoComprob tipoComprob = tipoComprobanteDAO.getByPrimaryKey(tipoComprobNr);
		transac.setTipoComprob(tipoComprob);
		// TODO falta calculor la letra de la transac
		transac.setLetra("X");

		transac.setTranFactCred(0);
		if (transac.getObservaciones() == null) {
			transac.setObservaciones(".");
		}
		// Agrego valores a los Items
		for (Items item : itemsSessionList) {
			item.setTipoComprob(tipoComprob);
			// item.setFecha(new Date(System.currentTimeMillis()));
			item.setGenteNr(gente.getId());
			//CAMBIE PARE QUE TENGA HORA
			item.setFechaTransac(DateUtil.getCurrentDateTime0());
			item.getId().setTransacNr(transacNr);
			item.setColo(0);
			item.setDescrip(stockDAO.getByPrimaryKey(item.getClave()).getDescripcion());

		}
		// TODO Cargo Prfijo FIJO en 0000
		transac.setPrefijo("0005");

		// Set Vendedor sino se especifica el vendedor se toma del agendado
		if (transacOrig.getVendedor() == null || transacOrig.getVendedor() == -1 || transacOrig.getVendedor() == 0) {
			transac.setVendedor(gente.getVendedorId());
		} else {
			transac.setVendedor(transacOrig.getVendedor());
		}

		transac.setOrdenCompra(transacOrig.getOrdenCompra());
		transac.setCuotas(gente.getComisionVenta().shortValue());
		transac.setCobrador(gente.getCobradorId());

		// Set condicion de venta
		if (transac.getCondiciones() == null || transac.getCondiciones() == 0) {
			transac.setCondiciones(gente.getCondicionVenta());
		}
		transac.setTipoIva(gente.getCodImputacionContableId());
		// TODO Falta buscar Impuestos para poder hacer join
		transac.setAlicuotaIva(0f);
		transac.setItemsList(itemsSessionList);
		transac.setSaldo(transac.getTotal());
		transac.setNrDomicilioEnt(transacOrig.getNrDomicilioEnt());
		try {
			transac.setBenef(transacOrig.getBenef());
		} catch (Exception e) {
		}

		Integer idNumeracionComprom = 0;
		switch (tipoComprobNr) {
		case 8:
			idNumeracionComprom = getUltimaNumeracion(Constants.ID_NUMERACIONES_PEDIDO_VTA);
			break;
		case 15:
			idNumeracionComprom = getUltimaNumeracion(Constants.ID_NUMERACIONES_PEDIDO_COMPRA);
			break;
		case 16:
			idNumeracionComprom = getUltimaNumeracion(Constants.ID_NUMERACIONES_PROFORMA);
			break;
		}
		transac.setNrComprob(FormatUtil.llenoConCeros(String.valueOf(idNumeracionComprom), 8));

		transac.setDestino(-1);

		return transac;
	}

	public Transac calculoTotales(List<Items> itemsSessionList, Integer genteNr, Transac transac) {
		int countItem = 1;
		for (Items item : itemsSessionList) {
			Integer cantidad = itemsSessionList.size();
			item.setStock(new Stock(item.getClave().toUpperCase()));

			item.setTalle(".");

			item.setFechaTransac(DateUtil.getCurrentDate());
			item.setFecha(DateUtil.getCurrentDate());
			item.setNrFabInt(".");
			item.setLetra("X");
			item.setCant2(item.getCant1());
			cantidad++;
			//item.getId().setItemNr(cantidad);
			// BigDecimal bonif =
			// MathUtil.redondearEn4BD(items.getBonif().divide(new
			// BigDecimal(100)));
			BigDecimal bonif = MathUtil.redondearEn4BD(item.getBonif().divide(new BigDecimal(100)));
			BigDecimal bruto = (item.getPrecio().multiply(BigDecimal.valueOf(item.getCant1())));
			BigDecimal totalItem = bruto.subtract(bruto.multiply(bonif));
			item.setNrCompInt(String.valueOf(MathUtil.redondearEn2BD(totalItem)));
			totalItem = MathUtil.redondearEn2BD(totalItem);
			item.setTotalItem(totalItem);
			// Calculo el impueto del Item
			item = generateImpuesto(item);

			ItemsId id = new ItemsId();
			id.setItemNr(countItem);
			item.setId(id);
			
			
			countItem++;

		}

		BigDecimal subTotal = new BigDecimal(0);
		BigDecimal totalImpuestos = new BigDecimal(0);
		for (Items itemsL : itemsSessionList) {
			subTotal = subTotal.add(itemsL.getTotalItem());
			totalImpuestos = totalImpuestos.add(itemsL.getTotalImpuesto());
		}
		subTotal = MathUtil.redondearEn2BD(subTotal);
		totalImpuestos = MathUtil.redondearEn2BD(totalImpuestos);
		transac.setNetoGrav(subTotal);
		transac.setIva(totalImpuestos);
		transac.setTotal(subTotal.add(totalImpuestos));
		return transac;
	}

	/**
	 * Calculo impuesto de un articulo
	 */
	private Items generateImpuesto(Items item) {
		Stock articulo = stockDAO.getByPrimaryKey(item.getStock().getId());
		Impuestos impuesto = articulo.getImpuestos();
		item.setPorcentajeImpuesto(impuesto.getAlicuota());
		item.setTotalImpuesto(
				item.getTotalItem().multiply(new BigDecimal(impuesto.getAlicuota())).divide(new BigDecimal(100)));
		return item;
	}

	public Transac editTransac(Transac transac) {

		Transac transac_result = buildTransac(transac.getItemsList(), transac.getTransacNr(), transac.getGente().getId(),
				transac.getTipoComprob().getNr(), transac);
		transacDAO.update(transac_result);
		for (Items items : transac_result.getItemsList()) {
			items.setTransac(transac_result);
			//try{
			itemsDAO.update(items);
			//}	
			//catch (Exception e) {}
		}
		return transac_result;
	}
	
	public Transac saveTransac(Transac transac) {

		Integer transacNr = getUltimaNumeracion(Constants.ID_NUMERACIONES_TRANSAC);
		transacNr = Integer.parseInt(String.valueOf(transacNr) + "1");
		Transac transac_result = buildTransac(transac.getItemsList(), transacNr, transac.getGente().getId(),
				transac.getTipoComprob().getNr(), transac);
		transacDAO.save(transac_result);
		for (Items items : transac_result.getItemsList()) {
			items.setTransac(transac_result);
			//try{
			itemsDAO.save(items);
			//}	
			//catch (Exception e) {}
		}
		return transac_result;
	}

	public Integer getUltimaNumeracion(Integer tipoNumeracion) {
		Numeraciones ultimaNumeracion = numeracionesDAO.getByPrimaryKey(tipoNumeracion);
		Integer valor = ultimaNumeracion.getLetraA() + 1;
		ultimaNumeracion.setLetraA(valor);
		numeracionesDAO.update(ultimaNumeracion);
		return valor;
	}

	public ObjectPaging getArticuloTransacciones(JSONObject jsonObject) {
		ObjectPaging objectPaging = new ObjectPaging();
		List<Object> objectList = new ArrayList<Object>();

		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
			String articuloId = gsonObject.has("articuloId") ? gsonObject.get("articuloId").getAsString() : "";
			JsonObject gsonObjectResult = gsonObject.get("busqueda").getAsJsonObject();
			String texto = gsonObjectResult.has("texto") ? gsonObjectResult.get("texto").getAsString() : "";
			int tipoComprobante = gsonObjectResult.has("tipoComprobante")
					? gsonObjectResult.get("tipoComprobante").getAsInt() : 0;

			int pageNumber = gsonObject.has("pageNumber") ? gsonObject.get("pageNumber").getAsInt() : 0;
			int pageSize = gsonObject.has("pageSize") ? gsonObject.get("pageSize").getAsInt() : 0;
			Long total_items = 0l;

			objectList = transacDAO.getArticuloTransacList(articuloId, texto, tipoComprobante, pageNumber, pageSize);
			if (objectList != null && objectList.size() > 0) {
				total_items = transacDAO.getArticuloTransacListCount(articuloId, texto, tipoComprobante, pageNumber,
						pageSize);
			}
			objectPaging = new ObjectPaging(objectList, total_items);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectPaging;
	}

	public ObjectPaging getDomiciliosTransacciones(JSONObject jsonObject) {
		ObjectPaging objectPaging = new ObjectPaging();
		List<Object> objectList = new ArrayList<Object>();

		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
			int domicilioId = gsonObject.has("domicilioId") ? gsonObject.get("domicilioId").getAsInt() : -1;
			JsonObject gsonObjectResult = gsonObject.get("busqueda").getAsJsonObject();
			String texto = gsonObjectResult.has("texto") ? gsonObjectResult.get("texto").getAsString() : "";
			int tipoComprobante = gsonObjectResult.has("tipoComprobante")
					? gsonObjectResult.get("tipoComprobante").getAsInt() : 0;

			int pageNumber = gsonObject.has("pageNumber") ? gsonObject.get("pageNumber").getAsInt() : 0;
			int pageSize = gsonObject.has("pageSize") ? gsonObject.get("pageSize").getAsInt() : 0;
			Long total_items = 0l;

			objectList = transacDAO.getDomiciliosTransacList(domicilioId, texto, tipoComprobante, pageNumber, pageSize);
			if (objectList != null && objectList.size() > 0) {
				total_items = transacDAO.getDomiciliosTransacListCount(domicilioId, texto, tipoComprobante, pageNumber,
						pageSize);

			}
			objectPaging = new ObjectPaging(objectList, total_items);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectPaging;
	}

	public TipoComprob getTipoComprobante(Integer id) {
		return tipoComprobanteDAO.getByPrimaryKey(id);
	}
	public ObjectPaging getDomiciliosTransaccionesAgrupados(JSONObject json) {
		ObjectPaging objectPaging = new ObjectPaging();
		List<Object> objectList = new ArrayList<Object>();
		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject gsonObject = (JsonObject) jsonParser.parse(json.toString());
			int domicilioId = gsonObject.has("domicilioId") ? gsonObject.get("domicilioId").getAsInt() : -1;
			JsonObject gsonObjectResult = gsonObject.get("busqueda").getAsJsonObject();
			String texto = gsonObjectResult.has("texto") ? gsonObjectResult.get("texto").getAsString() : "";
			int tipoComprobante = gsonObjectResult.has("tipoComprobante")
					? gsonObjectResult.get("tipoComprobante").getAsInt() : 0;

			int pageNumber = gsonObject.has("pageNumber") ? gsonObject.get("pageNumber").getAsInt() : 0;
			int pageSize = gsonObject.has("pageSize") ? gsonObject.get("pageSize").getAsInt() : 0;
			Long total_items = 0l;
			tipoComprobante = Constants.ID_PEDIDO_VENTA;
			objectList = transacDAO.getDomiciliosTransacList(domicilioId, texto, tipoComprobante, pageNumber, pageSize);
			if (objectList != null && objectList.size() > 0) {
				total_items = transacDAO.getDomiciliosTransacListCount(domicilioId, texto, tipoComprobante, pageNumber,
						pageSize);
				for (Object objtransac : objectList) {
					Transac transac = (Transac) objtransac;
					try {
						transac.setDomicilioEntrega(domiciliosDAO.getByPrimaryKey(transac.getNrDomicilioEnt()));
					} catch (Exception e) {
						e.printStackTrace();
					}

					Integer idMadre = -1;
					List<Tracking> trackingHijoList = trackingDAO.getTrackingByHijo(transac.getTransacNr());
					if (trackingHijoList != null && trackingHijoList.size() > 0) {
						idMadre = trackingHijoList.get(0).getMadre();
					} else {

						transac.setEstadoPedidoDeVenta(
								new EstadoPedidoDeVenta(1, "En preparación", transac.getFecha()));
					}
					List<Tracking> trackingList = trackingDAO.getTrackingMadre(idMadre);

					if (trackingList != null && trackingList.size() > 0) {
						Transac remito = null;
						Transac factura = null;
						remito = transacDAO.getByPrimaryKey(idMadre);
						for (Tracking tracking : trackingList) {
							Transac tmp = transacDAO.getByPrimaryKey(tracking.getHijo());
							if (tmp.getTipoComprob().getNr() == 1) {
								factura = tmp;
							}
						}
						if (factura == null) {
							factura = transacDAO.getByTransacAnterior(idMadre);
						}

						if (factura != null) {
							transac.setEstadoPedidoDeVenta(
									new EstadoPedidoDeVenta(4, "Retirado por afiliado", factura.getFecha()));
							DetaViajes detaViajes = detaViajesDAO.getByRemito(remito.getTransacNr());
							if (detaViajes != null) {
								Viaje viaje = detaViajesDAO.getViaje(detaViajes.getViajeNr());
								Transporte transporte = transporteDAO.getByPrimaryKey(viaje.getChoferNr());
								transac.setTransportista(transporte.getDescripcion());
							}
						} else {
							if (remito != null) {
								if (remito.getFechaEntrega() != null) {
									transac.setEstadoPedidoDeVenta(
											new EstadoPedidoDeVenta(4, "Pedido en farmacia", remito.getFechaEntrega()));
									DetaViajes detaViajes = detaViajesDAO.getByRemito(remito.getTransacNr());
									if (detaViajes != null) {
										Viaje viaje = detaViajesDAO.getViaje(detaViajes.getViajeNr());
										Transporte transporte = transporteDAO.getByPrimaryKey(viaje.getChoferNr());
										transac.setTransportista(transporte.getDescripcion());
									}
								} else {
									// Pregunto si hay viaje
									DetaViajes detaViajes = detaViajesDAO.getByRemito(remito.getTransacNr());
									if (detaViajes != null) {
										Viaje viaje = detaViajesDAO.getViaje(detaViajes.getViajeNr());
										Transporte transporte = transporteDAO.getByPrimaryKey(viaje.getChoferNr());
										transac.setTransportista(transporte.getDescripcion());
										transac.setEstadoPedidoDeVenta(
												new EstadoPedidoDeVenta(3, "Pedido en distribución", viaje.getFecha()));
									} else {
										transac.setEstadoPedidoDeVenta(
												new EstadoPedidoDeVenta(2, "Pedido en preparación", remito.getFecha()));
									}
								}
							} else {
								transac.setEstadoPedidoDeVenta(
										new EstadoPedidoDeVenta(1, "Pedido en preparación", transac.getFecha()));
							}
						}
					}

				}

			}

			objectPaging = new ObjectPaging(objectList, total_items);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectPaging;
	}

	public String exportTransacIndosur(JSONObject jsonObject) {
		JsonParser jsonParser = new JsonParser();
		JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
		String fechaDesde = gsonObject.has("fechaDesde") ? gsonObject.get("fechaDesde").getAsString() : "";
		String fechaHasta = gsonObject.has("fechaHasta") ? gsonObject.get("fechaHasta").getAsString() : "";
		Integer genteNr = gsonObject.has("genteNr") ? gsonObject.get("genteNr").getAsInt() : 0;
		JsonArray selectedList = gsonObject.get("selectedList").getAsJsonArray();
		String list = "";
		for (final JsonElement obj : selectedList) {
			if (list == "") {
				list = list + obj.getAsInt();
			} else {
				list = list + ", " + obj.getAsInt();
			}

		}
		list = "(" + list + ")";

		List<ExportTransacAfiliados> exportTransacAfiliadosList = transacDAO.getTransacAfiliadosItems(genteNr,
				new java.sql.Date(DateUtil.getDate(fechaDesde).getTime()),
				new java.sql.Date(DateUtil.getDate(fechaHasta).getTime()), list, 3);

		//
		String resultFinal = "";
		for (ExportTransacAfiliados exportTransacAfiliados : exportTransacAfiliadosList) {
			// Obtengo el kardex para el remito
			List<Kardex> kardexList = kardexDAO.getByTransacItem(exportTransacAfiliados.getTransacNr(),
					exportTransacAfiliados.getClaveMedicamento());

			// Consulto en trazabi
			List<Trazabi> trazabiList = trazabiDAO.getTransacSalidaMEdicamento(exportTransacAfiliados.getTransacNr(),
					exportTransacAfiliados.getClaveMedicamento());

			int nrTrazabi = 0;

			for (Kardex kardex : kardexList) {
				Integer cantidad = (int) kardex.getCantidad1();
				for (int g = 0; g < cantidad; g++) {

					String result = "";
					// Factura
					result = result + FormatUtil.llenoConEspaciosDerecha("", 20);
					// Fecha Remito
					try {
						result = result + DateUtil.getDate(exportTransacAfiliados.getFecha(), "dd/MM/yyyy");
					} catch (Exception e) {
						result = result + DateUtil.getDate(exportTransacAfiliados.getFecha(), "dd/MM/yyyy");
					}
					// Remito
					result = result + FormatUtil.llenoConEspaciosDerecha(exportTransacAfiliados.getLetra() + "-"
							+ Integer.parseInt(exportTransacAfiliados.getPrefijo()) + "-"
							+ Integer.parseInt(exportTransacAfiliados.getNrComprob()), 18);

					// NrAfiliado
					if (exportTransacAfiliados.getNrAfiliado() == null) {
						result = result + FormatUtil.llenoConEspaciosDerecha("", 15);
					} else {
						if (exportTransacAfiliados.getNrAfiliado().trim().length() > 15) {
							exportTransacAfiliados
									.setNrAfiliado(exportTransacAfiliados.getNrAfiliado().trim().substring(0, 15));
						}
						result = result
								+ FormatUtil.llenoConEspaciosDerecha(exportTransacAfiliados.getNrAfiliado().trim(), 15);
					}
					// Nombre
					if (exportTransacAfiliados.getApellidoAfiliado() == null) {
						result = result + FormatUtil.llenoConEspaciosDerecha("", 70);
					} else {
						if (exportTransacAfiliados.getApellidoAfiliado().trim().length() > 70) {
							exportTransacAfiliados.setApellidoAfiliado(
									exportTransacAfiliados.getApellidoAfiliado().trim().substring(0, 70));
						}
						result = result + FormatUtil
								.llenoConEspaciosDerecha(exportTransacAfiliados.getApellidoAfiliado().trim(), 70);
					}
					// Domicilio
					result = result + FormatUtil.llenoConEspaciosDerecha("", 70);
					// Localidad
					result = result + FormatUtil.llenoConEspaciosDerecha("", 70);
					// Reg
					result = result + FormatUtil.llenoConEspaciosDerecha("", 15);
					// Troquel
					if (exportTransacAfiliados.getTroquel() == null) {
						result = result + FormatUtil.llenoConEspaciosDerecha("", 15);
					} else {
						if (exportTransacAfiliados.getTroquel().trim().length() > 15) {
							exportTransacAfiliados
									.setTroquel(exportTransacAfiliados.getTroquel().trim().substring(0, 15));
						}
						result = result
								+ FormatUtil.llenoConEspaciosDerecha(exportTransacAfiliados.getTroquel().trim(), 15);
					}
					// Nombre Comercial
					if (exportTransacAfiliados.getNombreMedicamento() == null) {
						result = result + FormatUtil.llenoConEspaciosDerecha("", 70);
					} else {
						if (exportTransacAfiliados.getNombreMedicamento().trim().length() > 70) {
							exportTransacAfiliados.setNombreMedicamento(
									exportTransacAfiliados.getNombreMedicamento().trim().substring(0, 70));
						}
						result = result + FormatUtil
								.llenoConEspaciosDerecha(exportTransacAfiliados.getNombreMedicamento().trim(), 70);
					}

					// Cantidad
					/*
					String cantidad = "0";
					try {
						int cantInt = (int) Math.round(kardex.getCantidad1());
						cantidad = String.valueOf(cantInt);
					} catch (Exception e) {

					}
					
					result = result + FormatUtil.llenoConEspaciosDerecha(cantidad, 4);
					*/
					result = result + FormatUtil.llenoConEspaciosDerecha("1", 4);

					// Obtengo el lote por el despacho
					try {
						Despachos despachos = despachosDAO.getByPrimaryKey(kardex.getSubtotal().intValue());
						String lote = "";
						if (despachos.getSoloLote() == null) {
							lote = FormatUtil.parseLote(despachos.getDescrip());
							// Lote
							result = result + FormatUtil.llenoConEspaciosDerecha(String.valueOf(lote), 15);
						} else {
							// Lote
							result = result
									+ FormatUtil.llenoConEspaciosDerecha(String.valueOf(despachos.getSoloLote()), 15);
						}

						// Vencimiento
						try {
							result = result + DateUtil.getDate(despachos.getFechaIng(), "dd/MM/yyyy");
						} catch (Exception e) {
							result = result + FormatUtil.llenoConEspaciosDerecha("", 10);
						}
					} catch (Exception ex) {
						// Lote
						result = result + FormatUtil.llenoConEspaciosDerecha("XXX", 15);
						// Vencimiento
						result = result + DateUtil.getDate(
								DateUtil.sumarFechasDias(exportTransacAfiliados.getFecha(), 365), "dd/MM/yyyy");
					}

					if (trazabiList.size() > 0) {
						try {
							// Serie
							result = result
									+ FormatUtil.llenoConEspaciosDerecha(trazabiList.get(nrTrazabi).getSerieGtin(), 20);
						} catch (Exception e) {
							e.printStackTrace();
							// Serie
							result = result + FormatUtil.llenoConEspaciosDerecha("", 20);
						}
					} else {
						// Serie
						result = result + FormatUtil.llenoConEspaciosDerecha("", 20);

					}

					// Interno
					result = result + FormatUtil
							.llenoConEspaciosDerecha(String.valueOf(exportTransacAfiliados.getTransacNr()), 15);

					// Receta
					result = result + FormatUtil
							.llenoConEspaciosDerecha(String.valueOf(exportTransacAfiliados.getReceta()), 15);

					// CostoUnitario

					result = result + FormatUtil.llenoConEspaciosDerecha(exportTransacAfiliados.getTotal(), 10);
					
					
					
					if (trazabiList.size() > 0) {
						try {
							// GTIN
							result = result
									+ FormatUtil.llenoConEspaciosDerecha(trazabiList.get(nrTrazabi).getGtin(), 14);
						} catch (Exception e) {
							e.printStackTrace();
							// GTIN
							result = result + FormatUtil.llenoConEspaciosDerecha("", 14);
						}
					} else {
						// GTIN
						result = result + FormatUtil.llenoConEspaciosDerecha("", 14);

					}
					
					result = result + " ";

					nrTrazabi = nrTrazabi + 1;

					resultFinal = resultFinal + result + System.lineSeparator();
				}
			}
		}
		return resultFinal;
	}

	public List<Object> getTransacRemitos(JSONObject jsonObject) {
		JsonParser jsonParser = new JsonParser();
		JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
		String fechaDesde = gsonObject.has("fechaDesde") ? gsonObject.get("fechaDesde").getAsString() : "";
		String fechaHasta = gsonObject.has("fechaHasta") ? gsonObject.get("fechaHasta").getAsString() : "";
		// Medife Interno id 3922
		Integer genteNr = gsonObject.has("genteNr") ? gsonObject.get("genteNr").getAsInt() : 0;
		List<Object> exportTransacAfiliadosList = transacDAO.getRemitosAfiliados(genteNr,
				new java.sql.Date(DateUtil.getDate(fechaDesde).getTime()),
				new java.sql.Date(DateUtil.getDate(fechaHasta).getTime()));
		return exportTransacAfiliadosList;
	}

	public void updateFechaEntrega(String nrRemito, Integer tipoComprob, java.util.Date fechaEntrega) {
		transacDAO.updateFechaEntrega(nrRemito, tipoComprob, fechaEntrega);

	}

	public void updateFechaEntregaTransac(Integer transacNr, Integer tipoComprob, java.util.Date fechaEntrega) {
		transacDAO.updateFechaTransac(transacNr, tipoComprob, fechaEntrega);

	}

	public List<Transac> getTransacSinEstadoViajes(Integer transporte, java.util.Date fechaDesde) {
		return transacDAO.getTransacSinEstadoViajes(transporte, fechaDesde);

	}

	public List<TranAnexo> getTranAnexoFecha1(Integer fromTransac) {

		return tranAnexoDAO.getTranAnexoFecha1(fromTransac);
	}

	public ObjectPaging getDomiciliosTransaccionesAgrupadosAfiliado(JSONObject json) {
		JsonParser jsonParser = new JsonParser();
		JsonObject gsonObject = (JsonObject) jsonParser.parse(json.toString());
		int obraSocial = gsonObject.has("agendado") ? gsonObject.get("agendado").getAsInt() : -1;
		String afiliado = gsonObject.has("afiliado") ? gsonObject.get("afiliado").getAsString() : "";
		
		Domicilios domicilio = domiciliosDAO.getDomicilioAgendadoAfiliado(obraSocial, afiliado);
		if (domicilio == null) {
			return new ObjectPaging();
		}
		gsonObject.addProperty("domicilioId", domicilio.getId());

		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj = new JSONObject(gsonObject.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getDomiciliosTransaccionesAgrupados(jsonObj);
	}
	
	@Override
	public boolean cancelarPedidoVta(Integer transac_oc_long) {
		try{
			Transac transac = transacDAO.getByPrimaryKey(transac_oc_long);
			if(transac != null)
				transacDAO.remove(transac);
			
			return true;
		}
		catch(Exception ex){
			return false;
		}
	}

	@Override
	public JsonObject getByTransacNr(Integer transac_oc_long) {

		Transac transac = transacDAO.getByPrimaryKey(transac_oc_long);
		
		JsonObject oPedidoVta = new JsonObject();
		oPedidoVta.addProperty("vendedor", transac.getVendedor());
		oPedidoVta.addProperty("nrDomicilioEnt", transac.getNrDomicilioEnt());
		oPedidoVta.addProperty("nrDomicilioAlt", transac.getBenef());
		oPedidoVta.addProperty("benef", transac.getBenef());
		oPedidoVta.addProperty("condiciones", transac.getCondiciones());
		oPedidoVta.addProperty("idGente", transac.getGente().getId());
		oPedidoVta.addProperty("ordenCompra", transac.getOrdenCompra());
		oPedidoVta.addProperty("observaciones", transac.getObservaciones());
		oPedidoVta.addProperty("fechaEntrega", transac.getFechaEntrega().toString());
		
		List<Items> itemsList = itemsDAO.getTransacByIdTransac(transac_oc_long);
		List<JsonObject> jItemsList = new ArrayList<JsonObject>();
		JsonObject oItems;
		
		Integer exi = 0;
		try {
			exi = Integer.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_DEFAULT).getValor());
		} catch (Exception e) {
		}
		
		Existencias exiDefault = existenciasDAO.getByPrimaryKey(exi);
		
		Integer genteId = transac.getGente().getId();
		
		Gente gente = null;

		if (genteId != 0) {
			gente = genteDAO.getByPrimaryKey(genteId);
		}
		
		String formulaPrecios = null;

			try {
				formulaPrecios = parametrizacionDAO.getByPrimaryKey(Constants.ID_FORMULA_GET_PRECIOS).getValor();
			} catch (Exception e) {
			}
		
		for (Items item : itemsList) {
			oItems = new JsonObject();
			oItems.addProperty("cant1", item.getCant1());
			oItems.addProperty("precio", item.getPrecio());
			oItems.addProperty("bonif", item.getBonif());
			
			Stock aux = stockService.findArticuloCalculo(stockDAO.findArticuloById(item.getStock().getId()), exiDefault, gente, formulaPrecios);
			
			aux.setDescripC("[" + aux.getId() + "] " + aux.getDescripcion());
			
			oItems.addProperty("articulo", new Gson().toJson(aux));
			
			jItemsList.add(oItems);
		} 
		
		oPedidoVta.addProperty("itemsList", new Gson().toJson(jItemsList));
		
		return oPedidoVta;
	}

	@Override
	public boolean ordenCompraRepetida(String ordenCompra, Integer idGente) {
		return transacDAO.ordenCompraRepetida(ordenCompra, idGente);
	}

}
