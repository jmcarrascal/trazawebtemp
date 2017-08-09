package ar.com.cipres.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.services.ITransporteService;

@Component 
@Path("/transporte")
public class TransporteRestService {
		
	@Autowired
	private ITransporteService transporteService;
		
	@GET
	@Path("/getTransportes")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getTransportes() {	
		try{
			List<Object> list = transporteService.getTransportes();
			return new JsonResultList(true, "OK", list);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}
}
