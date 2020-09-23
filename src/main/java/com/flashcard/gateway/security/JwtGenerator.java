package com.flashcard.gateway.security;

import java.util.Date;

import com.flashcard.gateway.entities.Principal;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

public class JwtGenerator {
	
	private JwtConfig jwtConfig;
	
	public JwtGenerator(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}

	
	public String createJwt(Principal subject) {
		
		long nowMillis = System.currentTimeMillis();
		
		JwtBuilder builder = Jwts.builder()
				.setId(Long.toString(subject.getId()))
				.setSubject(subject.getUsername())
				.setIssuer("iagodev")
				.claim("role", subject.getRole().getName())
				.setIssuedAt(new Date(nowMillis))
				.setExpiration(new Date(nowMillis + jwtConfig.EXPIRATION))
				.signWith(jwtConfig.signatureAlg, jwtConfig.SIGNING_KEY);
		
		return jwtConfig.PREFIX + builder.compact();
	}
	
}
