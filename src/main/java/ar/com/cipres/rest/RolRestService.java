package ar.com.cipres.rest;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ar.com.cipres.dao.IRolDAO;
import ar.com.cipres.model.Rol;
import ar.com.cipres.wrapper.ResponseObjectId;

@Component 
@Path("/rol")
public class RolRestService {
	
	@Autowired
	private IRolDAO rolDAO;
	
	@POST
	@Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response save(Rol rol) {
		Response response = null;
		try {
			Serializable idRol = rolDAO.save(rol);
			ResponseObjectId ro = new ResponseObjectId();
			ro.setId((Integer)idRol);
			response = Response.status(Status.OK.getStatusCode()).entity(ro).build();
		} catch (DataAccessException e) {
			response = Response.status(Status.NOT_ACCEPTABLE.getStatusCode()).entity("Error al crear Rol.").build();
		} catch (ConstraintViolationException e) {
			response = Response.status(Status.PRECONDITION_FAILED.getStatusCode()).entity("Error al crear Rol. El nombre ya existe").build();
		}
		return response;
 	}
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public void update(Rol rol) {
		rolDAO.update(rol);
	}
	
	@GET
	@Path("/show/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Rol get(@PathParam("id") Integer id) {	
		return rolDAO.getByPrimaryKey(id);
	}
	
	@DELETE
	@Path("/remove/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void delete(@PathParam("id") Integer id) {	
		rolDAO.remove(rolDAO.getByPrimaryKey(id));
	}
	
	@POST
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly = true)
	public List<Rol> getAll() {		
		return rolDAO.getAll();
	}

}
