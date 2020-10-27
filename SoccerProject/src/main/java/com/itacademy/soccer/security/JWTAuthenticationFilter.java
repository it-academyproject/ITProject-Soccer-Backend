package com.itacademy.soccer.security;

import static com.itacademy.soccer.security.Constants.SECRET_KEY;
import static com.itacademy.soccer.security.Constants.HEADER_AUTHORIZATION_KEY;
import static com.itacademy.soccer.security.Constants.ISSUER_INFO;
import static com.itacademy.soccer.security.Constants.TOKEN_BEARER_PREFIX;
import static com.itacademy.soccer.security.Constants.TOKEN_EXPIRATION_TIME;

import static java.util.Collections.emptyList;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itacademy.soccer.dto.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;

	}
	
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException{
		try {

			User credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					credentials.getEmail(), 
					credentials.getPassword(), 
					new ArrayList<>()));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain Chain, 
											Authentication auth) throws IOException, ServletException {
		
		//It's authentication is ok, add token to response
		
		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
				.setSubject(((User)auth.getPrincipal()).getEmail())
				.setSubject(auth.getName())
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
		response.addHeader(HEADER_AUTHORIZATION_KEY, TOKEN_BEARER_PREFIX + " " + token);
		
	
		
	}
}

	
/*
	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain chain, 
											Authentication auth) throws IOException, ServletException {
		
		String token = Jwts.builder()
				.setSubject(((org.springframework.security.core.userdetails.User)auth.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
		
		response.addHeader(HEADER_AUTHORIZATION_KEY, TOKEN_BEARER_PREFIX + " " + token);
	}
	

}

	

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter{

	public JWTAuthenticationFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}
	
	//we analyse the user's credentials and send to AuthenticationManager
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
				throws AuthenticationException, IOException, ServletException {
		
		//We obtein JSON call from the request
		InputStream body = req.getInputStream();
		
		try {
		//This body is mapped to a user object
		User user = new ObjectMapper()
				.readValue(req.getInputStream(), User.class);
		
		//It's authenticated, user is compared with users of the WebSecurity class
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						user.getEmail(), 
						user.getPassword(), 
						new ArrayList<>())
				);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest httpRequest, 
											HttpServletResponse httpResponse, 
											FilterChain filterChain, 
											Authentication authentication) throws IOException, ServletException {
		
		//It's authentication is ok, add token to response
		final Instant now = Instant.now();
		
		String token = Jwts.builder()
				.setSubject(authentication.getName())
				.setExpiration(Date.from(now.plus(1, ChronoUnit.DAYS)))
				.signWith(SignatureAlgorithm.HS512, "secretsign")
				.compact();
		
		httpResponse.addHeader("Authorization", "Bearer " + token);
		
	}
}
*/
	

