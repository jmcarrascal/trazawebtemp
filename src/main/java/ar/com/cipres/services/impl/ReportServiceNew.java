package ar.com.cipres.services.impl;

import java.nio.charset.Charset;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ar.com.cipres.model.JsonResult;
import ar.com.cipres.services.IReportServiceNew;
import ar.com.cipres.util.PdfType;

@Service
public class ReportServiceNew implements IReportServiceNew {

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public JsonResult createPdf(JsonObject toCreate, PdfType tipo) {
		// set headers
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(toCreate.toString(), headers);
		// send request and parse result
		ResponseEntity<String> loginResponse = restTemplate.exchange(
				"http://localhost:8080/pdf-generator/" + tipo.GetMethod(), HttpMethod.POST, entity, String.class);
		if (loginResponse.getStatusCode() == HttpStatus.OK) {
			JsonObject userJson = new Gson().fromJson(loginResponse.getBody(), JsonObject.class);
			return new JsonResult(true, "Ok", userJson.get("b64Document").getAsString());
		} else
			return new JsonResult(false, "Error de comunicacion con ws generador de pdf");
	}

}
