package ar.com.cipres.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import ar.com.cipres.model.DataSendMercaderia;
import ar.com.cipres.model.Despachos;
import ar.com.cipres.model.FilterTrazabi;
import ar.com.cipres.model.Kardex;
import ar.com.cipres.model.Trazabi;
import ar.com.cipres.model.report.Data;
import ar.com.cipres.model.report.Header;
import ar.com.cipres.model.report.ReportTable;

public class FormatUtil {

	public static String llenarConCero(String s, int width) {
		String formattedString;

		// La serie es m�s corta que la anchura especificada,
		// por lo que tenemos que rellenarla con blancos.
		if (s.length() < width) {
			StringBuffer buffer = new StringBuffer(s);
			for (int i = s.length(); i < width; ++i) {
				buffer.append("0");
			}
			formattedString = buffer.toString();
		} else {
			formattedString = s.substring(0, width);
		}
		return formattedString;
	}
	
	public static String parseLote(String lote) {
		String result = lote;
		if (result == null) {
			result = "";
			return result;
		}
		try {
			// Encuentro la V
			int v = lote.indexOf("V=");
			if (v == -1) {
				v = lote.indexOf("V ");
			}
			if (v == -1) {
				v = lote.indexOf("V");
			}
			if (v != -1) {
				lote = lote.substring(0, v).trim();
			}
			int sizeLote = lote.length();
			int l = lote.indexOf("L=");
			if (l != -1) {
				l = l + 2;
			}
			if (l == -1) {
				l = lote.indexOf("L ");
				if (l != -1) {
					l = l + 2;
				}
			}
			if (l == -1) {
				l = lote.indexOf("L");
				if (l != -1) {
					l = l + 1;
				}

			}
			if (l != -1) {
				lote = lote.substring(l, sizeLote).trim();
			}

			System.out.println(lote);
			result = lote;

		} catch (Exception e) {
			e.printStackTrace();
		}
		result = result.replace(".", "");
		return result;
	}

	
	public static String getNumberString(Double number, Integer longitud){
		
		String numberString = String.valueOf(FormatUtil.redondearEn2(number));
		
		if (numberString.substring(numberString.indexOf(".")).length() > 2){
			
		}else{
			numberString = numberString + "0";
		}
		numberString = numberString.replace(".", "");
				
		numberString = FormatUtil.llenoConCeros(numberString, longitud);
		
		return numberString;
	}
	
	public static Integer getIntMes(String mesStr){
	
		switch (mesStr) {
		case "Enero":
			return 1;			
		case "Febrero":
			return 2;			
		case "Marzo":
			return 3;			
		case "Abril":
			return 4;			
		case "Mayo":
			return 5;			
		case "Junio":
			return 6;			
		case "Julio":
			return 7;			
		case "Agosto":
			return 8;			
		case "Septiembre":
			return 9;			
		case "Octubre":
			return 10;			
		case "Noviembre":
			return 11;			
		case "Diciembre":
			return 12;			

		default:
			return 1;
		}
		
		
	}

	public static Double castRemitoAnmat(String s) {
		Double result = 0d;
		try {
			String prefijo = s.substring(1, 5);
			System.out.println(prefijo);
			String nr = s.substring(5, 13);
			nr = String.valueOf(Integer.parseInt(nr));
			//prefijo = String.valueOf(Integer.parseInt(prefijo));
			System.out.println(prefijo);
			String decimal = nr + "." + prefijo;
			System.out.println(decimal);
			result = Double.parseDouble(decimal);
		} catch (Exception e) {

		}
		return result;
	}

