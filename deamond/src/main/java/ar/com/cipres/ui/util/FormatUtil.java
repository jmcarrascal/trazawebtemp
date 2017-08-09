package ar.com.cipres.ui.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;





public class FormatUtil {
	
	private Boolean success = false;
	private String msg = "";
	

	
	public static JsonObject InputStreamToJSONObject(InputStream ip) throws Exception{
		InputStreamReader isr = new InputStreamReader(ip,
				"utf-8");
		BufferedReader reader = new BufferedReader(isr);
		String line = reader.readLine();
		String value = new String();
		while(line != null)
		{
			value = value + line;
		    line = reader.readLine();
		}
		JsonParser parser = new JsonParser();
		JsonObject result = (JsonObject)parser.parse(value);
		return result;
	}

	public static JsonObject getSimpleResultMsg(Boolean success, String msg) {		
		JsonObject result = new JsonObject();
		result.addProperty("success", success);
		result.addProperty("msg", msg);
		return result;
	}


}
