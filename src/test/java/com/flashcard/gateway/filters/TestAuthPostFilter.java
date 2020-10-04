package com.flashcard.gateway.filters;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class TestAuthPostFilter {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testShouldFilter() throws Exception {
		String uri = "http://localhost:8080/login";
		assertEquals(true, uri.matches(".*/login.*|.*/auth.*"));
	}
	
	@Test
	public void testShouldFilterAuth() throws Exception {
		String uri = "http://localhost:8080/auth";
		assertEquals(true, uri.matches(".*/login.*|.*/auth.*"));
	}
	
	@Test
	public void testShouldNotFilter() throws Exception {
		String uri = "http://localhost:8080";
		assertEquals(false, uri.matches(".*/login.*|.*/auth.*"));
	}
	
	@Test
	public void testAuthController() throws Exception {
		mvc.perform(
				post("/test/auth")
				.header("Access-Control-Request-Method", "POST")
			    .header("Origin", "http://www.someurl.com")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("testname")));
	}
	
}
