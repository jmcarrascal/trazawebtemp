package ar.com.cipres.rest;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import ar.com.cipres.dao.IExistenciasDAO;
import ar.com.cipres.dao.IOperadorDAO;
import ar.com.cipres.dao.IUsuarioDAO;
import ar.com.cipres.dao.IUsuariosExistenciasDAO;
import ar.com.cipres.model.Existencias;
import ar.com.cipres.model.Operadores;
import ar.com.cipres.model.Usuario;
import ar.com.cipres.model.UsuariosExistencias;
import ar.com.cipres.wrapper.UsuarioWrapper;

@Component
@Path("/usuario")
public class UsuarioRestService {

	@Autowired
	private IUsuarioDAO usuarioDAO;

	@Autowired
	private IUsuariosExistenciasDAO usuariosExistenciasDAO;

	@Autowired
	private IExistenciasDAO existenciasDAO;

	@Autowired
	private IOperadorDAO operadorDAO;

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response save(Usuario usuario) {
		Response response = Response.status(Status.OK.getStatusCode()).build();
		try {
			//JsonObject jSon = new Gson().fromJson(json, JsonObject.class);
			//Usuario usuario = new Gson().fromJson(jSon.get("user"), Usuario.class);
			Type type = new TypeToken<List<Integer>>() {
			}.getType();
			//List<Integer> lExistencias = new Gson().fromJson(jSon.get("existencias"), type);
			UsuariosExistencias oExistencias;
			List<Integer> lExistencias = new ArrayList<Integer>();
			
			Serializable idUsuario = usuarioDAO.save(usuario);
			if (idUsuario != null) {
				for (Integer nrExistencia : lExistencias) {
					oExistencias = new UsuariosExistencias();
					oExistencias.setIdUsuario((Integer) idUsuario);
					oExistencias.setNrExistencia(nrExistencia);
					usuariosExistenciasDAO.save(oExistencias);
				}
				usuariosExistenciasDAO.flush();
				response = Response.status(Status.OK.getStatusCode()).build();
			} else {
				response = Response.status(Status.NOT_ACCEPTABLE.getStatusCode()).entity("Error al crear Usuario.")
						.build();
			}
		} catch (DataAccessException e) {
			response = Response.status(Status.NOT_ACCEPTABLE.getStatusCode()).entity("Error al crear Usuario.").build();
		} catch (ConstraintViolationException e) {
			response = Response.status(Status.PRECONDITION_FAILED.getStatusCode())
					.entity("Error al crear Usuario. El username ya existe.").build();
		}
		return response;
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	@Transactional(readOnly = false)
	public void update(String json) {
		JsonObject jSon = new Gson().fromJson(json, JsonObject.class);
		UsuarioWrapper usuario = new Gson().fromJson(jSon.get("user"), UsuarioWrapper.class);
		usuarioDAO.updateUser(usuario);
		
		Integer idUsuario = usuario.getId();
		
		Type type = new TypeToken<List<Integer>>() {
		}.getType();
		List<Integer> lExistencias = new Gson().fromJson(jSon.get("existencias"), type);
		UsuariosExistencias oExistencias;
		
		if (idUsuario != null) {
			//ELIMINO EXISTANCIAS		
			
			if(usuariosExistenciasDAO.deleteByIdUsuario(idUsuario)){
				for (Integer nrExistencia : lExistencias) {
					oExistencias = new UsuariosExistencias();
					oExistencias.setIdUsuario(idUsuario);
					oExistencias.setNrExistencia(nrExistencia);
					usuariosExistenciasDAO.save(oExistencias);
				}
				usuariosExistenciasDAO.flush();
			}
			/*
			  List<UsuariosExistencias> lUsuariosExistencias;
			lUsuariosExistencias = usuariosExistenciasDAO.getExistenciasByidUsuario(idUsuario);
			if (lUsuariosExistencias.size() > 0) {
				for (UsuariosExistencias oUsuariosExistencias : lUsuariosExistencias) {
					usuariosExistenciasDAO.remove(oUsuariosExistencias);
				}
				
				usuariosExistenciasDAO.flush();
			}
			for (Integer nrExistencia : lExistencias) {
				oExistencias = new UsuariosExistencias();
				oExistencias.setIdUsuario(idUsuario);
				oExistencias.setNrExistencia(nrExistencia);
				usuariosExistenciasDAO.save(oExistencias);
			}
			usuariosExistenciasDAO.flush();
			
			*/
		}
	}

	@GET
	@Path("/show/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario get(@PathParam("id") Integer id) {
		return usuarioDAO.getByPrimaryKey(id);
	}

	@POST
	@Path("/remove")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly = false)
	public void delete(Usuario usuario) {
		usuarioDAO.remove(usuario);
	}

	@POST
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly = true)
	public List<Usuario> getAll() {
		List<Usuario> lUsuarios = usuarioDAO.getAll();
		List<UsuariosExistencias> lUsuariosExistencias;
		List<Existencias> lExistencias;
		List<Usuario> aux = new ArrayList<Usuario>();

		for (Usuario oUsuario : lUsuarios) {
			lUsuariosExistencias = usuariosExistenciasDAO.getExistenciasByidUsuario(oUsuario.getId());
			lExistencias = new ArrayList<Existencias>();
			if (lUsuariosExistencias.size() > 0) {
				for (UsuariosExistencias oUsuariosExistencias : lUsuariosExistencias) {
					Existencias e = existenciasDAO.getByPrimaryKey(oUsuariosExistencias.getNrExistencia());
					lExistencias.add(e);
				}
				oUsuario.setlExistencias(lExistencias);
			}
			aux.add(oUsuario);
		}

		return aux;
	}

	@POST
	@Path("/getOperadores")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getOperadores(@FormParam("locked") Boolean locked) {
		return usuarioDAO.getOperadores(locked);
	}

	@POST
	@Path("/changePassw")
	@Produces(MediaType.APPLICATION_JSON)
	public void changePassw(@FormParam("id") Integer id, @FormParam("password") String password) {
		usuarioDAO.changePassw(id, password);
	}

	@GET
	@Path("/getOperadoresSk")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Operadores> getOperadoresSk() {
		return operadorDAO.getAll();
	}
}
