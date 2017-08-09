package ar.com.cipres.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.services.IExistenciasService;

@Component 
@Path("/existencias")
public class ExistenciasRestService {
		
	@Autowired
	private IExistenciasService existenciasService;
		
	@GET
	@Path("/getExistencias")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getExistencias() {	
		try{
			List<Object> list = existenciasService.getExistencias();
			return new JsonResultList(true, "OK", list);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}
}
