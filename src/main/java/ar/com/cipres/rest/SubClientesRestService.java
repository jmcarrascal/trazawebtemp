package ar.com.cipres.rest;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ar.com.cipres.dao.ISubClientesDAO;
import ar.com.cipres.model.SubClientes;

@Component
@Path("/subClientes")
public class SubClientesRestService {

	@Autowired
	private ISubClientesDAO subClientesDAO;

	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response insert(String json) {
		Response response = Response.status(Status.OK.getStatusCode()).build();
		try {
			JsonObject jSon = new Gson().fromJson(json, JsonObject.class);
			SubClientes oSubCliente = new Gson().fromJson(jSon.get("subCliente"), SubClientes.class);

			Serializable idSubCliente = subClientesDAO.save(oSubCliente);
			if (idSubCliente != null) {
				response = Response.status(Status.OK.getStatusCode()).build();
			} else {
				response = Response.status(Status.NOT_ACCEPTABLE.getStatusCode()).entity("Error al crear SubCliente.")
						.build();
			}
		} catch (DataAccessException e) {
			response = Response.status(Status.NOT_ACCEPTABLE.getStatusCode()).entity("Error al crear SubCliente.")
					.build();
		}
		return response;
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response update(String json) {
		Response response = Response.status(Status.OK.getStatusCode()).build();
		try {
			JsonObject jSon = new Gson().fromJson(json, JsonObject.class);
			SubClientes oSubCliente = new Gson().fromJson(jSon.get("subCliente"), SubClientes.class);
			subClientesDAO.update(oSubCliente);
		} catch (DataAccessException e) {
			response = Response.status(Status.NOT_ACCEPTABLE.getStatusCode()).entity("Error al actualizar SubCliente.")
					.build();
		}
		return response;
	}

	@GET
	@Path("/select/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SubClientes get(@PathParam("id") Integer id) {
		return subClientesDAO.getByPrimaryKey(id);
	}

	@POST
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly = false)
	public void delete(SubClientes oSubCliente) {
		subClientesDAO.remove(oSubCliente);
	}

	@POST
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly = true)
	public List<SubClientes> getAll() {
		return subClientesDAO.getAll();
	}
}
