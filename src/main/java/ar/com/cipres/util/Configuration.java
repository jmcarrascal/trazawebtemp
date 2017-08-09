package ar.com.cipres.util;

public class Configuration {
	private static Configuration instance = null;

	private Configuration() {
	}

	public static Configuration getInstance() {
		if (instance == null) {
			synchronized (Configuration.class) {
				if (instance == null) {
					instance = new Configuration();
					ConfigurationReader cr = new ConfigurationReader();
					cr.getConfigurationFromFile();
				}
			}
		}
		return instance;
	}

	private String zipFolder;
	private String URLOrdenCompra;

	public String getZipFolder() {
		return zipFolder;
	}

	public void setZipFolder(String zipFolder) {
		this.zipFolder = zipFolder;
	}

	public String getURLOrdenCompra() {
		return URLOrdenCompra;
	}

	public void setURLOrdenCompra(String uRLOrdenCompra) {
		URLOrdenCompra = uRLOrdenCompra;
	}

	public static void setInstance(Configuration instance) {
		Configuration.instance = instance;
	}

}
