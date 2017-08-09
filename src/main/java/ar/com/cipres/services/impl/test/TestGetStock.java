package ar.com.cipres.services.impl.test;

import java.sql.Timestamp;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.client.ServiceClient;

import com.inssjp.mywebservice.business.IWebServiceStub;
import com.inssjp.mywebservice.business.IWebServiceStub.GetTransaccionesNoConfirmadas;
import com.inssjp.mywebservice.business.IWebServiceStub.GetTransaccionesNoConfirmadasE;
import com.inssjp.mywebservice.business.IWebServiceStub.GetTransaccionesNoConfirmadasResponseE;
import com.inssjp.mywebservice.business.IWebServiceStub.TransaccionPlainWS;
import com.inssjp.mywebservice.business.IWebServiceStub.WebServiceError;

import ar.com.cipres.model.ResultSendMedicamento;

public class TestGetStock {
	
	public static void main(String[] args) {

		TestGetStock t = new TestGetStock();
		TransaccionPlainWS[] result = t.getTransaccionesARecepcionar("R020000000730", "DROGUERIAORIEN", "ORIEN1152");
		if (result != null) {
			for (TransaccionPlainWS transac :result) {
				System.out.println("serie: " + transac.get_numero_serial());
			}			 
		}

	}

	

	/**
	 * @param args
	 */
	public TransaccionPlainWS[] getTransaccionesARecepcionar(String remito, String usernamePami, String passwordPami) {
		IWebServiceStub service = null;
		try {
			Timestamp inicio =  new Timestamp(System.currentTimeMillis());
			
			service = new IWebServiceStub(
					"https://trazabilidad.pami.org.ar:9050/trazamed.WebService");
			// service = new
			// IWebServiceServiceStub("https://trazabilidad.pami.org.ar:9050/trazamed.WebService");

			ServiceClient serviceClient = service._getServiceClient();

			OMFactory omFactory = OMAbstractFactory.getOMFactory();

			OMElement security = omFactory
					.createOMElement(
							new QName(
									"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
									"Security", "wsse"), null);

			OMNamespace omNs = omFactory
					.createOMNamespace(
							"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
							"wsu");
			OMElement userNameToken = omFactory.createOMElement(
					"UsernameToken", omNs);

			OMElement userName = omFactory.createOMElement("Username", omNs);
			userName.addChild(omFactory.createOMText("testwservice"));

			OMElement password = omFactory.createOMElement("Password", omNs);
			password.addChild(omFactory.createOMText("testwservicepsw"));

			userNameToken.addChild(userName);
			userNameToken.addChild(password);

			security.addChild(userNameToken);
			serviceClient.addHeader(security);

			serviceClient.getOptions().setTimeOutInMilliSeconds(10000000);

			// serviceClient.setOptions(o);

			String error = null;
			GetTransaccionesNoConfirmadasE ge = new GetTransaccionesNoConfirmadasE();
			GetTransaccionesNoConfirmadas g = new GetTransaccionesNoConfirmadas();
			g.setArg0(usernamePami);
			g.setArg1(passwordPami);
			
			
			g.setArg14(remito);
			//g.setArg3("7798168630001");
			//GLN: 
			//g.setArg18("8137306466");
			ge.setGetTransaccionesNoConfirmadas(g);

			GetTransaccionesNoConfirmadasResponseE geR = new GetTransaccionesNoConfirmadasResponseE();
			geR = service.getTransaccionesNoConfirmadas(ge);
			System.out.println(geR.getGetTransaccionesNoConfirmadasResponse().get_return().getList().length);
			System.out.println("Inicio: " + inicio);
			System.out.println("Fin: " + new Timestamp(System.currentTimeMillis()));
			if (geR.getGetTransaccionesNoConfirmadasResponse().get_return()
					.getErrores() != null) {
				WebServiceError[] wseArray = geR
						.getGetTransaccionesNoConfirmadasResponse()
						.get_return().getErrores();
				for (WebServiceError wse : wseArray) {
					System.out.println(wse.get_d_error());
					error = wse.get_d_error();
				}
			}
			if (error == null && geR
					.getGetTransaccionesNoConfirmadasResponse()
					.get_return().getList() != null) {
				for (TransaccionPlainWS t : geR
						.getGetTransaccionesNoConfirmadasResponse()
						.get_return().getList()) {
					System.out.println("serie: " + t.get_numero_serial());
				}
				return geR.getGetTransaccionesNoConfirmadasResponse().get_return().getList(); 
			}		
			return null;
	}catch(Exception e){
		e.printStackTrace();
		return null;
	}
	}
}
