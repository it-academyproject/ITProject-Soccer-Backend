package com.itacademy.soccer.security;

import static com.itacademy.soccer.security.Constants.TOKEN_HEADER;
import static com.itacademy.soccer.security.Constants.TOKEN_ISSUER;
import static com.itacademy.soccer.security.Constants.JWT_SECRET;
import static com.itacademy.soccer.security.Constants.TOKEN_PREFIX;
import static com.itacademy.soccer.security.Constants.TOKEN_EXPIRATION_TIME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response)
					throws AuthenticationException{
		try {
			com.itacademy.soccer.dto.User credentials = new ObjectMapper().readValue(request.getInputStream(),  com.itacademy.soccer.dto.User.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					credentials.getEmail(), credentials.getPassword(), new ArrayList<>()));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(TOKEN_ISSUER)
				.setSubject(((User)auth.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
		response.addHeader(TOKEN_HEADER, TOKEN_PREFIX + " " + token);
	}
	
}
