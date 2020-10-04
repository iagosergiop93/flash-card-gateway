package com.flashcard.gateway.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.flashcard.gateway.controllers.TestController;

@WebMvcTest(TestController.class)
public class TestAuthPreFilter {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testResponseBody() throws Exception {
		
		MockHttpServletResponse res = mvc.perform(get("/test")).andReturn().getResponse();
		assertEquals("Hello World", res.getContentAsString());
	}
	
	@Test
	public void testCommonHeader() throws Exception {
		
		MockHttpServletResponse res = mvc.perform(get("/test")).andReturn().getResponse();
		assertEquals("11", res.getHeader("Content-Length"));
	}
	
	@Test
	public void testCorsHeader() throws Exception {
		
		MockHttpServletResponse res = mvc.perform(get("/test")
										.header("Access-Control-Request-Method", "GET")
									    .header("Origin", "http://www.someurl.com"))
										.andReturn().getResponse();
		assertEquals("*", res.getHeader("Access-Control-Allow-Origin"));
	}
}
