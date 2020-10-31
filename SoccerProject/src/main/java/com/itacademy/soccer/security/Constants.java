package com.itacademy.soccer.security;

public class Constants {

public static final String AUTH_LOGIN_URL = "/api/login";
	
	// Signing key for HS512 algorithm
	public static final String JWT_SECRET = "sohogowenonwsofnwopngsdnokdsnovsdnofnsepongpsngpSNGNXVLNSDOGNWESPNGPSNPGrnegponqepngpernqgpqnrgpqegbpqebgpebgpeugbrpe";	
	
	//JWT token defaults
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	public static final String TOKEN_ISSUER = "secure-api";
	public static final String TOKEN_AUDIENCE = "secure-app";
	
	public static final long TOKEN_EXPIRATION_TIME = 846_000_000; //10 days

	private Constants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
	
}
