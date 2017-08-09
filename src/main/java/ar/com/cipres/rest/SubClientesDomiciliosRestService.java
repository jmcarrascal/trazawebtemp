package ar.com.cipres.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.cipres.dao.IDomiciliosDAO;
import ar.com.cipres.dao.ISubClientesDomiciliosDAO;
import ar.com.cipres.model.Domicilios;
import ar.com.cipres.model.SubClientes;
import ar.com.cipres.model.SubClientesDomicilios;

@Component
@Path("/SubClientesDomicilios")
public class SubClientesDomiciliosRestService {

	@Autowired
	private ISubClientesDomiciliosDAO subClientesDomiciliosDAO;
	
	@Autowired
	private IDomiciliosDAO DomiciliosDAO;

	@GET
	@Path("/select/{idSubCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Domicilios> get(@PathParam("idSubCliente") Integer idSubCliente) {
		List<Domicilios> lDomi = new ArrayList<>();
		try {
			List<SubClientesDomicilios> lSubClientesDomicilios = subClientesDomiciliosDAO
					.getByidSubCliente(idSubCliente);
			Domicilios dAux;
			
			for (SubClientesDomicilios oSubCD : lSubClientesDomicilios) {
				dAux = DomiciliosDAO.getByPrimaryKey(oSubCD.getIdDomicilio());
				lDomi.add(dAux);
			}
		} catch (Exception ex) {

		}
		return lDomi;
	}
}
