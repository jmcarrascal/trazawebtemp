package ar.com.cipres.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.model.ObjectPaging;
import ar.com.cipres.services.IAgendadoService;
import ar.com.cipres.services.ITransacService;

@Component 
@Path("/public")
public class PublicRestService {
		
	@Autowired
	private IAgendadoService agendadoService;
	
	@Autowired
	private ITransacService transacService;
	
	
	
	
	@GET
	@Path("/getObraSocialAll")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getAgendado() {	
		try{					
			return new JsonResultList(true, "OK", agendadoService.getObraSocialAll(),0l);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false, e.getMessage(), null,0l);
		}
	}
	
	@POST
	@Path("/getPedidos")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getDomiciliosTransaccionesAgrupados(JSONObject json) {
		try{
			ObjectPaging objectPaging = transacService.getDomiciliosTransaccionesAgrupadosAfiliado(json);
			//ObjectPaging objectPaging = transacService.getDomiciliosTransaccionesAgrupados(json);
			return new JsonResultList(true, "OK", objectPaging.getObjectList(), objectPaging.getTotal_items());			
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null, 0l);
		}
	}
	
	
}
