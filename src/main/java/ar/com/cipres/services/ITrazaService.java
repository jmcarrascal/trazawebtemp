package ar.com.cipres.services;



import com.inssjp.mywebservice.business.IWebServiceStub.TransaccionPlainWS;

import ar.com.cipres.model.DataSendAnmat;
import ar.com.cipres.model.DataSendMercaderia;
import ar.com.cipres.model.IngresoMercaderia;
import ar.com.cipres.model.JsonResultIngresoMercaderia;
import ar.com.cipres.model.JsonResultRomaneo;
import ar.com.cipres.model.Transac;
import ar.com.cipres.model.Trazabi;



/**
 * 
 * @author Juan Manuel Carrascal
 */
public interface ITrazaService {
	
	public TransaccionPlainWS[] getTransaccionesARecepcionar(String remito, String usernamePami, String passwordPami) ;
	//public JsonResult confirmTransacAnmat(TransaccionPlainWS[] arrayTransac, String usernamePami, String passwordPami, String urlTraza, Integer transacNr) ;
	public JsonResultRomaneo getRomaneo(Integer transac_oc_long);
	public JsonResultIngresoMercaderia confirmTransacAnmat(DataSendAnmat dataSendAnmat,
			String string, String string2, String string3);
	public TransaccionPlainWS[] getTransaccionesARecepcionar(String comprob,
			String tipo, String string, String string2);
	
	public String sendMedicamentos(Integer transacNr) ;
	
	public Trazabi addTrazabi(IngresoMercaderia ingresoMercaderia, DataSendMercaderia dataSendMercaderia, Transac transac);
	public String getCaja(String hash);
	
	
}
