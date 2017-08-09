package ar.com.cipres.ui.serverhttp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.print.PrintException;

import org.json.simple.JSONObject;

import ar.com.cipres.ui.util.FormatUtil;
import ar.com.cipres.ui.util.PrinterLabelUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;

public class ServerHttp {

	String US_ASCII = "US-ASCII";
	String UTF_8 = "utf-8";
	private static String providerName;

	private static final Logger LOGGER = Logger
			.getLogger("ar.com.cipres.ui.serverhttp.ServerHttp");

	// public static void main(String[] args) throws Exception {
	// HttpServer server = HttpServer.create(new InetSocketAddress(12555), 0);
	// server.createContext("/getOSCertificates",
	// new LoadCertificatesHandler());
	// server.createContext("/status", new StatusHandler());
	// server.createContext("/uploadMultipartDoc", new
	// UploadMultipartDocHandler());
	// server.createContext("/uploadB64Doc", new UploadB64DocHandler());
	// server.createContext("/signDoc", new SignDocHandler());
	// server.createContext("/getFirstPageImage", new
	// GetFirstPageImageHandler());
	// server.createContext("/downloadSignedDoc",
	// new DownloadDocumentSignerHandler());
	// server.setExecutor(Executors.newCachedThreadPool());
	// server.start();
	// }

	public void startServer() throws Exception {
		HttpsServer s;
		HttpServer server = HttpServer.create(new InetSocketAddress(12555), 0);
		server.createContext("/printLabel", new PrintLabelHandler());
		server.createContext("/status", new StatusHandler());
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();
	}

	public static void main(String[] args) throws Exception {

		HttpsServer s;
		HttpServer server = HttpServer.create(new InetSocketAddress(12555), 0);
		server.createContext("/printLabel", new PrintLabelHandler());
		server.createContext("/status", new StatusHandler());
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();

	}

	static class StatusHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			JSONObject result = new JSONObject();
			result.put("success", true);
			String response = result.toJSONString();
			t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			t.getResponseHeaders().add("Content-Type",
					"application/json; charset=utf-8");
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class PrintLabelHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {

			JsonObject requetsBodyJson = null;
			try {
				requetsBodyJson = FormatUtil.InputStreamToJSONObject(t
						.getRequestBody());

				
				String printName = requetsBodyJson.get("printName").getAsString();
				
				JsonArray trazabiList = requetsBodyJson.get("trazabiList")
						.getAsJsonArray();

				PrinterLabelUtil.printer(trazabiList, printName);
			} catch (PrintException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			JSONObject result = new JSONObject();
			result.put("success", true);
			String response = result.toJSONString();
			t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
			t.getResponseHeaders().add("Content-Type",
					"application/json; charset=utf-8");
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

}
