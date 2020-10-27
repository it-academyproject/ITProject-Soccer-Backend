package com.itacademy.soccer.security;

public class Constants {

	public static final String LOGIN_URL = "/api/login";
	public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";
	
	public static final String ISSUER_INFO = "Enric";
	public static final String SECRET_KEY = "1234";
	public static final long TOKEN_EXPIRATION_TIME = 846_000_000; //10 days
}
