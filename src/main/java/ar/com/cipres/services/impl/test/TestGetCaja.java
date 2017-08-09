package ar.com.cipres.services.impl.test;

import java.util.ArrayList;
import java.util.List;

import ar.com.cipres.model.Trazabi;
import ar.com.cipres.util.FormatUtil;

public class TestGetCaja {

	public static void main(String[] args) {
		// for(Trazabi trazabi:
		// FormatUtil.getPosiblesTrazabi("010779537301438117170531108421000009052158")){
		// System.out.println("Gtin: " + trazabi.getGtin() + " Serie: " +
		// trazabi.getSerieGtin());
		// };

//		 for(Trazabi trazabi: FormatUtil.getCustomSplitPlus("10JUAN10PEDRO10",
//		 "10", "0779808468358721")){
//		 System.out.println("Gtin: " + trazabi.getGtin() + " Serie: " +
//		 trazabi.getSerieGtin());
//		 };
		FormatUtil.getSeries("21250262157693509456181719093010lc614");

		
	}
 
}
