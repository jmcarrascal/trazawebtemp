package ar.com.cipres.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.services.IZonaService;

@Component 
@Path("/zona")
public class ZonaRestService {
		
	@Autowired
	private IZonaService zonaService;
		
	@GET
	@Path("/getZonas")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getZonas() {	
		try{
			List<Object> list = zonaService.getZonas();
			return new JsonResultList(true, "OK", list);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}
}
