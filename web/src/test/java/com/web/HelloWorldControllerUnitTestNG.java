package com.kruzok.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:web-tiles-context.xml",
		"classpath*:web-context.xml" })
public class HelloWorldControllerUnitTestNG extends
		AbstractTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@BeforeClass
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}

	@Test(groups = { "unit.controller" })
	public void testIndex() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(model().attributeExists("name"))
				.andExpect(view().name("index"));
	}
}