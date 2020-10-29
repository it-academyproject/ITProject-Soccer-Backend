package com.itacademy.soccer.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
    	UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
    	
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    	//Obtain token from header
    	String token = request.getHeader("Authorization");
    					    	
    	//If the token is null, validate
    	if(token != null) {
    		String user = Jwts.parser()
    						.setSigningKey(Constants.JWT_SECRET)
    						.parseClaimsJws(token.replace(Constants.TOKEN_PREFIX, ""))
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
