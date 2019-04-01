package ar.com.cipres.services.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inssjp.mywebservice.business.IWebServiceStub;
import com.inssjp.mywebservice.business.IWebServiceStub.ConfirmacionTransaccionDTO;
import com.inssjp.mywebservice.business.IWebServiceStub.GetTransaccionesNoConfirmadas;
import com.inssjp.mywebservice.business.IWebServiceStub.GetTransaccionesNoConfirmadasE;
import com.inssjp.mywebservice.business.IWebServiceStub.GetTransaccionesNoConfirmadasResponseE;
import com.inssjp.mywebservice.business.IWebServiceStub.MedicamentosDTO;
import com.inssjp.mywebservice.business.IWebServiceStub.SendConfirmaTransacc;
import com.inssjp.mywebservice.business.IWebServiceStub.SendConfirmaTransaccE;
import com.inssjp.mywebservice.business.IWebServiceStub.SendConfirmaTransaccResponseE;
import com.inssjp.mywebservice.business.IWebServiceStub.SendMedicamentos;
import com.inssjp.mywebservice.business.IWebServiceStub.SendMedicamentosE;
import com.inssjp.mywebservice.business.IWebServiceStub.SendMedicamentosResponseE;
import com.inssjp.mywebservice.business.IWebServiceStub.TransaccionPlainWS;
import com.inssjp.mywebservice.business.IWebServiceStub.WebServiceError;

import ar.com.cipres.dao.IArtDespaDAO;
import ar.com.cipres.dao.IDespachosDAO;
import ar.com.cipres.dao.IDomiciliosDAO;
import ar.com.cipres.dao.IExiArtDAO;
import ar.com.cipres.dao.IGenteDAO;
import ar.com.cipres.dao.IItemsDAO;
import ar.com.cipres.dao.IKardexDAO;
import ar.com.cipres.dao.INumerKarDAO;
import ar.com.cipres.dao.IParametrizacionDAO;
import ar.com.cipres.dao.IPrefijoExistenciaDAO;
import ar.com.cipres.dao.IStockDAO;
import ar.com.cipres.dao.ITransacDAO;
import ar.com.cipres.dao.ITrazabiDAO;
import ar.com.cipres.dao.IUsuarioDAO;
import ar.com.cipres.model.ArtDespa;
import ar.com.cipres.model.ArtDespaId;
import ar.com.cipres.model.DataSendAnmat;
import ar.com.cipres.model.DataSendMercaderia;
import ar.com.cipres.model.Despachos;
import ar.com.cipres.model.Domicilios;
import ar.com.cipres.model.ExiArt;
import ar.com.cipres.model.ExiArtId;
import ar.com.cipres.model.Existencias;
import ar.com.cipres.model.FilterTrazabi;
import ar.com.cipres.model.Gente;
import ar.com.cipres.model.IngresoMercaderia;
import ar.com.cipres.model.Items;
import ar.com.cipres.model.JsonResultIngresoMercaderia;
import ar.com.cipres.model.JsonResultRomaneo;
import ar.com.cipres.model.Kardex;
import ar.com.cipres.model.KardexId;
import ar.com.cipres.model.KardexInfo;
import ar.com.cipres.model.NumerKar;
import ar.com.cipres.model.ResultSendMedicamento;
import ar.com.cipres.model.RomaneoMedicamento;
import ar.com.cipres.model.Stock;
import ar.com.cipres.model.Transac;
import ar.com.cipres.model.Trazabi;
import ar.com.cipres.model.Usuario;
import ar.com.cipres.services.IReportService;
import ar.com.cipres.services.ITrazaService;
import ar.com.cipres.util.CastTransacPlainWS;
import ar.com.cipres.util.Constants;
import ar.com.cipres.util.DateUtil;
import ar.com.cipres.util.FilterCriteria;
import ar.com.cipres.util.FormatUtil;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
@Transactional
public class TrazaService implements ITrazaService {

	@Autowired
	private ITrazabiDAO trazabiDAO;

	@Autowired
	private IParametrizacionDAO parametrizacionDAO;
	 
	@Autowired
	private IPrefijoExistenciaDAO prefijoExistenciaDAO;

	@Autowired
	private IReportService reportService;

	@Autowired
	private IGenteDAO genteDAO;

	@Autowired
	private IDomiciliosDAO domiciliosDAO;

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
	private IUsuarioDAO usuarioDAO;

