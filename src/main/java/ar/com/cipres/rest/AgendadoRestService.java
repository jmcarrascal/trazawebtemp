package ar.com.cipres.rest;

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

import ar.com.cipres.model.Domicilios;
import ar.com.cipres.model.Gente;
import ar.com.cipres.model.JsonResult;
import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.model.ObjectPaging;
import ar.com.cipres.services.IAgendadoService;
import ar.com.cipres.util.ObjCodigoDescrip;

@Component 
@Path("/agendado")
public class AgendadoRestService {
		
	@Autowired
	private IAgendadoService agendadoService;
	
	@POST
	@Path("/findAgendadoShort")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList findAgendadoShort(ObjCodigoDescrip objCodigoDescrip) {	
//		JsonResultList result = null; 
		try{
			List<Object> genteList = agendadoService.findAgendadoShort(objCodigoDescrip);
			
			//result = trazaService.confirmTransacAnmat(dataSendAnmat,  "DROGUERIAORIEN", "ORIEN1152","https://trazabilidad.pami.org.ar:9050/trazamed.WebService");
			return new JsonResultList(true, "OK", genteList);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null);
		}
	}

	@GET
	@Path("/updateCondTributariaAFIP")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult updateCondTributariaAFIP() {	
		try{
			agendadoService.updateCondTributariaAFIP();			
			return new JsonResult(true, "OK");
		}catch(Exception e){
			return new JsonResult(false,e.getMessage());
		}
	}	
	
	@POST
	@Path("/getCondTributariaAFIP")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult getCondTributariaAFIP(JSONObject json) {	
		try{
			List<Object> lCond = agendadoService.getCondTributariaAFIP(json);
			return new JsonResult(true, "OK",lCond);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult(true,e.getMessage(), null);
		}
	}	
	
	@POST
	@Path("/findPersons")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList findPersons(JSONObject json) {	
		try{
			ObjectPaging objectPaging = agendadoService.findPersons(json);
			return new JsonResultList(true, "OK", objectPaging.getObjectList(), objectPaging.getTotal_items());
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null, 0l);
		}
	}
	
	@POST
	@Path("/findAgendado")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList findAgendado(JSONObject json) {	
		try{
			ObjectPaging objectPaging = agendadoService.findAgendado(json);
			return new JsonResultList(true, "OK", objectPaging.getObjectList(), objectPaging.getTotal_items());
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null, 0l);
		}
	}
	
	@POST
	@Path("/saveAgendado")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult saveAgendado(Gente gente) {
		try{			
			agendadoService.saveAgendado(gente);			
			return new JsonResult(true, "OK");
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult(false,e.getMessage());
		}			
	}
	
	
	@GET
	@Path("/getAgendado/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult getAgendado(@PathParam("id")Integer id) {	
		try{					
			return new JsonResult(true, "OK", agendadoService.getAgendado(id));
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult(false,e.getMessage(), null);
		}
	}
	
	@GET
	@Path("/find/{searchString}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList findAgendado(@PathParam("searchString")String searchString) {	
		try{
			List<Object> objectPaging = agendadoService.findAgendado(searchString);
			return new JsonResultList(true, "OK", ((ObjectPaging) objectPaging).getObjectList(), ((JsonResultList) objectPaging).getTotal_items());
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null, 0l);
		}
	}
	
	@GET
	@Path("/getAgendadoProveedorAll")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getAgendadoProveedorAll() {	
		try{					
			return new JsonResultList(true, "OK", agendadoService.getAgendadoProveedorAll());
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}

	@GET
	@Path("/getAgendadoDomicilio/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult getAgendadoDomicilio(@PathParam("id")Integer id) {
		try{					
			return new JsonResult(true, "OK", agendadoService.getAgendadoDomicilio(id));
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult(false,e.getMessage(), null);
		}
	}
	
	@GET
	@Path("/searchDomicilio/{address}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAgendadoDomicilio(@PathParam("address")String address) {
		try{					
			return  (String) agendadoService.getGoogleAddresses(address);
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	@POST
	@Path("/saveAgendadoDomicilio")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult saveAgendadoDomicilio(Domicilios domicilio) {
		try{			
			agendadoService.saveAgendadoDomicilio(domicilio);			
			return new JsonResult(true, "OK");
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult(false,e.getMessage());
		}			
	}

	@POST
	@Path("/getAgendadoDomicilios")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getAgendadoDomicilios(JSONObject json) {
		try{
			ObjectPaging objectPaging = agendadoService.getAgendadoDomicilios(json);
			return new JsonResultList(true, "OK", objectPaging.getObjectList(), objectPaging.getTotal_items());
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null, 0l);
		}
	}
	
	@POST
	@Path("/getAgendadoTransacciones")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getAgendadoTransacciones(JSONObject json) {
		try{
			ObjectPaging objectPaging = agendadoService.getAgendadoTransacciones(json);
			return new JsonResultList(true, "OK", objectPaging.getObjectList(), objectPaging.getTotal_items());
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null, 0l);
		}
	}
	
	@GET
	@Path("/getAgendadoDomicilio/{idGente}/{tipoDomicilio}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getAgendadoDomicilioTipo(@PathParam("idGente")Integer idGente, @PathParam("tipoDomicilio")Integer tipoDomicilio) {
		try{
			List<Object> list = agendadoService.getAgendadoDomicilioTipo(idGente, tipoDomicilio);
			return new JsonResultList(true, "OK", list, new Long(list.size()));
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}
	
	@GET
	@Path("/getAgendadoDomicilioByAlternativo/{idGente}/{tipoDomicilio}/{alternativo}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getAgendadoDomicilioAlternativo(@PathParam("idGente")Integer idGente, @PathParam("tipoDomicilio")Integer tipoDomicilio, @PathParam("alternativo")Boolean alternativo ) {
		try{
			List<Object> list = agendadoService.getAgendadoDomicilioTipo(idGente, tipoDomicilio, alternativo);
			return new JsonResultList(true, "OK", list, new Long(list.size()));
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}
}
