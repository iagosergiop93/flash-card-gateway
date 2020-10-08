package com.flashcard.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.flashcard.gateway.filter.AuthPostFilter;
import com.flashcard.gateway.filter.AuthPreFilter;
import com.flashcard.gateway.security.JwtConfig;
import com.flashcard.gateway.security.JwtDecoder;
import com.flashcard.gateway.security.JwtGenerator;
import com.flashcard.gateway.security.PrincipalEncoder;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class FlashCardGatewayApplication {
	
	private Logger logger = LoggerFactory.getLogger(FlashCardGatewayApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(FlashCardGatewayApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**")
	                    .allowedOrigins("*")
	                    .allowedMethods("GET", "POST", "PUT", "DELETE")
	                    .allowedHeaders("Content-Type", "Content-Length", "Authorization")
	                    .exposedHeaders("Authorization");
	        }
	    };
	}
	
	@Bean
	public JwtConfig createJwtConfig() {
		JwtConfig config = new JwtConfig();
		
		return config;
	}
	
	@Bean
	public PrincipalEncoder createPrincipalEncoder(JwtConfig jwtConfig) {
		logger.info("Creating PrincipalEncoder with: " + jwtConfig.toString());
		jwtConfig.initJwtConfig();
		JwtGenerator gen = new JwtGenerator(jwtConfig);
		JwtDecoder dec = new JwtDecoder(jwtConfig);
		
		return new PrincipalEncoder(gen, dec);
	}
	
	@Bean
	public AuthPreFilter authPreFilter(PrincipalEncoder principalEnc) {
		return new AuthPreFilter(principalEnc);
	}
	
	@Bean
	public AuthPostFilter authPostFilter(PrincipalEncoder principalEnc) {
		return new AuthPostFilter(principalEnc);
	}

}