	private ResultSendMedicamento confirmTransac(List<ConfirmacionTransaccionDTO> seriesTransac, String usr_pami,
			String pass_pami, String urlTraza) throws Exception {

		IWebServiceStub service = null;
		ResultSendMedicamento result = new ResultSendMedicamento();
		try {

			service = new IWebServiceStub(urlTraza);

			Long timeout = 10l * 60l * 1000l; // Cinco minutos

			service._getServiceClient().getOptions().setTimeOutInMilliSeconds(timeout);

			ServiceClient serviceClient = service._getServiceClient();
			OMFactory omFactory = OMAbstractFactory.getOMFactory();

			OMElement security = omFactory.createOMElement(
					new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
							"Security", "wsse"),
					null);

			OMNamespace omNs = omFactory.createOMNamespace(
					"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsu");
			OMElement userNameToken = omFactory.createOMElement("UsernameToken", omNs);

			OMElement userName = omFactory.createOMElement("Username", omNs);
			userName.addChild(omFactory.createOMText("testwservice"));

			OMElement password = omFactory.createOMElement("Password", omNs);
			password.addChild(omFactory.createOMText("testwservicepsw"));

			userNameToken.addChild(userName);
			userNameToken.addChild(password);

			security.addChild(userNameToken);
			serviceClient.addHeader(security);

			try {

				SendConfirmaTransaccE ge_ = new SendConfirmaTransaccE();
				SendConfirmaTransacc g_ = new SendConfirmaTransacc();
				g_.setArg0(usr_pami);
				g_.setArg1(pass_pami);
				ConfirmacionTransaccionDTO[] result_ = new ConfirmacionTransaccionDTO[seriesTransac.size()];
				g_.setArg2(seriesTransac.toArray(result_));
				ge_.setSendConfirmaTransacc(g_);

				SendConfirmaTransaccResponseE result_confirm = service.sendConfirmaTransacc(ge_);
				String id_transac_ = "";
				if (result_confirm.getSendConfirmaTransaccResponse().get_return().getErrores() != null
						&& result_confirm.getSendConfirmaTransaccResponse().get_return().getErrores().length > 0) {
					WebServiceError[] wseArray = result_confirm.getSendConfirmaTransaccResponse().get_return()
							.getErrores();
					for (WebServiceError wse : wseArray) {
						result.setExito(false);
						result.setErrores(wse.get_d_error());
					}

				} else {
					id_transac_ = result_confirm.getSendConfirmaTransaccResponse().get_return()
							.getId_transac_asociada();
					result.setExito(true);
					result.setErrores("OK");
					result.setTransacNr(id_transac_);

				}

			} catch (Exception e) {
				result.setExito(false);
				result.setErrores("No se pudo obtener el id_transac_global");
			}

		} catch (AxisFault e1) {
			result.setExito(false);
			result.setErrores("No se pudo obtener el id_transac_global");
			e1.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setExito(false);
			result.setErrores("No se pudo obtener el id_transac_global");
		}

		System.out.println(result.getErrores());
		System.out.println(result.getTransacNr());

