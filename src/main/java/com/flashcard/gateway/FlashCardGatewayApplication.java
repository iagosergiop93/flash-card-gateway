package com.flashcard.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.flashcard.gateway.filter.AuthPreFilter;
import com.flashcard.gateway.security.JwtConfig;
import com.flashcard.gateway.security.PrincipalEncoder;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class FlashCardGatewayApplication {

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
	                    .exposedHeaders("Authorization", "Access-Control-Allow-Origin");
	        }
	    };
	}
	
	@Bean(initMethod = "initJwtConfig")
	public JwtConfig createJwtConfig() {
		JwtConfig config = new JwtConfig();
		return config;
	}
	
	@Bean
	public PrincipalEncoder createPrincipalEncoder(JwtConfig jwtConfig) {
		 return jwtConfig.createPrincipalEncoder();
	}

}
