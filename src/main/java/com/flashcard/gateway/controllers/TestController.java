package com.flashcard.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flashcard.gateway.entities.Principal;

@RestController
public class TestController {
	
	@GetMapping("/test")
	public String testFilters() {
		return "Hello World";
	}
	
	@PostMapping("/test/auth")
	public Principal testPostFilter() {
		Principal p = new Principal();
		p.setUsername("testname");
		
		return p;
	}
}
