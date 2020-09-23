package com.flashcard.gateway.security;

import com.flashcard.gateway.entities.Principal;
import com.flashcard.gateway.entities.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.UnsupportedJwtException;

public class PrincipalEncoder {
	
	private JwtGenerator jwtGenerator;
	
	private JwtDecoder jwtDecoder;
	
	public PrincipalEncoder(JwtGenerator jwtGenerator, JwtDecoder jwtDecoder) {
		super();
		this.jwtGenerator = jwtGenerator;
		this.jwtDecoder = jwtDecoder;
	}

	public String encodePrincipal(Principal principal) {
		return jwtGenerator.createJwt(principal);
	}
	
	public Principal decodePrincipal(String token) {
		
		Principal principal = new Principal();
		
		try {
			Claims claims = jwtDecoder.decodeJWT(token);
			
			long id = Long.parseLong(claims.getId());
			String username = claims.getSubject();
			Role role = new Role();
			role.setName(claims.get("role", String.class));
			
			principal.setId(id);
			principal.setUsername(username);
			principal.setRole(role);
			
		} catch(UnsupportedJwtException e) {
			System.out.println("Jwt is invalid");
			throw e;
		}
		
		return principal;
	}
	
}
