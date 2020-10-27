package com.itacademy.soccer.security;

import static com.itacademy.soccer.security.Constants.HEADER_AUTHORIZATION_KEY;
import static com.itacademy.soccer.security.Constants.SECRET_KEY;
import static com.itacademy.soccer.security.Constants.TOKEN_BEARER_PREFIX;

import static java.util.Collections.emptyList;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest req,
									HttpServletResponse res,
									FilterChain chain)
					throws IOException, ServletException {
		String header = req.getHeader(HEADER_AUTHORIZATION_KEY);
		if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		//Obtain token from header
		String token = request.getHeader(HEADER_AUTHORIZATION_KEY);
						
		//If the token is null, validate
		if(token != null) {
			String user = Jwts.parser()
							.setSigningKey(SECRET_KEY)
							.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
							.getBody()
							.getSubject();
							
			if(user!=null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
			
}

/*
public class JWTAuthorizationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest servletrequest, 
			ServletResponse servletresponse, FilterChain filterchain) 
			throws IOException, ServletException{
		
		String header = ((HttpServletRequest)servletrequest).getHeader("Authorization");
		
		if (header == null || !header.startsWith("Bearer ")) {
			filterchain.doFilter(servletrequest, servletresponse);
			return;
		}
		
		Authentication authentication = getAuthentication((HttpServletRequest)servletrequest);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterchain.doFilter(servletrequest, servletresponse);
		
	}

	private Authentication getAuthentication(HttpServletRequest servletrequest) {

		//Obtain token from header
		String token = servletrequest.getHeader("Authorization");
				
		//If the token is null, validate
		if(token != null) {
					
			String user = Jwts.parser()
					.setSigningKey("secretsign")
					.parseClaimsJws(token.replace("Bearer ", ""))
					.getBody()
					.getSubject();
					
			if(user!=null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}	
}
*/