package com.itacademy.soccer.security;

public class JwtException extends RuntimeException {

	private static final long serialVersionUID = 2115744392042460405L;

    private static final String DETAILS = "exception type JWT";

    public JwtException(String detail) {
        super(DETAILS + ": -> " + detail);
    }
    
}
