
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

import com.google.gson.Gson;

import ar.com.cipres.model.JsonResult;
import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.model.ObjectPaging;
import ar.com.cipres.model.Stock;
import ar.com.cipres.services.IStockService;
import ar.com.cipres.wrapper.UsuarioWrapper;

@Component 
@Path("/articulo")
public class ArticuloRestService {
		
	@Autowired
	private IStockService articuloService;
	
	
	
	@POST
	@Path("/findArticulo")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList findArticulo(JSONObject json) {	
		try{
			ObjectPaging objectPaging = articuloService.findArticulo(json);	
			if(objectPaging!=null)
				return new JsonResultList(true, "OK", objectPaging.getObjectList(), objectPaging.getTotal_items());
			else
				return new JsonResultList(false, "Error al buscar articulos", null);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null);
		}
	}
	
	@POST
	@Path("/findArticulos")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList findArticulos(JSONObject json) {	
		try{
			ObjectPaging objectPaging = articuloService.findArticulosUsuariosExistencias(json);	
			if(objectPaging!=null)
				return new JsonResultList(true, "OK", objectPaging.getObjectList(), objectPaging.getTotal_items());
			else
				return new JsonResultList(false, "Error al buscar articulos", null);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null);
		}
	}
	
	
	@GET
	@Path("/findArticuloByKey/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult get_by_descrip(@PathParam("id") String id) {
		try{
			Object articulo = articuloService.findArticuloByKey(id);						
			return new JsonResult(true, "Ok", articulo);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult(false, e.getMessage());
		}

	}
	
	@GET
	@Path("/getArticuloMadreAll")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getArticuloMadreAll() {	
		try{
			List<Object> list = articuloService.getArticuloMadreAll();
			return new JsonResultList(true, "OK", list);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(false,e.getMessage(), null);
		}
	}

	@GET
	@Path("/updateAlfaBeta")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult updateAlfaBeta() {	
		try{
			articuloService.updateAlfaBeta();			
			return new JsonResult(true, "OK");
		}catch(Exception e){
			return new JsonResult(false,e.getMessage());
		}
	}	
	
	@POST
	@Path("/saveArticulo")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResult saveArticulo(Stock stock) {	
		try{
			articuloService.saveArticulo(stock);			
			return new JsonResult(true, "OK");
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult(false,e.getMessage());
		}
	}	
	
	
}
