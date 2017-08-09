package ar.com.cipres.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component("logoutGuard")
public class LogoutGuardFilter extends OncePerRequestFilter {
    private RequestMatcher matcher = new AntPathRequestMatcher("/j_spring_security_logout","GET");
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(matcher.matches(request)) {
        	request.getSession().invalidate();
        	response.sendRedirect("index.html");
            // invalid logout request handle however you like (i.e. send a 404)
        } else {
            filterChain.doFilter(request, response);
        }
    }
}