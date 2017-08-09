package ar.com.cipres.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.services.IVendedorService;

@Component 
@Path("/vendedor")
public class VendedorRestService {
		
	@Autowired
	private IVendedorService vendedorService;
		
	@GET
	@Path("/getVendedores")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getVendedores() {	
		try{
			List<Object> list = vendedorService.getVendedores();
			return new JsonResultList(true, "OK", list);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}
}
