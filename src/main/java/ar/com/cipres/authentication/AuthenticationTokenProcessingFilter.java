package ar.com.cipres.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	// @Autowired
	// private TokenUtils tokenUtils;
	private final AuthenticationManager authManager;
/*	private final UserDetailsService userService;*/

//	public AuthenticationTokenProcessingFilter(AuthenticationManager authManager, UserDetailsService userService) {

	public AuthenticationTokenProcessingFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
/*		this.userService = userService;*/
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {	
/*		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting a http request");
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader("Auth-Token");

		String userName = TokenUtils.getUserNameFromToken(authToken);
		if (userName != null) {
			UserDetails userDetails = this.userService.loadUserByUsername(userName);
			if (TokenUtils.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
				SecurityContextHolder.getContext().setAuthentication(this.authManager.authenticate(authentication));
			}
		}
*/
		chain.doFilter(request, response);
//		if (SecurityContextHolder.getContext().getAuthentication()!=null) 
//			((HttpServletRequest)request).getSession();
	}
}