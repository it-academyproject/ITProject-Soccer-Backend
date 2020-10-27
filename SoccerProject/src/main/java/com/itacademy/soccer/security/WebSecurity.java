package com.itacademy.soccer.security;

import static com.itacademy.soccer.security.Constants.LOGIN_URL;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private UserDetailsService userDetailsService;

	public WebSecurity(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;

	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception{

	http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.csrf().disable()
		.authorizeRequests().antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
		.antMatchers("/api/users/managers").permitAll()
		.anyRequest().authenticated().and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager()))
		.addFilter(new JWTAuthorizationFilter(authenticationManager()));

	
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// The class that retrieves the users is defined
		auth.userDetailsService(userDetailsService);

	}

	
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("ask").password("{noop}123").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("user1").password("{noop}pass1").roles("USER");
		auth.inMemoryAuthentication().withUser("user2").password("{noop}pass2").roles("USER");
		auth.inMemoryAuthentication().withUser("user3").password("{noop}pass3").roles("USER");
	
	}
	*/
	
	
	
}
