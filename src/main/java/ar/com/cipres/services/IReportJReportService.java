package ar.com.cipres.services;

import java.io.InputStream;

public interface IReportJReportService {

	String getPdfReport(InputStream stream, String string);
	
}