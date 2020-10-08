package com.flashcard.gateway.security;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
public class JwtConfig {
	
	private Logger logger = LoggerFactory.getLogger(JwtConfig.class);
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.header}")
	private String header;
	
	public final String PREFIX = "Bearer ";
	
	public final int EXPIRATION = 7 * 24 * 60 * 60 * 1000;
	public final SignatureAlgorithm signatureAlg = SignatureAlgorithm.HS512;
	
	public Key SIGNING_KEY;
	
	public void initJwtConfig() {
		logger.info("Creating JWT Key: " + secret);
		byte[] secretBytes = DatatypeConverter.parseBase64Binary(secret);
		this.SIGNING_KEY = new SecretKeySpec(secretBytes, this.signatureAlg.getJcaName());
		logger.info(SIGNING_KEY.toString());
	}
	
}
