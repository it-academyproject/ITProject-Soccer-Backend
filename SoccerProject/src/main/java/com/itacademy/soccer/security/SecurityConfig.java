package com.itacademy.soccer.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// permitimos el acceso a /login a cualquiera
		//	http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll() 

		// permitimos el acceso a cualquier web api a cualquiera
			http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll() //comentar esta linea y descomentar linea anterior para correcto funcionamiento de autenticacion por token
		
				.anyRequest().authenticated() // cualquier otra peticion requiere autenticacion
				.and()
				// Las peticiones /login pasaran previamente por este filtro
				.addFilterBefore(new LoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)

				// Las demás peticiones pasarán por este filtro para validar el token
				.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Creamos una cuenta de usuario por default
		auth.inMemoryAuthentication().withUser("ask").password("{noop}123").roles("ADMIN");

		auth.inMemoryAuthentication().withUser("user1").password("{noop}pass1").roles("USER");
		auth.inMemoryAuthentication().withUser("user2").password("{noop}pass2").roles("USER");
		auth.inMemoryAuthentication().withUser("user3").password("{noop}pass3").roles("USER");
	}
}

/* 	IMPLEMENTACION DEL METODO CONFIGURE PARA PERMITIR SIN TOKEN SOLO EL ACCESO A "/login"

 @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll() // permitimos el acceso a /login a
																					// // cualquiera
				.anyRequest().authenticated() // cualquier otra peticion requiere autenticacion
				.and()
				// Las peticiones /login pasaran previamente por este filtro
				.addFilterBefore(new LoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)

				// Las demás peticiones pasarán por este filtro para validar el token
				.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}

 */
