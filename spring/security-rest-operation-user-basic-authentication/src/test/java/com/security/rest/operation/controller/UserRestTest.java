package com.security.rest.operation.controller;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class UserRestTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Value(value = "${rest.endpoint.user}")
	private String restEndpointUser;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void testGetAllUsers() throws Exception {
		this.mockMvc.perform(get(this.restEndpointUser)).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.users", hasSize(4)));
	}

	@Test
	public void testGetUserById() throws Exception {
		final String userId = "1";
		this.mockMvc.perform(get(this.restEndpointUser.concat("/").concat(userId)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$", hasEntry("user_id", Integer.parseInt(userId))))
			.andExpect(jsonPath("$", hasEntry("user_name", "user1")))
			.andExpect(jsonPath("$", hasEntry("email", "user1@email.com")))
			.andExpect(jsonPath("$.roles", hasSize(2)));
	}

	@Test
	public void testGetUserByIdWithNotFoundResponse() throws Exception {
		final String userId = "10";
		this.mockMvc.perform(get(this.restEndpointUser.concat("/").concat(userId)))
			.andExpect(status().isNotFound());
	}

	@Test
	public void testGetUserByUserName() throws NumberFormatException, Exception {
		final String userName = "user1";
		this.mockMvc.perform(get(this.restEndpointUser.concat("/name/").concat(userName)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$", hasEntry("user_id", 1)))
			.andExpect(jsonPath("$", hasEntry("user_name", userName)))
			.andExpect(jsonPath("$", hasEntry("email", "user1@email.com")))
			.andExpect(jsonPath("$.roles", hasSize(2)));
	}

	@Test
	public void testGetUserByUserNameWithNotFoundResponse() throws NumberFormatException, Exception {
		final String userName = "user10";
		this.mockMvc.perform(get(this.restEndpointUser.concat("/name/").concat(userName)))
			.andExpect(status().isNotFound());
	}

}
