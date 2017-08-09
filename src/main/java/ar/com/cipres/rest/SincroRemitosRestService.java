package ar.com.cipres.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.dao.IParametrizacionDAO;
import ar.com.cipres.model.Parametrizacion;
import ar.com.cipres.model.TranAnexo;
import ar.com.cipres.model.Transac;
import ar.com.cipres.services.ITransacService;
import ar.com.cipres.services.impl.test.CallRestService;
import ar.com.cipres.util.Constants;
import ar.com.cipres.util.DateUtil;
import ar.com.cipres.util.FormatUtil;

@Component
@Path("/sincro")
public class SincroRemitosRestService {

	private static final Log log = LogFactory.getLog(SincroRemitosRestService.class);

	@Autowired
	private ITransacService transacService;

	@Autowired
	private IParametrizacionDAO parametrizacionDAO;
	
	@GET
	@Path("/oca")
	@Produces(MediaType.APPLICATION_JSON)
	public void sicroOca() {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect("imap.gmail.com", "jmcarrascal@gmail.com", "minuevavida");
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			SearchTerm sender = new FromTerm(new InternetAddress("noReply@oca.com.ar"));
			SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.GT,
					DateUtil.restarFechasDias(new Date(System.currentTimeMillis()), 10));
			SearchTerm andTerm = new AndTerm(sender, newerThan);
			Message[] messages = inbox.search(andTerm);
			for (int i = 1; i < messages.length; i++) {
				Multipart multipart = (Multipart) messages[i].getContent();
				for (int j = 0; j < multipart.getCount(); j++) {
					try {
						BodyPart bodyPart = multipart.getBodyPart(j);
						if (j == 0) {
							continue; // dealing with attachments only
						}

						InputStream is = bodyPart.getInputStream();
						BufferedReader in = new BufferedReader(new InputStreamReader(is));
						String line;
						while ((line = in.readLine()) != null) {
							System.out.println(line);
							try {
								String nrRemito = FormatUtil.llenoConCeros(line.substring(19, 27).trim(), 8);
								Date fechaEntrega = DateUtil.getDateFormat(line.substring(52, 62), "yyyy-MM-dd");
								transacService.updateFechaEntrega(nrRemito, 3, fechaEntrega);
								log.fatal("Update Remito " + nrRemito + " con fecha " + fechaEntrega);
							} catch (Exception e) {

							}
							System.out.println(line);
						}
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception mex) {
			mex.printStackTrace();
		}
	}

	@GET
	@Path("/serlab")
	@Produces(MediaType.APPLICATION_JSON)
	public void sicroSerlab() {
		// Me traigo las transacciones a actualizar
		Integer transporte = 95;

		List<Transac> listTransac = transacService.getTransacSinEstadoViajes(transporte,
				DateUtil.restarFechasDias(new Date(System.currentTimeMillis()), 10));
		log.fatal("Recupero " + listTransac.size() + " Remitos a actualizar de Serlab");
		for (Transac transac : listTransac) {
			try {
				String response = CallRestService.callSerlab(transac.getNrComprob());
				if (response.indexOf("Estado: Entregado") != -1) {
					transacService.updateFechaEntrega(transac.getNrComprob(), 3, transac.getFecha());
					log.fatal("Update Remito " + transac.getNrComprob() + " con fecha " + transac.getFecha());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/interno")
	@Produces(MediaType.APPLICATION_JSON)
	public void sicroInterno() {
		
		
		
		Integer lastTransac = 13510511;
		Parametrizacion update = parametrizacionDAO.getByPrimaryKey(Constants.ID_TRANSAC_LAST_UPDATE);
		try{
			
			lastTransac = Integer.parseInt(update.getValor());
		}catch(Exception e){
			
		}
		
		List<TranAnexo> listTransac = transacService.getTranAnexoFecha1(lastTransac);
		
		for (TranAnexo tranAnexo : listTransac) {
			try {
				Date fecha = DateUtil.getDateFormat(tranAnexo.getStr01(), "dd/MM/yy");
				transacService.updateFechaEntregaTransac(tranAnexo.getTransacNr(), 3, fecha);
				log.fatal("Update Remito " + tranAnexo.getTransacNr() + " con fecha " + fecha);
				lastTransac = tranAnexo.getTransacNr();
			} catch (Exception e) {

			}
		}
		update.setValor(String.valueOf(lastTransac));
		
		parametrizacionDAO.update(update);
		
		log.fatal("Recupero " + listTransac.size() + " Remitos a actualizar de Interno");
	}

}
