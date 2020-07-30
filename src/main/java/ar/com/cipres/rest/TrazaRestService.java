package ar.com.cipres.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inssjp.mywebservice.business.IWebServiceStub.TransaccionPlainWS;

import ar.com.cipres.dao.IItemsDAO;
import ar.com.cipres.dao.IKardexDAO;
import ar.com.cipres.dao.IParametrizacionDAO;
import ar.com.cipres.dao.IUsuarioDAO;
import ar.com.cipres.model.DataSendAnmat;
import ar.com.cipres.model.Items;
import ar.com.cipres.model.JsonResultIngresoMercaderia;
import ar.com.cipres.model.JsonResultRomaneo;
import ar.com.cipres.model.Usuario;
import ar.com.cipres.services.ITransacService;
import ar.com.cipres.services.ITrazaService;
import ar.com.cipres.util.Constants;

@Component
@Path("/traza")
public class TrazaRestService {

	@Autowired
	private ITrazaService trazaService;

	@Autowired
	private IParametrizacionDAO parametrizacionDAO;

	@Autowired
	private IKardexDAO kardexDAO;

	@Autowired
	private IItemsDAO itemsDAO;
	
	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Autowired
	private ITransacService transacService;
	

	@GET
 	@Path("/get_user")
 	public Integer get_user() {	
 		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
 	     String name = auth.getName();
 	    Usuario usuario = usuarioDAO.getByUsername(name);
 		return usuario.getUsersk();
 	}

	@GET
	@Path("/get_comprob_dupli")
	@Produces(MediaType.APPLICATION_JSON)
	public String get_dupli() {
		// return trazaService.getTransaccionesARecepcionar(comprob,tipo,
		// "DROGUERIAORIEN", "ORIEN1152");
		// String usuario =
		// parametrizacionDAO.getByPrimaryKey(Constants.PARAM_USR_PAMI).getValor();
		// String pass =
		// parametrizacionDAO.getByPrimaryKey(Constants.PARAM_PASS_PAMI).getValor();
		List<Object[]> result = kardexDAO.getKarGroup();
		int contadorreg = 0;
		for (Object[] kar : result) {
			Long contador = (Long) kar[0];
			Integer transac = (Integer) kar[1];
			String clave = (String) kar[2];

			if (contador > 1) {
				contadorreg++;
				// System.out.println("Transac: " + transac);
				// System.out.println("Clave: " + clave);
				// System.out.println("Contador: " + contador);
				Double totalitems = 0d;
				List<Items> items_lisst = itemsDAO.getByTransacArticulo(
						transac, clave);
				for (Items i : items_lisst) {
					totalitems = totalitems + i.getCant1();
					// System.out.println("Cantidad: " + i.getCant1());
				}

				Double totalkar = kardexDAO.getSumKar(clave, transac);
				if (totalitems < totalkar) {
					System.out.println("Transac: " + transac);
					System.out.println("Total OC: " + totalitems);
					System.out.println("Total kardex: " + totalkar);
				}

			}
		}

		System.out.println("Registros duplicados: " + contadorreg);
		return "ok";

	}

	@GET
	@Path("/findRemitoARecepcionar/{comprob}/{tipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public TransaccionPlainWS[] get(@PathParam("comprob") String comprob,
			@PathParam("tipo") String tipo) {
		// return trazaService.getTransaccionesARecepcionar(comprob,tipo,
		// "DROGUERIAORIEN", "ORIEN1152");
		String usuario = parametrizacionDAO.getByPrimaryKey(
				Constants.PARAM_USR_PAMI).getValor();
		String pass = parametrizacionDAO.getByPrimaryKey(
				Constants.PARAM_PASS_PAMI).getValor();
		return trazaService.getTransaccionesARecepcionar(comprob, tipo,
				usuario, pass);
	}

	@GET
	@Path("/getRomaneo/{trnsacnr_oc}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultRomaneo getRomaneo(
			@PathParam("trnsacnr_oc") String trnsacnr_oc) {
		Integer transac_oc_long = 0;
		try {
			transac_oc_long = Integer.parseInt(trnsacnr_oc);
		} catch (NullPointerException ne) {
			return new JsonResultRomaneo(false,
					"La orden de compra ingresada es invalida", null, "");
		}

		
		JsonResultRomaneo jsonResultRomaneo = transacService.getRomaneoOc(transac_oc_long);

		return jsonResultRomaneo;
	}

	@POST
	@Path("/confirmTransacAnmat")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultIngresoMercaderia confirmTransacAnmat(DataSendAnmat dataSendAnmat) {
		JsonResultIngresoMercaderia result = null;
		try {
			String usuario = parametrizacionDAO.getByPrimaryKey(
					Constants.PARAM_USR_PAMI).getValor();
			String pass = parametrizacionDAO.getByPrimaryKey(
					Constants.PARAM_PASS_PAMI).getValor();

			result = trazaService
					.confirmTransacAnmat(dataSendAnmat, usuario, pass,
							"https://trazabilidad.pami.org.ar:9050/trazamed.WebService");			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResultIngresoMercaderia(false, e.getMessage());
		}
	}

	@GET
	@Path("/sendMedicamento/{transacNr}/{clave}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly = false)
	public String sendMedicamento(@PathParam("transacNr") String transacNr,
			@PathParam("clave") String clave) {
		// Invoco al metodo de facturacion
		String msg = "";
		Integer transacNrInt = 0;
		try {
			transacNrInt = Integer.parseInt(transacNr);
		} catch (NumberFormatException e) {
			return "El formato de la transaccion no es valido";
		}
		trazaService.sendMedicamentos(transacNrInt);

		return "OK";
	}
	
	
	@GET
	@Path("/sendMedicamentoTest/{transacNr}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly = false)
	public String sendMedicamentoTest(@PathParam("transacNr") String transacNr) {
		// Invoco al metodo de facturacion
		String msg = "";
		Integer transacNrInt = 0;
		try {
			transacNrInt = Integer.parseInt(transacNr);
		} catch (NumberFormatException e) {
			return "El formato de la transaccion no es valido";
		}
		msg = trazaService.testAsync(transacNr);

		return msg;
	}
	
	@GET
	@Path("/public/getCaja/{hash}")
	@Transactional(readOnly = false)
	public String getCaja(@PathParam("hash") String hash) {		
		String result = trazaService.getCaja(hash);
		return result;
	}

}
