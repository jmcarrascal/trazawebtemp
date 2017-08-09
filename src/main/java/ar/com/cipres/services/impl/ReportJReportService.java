package ar.com.cipres.services.impl;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.cipres.services.IReportJReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;

@Service
@Transactional
public class ReportJReportService implements IReportJReportService {

	public String getPdfReport(InputStream stream, String reportName) {

		Map<String, Object> params = new HashMap<>();

		try {

			JasperReport jasperReport = JasperCompileManager.compileReport(
					getClass().getClassLoader().getResourceAsStream("/reports/" + reportName + ".jrxml"));
			
			
			JsonDataSource ds = new JsonDataSource(stream);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);
						
			//JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getClassLoader().getResourceAsStream("/reports/" + reportName + ".jasper"), params, ds);

			byte[] result = JasperExportManager.exportReportToPdf(jasperPrint);

			return new String(Base64.encodeBase64(result), StandardCharsets.UTF_8);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}