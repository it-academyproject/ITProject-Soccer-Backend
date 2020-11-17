package com.itacademy.soccer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.itacademy.soccer.security.Constants.*;

import java.util.Date;
import java.util.List;

public class JwtService {

	public static boolean isOurToken(String token) {
		return token != null && token.startsWith(TOKEN_PREFIX) && token.split("\\.").length == 3;
	}
	
	public static boolean hasNonExpired(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody().getExpiration().before(new Date());
	}

	public static String createToken(String user, List<String> roles) {
		return Jwts.builder().setIssuedAt(new Date()).setIssuer(TOKEN_ISSUER)
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.setSubject(user).claim("roles", roles).setAudience(TOKEN_AUDIENCE)
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
	}

	public static String extractUsername(String token) {
		return decodeToken(token).getSubject();
	}

	@SuppressWarnings("unchecked")
	public static List<String> extractRoles(String token) {
		return (List<String>) decodeToken(token).get("roles");
	}

	private static Claims decodeToken(String token) {
		
		if (!isOurToken(token)) {
			throw new JwtException("This is not Our Type of Token");
		}
		
		if (hasNonExpired(token)) {
			throw new JwtException("This credential is expired.");
		}
		
		try {
			
			return Jwts.parser()
					.setSigningKey(JWT_SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody();

		} catch (Exception exception) {
			throw new JwtException("Security JWT has a problem: " + exception.getMessage());
		}
	}

}
