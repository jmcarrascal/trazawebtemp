package ar.com.cipres.services;

import com.google.gson.JsonObject;

import ar.com.cipres.model.JsonResult;
import ar.com.cipres.util.PdfType;

public interface IReportServiceNew {
	JsonResult createPdf(JsonObject toCreate, PdfType tipo) throws Exception;
}
