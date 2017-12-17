package com.educonnect.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test", "local" })
public class HomeControllerTest {
	@Value("${local.server.port}")
	private int serverPort;

	@Autowired
	public Environment env;

	@Before
	public void setup() {
		RestAssured.port = serverPort;
	}

	private static final Logger log = LoggerFactory.getLogger(HomeControllerTest.class);

	@Autowired
	private TestRestTemplate template;

	@Test
	public void home() throws Exception {
		ResponseEntity<String> result = template.getForEntity("/", String.class);
		log.debug("body : {}", result.getBody());
	}

	@Test
	public void signup() throws Exception {
		ResponseEntity<String> result = template.getForEntity("/member/parentSignUp", String.class);
		log.debug("body : {}", result.getBody());
	}

}
