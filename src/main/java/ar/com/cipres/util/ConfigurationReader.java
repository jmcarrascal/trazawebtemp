package ar.com.cipres.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
	public boolean getConfigurationFromFile() {
		boolean result = false;
		InputStream inputStream = null;
		try {
			Configuration conf = Configuration.getInstance();
			Properties prop = new Properties();
			String propFileName = "application.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} 
			
			 String zipFolder  = prop.getProperty("zipFolder");
			 String URLOrdenCompra  = prop.getProperty("URLOrdenCompra");

			conf.setURLOrdenCompra(URLOrdenCompra);;
			conf.setZipFolder(zipFolder);

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
