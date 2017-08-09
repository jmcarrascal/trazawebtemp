package ar.com.cipres.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ar.com.cipres.dao.ICondTributariaAFIPDAO;
import ar.com.cipres.dao.IDomiciliosAnexoDAO;
import ar.com.cipres.dao.IDomiciliosDAO;
import ar.com.cipres.dao.IGenteDAO;
import ar.com.cipres.dao.IProvinciaDAO;
import ar.com.cipres.dao.ITransacDAO;
import ar.com.cipres.model.Domicilios;
import ar.com.cipres.model.DomiciliosAnexo;
import ar.com.cipres.model.Gente;
import ar.com.cipres.model.ObjectPaging;
import ar.com.cipres.model.Provincia;
import ar.com.cipres.services.IAgendadoService;
import ar.com.cipres.services.ITransacService;
import ar.com.cipres.services.impl.test.CallRestService;
import ar.com.cipres.util.ObjCodigoDescrip;
import ar.com.cipres.util.ZipUtil;
import ar.com.cipres.util.Configuration;
import ar.com.cipres.util.EntryParameter;
import ar.com.cipres.util.JDBCBatchOperation;

/**
 * Clase que implementa los servicios referentes a Temas
 * 
 * @author Juan Manuel Carrascal.
 */
@Service
public class AgendadoService implements IAgendadoService {

	@Autowired
	private IProvinciaDAO provinciaDAO;

	@Autowired
	private IGenteDAO genteDAO;

	@Autowired
	private ITransacService transacService;

	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	private IDomiciliosDAO domiciliosDAO;

	@Autowired
	private IDomiciliosAnexoDAO domiciliosAnexoDAO;

	@Autowired
	private ITransacDAO transacDAO;
	
	@Autowired
	private ICondTributariaAFIPDAO condTributariaAFIPDAO;

	public List<Object> findAgendado(ObjCodigoDescrip objCodigoDescrip) {
		List<Object> genteList = new ArrayList<Object>();
		genteList = genteDAO.findAgendado(objCodigoDescrip);
		return genteList;
	}

	public List<Object> findAgendado(String searchString) {
		List<Object> genteList = new ArrayList<Object>();
		genteList = genteDAO.findAgendado(searchString);
		return genteList;
	}

