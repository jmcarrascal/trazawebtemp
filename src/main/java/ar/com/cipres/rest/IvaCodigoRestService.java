package ar.com.cipres.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.services.IIvaCodigoService;

@Component 
@Path("/ivacodigo")
public class IvaCodigoRestService {
		
	@Autowired
	private IIvaCodigoService ivaCodigoService;
		
	@GET
	@Path("/getIvaCodigos")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getIvaCodigos() {	
		try{
			List<Object> list = ivaCodigoService.getIvaCodigos();
			return new JsonResultList(true, "OK", list);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}
}
