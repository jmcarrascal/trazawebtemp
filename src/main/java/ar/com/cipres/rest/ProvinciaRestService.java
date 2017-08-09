package ar.com.cipres.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.services.IProvinciaService;

@Component 
@Path("/provincia")
public class ProvinciaRestService {
		
	@Autowired
	private IProvinciaService provinciaService;
		
	@GET
	@Path("/getProvincias")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getProvincias() {	
		try{
			List<Object> list = provinciaService.getProvincias();
			return new JsonResultList(true, "OK", list);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}
}
