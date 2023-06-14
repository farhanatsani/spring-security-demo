package com.security.demo;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class SecurityDemoApplicationTests {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;
	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}
	@Test
	public void givenRequestOnPrivateService_shouldFailWith401() throws Exception {
		mvc.perform(get("/api/v1/users/profile/john")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
	@WithMockUser("john")
	@Test
	public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
		String result = mvc.perform(get("/api/v1/users/profile/john")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
	@Test
	void testMD5() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJqb2huIiwiaXNzIjoiU3ByaW5nIHNlY3VyaXR5IGRlbW8iLCJzdWIiOiJEZW1vIGp3dCIsImlhdCI6MTY4NjY0MTQ2OSwiZXhwIjoxNjg2NjQ1MDY5fQ.G6p-s0hb2PUC7cIAlX4TGhm1r2NS4NxOL235h6LUiGlQVMpXpCCLVMYWWOJE3XfwxpIUMm41REdkIjucG0hWnA";
		String md5Hex = DigestUtils.md5Hex(token).toUpperCase();

	}
}
