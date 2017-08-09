package ar.com.cipres.rest;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.model.JsonResultList;
import ar.com.cipres.services.IKardexService;
import ar.com.cipres.util.ObjCodigoDescrip;

@Component 
@Path("/kardex")
public class KardexRestService {
			
		
	@Autowired
	private IKardexService kardexService;
	
	
	@POST
	@Path("/findKardex")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList findAgendadoShort(ObjCodigoDescrip objCodigoDescrip) {	
		JsonResultList result = null; 
		try{
			List<Object> genteList = kardexService.findIngreso(objCodigoDescrip);			
			//result = trazaService.confirmTransacAnmat(dataSendAnmat,  "DROGUERIAORIEN", "ORIEN1152","https://trazabilidad.pami.org.ar:9050/trazamed.WebService");
			return new JsonResultList(true, "OK", genteList);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null);
		}
	}
	
	
	@POST
	@Path("/findIngresoRefInterna")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList findIngresoRefInterna(ObjCodigoDescrip objCodigoDescrip) {	
		JsonResultList result = null; 
		try{
			List<Object> genteList = kardexService.findIngresoRefInterna(objCodigoDescrip);			
			//result = trazaService.confirmTransacAnmat(dataSendAnmat,  "DROGUERIAORIEN", "ORIEN1152","https://trazabilidad.pami.org.ar:9050/trazamed.WebService");
			return new JsonResultList(true, "OK", genteList);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null);
		}
	}
	
	@POST
	@Path("/reportIngreso")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList getReportIngreso(ObjCodigoDescrip objCodigoDescrip) {	
		JsonResultList result = null; 
		try{
			String resultStr = kardexService.getReportIngreso(objCodigoDescrip);			
			//result = trazaService.confirmTransacAnmat(dataSendAnmat,  "DROGUERIAORIEN", "ORIEN1152","https://trazabilidad.pami.org.ar:9050/trazamed.WebService");
			return new JsonResultList(true, resultStr, null);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null);
		}
	}
	
	@POST
	@Path("/reportIngresoRefInterna")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultList reportIngresoRefInterna(ObjCodigoDescrip objCodigoDescrip) {	
		JsonResultList result = null; 
		try{
			String resultStr = kardexService.reportIngresoRefInterna(objCodigoDescrip);			
			//result = trazaService.confirmTransacAnmat(dataSendAnmat,  "DROGUERIAORIEN", "ORIEN1152","https://trazabilidad.pami.org.ar:9050/trazamed.WebService");
			return new JsonResultList(true, resultStr, null);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultList(true,e.getMessage(), null);
		}
	}
	
	

}
