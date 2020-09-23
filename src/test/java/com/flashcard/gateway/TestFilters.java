package com.flashcard.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.flashcard.gateway.controllers.TestController;

@WebMvcTest(TestController.class)
public class TestFilters {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void firstTest() throws Exception {
		
		MockHttpServletResponse res = mvc.perform(get("/test")).andReturn().getResponse();
		assertEquals("Hello World", res.getContentAsString());
	}
}
