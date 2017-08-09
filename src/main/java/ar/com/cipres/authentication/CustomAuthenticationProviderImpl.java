package ar.com.cipres.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ar.com.cipres.authentication.crypto.MD5;
import ar.com.cipres.dao.IUsuarioDAO;
import ar.com.cipres.model.Usuario;



public class CustomAuthenticationProviderImpl implements AuthenticationProvider {
	
	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		Usuario usuario = usuarioDAO.getByUsername(auth.getName());
		if (usuario != null){
			String saltMd5 = MD5.getMD5(usuario.getSalt());
			String passMd5 = MD5.getMD5(auth.getCredentials().toString());
			String passSaltMd5 = MD5.getMD5(saltMd5.concat(passMd5));
			
			if (passSaltMd5.equals(usuario.getPassword())){
				List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
				AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_"+usuario.getRol().getAbrev()));
				return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), AUTHORITIES);
			}
		}
		
		throw new BadCredentialsException("Username/Password incorrectas" + auth.getPrincipal());
	}

	@Override
	public boolean supports(Class<? extends Object> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}
}
