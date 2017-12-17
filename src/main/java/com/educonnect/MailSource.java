package com.educonnect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;

@Getter
public class MailSource {

	@Value("${property.sender}")
	private String sender;

	@Value("${property.sender_password}")
	private String sender_password;

	@Value("${property.sender_url}")
	private String sender_url;

	private String domainForToken;

	private static final Logger log = LoggerFactory.getLogger(MailSource.class);

	public MailSource() {
	}
	
	public MailSource(String sender, String sender_password, String sender_url) {
		this.sender = sender;
		this.sender_password = sender_password;
		this.sender_url = sender_url;
		this.domainForToken = sender_url + "/api/token";
	}
}
