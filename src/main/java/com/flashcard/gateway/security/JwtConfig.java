package com.flashcard.gateway.security;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
public class JwtConfig {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.header}")
	private String header;
	
	public final String PREFIX = "Bearer ";
	
	public final int EXPIRATION = 7 * 24 * 60 * 60 * 1000;
	public final SignatureAlgorithm signatureAlg = SignatureAlgorithm.HS512;
	
	public Key SIGNING_KEY;
	
	private JwtDecoder createDecoder() {
		JwtDecoder decoder = new JwtDecoder(this);
		return decoder;
	}
	
	private JwtGenerator createGenerator() {
		JwtGenerator gen = new JwtGenerator(this);
		return gen;
	}
	
	public void setKey() {
		if(this.SIGNING_KEY == null) {
			System.out.println("Creating JWT Key");
			byte[] secretBytes = DatatypeConverter.parseBase64Binary(secret);
			this.SIGNING_KEY = new SecretKeySpec(secretBytes, this.signatureAlg.getJcaName());
		}
	}
	
	public PrincipalEncoder createPrincipalEncoder() {
		JwtGenerator gen = createGenerator();
		JwtDecoder dec = createDecoder();
		
		return new PrincipalEncoder(gen, dec);
	}
	
}
