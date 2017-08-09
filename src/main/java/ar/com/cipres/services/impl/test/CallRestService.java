package ar.com.cipres.services.impl.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CallRestService {

	public static void main(String[] args) {

		//System.out.println(CallRestService.callGoogleAddres("Rosario 563, CABA"));
		
		try {
			callSerlab("00200451");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String callGoogleAddres(String address) {

		try {
			address = address.replaceAll(" ", "%20");
			URL url = new URL("https://maps.google.com/maps/api/geocode/json?address=" + address
					+ "&key=AIzaSyBH5YtJkzLYgOi0UentzSGzqJYLm-X7Z4g");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			String outputFinal = "";
			while ((output = br.readLine()) != null) {
				outputFinal = outputFinal + output;
			}
			JsonParser jsonParser = new JsonParser();

			JsonObject jb = jsonParser.parse(outputFinal).getAsJsonObject();
			jb.addProperty("success", true);

			conn.disconnect();
			return jb.get("results").toString();

		} catch (MalformedURLException e) {

			e.printStackTrace();
			return "{'success': False}";

		} catch (IOException e) {

			e.printStackTrace();
			return "{'success': False}";

		}

	}

	// HTTP POST request
	public static String callSerlab(String remito) throws Exception {

		String url = "http://serlablogistica.com.ar/tracking.php";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");		
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		

		String urlParameters = "password=a28d1d639200f193862388fa2cc0b597&code=" + remito;

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();


		return response.toString();

	}

	

}