		return result;
	}

	/**
	 * @param args
	 */
	public TransaccionPlainWS[] getTransaccionesARecepcionar(String remito, String usernamePami, String passwordPami) {
		IWebServiceStub service = null;

		try {

			Timestamp inicio = new Timestamp(System.currentTimeMillis());

			service = new IWebServiceStub("https://trazabilidad.pami.org.ar:9050/trazamed.WebService");
			// service = new
			// IWebServiceServiceStub("https://trazabilidad.pami.org.ar:9050/trazamed.WebService");

			ServiceClient serviceClient = service._getServiceClient();

			OMFactory omFactory = OMAbstractFactory.getOMFactory();

			OMElement security = omFactory.createOMElement(
					new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
							"Security", "wsse"),
					null);

			OMNamespace omNs = omFactory.createOMNamespace(
					"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsu");
			OMElement userNameToken = omFactory.createOMElement("UsernameToken", omNs);

			OMElement userName = omFactory.createOMElement("Username", omNs);
			userName.addChild(omFactory.createOMText("testwservice"));

			OMElement password = omFactory.createOMElement("Password", omNs);
			password.addChild(omFactory.createOMText("testwservicepsw"));

			userNameToken.addChild(userName);
			userNameToken.addChild(password);

			security.addChild(userNameToken);
			serviceClient.addHeader(security);

			serviceClient.getOptions().setTimeOutInMilliSeconds(10000000);

			// serviceClient.setOptions(o);

			String error = null;
			GetTransaccionesNoConfirmadasE ge = new GetTransaccionesNoConfirmadasE();
			GetTransaccionesNoConfirmadas g = new GetTransaccionesNoConfirmadas();
			g.setArg0(usernamePami);
			g.setArg1(passwordPami);

			g.setArg14(remito);
			ge.setGetTransaccionesNoConfirmadas(g);

			GetTransaccionesNoConfirmadasResponseE geR = new GetTransaccionesNoConfirmadasResponseE();
			geR = service.getTransaccionesNoConfirmadas(ge);
			System.out.println(geR.getGetTransaccionesNoConfirmadasResponse().get_return().getList().length);
			System.out.println("Inicio: " + inicio);
			System.out.println("Fin: " + new Timestamp(System.currentTimeMillis()));
			if (geR.getGetTransaccionesNoConfirmadasResponse().get_return().getErrores() != null) {
				WebServiceError[] wseArray = geR.getGetTransaccionesNoConfirmadasResponse().get_return().getErrores();
				for (WebServiceError wse : wseArray) {
					System.out.println(wse.get_d_error());
					error = wse.get_d_error();
				}
			}
			if (error == null && geR.getGetTransaccionesNoConfirmadasResponse().get_return().getList() != null) {

				TransaccionPlainWS[] result = new TransaccionPlainWS[geR.getGetTransaccionesNoConfirmadasResponse()
						.get_return().getList().length];
				int i = 0;
				for (TransaccionPlainWS t : geR.getGetTransaccionesNoConfirmadasResponse().get_return().getList()) {
					TransaccionPlainWS tmp = new TransaccionPlainWS();
					tmp.set_id_transaccion(t.get_id_transaccion());
					//tmp.setSelected(false);
					tmp.set_gtin(t.get_gtin());
					tmp.set_nombre(t.get_nombre());
					tmp.set_numero_serial(t.get_numero_serial());
					tmp.set_lote(t.get_lote());
					tmp.set_n_remito(t.get_n_remito());
					tmp.set_gln_origen(t.get_gln_origen());
					tmp.set_f_transaccion(t.get_f_transaccion());
					tmp.set_vencimiento(t.get_vencimiento());
					System.out.println("serie: " + t.get_numero_serial());
					result[i] = tmp;
					i++;
				}

				return result;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public JsonResultIngresoMercaderia confirmTransacAnmat(DataSendAnmat dataSendAnmat, String usernamePami,
			String passwordPami, String urlTraza) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		Usuario usuario = usuarioDAO.getByUsername(name);
		Integer genteNr = 0;
		boolean validoTransac = true;
		boolean useOC = true;
		// Pregunto si la transac es valida
		Transac transacSk = null;
		try {
			transacSk = transacDAO.getByPrimaryKey(dataSendAnmat.getTransacNr());
		} catch (Exception e) {
		}
		if (dataSendAnmat.getTransacNr() == 0) {
			useOC = false;
		} else {

			

			if (transacSk == null) {
				return new JsonResultIngresoMercaderia(false, "El número de transacción ingresado no se es válido");
			} else {
				if (transacSk.getTipoComprob().getNr() != 15 && transacSk.getDestino() == -1) {
					return new JsonResultIngresoMercaderia(false, "El tipo de comprobante ingresado no se es válido");
				}
			}
		}
		// Filtrar por los seleccionados
		List<ConfirmacionTransaccionDTO> series = new ArrayList<ConfirmacionTransaccionDTO>();
		List<Trazabi> trazabiList = new ArrayList<Trazabi>();

		List<ExiArt> exiArtList = new ArrayList<ExiArt>();
		Existencias existencias = new Existencias();
		if (parametrizacionDAO.getByPrimaryKey(Constants.PARAM_USE_EXIT_DEFAULT).getValor().equals("true")) {
			existencias.setNr(
					Integer.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_DEFAULT).getValor()));
		} else {

			if (useOC) {				
				try{
					existencias.setNr(prefijoExistenciaDAO.getPrefijoExistencia(transacSk.getPrefijo()).get(0).getNrExistencia());
				}catch(Exception e){
					e.printStackTrace();
					existencias.setNr(Integer.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_DEFAULT).getValor()));
				}
				
			} else {
				existencias.setNr(
						Integer.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_DEFAULT).getValor()));
			}
		}
		
		if (dataSendAnmat.getTransacNr() == 0) {
			useOC = false;
			try{
				existencias.setNr(Integer.parseInt(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_EXIT_ZERO).getValor()));
			}catch(Exception e){}
		} 
		Integer sucursalNr = 1;
		for (TransaccionPlainWS transacSerie : dataSendAnmat.getArrayTransac()) {
			if (transacSerie.getSelected() != null && transacSerie.getSelected()) {
				ConfirmacionTransaccionDTO confirmacionTransaccionDTO = new ConfirmacionTransaccionDTO();
				confirmacionTransaccionDTO.setP_ids_transac(Long.parseLong(transacSerie.get_id_transaccion()));
				confirmacionTransaccionDTO
						.setF_operacion(DateUtil.getFormatedSTDDate(new Timestamp(System.currentTimeMillis())));
				series.add(confirmacionTransaccionDTO);
				// TODO Obtener el cod de articulo desde la Orden de Compra
				// Consultar si existe el articulo
				Stock stock = stockDAO.getByPrimaryKey(transacSerie.getIdArticulo());
				if (stock == null) {
					return new JsonResultIngresoMercaderia(false, "No se encuentró ningún medicamento con GTIN: "
							+ transacSerie.get_gtin() + ", debe ingresa el Gtin previo a ingresar el pedido");
				}
				ExiArtId exiArtId = new ExiArtId();
				exiArtId.setClave(stock.getId());
				exiArtId.setExistencias(existencias);
				boolean existExiArt = false;
				// Busco lista de ExiArt
				for (ExiArt exiArtTmp : exiArtList) {
					if (exiArtTmp.getId().getClave().equals(exiArtId.getClave())
							&& exiArtTmp.getId().getExistencias().getNr() == exiArtId.getExistencias().getNr()) {
						exiArtTmp.setCantidad1(exiArtTmp.getCantidad1() + 1);
						existExiArt = true;
						break;
					}
				}
				if (!existExiArt) {
					ExiArt exiArt = new ExiArt();
					exiArt.setId(exiArtId);
					exiArt.setCantidad1(1d);
					exiArt.setStock(stock);
					exiArtList.add(exiArt);
				}

			}
		}
		if (useOC) {
			// Validar si la cant1entregado es <= cant1
			System.out.println(exiArtList.size());
			for (ExiArt exiArtValid : exiArtList) {
				List<Items> itemsList = itemsDAO.getByTransacArticulo(transacSk.getTransacNr(),
						exiArtValid.getStock().getId());
				if (itemsList.size() > 0) {
					if (itemsList.get(0)
							.getCant1entregado() >= (itemsList.get(0).getCant1() + exiArtValid.getCantidad1())) {

					} else {
						// return new JsonResult(false,
						// "Existe una diferencia entre la cantidad pedida y la
						// entregada sobre el producto: "
						// +exiArtValid.getStock().getId() + ".");
					}

				}
			}
		}
		// //Informar a Anmat
		List<KardexInfo> kardexInfoList = new ArrayList<KardexInfo>();
		List<Kardex> kardexList = new ArrayList<Kardex>();
		try {
			String cuit = parametrizacionDAO.getByPrimaryKey(Constants.PARAM_CUIT).getValor();

			ResultSendMedicamento resultSendMedicamento = confirmTransac(series, usernamePami, passwordPami, urlTraza);
			if (resultSendMedicamento.isExito()) {

				for (TransaccionPlainWS transacSerie : dataSendAnmat.getArrayTransac()) {
					if (transacSerie.getSelected() != null && transacSerie.getSelected()) {
						// Consultar el articulo
						Stock stock = stockDAO.getByPrimaryKey(transacSerie.getIdArticulo());
						ExiArtId exiArtId = new ExiArtId();
						exiArtId.setClave(stock.getId());
						exiArtId.setExistencias(existencias);

						Trazabi trazabiTmp = CastTransacPlainWS.CastTransacPlainWSToTrazabi(transacSerie);
						if (stock != null) {
							trazabiTmp.setArticulo(stock.getId());
						}
						// Me fijo si el despacho existe
						String date_short = DateUtil.CastStringToDateShort(transacSerie.get_vencimiento());
						String filterTextLote = "L=" + transacSerie.get_lote() + " V=" + date_short;
						FilterCriteria filterdescrip = new FilterCriteria("descrip", filterTextLote, new Short("0"));
						List<FilterCriteria> filterCriteriaList = new ArrayList<FilterCriteria>();
						filterCriteriaList.add(filterdescrip);
						Integer despanr = null;
						List<Despachos> despachosList = despachosDAO.getAll(filterCriteriaList);
						Despachos despachos_ = new Despachos();
						if (despachosList != null && despachosList.size() > 0) {
							// Hay que tomar el despanr
							despanr = despachosList.get(0).getDespaNr();
							despachos_ = despachosList.get(0);
						} else {
							// Hay que crear un nuevo despacho
							Despachos despachos = new Despachos();
							despachos.setFechaIng(DateUtil.getDateShortDateStr(transacSerie.get_vencimiento()));
							despachos.setDescrip(filterTextLote);
							despachos.setSoloLote(transacSerie.get_lote());
							despachos.setTrazable(-1);
							despachosDAO.save(despachos);
							
							despanr = despachos.getDespaNr();
							despachos_ = despachos;
						}

						trazabiTmp.setNrlote(String.valueOf(despanr));
						// Get Id
						String lastnumber = String.valueOf(trazabiDAO.getMaxTrazabi());
						lastnumber = lastnumber.substring(0, lastnumber.length() - 1);
						String number = String.valueOf((Integer.parseInt(lastnumber) + 1)) + "1";
						trazabiTmp.setNr(Integer.parseInt(number));
						trazabiTmp.setRespuestaIngreso(resultSendMedicamento.getTransacNr());
						trazabiTmp.setCant(1f);
						trazabiTmp.setExinr(1);
						trazabiTmp.setTipoIng(0);
						trazabiTmp.setImpreso(0);
						trazabiTmp.setSucursal(1);
						trazabiTmp.setTipoSalida(0);
						// Orien
						// trazabiTmp.setCuitDestinoIng("30686262312");
						// Drofar
						trazabiTmp.setCuitDestinoIng(cuit);
						trazabiTmp.setNrOpIng(46);
						if (useOC) {
							trazabiTmp.setObserIng(String.valueOf(transacSk.getTransacNr()));
						}
						Random randomGenerator = new Random();
						trazabiTmp.setUnico(randomGenerator.nextInt(10000));
						trazabiTmp.setGlnorigenSal(".");
						trazabiTmp.setGlndestinoSal(".");
						trazabiTmp.setCuitOrigenSal(".");
						trazabiTmp.setGlndestinoSal(".");
						trazabiTmp.setNrOpEgr(0);
						trazabiTmp.setNrTransacSalida(0);
						if (kardexInfoList.size() < 1) {
							KardexInfo kardexInfonew = new KardexInfo();
							kardexInfonew.setClave(stock.getId());
							kardexInfonew.setDespacho(Double.valueOf(String.valueOf(despachos_.getDespaNr())));
							kardexInfonew.setFactura(transacSerie.get_n_factura());
							kardexInfonew.setCantidad(1);
							kardexInfoList.add(kardexInfonew);

						} else {
							// Busco si existe el kardex en la lista de kardex
							boolean existekardex = false;
							for (int ilist = 0; ilist < kardexInfoList.size(); ilist++) {
								if (kardexInfoList.get(ilist).getClave().equals(stock.getId())
										&& kardexInfoList.get(ilist).getDespacho()
												.equals(Double.valueOf(String.valueOf(despachos_.getDespaNr())))) {
									kardexInfoList.get(ilist).setCantidad(kardexInfoList.get(ilist).getCantidad() + 1);
									existekardex = true;
								}
							}
							if (existekardex == false) {
								KardexInfo kardexInfoNew = new KardexInfo();
								kardexInfoNew.setClave(stock.getId());
								kardexInfoNew.setDespacho(Double.valueOf(String.valueOf(despachos_.getDespaNr())));
								kardexInfoNew.setFactura(transacSerie.get_n_factura());
								kardexInfoNew.setCantidad(1);
								kardexInfoList.add(kardexInfoNew);
							}
						}
						try {

							FilterCriteria filtergln = new FilterCriteria("gln", transacSerie.get_gln_origen(),
									new Short("0"));
							List<FilterCriteria> filterCriteriaListGln = new ArrayList<FilterCriteria>();
							filterCriteriaListGln.add(filtergln);
							List<Domicilios> domiciliosList = domiciliosDAO.getAll(filterCriteriaListGln);
							genteNr = domiciliosList.get(0).getGenteId();
							trazabiTmp.setGenteNr(genteNr);
							Gente genteTmp = genteDAO.getByPrimaryKey(genteNr);
							trazabiTmp.setCuitOrigenIng(genteTmp.getCuit().replaceAll("-", ""));

						} catch (Exception e) {

							System.out.println("");
							e.printStackTrace();
						}

						trazabiDAO.save(trazabiTmp);
						System.out.println(trazabiTmp.getNr());
						// Agrego ArtDespa
						ArtDespa artDespa = null;
						ArtDespaId artDespaId = new ArtDespaId();
						artDespaId.setArtic(stock.getId());
						artDespaId.setDespachos(despachos_);
						artDespa = artDespaDAO.getByPrimaryKey(artDespaId);
						if (artDespa == null) {
							artDespa = new ArtDespa();
							artDespa.setCantidad(1d);
							artDespa.setExinr(existencias.getNr());
							artDespa.setId(artDespaId);
							artDespa.setPreco(BigDecimal.ZERO);
							artDespa.setVendido(0f);
							artDespaDAO.save(artDespa);
						} else {
							artDespa.setCantidad(artDespa.getCantidad() + 1);
							artDespaDAO.update(artDespa);
						}
					}

				}
				if (!usuario.getRol().getAbrev().equals("INGRESO_DEVOLUCIONES")) {
					// Actualizar stock ExiArt
					for (ExiArt exiArtUpdate : exiArtList) {
						
						ExiArtId exiArtId = exiArtUpdate.getId();

						ExiArt exiArt = exiArtDAO.getByPrimaryKey(exiArtId);
						if (exiArt == null){
							exiArt = new ExiArt();
							exiArt.setId(exiArtId);
							exiArt.setCantidad1(exiArtUpdate.getCantidad1());
							exiArt.setCantidad2(0d);
							exiArtDAO.save(exiArt);
						}else{
							exiArt.setCantidad1(exiArt.getCantidad1() + exiArtUpdate.getCantidad1());
							exiArtDAO.update(exiArt);
							
						}
						
						
						if (useOC) {
							// Dar de baja articulos en la orden de compra
							// Encontrar Item
							List<Items> itemsList = itemsDAO.getByTransacArticulo(transacSk.getTransacNr(),
									exiArtUpdate.getId().getClave());
							if (itemsList.size() > 0) {
								itemsList.get(0).setCant1entregado(
										(itemsList.get(0).getCant1entregado() + exiArtUpdate.getCantidad1()));
								System.out.println("Update  items itemnr: " + itemsList.get(0).getId().getItemNr());
								itemsDAO.update(itemsList.get(0));
							}
						}
					}
					// Actualizar stock ExiArt
					for (KardexInfo kardexInfoK : kardexInfoList) {
						try {
							// Creación del kardex
							Kardex kardex = new Kardex();
							KardexId kardexId = new KardexId();
							kardexId.setTransacNr(generateTransacNrKar(sucursalNr));
							// Consultar el articulo
							Stock stock = stockDAO.getByPrimaryKey(kardexInfoK.getClave());
							kardexId.setStock(stock);
							kardex.setId(kardexId);
							kardex.setArticNr(0);
							if (useOC) {
								kardex.setAgendadoNr(transacSk.getGente().getId());
							} else {
								kardex.setAgendadoNr(genteNr);
							}
							kardex.setCantidad1(Float.parseFloat(String.valueOf(kardexInfoK.getCantidad())));
							kardex.setCantidad2(0f);

							kardex.setSubtotal(kardexInfoK.getDespacho());
							try{
								kardex.setDespachos(despachosDAO.getByPrimaryKey(kardexInfoK.getDespacho().intValue()));
							}catch(Exception e){
								e.printStackTrace();
							}
							
							if (useOC) {
								kardex.setObser(transacSk.getGente().getRazonSocial() + " " + kardexInfoK.getFactura());
							} else {
								kardex.setObser("Ingreso Sin Orden de Compra " + kardexInfoK.getFactura());
							}
							kardex.setExistenciaNr(existencias.getNr());
							kardex.setFechaComprob(DateUtil.getDateSkString(new Date(System.currentTimeMillis())));
							kardex.setFechaTransac(new Date(System.currentTimeMillis()));
							try{
								kardex.setOperadorNr(usuario.getUsersk());
							}catch(Exception e){
								kardex.setOperadorNr(10);
							}
							if (useOC) {
								List<Items> itemsList = itemsDAO.getByTransacArticulo(transacSk.getTransacNr(),
										kardexInfoK.getClave());
								kardex.setPreCosto(itemsList.get(0).getPreCosto());
								kardex.setPreVta(itemsList.get(0).getPrecio());
							} else {
								kardex.setPreCosto(BigDecimal.ZERO);
								kardex.setPreVta(BigDecimal.ZERO);
							}
							kardex.setSucursalNr(sucursalNr);
							kardex.setPreFob(FormatUtil.castRemitoAnmat(dataSendAnmat.getNrComprob()));
							kardex.setTalle(".");
							kardex.setVendedorNr(1);

							kardex.setTipoComprobante(20);
							if (useOC) {
								kardex.setTransacOrigen(transacSk.getTransacNr());
							} else {
								kardex.setTransacOrigen(0);
							}

							// kardex.set
							kardexDAO.save(kardex);
							kardexList.add(kardex);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
				String reportStr = reportService.createTablePDF(FormatUtil.convertKardexJsonReporteIngreso(kardexList));
				return new JsonResultIngresoMercaderia(true,
						"Se ha informado a Anmat exitosamente, el numero de transacción de respuesta es: "
								+ String.valueOf(resultSendMedicamento.getTransacNr()),
						false, null, reportStr);
			} else {
				return new JsonResultIngresoMercaderia(false, resultSendMedicamento.getErrores(), false, null, "");
			}

		} catch (Exception e) {

			e.printStackTrace();
			return new JsonResultIngresoMercaderia(true,
					"Debe comunicar este error al administrador: " + e.getMessage(), false, null, "");

		}
		// return new JsonResult(false, "Prueba validaciones");
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

	public JsonResultRomaneo getRomaneo(Integer transac_oc_long) {
		List<Items> itemsList = itemsDAO.getOcByTransac(transac_oc_long, true);
		if (itemsList == null || itemsList.size() == 0) {
			return new JsonResultRomaneo(false, "La orden de compra ingresada no es valida", null, "");
		}
		List<RomaneoMedicamento> romaneoMedicamentoList = new ArrayList<RomaneoMedicamento>();
		for (Items items : itemsList) {
			RomaneoMedicamento romaneoMedicamento = new RomaneoMedicamento(items.getStock().getGtin(),
					items.getStock().getId(), items.getCant1(), items.getCant1entregado(), items.getDescrip());
			romaneoMedicamentoList.add(romaneoMedicamento);
		}
		return new JsonResultRomaneo(true, "Ok", romaneoMedicamentoList, "");
	}

	@Override
	public TransaccionPlainWS[] getTransaccionesARecepcionar(String comprob, String tipo, String usernamePami,
			String passwordPami) {
		IWebServiceStub service = null;

		try {
			Timestamp inicio = new Timestamp(System.currentTimeMillis());

			service = new IWebServiceStub("https://trazabilidad.pami.org.ar:9050/trazamed.WebService");
			// service = new
			// IWebServiceServiceStub("https://trazabilidad.pami.org.ar:9050/trazamed.WebService");

			ServiceClient serviceClient = service._getServiceClient();

			OMFactory omFactory = OMAbstractFactory.getOMFactory();

			OMElement security = omFactory.createOMElement(
					new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
							"Security", "wsse"),
					null);

			OMNamespace omNs = omFactory.createOMNamespace(
					"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsu");
			OMElement userNameToken = omFactory.createOMElement("UsernameToken", omNs);

			OMElement userName = omFactory.createOMElement("Username", omNs);
			userName.addChild(omFactory.createOMText("testwservice"));

			OMElement password = omFactory.createOMElement("Password", omNs);
			password.addChild(omFactory.createOMText("testwservicepsw"));

			userNameToken.addChild(userName);
			userNameToken.addChild(password);

			security.addChild(userNameToken);
			serviceClient.addHeader(security);

			serviceClient.getOptions().setTimeOutInMilliSeconds(10000000);

			// serviceClient.setOptions(o);

			String error = null;
			GetTransaccionesNoConfirmadasE ge = new GetTransaccionesNoConfirmadasE();
			GetTransaccionesNoConfirmadas g = new GetTransaccionesNoConfirmadas();
			g.setArg0(usernamePami);
			g.setArg1(passwordPami);
			if (tipo.equals("remito")) {
				g.setArg14(comprob);
			}
			if (tipo.equals("factura")) {
				g.setArg15(comprob);
			}

			ge.setGetTransaccionesNoConfirmadas(g);

			GetTransaccionesNoConfirmadasResponseE geR = new GetTransaccionesNoConfirmadasResponseE();
			geR = service.getTransaccionesNoConfirmadas(ge);
			if (geR.getGetTransaccionesNoConfirmadasResponse().get_return().getErrores() != null) {
				WebServiceError[] wseArray = geR.getGetTransaccionesNoConfirmadasResponse().get_return().getErrores();
				for (WebServiceError wse : wseArray) {
					System.out.println(wse.get_d_error());
					error = wse.get_d_error();
				}
			}
			if (error == null && geR.getGetTransaccionesNoConfirmadasResponse().get_return().getList() != null) {
				for (TransaccionPlainWS t : geR.getGetTransaccionesNoConfirmadasResponse().get_return().getList()) {
					System.out.println("serie: " + t.get_numero_serial());
				}
				return geR.getGetTransaccionesNoConfirmadasResponse().get_return().getList();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String sendMedicamentos(Integer transacNr) {

		// Get trazabi
		Trazabi trazabi = trazabiDAO.getByPrimaryKey(transacNr);

		MedicamentosDTO m = new MedicamentosDTO();
		try {
			if (trazabi.getCodEventoSal() == 111) {
				
				Integer nrDomi = Integer.parseInt(transacDAO.getByPrimaryKey(trazabi.getNrTransacSalida()).getBenef());
				
				
				if (nrDomi != null) {
					Domicilios domicilios = domiciliosDAO.getByPrimaryKey(nrDomi);
					genteDAO.getByPrimaryKey(domicilios.getGenteId());
					m.setApellido(domicilios.getApellidoAfiliado());
					m.setNombres(domicilios.getNombreAfiliado());
					m.setNro_asociado(domicilios.getNrAfiliado());
					m.setId_obra_social(String.valueOf(genteDAO.getByPrimaryKey(domicilios.getGenteId()).getNrObraSocial()));
					
				}
			}
		} catch (Exception e) {
		}

		SendMedicamentos sm = new SendMedicamentos();

		SendMedicamentosE req = new SendMedicamentosE();

		MedicamentosDTO[] arg0Array = new MedicamentosDTO[1];

		// Carga del objeto Requet
		String errorParseo = "";
		// Cargo Cabezera.

		try {
			m.setId_evento(String.valueOf(trazabi.getCodEventoSal()));
			m.setGtin(trazabi.getGtin());
			m.setGln_origen(trazabi.getGlnorigenSal());
			m.setGln_destino(trazabi.getGlndestinoSal());
			m.setCuit_origen(trazabi.getCuitOrigenSal());
			m.setCuit_destino(trazabi.getCuitDestinoSal());
			m.setNumero_serial(trazabi.getSerieGtin());
			m.setVencimiento(trazabi.getVencimLote());
			
			// Parseo FEvento
			Date fechaHoraIngreso = trazabi.getFechaSalida();
			m.setF_evento(DateUtil.getFormatedDate(new Timestamp(fechaHoraIngreso.getTime())));
			// Parseo despacho
			Despachos despachos = despachosDAO.getByPrimaryKey(Integer.parseInt(String.valueOf(trazabi.getNrlote())));
			m.setLote(despachos.getSoloLote());
			m.setN_remito(trazabi.getNrRemitoPropio());
			// Parseo HEvento
			m.setH_evento(DateUtil.getFormatedHour(new Timestamp(fechaHoraIngreso.getTime())));
			
			

			System.out.println();
		} catch (Exception e) {
			errorParseo = "ERROR AL RECUPERAR LOS DATOS";
			e.printStackTrace();
		}

		arg0Array[0] = m;

		String usuario = parametrizacionDAO.getByPrimaryKey(Constants.PARAM_USR_PAMI).getValor();
		String pass = parametrizacionDAO.getByPrimaryKey(Constants.PARAM_PASS_PAMI).getValor();

		sm.setArg0(arg0Array);
		sm.setArg1(usuario);
		sm.setArg2(pass);
		req.setSendMedicamentos(sm);

		System.out.println("Envio de Venta de Nr: " + trazabi.getNr());
		System.out.println("GNL Origen: " + trazabi.getGlnorigenSal());
		System.out.println("GNL Destino: " + trazabi.getGlndestinoSal());
		System.out.println("Fecha Evento: " + trazabi.getFechaSalida());
		System.out.println("ID Evento: " + trazabi.getCodEventoSal());

		IWebServiceStub service = null;
		try {
			Timestamp inicio = new Timestamp(System.currentTimeMillis());

			service = new IWebServiceStub("https://trazabilidad.pami.org.ar:9050/trazamed.WebService");

			ServiceClient serviceClient = service._getServiceClient();

			OMFactory omFactory = OMAbstractFactory.getOMFactory();

			OMElement security = omFactory.createOMElement(
					new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
							"Security", "wsse"),
					null);

			OMNamespace omNs = omFactory.createOMNamespace(
					"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsu");
			OMElement userNameToken = omFactory.createOMElement("UsernameToken", omNs);

			OMElement userName = omFactory.createOMElement("Username", omNs);
			userName.addChild(omFactory.createOMText("testwservice"));

			OMElement password = omFactory.createOMElement("Password", omNs);
			password.addChild(omFactory.createOMText("testwservicepsw"));

			userNameToken.addChild(userName);
			userNameToken.addChild(password);

			security.addChild(userNameToken);
			serviceClient.addHeader(security);

			serviceClient.getOptions().setTimeOutInMilliSeconds(1000000000);

			String msg = "OK";
			try {
				SendMedicamentosResponseE smr = new SendMedicamentosResponseE();

				smr = service.sendMedicamentos(req);

				if (!smr.getSendMedicamentosResponse().get_return().getResultado()) {
					if (smr.getSendMedicamentosResponse().get_return().getErrores() != null) {
						WebServiceError[] wseArray = smr.getSendMedicamentosResponse().get_return().getErrores();
						for (WebServiceError wse : wseArray) {
							System.out.println(wse.get_d_error());
							msg = wse.get_d_error();
						}
					}
				} else {
					// Actulizo trazabi
					trazabi.setRespuestaSalida(smr.getSendMedicamentosResponse().get_return().getCodigoTransaccion());
					trazabiDAO.update(trazabi);
					//Demora
					Long resta = (new Timestamp(System.currentTimeMillis())).getTime() - inicio.getTime();
					System.out.println("Nr Transac Respuesta: "
							+ smr.getSendMedicamentosResponse().get_return().getCodigoTransaccion() + " Tiempo de respuesta: " + resta);
				}
			} catch (Exception e) {
				msg = "No se pudo conectar con el servidor de ANMAT";
			}
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			return "Error de conectibidad";
		}
	}

	public Trazabi addTrazabi(IngresoMercaderia ingresoMercaderia, DataSendMercaderia dataSendMercaderia,
			Transac transac) {
		Trazabi trazabi = new Trazabi();
		trazabi.setGlndestinoIng(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_GLN).getValor());
		if (transac == null){
			trazabi.setCuitOrigenIng("-");
			trazabi.setGenteNr(0);
			
		}else{
			Domicilios domicilio = domiciliosDAO.getDomicilioPrincipal(transac.getGente().getId());
			if (domicilio != null) {
				trazabi.setGlnorigenIng(domicilio.getGln());
				
			}
			trazabi.setCuitOrigenIng(transac.getGente().getCuit().replaceAll("-", ""));
			trazabi.setGenteNr(transac.getGente().getId());
			
		}
		
		
		
		trazabi.setCuitOrigenSal(".");
		trazabi.setCuitDestinoSal(".");
		trazabi.setGlnorigenSal(".");
		trazabi.setGlndestinoSal(".");
		trazabi.setCodEventoIng(41);
		trazabi.setFechaIng(new Date(System.currentTimeMillis()));
		trazabi.setArticulo(ingresoMercaderia.getArticulo().getId());
		trazabi.setNrRemCompra(dataSendMercaderia.getNrComprob());
		trazabi.setNrFacCompra(dataSendMercaderia.getNrComprob());
		trazabi.setObserIng("0");
		trazabi.setGtin(".");
		trazabi.setSerieGtin("");
		trazabi.setCant(1f);
		trazabi.setTrazaObli(0);
		trazabi.setVencimLote(ingresoMercaderia.getVencimiento());
		
		// Me fijo si el despacho existe
		String date_short = DateUtil.CastStringToDateShort(ingresoMercaderia.getVencimiento());
		String filterTextLote = "L=" + ingresoMercaderia.getLote() + " V=" + date_short;
		FilterCriteria filterdescrip = new FilterCriteria("descrip", filterTextLote, new Short("0"));
		List<FilterCriteria> filterCriteriaList = new ArrayList<FilterCriteria>();
		filterCriteriaList.add(filterdescrip);
		Integer despanr = null;
		List<Despachos> despachosList = despachosDAO.getAll(filterCriteriaList);
		if (despachosList != null && despachosList.size() > 0) {
			// Hay que tomar el despanr
			despanr = despachosList.get(0).getDespaNr();
		} else {
			// Hay que crear un nuevo despacho
			Despachos despachos = new Despachos();
			despachos.setFechaIng(DateUtil.getDateShortDateStr(ingresoMercaderia.getVencimiento()));
			despachos.setDescrip(filterTextLote);
			despachos.setSoloLote(ingresoMercaderia.getLote());
			despachosDAO.save(despachos);
			despanr = despachos.getDespaNr();
		}
		trazabi.setNrlote(String.valueOf(despanr));
		// Get Id
		String lastnumber = String.valueOf(trazabiDAO.getMaxTrazabi());
		lastnumber = lastnumber.substring(0, lastnumber.length() - 1);
		String number = String.valueOf((Integer.parseInt(lastnumber) + 1)) + "1";
		trazabi.setNr(Integer.parseInt(number));
		trazabi.setNrOpIng(46);
		trazabi.setExinr(1);
		trazabi.setTipoIng(0);
		trazabi.setImpreso(0);
		trazabi.setSucursal(1);
		trazabi.setTipoSalida(0);
		trazabi.setCodEventoSal(null);
		trazabi.setCuitDestinoIng(parametrizacionDAO.getByPrimaryKey(Constants.PARAM_CUIT).getValor());
		Random randomGenerator = new Random();
		trazabi.setUnico(randomGenerator.nextInt(10000));
		trazabi.setNrOpEgr(0);
		trazabi.setNrTransacSalida(0);
		trazabi.setHash(ingresoMercaderia.getArticulo().getId());
		trazabiDAO.save(trazabi);
		System.out.println(trazabi.getNr());
		return trazabi;
	}

	public String getCaja(String hash) {
		FilterTrazabi filterTrazabi = FormatUtil.getFilterTrazabi(hash);
		String result = "0";
		Integer temp = trazabiDAO.getNrCaja(filterTrazabi);

		result = String.valueOf(temp);
		return result;
	}

}