	public static Long formatCuit(String cuit) throws Exception {
		Long cuitFormated = 0l;
		System.out.println("Cuit " + cuit);
		cuit = cuit.trim().replaceAll("-", "");
		cuit = cuit.trim().replaceAll(" ", "");
		try {
			cuitFormated = Long.parseLong(cuit);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return cuitFormated;
	}

	public static boolean validar(String cadena, Long[] valores) {

		char[] nroClave = cadena.toCharArray();
		int checksuma = 0;

		for (int i = 0; i < 11; i++) {
			checksuma += Integer.parseInt(String.valueOf(nroClave[i])) * i;
		}

		for (Long valor : valores) {
			System.out.println("comparo valor: " + valor + "checksuma ="
					+ checksuma);
			if (checksuma == valor)
				return true;
		}
		return false;
	}

	public static String getError(String valorInt) {
		if (valorInt != null) {
			int i = valorInt.indexOf("|", 1);
			while (i != -1) {
				valorInt = valorInt.substring(i);
				i = valorInt.indexOf("|", 1);

			}
			return valorInt.replaceFirst("\\|", "");
		} else {
			return null;
		}
	}

	public static String llenoConCeros(String valor, int longitud) {
		while (valor.length() < longitud) {
			valor = "0" + valor;
		}
		return valor;
	}
	
	public static String llenoConEspaciosDerecha(String valor, int longitud) {
		while (valor.length() < longitud) {
			valor = valor + " ";
		}
		return valor;
	}

	public static String llenoDosCeros(String valor) {
		int valorN = 0;
		String result = "00";
		try {
			valorN = Integer.parseInt(valor);
			result = llenoConCeros(String.valueOf(valorN), 2);
		} catch (Exception e) {

		}
		return result;
	}

	public static double redondearEn2(double numero) {
		return Math.rint(numero * 100) / 100;
	}

	public static double redondearEn6(double numero) {
		return Math.rint(numero * 1000000) / 1000000;
	}

	public static String getSignoPorNegativoRow(Float valor) {
		if (valor < 0) {
			return "red";
		} else {
			return "black";
		}
	}

	public static String getSignoPorNegativoRow(Double valor) {
	
		if (valor != null && valor < 0) {
			return "red";
		} else {
			return "black";
		}
	}

	public static BigDecimal getSaldoCalculado(Integer tipoComprob,
			BigDecimal saldo) {
		if (tipoComprob == 2 || tipoComprob == 4 || tipoComprob == 6
				|| tipoComprob == 10) {
			return saldo.multiply(BigDecimal.valueOf(-1l));
		} else {
			return saldo;
		}
	}

	public static BigDecimal getSaldoCalculado(Short factCtaCte,
			BigDecimal saldo) {

		return saldo.multiply(BigDecimal.valueOf(factCtaCte));

	}

	public static String getSignoRow(Integer tipoComprob) {
		if (tipoComprob == 2 || tipoComprob == 4 || tipoComprob == 6
				|| tipoComprob == 10) {
			return "red";
		} else {
			return "black";
		}
	}

	public static List<Trazabi> getPosiblesTrazabi(String valorABuscar) {
		List<Trazabi> trazabiList = new ArrayList<Trazabi>();
		try {
			String gtin = valorABuscar.substring(2, 16);
			// Pregunto si el la posicion
			String del1 = valorABuscar.substring(16, 18);
			String restoGtin = valorABuscar.substring(18);
			String serie = "";
			if (del1.equals("17")) {
				String restoGtinVenci = restoGtin.substring(6);
				trazabiList.addAll(FormatUtil.getCustomSplitPlus(
						restoGtinVenci, "21", gtin));
			}
			if (del1.equals("21")) {
				// Pregunto si tiene 17
				int initLote = restoGtin.indexOf("10");
				if (initLote == -1) {
					serie = restoGtin;
					trazabiList.add(new Trazabi(gtin, serie));
				} else {
					String serie1 = restoGtin.substring(0, initLote);
					String serie2 = "";
					String serie3 = "";
					trazabiList.add(new Trazabi(gtin, serie1));
					int delimitadorSerie1 = restoGtin.substring(initLote + 2)
							.indexOf("10");

					if (delimitadorSerie1 != -1) {
						delimitadorSerie1 = delimitadorSerie1 + initLote;
						serie2 = restoGtin.substring(0, delimitadorSerie1 + 2);
						trazabiList.add(new Trazabi(gtin, serie2));
					}

					int delimitadorSerie2 = restoGtin.substring(
							delimitadorSerie1 + 4).indexOf("10");
					if (delimitadorSerie2 != -1) {
						delimitadorSerie2 = delimitadorSerie1
								+ delimitadorSerie2;
						serie3 = restoGtin.substring(0, delimitadorSerie2 + 4);
						trazabiList.add(new Trazabi(gtin, serie3));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trazabiList;
	}

	public static List<Trazabi> getCustomSplit(String cadena, String valor,
			String gtin) {
		List<Trazabi> result = new ArrayList<Trazabi>();
		String valorCompuesto = "";

		for (String tmpValor : cadena.split(valor)) {
			if (valorCompuesto.equals("")) {
				if (!tmpValor.equals("")) {
					valorCompuesto = tmpValor;
					result.add(new Trazabi(gtin, valorCompuesto));
				} else {
					String tmp = cadena.substring(2);
					result.add(new Trazabi(gtin, tmp));
				}
			} else {
				if (!tmpValor.equals("")) {
					valorCompuesto = valorCompuesto + valor + tmpValor;
					result.add(new Trazabi(gtin, valorCompuesto));
				}
			}
		}
		return result;

	}

	public static List<Trazabi> getCustomSplitPlus(String cadena, String valor,
			String gtin) {
		List<Trazabi> result = new ArrayList<Trazabi>();
		String valorCompuesto = null;

		for (String tmpValor : cadena.split(valor)) {
			if (valorCompuesto == null) {
				valorCompuesto = "";
			} else {
				if (!tmpValor.equals("")) {
					valorCompuesto = valorCompuesto + tmpValor;
					result.add(new Trazabi(gtin, valorCompuesto));
				}
			}
		}
		return result;

	}

	public static List<String> getSeries(String cadena) {
		List<String> result = new ArrayList<String>();
		String valorLote = "10";
		String valorVenci = "17";
		String[] posiblesSerieTmp = cadena.split("21");
		List<String> posiblesSerie = new ArrayList<String>();
		for(String tmpRemove: posiblesSerieTmp){
			if (!tmpRemove.equals("")){
				posiblesSerie.add(tmpRemove);
			}
		}
		
		int i = 0;
		
		for (String t : posiblesSerie) {
			String cadenaArmada = "";
			int j = 0;
			for (String tmp_ : posiblesSerie) {
				if (i <= j && !tmp_.equals("")) {
					if (cadenaArmada.equals("")) {
						cadenaArmada = tmp_;
					} else {

						cadenaArmada = cadenaArmada + "21" + tmp_;
					}
				}
				j++;
			}
			i++;
			System.out.println("Cadena Armada: " + cadenaArmada);

			if (!cadenaArmada.equals("")) {
				String valorCompuesto = null;
				for (String b : cadenaArmada.split(valorLote)) {
					if (valorCompuesto == null) {
						valorCompuesto = b;
						result.add(b);
					} else {
						valorCompuesto = valorCompuesto + valorLote + b;
						result.add(valorCompuesto);
					}

				}
			}

			if (!cadenaArmada.equals("")) {
				String valorCompuesto = null;
				for (String b : cadenaArmada.split(valorVenci)) {
					if (valorCompuesto == null) {
						valorCompuesto = b;
						result.add(b);
					} else {
						valorCompuesto = valorCompuesto + valorVenci + b;
						result.add(valorCompuesto);
					}

				}
			}
		}

		return result;

	}

	public static FilterTrazabi getFilterTrazabi(String valorABuscar) {
		FilterTrazabi filterTrazabi = new FilterTrazabi();
		try {
			String gtin = valorABuscar.substring(2, 16);
			// Pregunto si el la posicion
			String del1 = valorABuscar.substring(16, 18);
			String restoGtin = valorABuscar.substring(18);
			filterTrazabi.setGtin(gtin);
			filterTrazabi.setSeries(getSeries(restoGtin));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filterTrazabi;
	}
	
	
public static String convertKardexJsonReporteIngreso(List<Kardex> kardexList){
		
		List<Header> headerList = new ArrayList<Header>();
		headerList.add(new Header("Fecha", 0, 1.2d));
		headerList.add(new Header("ExiNr", 1, 0.8d));
		headerList.add(new Header("Lote", 0, 2d));
		headerList.add(new Header("Transac-Kardex", 0, 3d));
		headerList.add(new Header("Articulo-Descripción", 0, 3d));
		headerList.add(new Header("Cantidad 1", 2, 2d));
		headerList.add(new Header("Observaciones", 0, 2d));
		headerList.add(new Header("OperNr", 2, 2d));
		headerList.add(new Header("Refef",2, 2d));
		List<Data> dataList = new ArrayList<Data>();
		float cant =0f;
		for(Kardex kardex: kardexList){
			dataList.add(new Data(DateUtil.getDateSk(kardex.getFechaComprob()),0));
			dataList.add(new Data(String.valueOf(kardex.getExistenciaNr()),1));
			String lote ="";
			if (kardex.getDespachos() != null){
				lote = "[" + kardex.getDespachos().getDespaNr() + "] [" +kardex.getDespachos().getSoloLote() +"] \n" + DateUtil.getDate(kardex.getDespachos().getFechaIng()) ; 
			}
			dataList.add(new Data(lote,2));
			dataList.add(new Data(String.valueOf(kardex.getTransacOrigen() + "-" + kardex.getId().getTransacNr()),0));
			dataList.add(new Data(kardex.getId().getStock().getDescripC(),0));
			dataList.add(new Data(String.valueOf(kardex.getCantidad1()),2));
			String obs = kardex.getObser();
			if (kardex.getObser() == null || kardex.getObser() == ""){
				obs = "---";
			}
			dataList.add(new Data(obs,0));
			dataList.add(new Data(String.valueOf(kardex.getOperadorNr()),2));
			dataList.add(new Data(String.valueOf(kardex.getPreFob()),2));
			cant = cant + kardex.getCantidad1();
		}
		
		if (cant != 0f){
			
			dataList.add(new Data("Total",0));
			dataList.add(new Data("--",1));
			dataList.add(new Data("--",2));
			dataList.add(new Data("--",0));
			dataList.add(new Data("--",0));
			dataList.add(new Data(String.valueOf(cant),2));
			dataList.add(new Data("--",0));
			dataList.add(new Data("--",2));
			dataList.add(new Data("--",2));
			
		}
		
		ReportTable report = new ReportTable(headerList, "Reporte Ingreso ", dataList);
		Gson gson = new Gson();

		// convert java object to JSON format,
		// and returned as JSON formatted string
		String json = gson.toJson(report);
		return json;
	}

}
