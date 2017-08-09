package ar.com.cipres.authentication.resources;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import ar.com.cipres.authentication.crypto.MD5;
import ar.com.cipres.dao.IUsuarioDAO;
import ar.com.cipres.model.Usuario;
import ar.com.cipres.util.DateUtil;


@Component
@Path("/user")
public class UserResource {

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;
	
	@Autowired
	private IUsuarioDAO usuarioDAO;

	@Path("authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly = false)
	public UserTransfer authenticate(@FormParam("username") String username, @FormParam("password") String password, @FormParam("iddependencia")Long idDependencia) {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		Authentication authentication = this.authManager.authenticate(authenticationToken);
		Map<String, Boolean> roles = new HashMap<String, Boolean>();
		/*
		 * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
		if (authentication!=null){
			
			String authorization = username + ":" + password;
			String basic = new String(Base64.encode(authorization.getBytes(Charset.forName("US-ASCII"))));
			
			List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
			
			Usuario usuario = usuarioDAO.getByUsername(username);
			if (usuario != null){
				String saltMd5 = MD5.getMD5(usuario.getSalt());
				String passMd5 = MD5.getMD5(password);
				String passSaltMd5 = MD5.getMD5(saltMd5.concat(passMd5));
				
				if (passSaltMd5.equals(usuario.getPassword())){
					AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_"+usuario.getRol().getAbrev()));
					AUTHORITIES.add(new SimpleGrantedAuthority("EMPRESA1"));
					Authentication newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), AUTHORITIES);
					SecurityContextHolder.getContext().setAuthentication(newAuth);
					
					usuario.setLastLogin(DateUtil.getCurrentDate());
					usuarioDAO.update(usuario);

					return new UserTransfer((String)authentication.getPrincipal(), usuario.getId(), roles, basic, usuario.getRol().getId(), usuario.getUseBulkLoad());
				}
			}
			
			throw new BadCredentialsException("Username/Password incorrectas" + authentication.getPrincipal());
			
			
		}
		return null;
	}
	
//	@Path("logout")
//	@GET
//	public void logout(){
//		
//		SecurityContextHolder.getContext().setAuthentication(null);
//	}
}
