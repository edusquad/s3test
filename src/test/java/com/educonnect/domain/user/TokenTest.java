package com.educonnect.domain.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TokenTest {
	@Value("${local.server.port}")
	private int serverPort;

	@Autowired
	public Environment env;

	@Before
	public void setup() {
		RestAssured.port = serverPort;
	}

	@Test
	public void create() throws Exception {
		String uemail = env.getProperty("property.host");
		Token token = new Token(uemail);
		RestAssured.given().contentType(ContentType.JSON).body(token).when().post("/generateToken").then()
				.statusCode(HttpStatus.FOUND.value());
	}

}