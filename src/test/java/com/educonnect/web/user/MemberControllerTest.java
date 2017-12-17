package com.educonnect.web.user;

import static org.junit.Assert.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.educonnect.security.model.MemberRepository;
import com.educonnect.support.test.AbstractIntegrationTest;
import com.educonnect.support.test.HtmlFormDataBuilder;

public class MemberControllerTest extends AbstractIntegrationTest {

	private static final Logger log = LoggerFactory.getLogger(MemberControllerTest.class);

	@Autowired
	MemberRepository parentMemberRepository;

	@Test
	public void create() {
		String userId = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("uemail", userId + "@gmail.com").addParameter("upw", "password!@").build();
		ResponseEntity<String> result = template.postForEntity("/parentSignUp", request, String.class);
		assertEquals(HttpStatus.FOUND, result.getStatusCode());
		log.debug("body : {}", result.getBody());
	}

	@Test
	public void 로그인() throws Exception {
		String userId = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("uemail", userId + "@gmail.com").addParameter("upw", "password!@").build();
		ResponseEntity<String> result = template.postForEntity("/parentSignUp", request, String.class);
		log.debug("body : {}", result.getBody());

		result = template.postForEntity("/login", request, String.class);
		assertEquals(HttpStatus.FOUND, result.getStatusCode());
		log.debug("body : {}", result.getBody());
	}
}
