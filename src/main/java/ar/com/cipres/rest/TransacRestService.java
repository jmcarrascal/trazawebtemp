package ar.com.cipres.rest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;

import ar.com.cipres.dao.ICondicionVentaDAO;
import ar.com.cipres.dao.IDomiciliosDAO;
import ar.com.cipres.dao.IIvaCodigoDAO;
import ar.com.cipres.dao.ITransacDAO;
import ar.com.cipres.dao.IVendedorDAO;
import ar.com.cipres.model.ChartBasic;
import ar.com.cipres.model.CondicionVenta;
import ar.com.cipres.model.Domicilios;
import ar.com.cipres.model.Gente;
import ar.com.cipres.model.Items;
import ar.com.cipres.model.IvaCodigo;
import ar.com.cipres.model.JsonResult;
import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.model.JsonResultRomaneo;
import ar.com.cipres.model.ObjectPaging;
import ar.com.cipres.model.TipoComprob;
import ar.com.cipres.model.Transac;
import ar.com.cipres.model.Vendedor;
import ar.com.cipres.services.IReportServiceNew;
import ar.com.cipres.services.ITransacService;
import ar.com.cipres.util.Configuration;
import ar.com.cipres.util.DateUtil;
import ar.com.cipres.util.PdfType;

@Component
@Path("/transac")
public class TransacRestService {

	@Autowired
	private ITransacService transacService;

	@Autowired
	private ITransacDAO transacDAO;
	// @Autowired
	// private IReportJReportService reportJReportService;

	@Autowired
	private IDomiciliosDAO domiciliosDAO;
	
	@Autowired
	private IVendedorDAO vendedorDAO;

	@Autowired
	private ICondicionVentaDAO condicionVentaDAO;
	
	@Autowired
	private IReportServiceNew reportServiceNew;

	@Autowired
	private IIvaCodigoDAO ivaCodigoDAO;

	@GET
	@Path("/getRomaneoOc/{trnsacnr_oc}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultRomaneo getOc(@PathParam("trnsacnr_oc") String trnsacnr_oc) {
		Integer transac_oc_long = 0;
		try {
			transac_oc_long = Integer.parseInt(trnsacnr_oc);
		} catch (NullPointerException ne) {
			return new JsonResultRomaneo(false, "La orden de compra ingresada es invalida", null, "");
		}

		JsonResultRomaneo jsonResultRomaneo = transacService.getRomaneoOc(transac_oc_long);

		return jsonResultRomaneo;
	}

	@POST
	@Path("/getArticuloTransacciones")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getArticuloTransacciones(JSONObject json) {
		try {
			ObjectPaging objectPaging = transacService.getArticuloTransacciones(json);
			return new JsonResultList(true, "OK", objectPaging.getObjectList(), objectPaging.getTotal_items());
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResultList(true, e.getMessage(), null, 0l);
		}
	}

	@POST
	@Path("/getDomiciliosTransacciones")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getDomiciliosTransacciones(JSONObject json) {
		try {
			ObjectPaging objectPaging = transacService.getDomiciliosTransacciones(json);
			return new JsonResultList(true, "OK", objectPaging.getObjectList(), objectPaging.getTotal_items());
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResultList(true, e.getMessage(), null, 0l);
		}
	}

	@POST
	@Path("/getDomiciliosTransaccionesAgrupados")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getDomiciliosTransaccionesAgrupados(JSONObject json) {
		try {
			ObjectPaging objectPaging = transacService.getDomiciliosTransaccionesAgrupados(json);
			return new JsonResultList(true, "OK", objectPaging.getObjectList(), objectPaging.getTotal_items());
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResultList(true, e.getMessage(), null, 0l);
		}
	}