	public List<Object> findAgendadoShort(ObjCodigoDescrip objCodigoDescrip) {
		List<Object> genteList = new ArrayList<Object>();
		genteList = genteDAO.findAgendadoShort(objCodigoDescrip);
		return genteList;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public void saveAgendado(Gente gente) {
		if (gente.getId() == null) {
			Integer max = genteDAO.getMax("id");
			gente.setId(max + 1);
			if (gente.getActivGrav() == null) {
				gente.setActivGrav((short) 0); // TODO: No hay donde se edita,
												// por ahora se pone en 0
			}
			if (gente.getNoFactura() == null) {
				gente.setNoFactura((short) 0); // TODO: No hay donde se edita,
												// por ahora se pone en 0
			}
			if (gente.getMarcado1() == null) {
				gente.setMarcado1((short) 0); // TODO: Si no se marca viene
												// null, ver si se puede hacer
												// automatico
			}
			if (gente.getMarcado2() == null) {
				gente.setMarcado2((short) 0); // TODO: Si no se marca viene
												// null, ver si se puede hacer
												// automatico
			}
			if (gente.getMarcado3() == null) {
				gente.setMarcado3((short) 0); // TODO: Si no se marca viene
												// null, ver si se puede hacer
												// automatico
			}
			gente.setFechaIngreso(new Date(System.currentTimeMillis()));
			genteDAO.save(gente);
		} else {
			genteDAO.update(gente);
		}
	}

	public Object getAgendado(Integer id) {
		Gente gente = genteDAO.getByPrimaryKey(id);
		return gente;
	}

	public Object getAgendadoDomicilio(Integer id) {
		Domicilios domicilio = domiciliosDAO.getByPrimaryKey(id);
		return domicilio;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public void saveAgendadoDomicilio(Domicilios domicilio) {
		if (domicilio.getId() == null) {
			if (domicilio.getDomicilioPrincipal() == null) {
				domicilio.setDomicilioPrincipal((short) 0); // TODO: Si no se
															// marca viene null,
															// ver si se puede
															// hacer automatico
			}
			if (domicilio.getDomicilioEntrega() == null) {
				domicilio.setDomicilioEntrega((short) 0); // TODO: Si no se
															// marca viene null,
															// ver si se puede
															// hacer automatico
			}
			if (domicilio.getDomicilioCobranza() == null) {
				domicilio.setDomicilioCobranza((short) 0); // TODO: Si no se
															// marca viene null,
															// ver si se puede
															// hacer automatico
			}
			if (domicilio.getDomicilioMailing() == null) {
				domicilio.setDomicilioMailing((short) 0); // TODO: Si no se
															// marca viene null,
															// ver si se puede
															// hacer automatico
			}
			Provincia provincia = provinciaDAO.getByPrimaryKey(domicilio.getProvinciaId());
			domicilio.setProvincia(provincia.getDescripcion());
			domiciliosDAO.save(domicilio);

		} else {
			if (domicilio.getDomicilioPrincipal() == null) {
				domicilio.setDomicilioPrincipal((short) 0); // TODO: Si no se
															// marca viene null,
															// ver si se puede
															// hacer automatico
			}
			if (domicilio.getDomicilioEntrega() == null) {
				domicilio.setDomicilioEntrega((short) 0); // TODO: Si no se
															// marca viene null,
															// ver si se puede
															// hacer automatico
			}
			if (domicilio.getDomicilioCobranza() == null) {
				domicilio.setDomicilioCobranza((short) 0); // TODO: Si no se
															// marca viene null,
															// ver si se puede
															// hacer automatico
			}
			if (domicilio.getDomicilioMailing() == null) {
				domicilio.setDomicilioMailing((short) 0); // TODO: Si no se
															// marca viene null,
															// ver si se puede
															// hacer automatico
			}
			Provincia provincia = provinciaDAO.getByPrimaryKey(domicilio.getProvinciaId());
			domicilio.setProvincia(provincia.getDescripcion());

			domiciliosDAO.update(domicilio);

		}
		DomiciliosAnexo domiciliosAnexo = domicilio.getDomiciliosAnexo();
		if (domiciliosAnexo != null) {
			domiciliosAnexo.setId(domicilio.getId());
			domiciliosAnexoDAO.saveOrUpdate(domiciliosAnexo);
		}
	}

	public void updateCondTributariaAFIP() {
		try {
			boolean anduvo = false;
			String URL = "http://www.afip.gob.ar/genericos/cInscripcion/archivos/apellidoNombreDenominacion.zip";

			String zipName = "UpdateAFIP.zip";
			// COMENTADO HASTA TENER USER

			File file = new File(Configuration.getInstance().getZipFolder() + "/" + zipName);
			if (file.exists()) {
				file.delete();
			}

			FileUtils.copyURLToFile(new URL(URL), new File(Configuration.getInstance().getZipFolder() + "/" + zipName));

			file = new File(Configuration.getInstance().getZipFolder() + "/" + zipName);
			if (file.length() > 0) {
				ZipUtil unZip = new ZipUtil();
				unZip.unZipIt(Configuration.getInstance().getZipFolder() + "/" + zipName, Configuration.getInstance().getZipFolder() + "/ZipAFIPOutput/");

				File folder = new File(Configuration.getInstance().getZipFolder() + "/ZipAFIPOutput/utlfile/padr");

				sessionFactory.getCurrentSession().createSQLQuery("truncate table trazaweb.CondTributariaAFIP").executeUpdate();
				
				for (final File fileEntry : folder.listFiles()) {
					if (!fileEntry.isDirectory()) {
						try (BufferedReader br = new BufferedReader(new FileReader(fileEntry))) {
							String line = br.readLine();
							List<List<Map.Entry<Type, Object>>> lRegistros = new ArrayList<>();
							String query="insert into CondTributariaAFIP values (?,?,?,?,?,?,?,?)";
							int count = 0;
							
							while (null != (line = br.readLine())) {
								List<Map.Entry<Type, Object>> params = new ArrayList<>();
								String CUIT = line.substring(0, 11);
								params.add(new EntryParameter(String.class, CUIT));
								String denominacion = line.substring(11, 41);
								params.add(new EntryParameter(String.class, denominacion));
								String impGanancias = line.substring(41, 43);
								params.add(new EntryParameter(String.class, impGanancias));
								String impIVA = line.substring(43, 45);
								params.add(new EntryParameter(String.class, impIVA));
								String monotributo = line.substring(45, 47);
								params.add(new EntryParameter(String.class, monotributo));
								String integranteSoc = line.substring(47, 48);
								params.add(new EntryParameter(String.class, integranteSoc));
								String empleador = line.substring(48, 49);
								params.add(new EntryParameter(String.class, empleador));
								String actividadMonotributo = line.substring(49, 51);
								params.add(new EntryParameter(String.class, actividadMonotributo));
								lRegistros.add(params);
							
								count++;
								if (count % 100000 == 0) {
									JDBCBatchOperation.BulkInsert(query, lRegistros, "trazaweb");
									lRegistros = new ArrayList<>();
								}
								
							}

							if(!lRegistros.isEmpty())
								anduvo = JDBCBatchOperation.BulkInsert(query, lRegistros, "trazaweb");
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ObjectPaging getAgendadoDomicilios(JSONObject jsonObject) {
		ObjectPaging objectPaging = new ObjectPaging();
		List<Object> objectList = new ArrayList<Object>();

		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
			int genteId = gsonObject.has("genteId") ? gsonObject.get("genteId").getAsInt() : -1;
			JsonObject gsonObjectResult = gsonObject.get("busqueda").getAsJsonObject();
			String texto = gsonObjectResult.has("texto") ? gsonObjectResult.get("texto").getAsString() : "";
			int tipoDomicilio = gsonObjectResult.has("tipoDomicilio") ? gsonObjectResult.get("tipoDomicilio").getAsInt()
					: 0;
			Boolean principal = false;
			Boolean entrega = false;
			Boolean cobranza = false;
			Boolean mailing = false;
			Boolean alternativo = false;
			switch (tipoDomicilio) {
			case 1:
				principal = true;
				break;
			case 2:
				entrega = true;
				break;
			case 3:
				cobranza = true;
				break;
			case 4:
				mailing = true;
				break;
			}

			int pageNumber = gsonObject.has("pageNumber") ? gsonObject.get("pageNumber").getAsInt() : 0;
			int pageSize = gsonObject.has("pageSize") ? gsonObject.get("pageSize").getAsInt() : 0;
			Long total_items = 0l;
			
			objectList = domiciliosDAO.getDomicilioList(genteId, texto, principal, entrega, cobranza, mailing,
					pageNumber, pageSize, alternativo);
			if (objectList != null && objectList.size() > 0) {
				total_items = domiciliosDAO.getDomicilioListCount(genteId, texto, principal, entrega, cobranza, mailing,
						alternativo);
			}
			objectPaging = new ObjectPaging(objectList, total_items);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectPaging;
	}
	
	public List<Object> getCondTributariaAFIP(JSONObject jsonObject) {
		List<Object> objectList = new ArrayList<Object>();

		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
			JsonObject gsonObjectResult = gsonObject.get("busqueda").getAsJsonObject();
			String data = gsonObjectResult.has("data") ? gsonObjectResult.get("data").getAsString() : "";
			
			objectList = condTributariaAFIPDAO.getCondTributaria(data);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectList;
	}
	
	public ObjectPaging findPersons(JSONObject jsonObject) {
		ObjectPaging objectPaging = new ObjectPaging();
		List<Object> objectList = new ArrayList<Object>();

		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
			JsonObject gsonObjectResult = gsonObject.get("busqueda").getAsJsonObject();
			String data = gsonObjectResult.has("data") ? gsonObjectResult.get("data").getAsString() : "";
			int relacionId = gsonObjectResult.has("relacionId") ? gsonObjectResult.get("relacionId").getAsInt() : 0;

			int pageNumber = gsonObject.has("pageNumber") ? gsonObject.get("pageNumber").getAsInt() : 0;
			int pageSize = gsonObject.has("pageSize") ? gsonObject.get("pageSize").getAsInt() : 0;
			Long total_items = 0l;

			objectList = genteDAO.findPersons(data, relacionId, pageNumber, pageSize);

			if (objectList != null && objectList.size() > 0) {
				total_items = genteDAO.findPersonsCount(data, relacionId);
				// Calculo saldo actual
				objectList = transacService.getSaldoActualByAgendados(objectList);
			}
			objectPaging = new ObjectPaging(objectList, total_items);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectPaging;
	}

	public ObjectPaging findAgendado(JSONObject jsonObject) {
		ObjectPaging objectPaging = new ObjectPaging();
		List<Object> objectList = new ArrayList<Object>();

		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
			JsonObject gsonObjectResult = gsonObject.get("busqueda").getAsJsonObject();
			String razonSocial = gsonObjectResult.has("razonSocial") ? gsonObjectResult.get("razonSocial").getAsString()
					: "";
			String cuit = gsonObjectResult.has("cuit") ? gsonObjectResult.get("cuit").getAsString() : "";
			int relacionId = gsonObjectResult.has("relacionId") ? gsonObjectResult.get("relacionId").getAsInt() : 0;

			int pageNumber = gsonObject.has("pageNumber") ? gsonObject.get("pageNumber").getAsInt() : 0;
			int pageSize = gsonObject.has("pageSize") ? gsonObject.get("pageSize").getAsInt() : 0;
			Long total_items = 0l;

			objectList = genteDAO.findAgendado(razonSocial, cuit, relacionId, pageNumber, pageSize);

			if (objectList != null && objectList.size() > 0) {
				total_items = genteDAO.findAgendadoCount(razonSocial, cuit, relacionId);
				// Calculo saldo actual
				objectList = transacService.getSaldoActualByAgendados(objectList);
			}
			objectPaging = new ObjectPaging(objectList, total_items);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectPaging;
	}

	public List<Object> getAgendadoProveedorAll() {
		return genteDAO.getAgendadoProveedorAll();
	}

	public ObjectPaging getAgendadoTransacciones(JSONObject jsonObject) {
		ObjectPaging objectPaging = new ObjectPaging();
		List<Object> objectList = new ArrayList<Object>();

		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
			int genteId = gsonObject.has("genteId") ? gsonObject.get("genteId").getAsInt() : -1;
			JsonObject gsonObjectResult = gsonObject.get("busqueda").getAsJsonObject();
			String texto = gsonObjectResult.has("texto") ? gsonObjectResult.get("texto").getAsString() : "";
			int tipoComprobante = gsonObjectResult.has("tipoComprobante")
					? gsonObjectResult.get("tipoComprobante").getAsInt() : 0;
			int Estado = gsonObjectResult.has("estado") ? gsonObjectResult.get("estado").getAsInt() : 0;

			int pageNumber = gsonObject.has("pageNumber") ? gsonObject.get("pageNumber").getAsInt() : 0;
			int pageSize = gsonObject.has("pageSize") ? gsonObject.get("pageSize").getAsInt() : 0;
			Long total_items = 0l;

			objectList = transacDAO.getAgendadoTransacList(genteId, texto, tipoComprobante, Estado, pageNumber,
					pageSize);
			if (objectList != null && objectList.size() > 0) {
				total_items = transacDAO.getAgendadoTransacListCount(genteId, texto, tipoComprobante, Estado,
						pageNumber, pageSize);
			}
			objectPaging = new ObjectPaging(objectList, total_items);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectPaging;
	}

	public List<Object> getAgendadoDomicilioTipo(Integer idGente, Integer tipoDomicilio) {
		Boolean principal = false;
		Boolean entrega = false;
		Boolean cobranza = false;
		Boolean mailing = false;

		switch (tipoDomicilio) {
		case 1:
			principal = true;
			break;
		case 2:
			entrega = true;
			break;
		case 3:
			cobranza = true;
			break;
		case 4:
			mailing = true;
			break;
		}
		List<Domicilios> list = domiciliosDAO.getAgendadoDomicilioTipo(idGente, principal, entrega, cobranza, mailing);
		List<Object> objList = new ArrayList<Object>();
		for (Domicilios item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(String.valueOf(item.getId()), item.getDescripC());
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	@Override
	public List<Object> getAgendadoDomicilioTipo(Integer idGente, Integer tipoDomicilio, Boolean alternativo) {
		Boolean principal = false;
		Boolean entrega = false;
		Boolean cobranza = false;
		Boolean mailing = false;

		switch (tipoDomicilio) {
		case 1:
			principal = true;
			break;
		case 2:
			entrega = true;
			break;
		case 3:
			cobranza = true;
			break;
		case 4:
			mailing = true;
			break;
		}
		List<Domicilios> list = domiciliosDAO.getAgendadoDomicilioByAlternativo(idGente, principal, entrega, cobranza,
				mailing, alternativo);
		List<Object> objList = new ArrayList<Object>();
		for (Domicilios item : list) {
			Object objCodigoDescrip = new ObjCodigoDescrip(String.valueOf(item.getId()), item.getDescripC());
			objList.add(objCodigoDescrip);
		}
		return objList;
	}

	@Override
	public Object getGoogleAddresses(String address) {

		// Llamar al rest y pasarla address por queryString:
		// http://maps.google.com/maps/api/geocode/json?address=
		// Retornar el response

		return CallRestService.callGoogleAddres(address);
	}

	public List<Object> getObraSocialAll() {

		return genteDAO.getObraSocialAll();
	}

}
