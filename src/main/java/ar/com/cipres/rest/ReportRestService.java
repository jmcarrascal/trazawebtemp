package ar.com.cipres.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.model.JsonResultIngresoMercaderia;
import ar.com.cipres.services.IReportService;

@Component 
@Path("/report")
public class ReportRestService {
		
	@Autowired
	private IReportService reportService;
	
		
	@POST
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResultIngresoMercaderia ingresarMercaderia(String json) {	
		try{
			String resultStr = reportService.createTablePDF(json);
			return new JsonResultIngresoMercaderia(true, resultStr);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResultIngresoMercaderia(false, e.getMessage());
		}
	}
	
	

}