	@POST
	@Path("/exportTransacMedifeInterno")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList exportTransacMedifeInterno(JSONObject json) {
		try {
			String result = transacService.exportTransacMedifeInterno(json);
			return new JsonResultList(true, result, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResultList(false, e.getMessage(), null, 0l);
		}
	}

	@POST
	@Path("/cancelarPedidoVta")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult cancelarPedidoVta(JSONObject jsonObject) {
		JsonResult result = null;
		// Integer transac_oc_long = 0;
		try {

			JsonParser jsonParser = new JsonParser();
			JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
			String id = gsonObject.has("id") ? gsonObject.get("id").getAsString() : "";

			Boolean aux = transacService.cancelarPedidoVta(Integer.parseInt(id));
			if (aux)
				result = new JsonResult(true, "Ok");
			else
				result = new JsonResult(false, "Error");
			return result;
		} catch (NullPointerException ne) {
			return null;
		}

	}

	@POST
	@Path("/exportTransacIndosur")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList exportTransacIndosur(JSONObject json) {
		try {
			String result = transacService.exportTransacIndosur(json);
			return new JsonResultList(true, result, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResultList(false, e.getMessage(), null, 0l);
		}
	}

	@POST
	@Path("/getTransacMedifeInterno")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getTransacMedifeInterno(JSONObject json) {
		try {
			List<Object> list = transacService.getTransacMedifeInterno(json);
			return new JsonResultList(true, "Ok", list, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResultList(false, e.getMessage(), null, 0l);
		}
	}

	@POST
	@Path("/getTransacRemitos")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getTransacRemitos(JSONObject json) {
		try {
			List<Object> list = transacService.getTransacRemitos(json);
			return new JsonResultList(true, "Ok", list, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResultList(false, e.getMessage(), null, 0l);
		}
	}

	@GET
	@Path("/getByTransacNr/{transacnr}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult getByTransacNr(@PathParam("transacnr") String trnsacnr_oc) {
		JsonResult result = null;
		Integer transac_oc_long = 0;
		try {
			transac_oc_long = Integer.parseInt(trnsacnr_oc);
			JsonObject aux = transacService.getByTransacNr(transac_oc_long);
			if (aux != null)
				result = new JsonResult(true, "Ok", new Gson().fromJson(aux, Object.class));
			else
				result = new JsonResult(false, "Error");
			return result;
		} catch (NullPointerException ne) {
			return null;
		}

	}

	@GET
	@Path("/getTransac/{transacnr}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getTransac(@PathParam("transacnr") String trnsacnr_oc) {
		Integer transac_oc_long = 0;
		try {
			transac_oc_long = Integer.parseInt(trnsacnr_oc);
			List<Object> itemsList = transacService.getTransacReport(transac_oc_long);
			return new JsonResultList(true, "ok", itemsList);
		} catch (NullPointerException ne) {
			return new JsonResultList(false, "La orden de compra ingresada es invalida", null);
		}

	}

	@GET
	@Path("/getTiposComprobante")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getTiposComprobante() {
		// Integer transac_oc_long = 0;
		try {
			List<Object> objList = transacService.getTiposComprobante();
			return new JsonResultList(true, "ok", objList);
		} catch (NullPointerException ne) {
			return new JsonResultList(false, "Error al recuperar los tipo de comporbantes", null);
		}

	}

	@GET
	@Path("/getComprobPDFTransac/{transacnr}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult getComprobPDFTransac(@PathParam("transacnr") int trnsacnr_oc) {
		try {
			List<Object> tran = transacService.getTransacReport(trnsacnr_oc);
			JsonObject objToSend = new JsonObject();
			JsonArray listaItems = new JsonArray();
			Transac auxTran = null;
			for (Object obj : tran) {
				Items auxItem = (Items) obj;
				listaItems.add(parseItemToJsonObject(auxItem));
				auxTran = auxItem.getTransac();
			}
			objToSend.add("listaItems", listaItems);
			objToSend.add("proveedor", parseProveedorToJsonObject(null));

			// DOMICILIOS

			Domicilios oEntrega = domiciliosDAO.getByPrimaryKey(auxTran.getDomicilioEntrega().getId());
			if (!auxTran.getBenef().equals("")) {
				Domicilios oAlternativo = domiciliosDAO.getByPrimaryKey(Integer.parseInt(auxTran.getBenef()));
				objToSend.add("domicilioAlternativo", parseDomicilioAlternativoToJsonObject(oAlternativo));
			}
			
			objToSend.add("cliente", parseClienteToJsonObject(auxTran.getGente(), oEntrega, ""));
			objToSend.addProperty("lote", auxTran.getPrefijo());
			objToSend.addProperty("factura", auxTran.getNrComprob() + "/" + auxTran.getTransacNr());
			objToSend.addProperty("tipoIva", "IVA Resp.Insc"); // revisar
			
			Vendedor oVendedor = vendedorDAO.getByPrimaryKey(auxTran.getVendedor());
			
			objToSend.addProperty("atendido", oVendedor.getDescripcion());
			
			objToSend.addProperty("ordenDeCompra", auxTran.getOrdenCompra());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			objToSend.addProperty("fechaEntrega", sdf.format(auxTran.getFechaEntrega()));
			objToSend.addProperty("armador", ""); // revisar
			SimpleDateFormat sdfhm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			objToSend.addProperty("fecha", sdfhm.format(auxTran.getFechaTransac()));
			String terminos = "1) Los precios incluyen IVA\n2) Disponibilidad de unidades: Según Stock\n3) Los descuentos expresados en esta cotizacion seran aplicados a los PVP o Precios sugeridos al Publico a la fecha de confirmacion de la provision\nNota: La entrega sin cargo se realizará en CABA y GBA cuando la compra sea mayor a $500 y en el interior del pais deberá superar los $5.000. En caso de que el envio al interior del país no supere ese monto se adicionará un costo de envio de $250\nCheques a la orden de REDIMER S.A. “SIN LA LEYENDA : NO A LA ORDEN”";
			objToSend.addProperty("terminos", terminos);
			
			CondicionVenta oCondicion = condicionVentaDAO.getByPrimaryKey(auxTran.getCondiciones());
			
			objToSend.addProperty("condicion", oCondicion.getDescripcion());
			objToSend.addProperty("observaciones", auxTran.getObservaciones());
			// Ahi hay que agregar las retenciones, pero no sabemos de donde
			// salen aun
			objToSend.addProperty("retenciones", 0);
			return reportServiceNew.createPdf(objToSend, PdfType.PEDIDO_VENTA_CON_ALTERNATIVO);

			// ***** Esto es para el reporte viejo *****
			/*
			 * List<Object> itemsList =
			 * transacService.getTransacReport(transac_oc_long); Gson gson = new
			 * Gson(); String result = gson.toJson(itemsList); InputStream
			 * stream = new
			 * ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
			 * String base64Report = reportJReportService.getPdfReport(stream,
			 * "comprobante_crm"); return new JsonResult(true, "ok",
			 * base64Report);
			 */
		} catch (NullPointerException ne) {
			return new JsonResult(false, "La orden de compra ingresada es invalida", null);
		} catch (Exception ne) {
			return new JsonResult(false, "Error al parsear respuesta de servicio generador de pdf", null);
		}
	}

	private JsonObject parseItemToJsonObject(Items item) {
		JsonObject auxJItem = new JsonObject();
		auxJItem.addProperty("idArticulo", item.getStock().getId());
		auxJItem.addProperty("descripcion", item.getStock().getDescripcion());
		auxJItem.addProperty("cant", item.getCant1().intValue());
		auxJItem.addProperty("precio", item.getPrecio());
		auxJItem.addProperty("porcentajeIVA", item.getPorcentajeImpuesto());
		return auxJItem;
	}

	private JsonObject parseDomicilioAlternativoToJsonObject(Domicilios dom) {
		JsonObject cliente = new JsonObject();
		if (dom != null) {
			cliente.addProperty("calleYNumero", dom.getDomicilio() == null ? "" : dom.getDomicilio());
			cliente.addProperty("ciudad", dom.getLocalidad() == null ? "" : dom.getLocalidad());
			cliente.addProperty("provincia", dom.getProvincia() == null ? "" : dom.getProvincia());
			cliente.addProperty("codPostal", dom.getCodigoPostal() == null ? "" : dom.getCodigoPostal());
			cliente.addProperty("numTelefono", dom.getTelefonoVoz() == null ? "" : dom.getTelefonoVoz());
		} else {
			cliente.addProperty("calleYNumero", "");
			cliente.addProperty("ciudad", "");
			cliente.addProperty("provincia", "");
			cliente.addProperty("codPostal", "");
			cliente.addProperty("numTelefono", "");
		}
		return cliente;
	}

	private JsonObject parseClienteToJsonObject(Gente gente, Domicilios dom, String tipoTransporte) {
		JsonObject cliente = new JsonObject(); // revisar todo el metodo
		cliente.addProperty("clientNumber", gente.getId());
		cliente.addProperty("razonSocial", gente.getRazonSocial());

		IvaCodigo iva = ivaCodigoDAO.getByPrimaryKey(gente.getTipoIvaId());

		cliente.addProperty("tipoResponsable", iva.getDescripcion());
		cliente.addProperty("cuit", gente.getCuit());
		cliente.addProperty("tipoTransporte", tipoTransporte);
		cliente.addProperty("calleYNumero", dom.getDomicilio() == null ? "" : dom.getDomicilio());
		cliente.addProperty("codPostal", dom.getCodigoPostal() == null ? "" : dom.getCodigoPostal());
		cliente.addProperty("ciudad", dom.getLocalidad() == null ? "" : dom.getLocalidad());
		cliente.addProperty("provincia", dom.getProvincia() == null ? "" : dom.getProvincia());
		cliente.addProperty("numTelefono", dom.getTelefonoVoz() == null ? "" : dom.getTelefonoVoz());
		cliente.addProperty("fax", dom.getTelefonoFax() == null ? "" : dom.getTelefonoFax());
		cliente.addProperty("email", dom.getEmail() == null ? "" : dom.getEmail());
		return cliente;
	}

	private JsonObject parseProveedorToJsonObject(Gente gente) {
		JsonObject proveedor = new JsonObject();
		if (gente != null) {
			proveedor.addProperty("razonSocial", gente.getRazonSocial());
			proveedor.addProperty("calleYNumero", gente.getDomicilioPricipal().getDomicilio());
			proveedor.addProperty("codPostal", gente.getDomicilioPricipal().getCodigoPostal());
			proveedor.addProperty("ciudad", gente.getDomicilioPricipal().getLocalidad());
			proveedor.addProperty("telefono", gente.getDomicilioPricipal().getTelefonoVoz());
			proveedor.addProperty("email", gente.getDomicilioPricipal().getEmail());
			proveedor.addProperty("glnOrigen", gente.getDomicilioPricipal().getGln());
			proveedor.addProperty("paginaWeb", "");
			proveedor.addProperty("cuit", gente.getCuit());
			proveedor.addProperty("iiBB", gente.getNumeroIb());
			proveedor.addProperty("fechaInicioActividades", gente.getFechaIngreso().toString());
		} else {
			proveedor.addProperty("razonSocial", "Redimer S.A.");
			proveedor.addProperty("calleYNumero", "Av. Juan B. Justo 2713");
			proveedor.addProperty("codPostal", "1414");
			proveedor.addProperty("ciudad", "CABA");
			proveedor.addProperty("telefono", "4855-2893/3564/6348");
			proveedor.addProperty("email", "info@drofar.com.ar");
			proveedor.addProperty("glnOrigen", "7798166400002");
			proveedor.addProperty("paginaWeb", "www.drofar.com.ar");
			proveedor.addProperty("cuit", "30-70741652-8");
			proveedor.addProperty("iiBB", "901-214897-5");
			proveedor.addProperty("fechaInicioActividades", "02/2001");
		}
		return proveedor;
	}

	@GET
	@Path("/get_indicadores_pedido_s_remitar")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult getIndicadoresPedidoSRemitar() {
		try {
			ChartBasic result = transacService.getIndicadoresPedidoSRemitar();
			return new JsonResult(true, "ok", result);
		} catch (NullPointerException ne) {
			return new JsonResult(false, "Error al recuperar", null);
		}
	}

	@GET
	@Path("/get_indicadores_pedido_s_remitar_pick")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult getIndicadoresPedidoSRemitarPick() {
		try {
			ChartBasic result = transacService.getIndicadoresPedidoSRemitarPick();
			return new JsonResult(true, "ok", result);
		} catch (NullPointerException ne) {
			return new JsonResult(false, "Error al recuperar", null);
		}
	}

	@GET
	@Path("/get_indicadores_pedido_s_viaje")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult getIndicadoresRemitoSinViaje() {
		try {
			ChartBasic result = transacService.getIndicadoresRemitoSinViaje();
			return new JsonResult(true, "ok", result);
		} catch (NullPointerException ne) {
			return new JsonResult(false, "Error al recuperar", null);
		}
	}

	@POST
	@Path("/editTransac")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public JsonResult editTransac(FormDataMultiPart files,
			@FormDataParam("file") List<FormDataContentDisposition> fileDetail,
			@FormDataParam("transac") String sTransac, @FormDataParam("filesToDelete") String sfilesToDelete) {

		try {
			List<File> lFiles = new ArrayList<>();
			Transac transac = new Gson().fromJson(sTransac, Transac.class);
			List<String> filesToDelete = new Gson().fromJson(sfilesToDelete, List.class);
			List<FormDataBodyPart> lFDBP = files.getFields("file");

			try {
				if (lFDBP != null) {
					for (FormDataBodyPart fdbpItem : lFDBP) {
						lFiles.add(fdbpItem.getEntityAs(File.class));
					}
				}
				transac.setFechaEntrega(DateUtil.getDateStringParseFormat(transac.getFechaTemp(), "ddMMyyyy"));
				if (transac.getFechaEntrega().getTime() < DateUtil.getCurrentDateTime0().getTime()) {
					return new JsonResult(false, " La fecha de entrega debe ser mayor o igual a la fecha actual", null);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new JsonResult(false, e.getMessage(), null);
			}
			Transac transac_result = transacService.editTransac(transac);

			try {
				// ELIMINAR ARCHIVOS ELIMINADOS
				if (filesToDelete != null) {
					for (String FileName : filesToDelete) {
						File file = new File(Configuration.getInstance().getURLOrdenCompra() + transac_result.getTransacNr() + "/" + FileName);
						file.delete();
					}
				}

				if (!lFiles.isEmpty()) {
					int cont = 0;
					for (File fItem : lFiles) {
						// SALVAR ADJUNTOS
						String directoryName = Configuration.getInstance().getURLOrdenCompra() + transac_result.getTransacNr();
						File directory = new File(directoryName);
						if (!directory.exists()) {
							if (!directory.mkdir()) {
								return new JsonResult(false, "Error al guardar adjunto.", null);
							}
						}

						Files.copy(fItem.toPath(),
								(new File(directoryName + "/" + fileDetail.get(cont).getFileName())).toPath(),
								StandardCopyOption.REPLACE_EXISTING);
						cont++;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return new JsonResult(false, ex.getMessage(), null);
			}

			return new JsonResult(true, "Ok", transac_result.getTransacNr());
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, e.getMessage(), null);
		}
	}

	@POST
	@Path("/saveTransac")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public JsonResult saveTransac(FormDataMultiPart files,
			@FormDataParam("file") List<FormDataContentDisposition> fileDetail,
			@FormDataParam("transac") String sTransac) {
		List<File> lFiles = new ArrayList<>();
		try {
			boolean ordenCompraRepetida = false;
			Transac transac = new Gson().fromJson(sTransac, Transac.class);
			List<FormDataBodyPart> lFDBP = files.getFields("file");
			try {
				if (lFDBP != null) {
					for (FormDataBodyPart fdbpItem : lFDBP) {
						lFiles.add(fdbpItem.getEntityAs(File.class));
					}
				}
				transac.setFechaEntrega(DateUtil.getDateStringParseFormat(transac.getFechaTemp(), "ddMMyyyy"));
				if (transac.getFechaEntrega().getTime() < DateUtil.getCurrentDateTime0().getTime()) {
					return new JsonResult(false, " La fecha de entrega debe ser mayor o igual a la fecha actual", null);
				}
				if (transac.getOrdenCompra() != "") {
					if (transacDAO.ordenCompraRepetida(transac.getOrdenCompra(), transac.getGente().getId())) {
						ordenCompraRepetida = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new JsonResult(false, e.getMessage(), null);
			}
			Transac transac_result = transacService.saveTransac(transac);

			try {
				// if (file != null) {
				if (!lFiles.isEmpty()) {
					int cont = 0;
					for (File fItem : lFiles) {
						// SALVAR ADJUNTO
						// String directoryName =
						// "/share/attachments/saleOrder/" +
						// transac_result.getTransacNr();
						String directoryName = Configuration.getInstance().getURLOrdenCompra() + transac_result.getTransacNr();
						File directory = new File(directoryName);
						if (!directory.exists()) {
							if (!directory.mkdir()) {
								System.out.println("URL: " + directoryName);
								return new JsonResult(false, "Error al guardar adjunto.", null);
							}
						}
						Files.copy(fItem.toPath(),
								(new File(directoryName + "/" + fileDetail.get(cont).getFileName())).toPath(),
								StandardCopyOption.REPLACE_EXISTING);
						cont++;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return new JsonResult(false, ex.getMessage(), null);
			}

			if (ordenCompraRepetida)
				return new JsonResult(true, "La orden de compra fue previamente ingresada en otro pedido de venta.",
						transac_result.getTransacNr());
			else
				return new JsonResult(true, "Ok", transac_result.getTransacNr());
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, e.getMessage(), null);
		}
	}

	@GET
	@Path("/getTransacFile/{id}/{nombre}")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response getTransacFile(@PathParam("id") String id, @PathParam("nombre") String nombre) {
		try {
			String rutaFile = Configuration.getInstance().getURLOrdenCompra() + id + "/" + nombre;
			File file = new File(rutaFile);
			if (file != null)
				return Response.ok(file).build();

			return Response.noContent().build();
		} catch (NullPointerException ne) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/getTransacFilesList/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult getTransacFilesList(@PathParam("id") String id) {
		try {
			List<String> lArchivos = new ArrayList<String>();
			String directoryName = Configuration.getInstance().getURLOrdenCompra() + id;
			File folder = new File(directoryName);
			for (File fileEntry : folder.listFiles()) {
				if (fileEntry.isFile() && !fileEntry.isHidden()) {
					lArchivos.add(fileEntry.getName());
				}
			}
			return new JsonResult(true, "ok", lArchivos);
		} catch (NullPointerException ne) {
			return new JsonResult(false, "Error al listar archivos", null);
		}
	}

	@GET
	@Path("/getTipoComprobante/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult getTipoComprobante(@PathParam("id") Integer id) {
		try {
			TipoComprob tipoComprob = transacService.getTipoComprobante(id);
			return new JsonResult(true, "ok", tipoComprob);
		} catch (NullPointerException ne) {
			return new JsonResult(false, "La orden de compra ingresada es invalida", null);
		}

	}

}
