package com.flashcard.gateway.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class JwtConfigTest {
	
	@TestConfiguration
	static class JwtConfigTestConfiguration {
		
		@Bean
		public JwtConfig jwtConfig() {
			return new JwtConfig();
		}
		
	}
	
	@Autowired
	public JwtConfig jwtConfig; 
	
	@Test
	public void testJwtConfigStartup() throws Exception {
		System.out.println(jwtConfig);
		assertDoesNotThrow(() -> {
			jwtConfig.initJwtConfig();
		});
	}
	
}
