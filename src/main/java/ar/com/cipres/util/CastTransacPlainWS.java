package ar.com.cipres.util;

import java.sql.Date;

import com.inssjp.mywebservice.business.IWebServiceStub.TransaccionPlainWS;

import ar.com.cipres.model.IngresoMercaderia;
import ar.com.cipres.model.Trazabi;

public class CastTransacPlainWS {

	public static Trazabi CastTransacPlainWSToTrazabi(TransaccionPlainWS transac){
		Trazabi trazabi = new Trazabi();
		trazabi.setGlndestinoIng(transac.get_gln_destino());		
		trazabi.setGlnorigenIng(transac.get_gln_origen());
		try{
			trazabi.setCodEventoIng(Integer.parseInt(String.valueOf(transac.get_id_evento())));
		}catch(NumberFormatException e){}
		trazabi.setFechaIng(new Date(System.currentTimeMillis()));
		trazabi.setGtin(transac.get_gtin());
		trazabi.setSerieGtin(transac.get_numero_serial());
		trazabi.setVencimLote(transac.get_vencimiento());
		trazabi.setTrazaObli(-1);
		trazabi.setNrRemCompra(transac.get_n_remito());
		trazabi.setNrFacCompra(transac.get_n_factura());
		transac.setHash(transac.getHash().replaceAll("'", "-"));
		if (transac.getHash().length() > 55){
			trazabi.setHash(transac.getHash().substring(0, 54));
		}else{
			trazabi.setHash(transac.getHash());
		}
				
		return trazabi;
	}
}
