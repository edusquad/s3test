package com.educonnect.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode(of = "token")
@Table(name = "tbl_tokens")
@Entity
@Setter
@Getter
public class Token {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true, length = 50)
	private String uemail;

	@Column(nullable = false, unique = true)
	private String token;

	public Token() {}

	public Token(String uemail) {
		this.uemail = uemail;
		this.token = generateToken();
	}

	private String generateToken() {
		return RandomStringUtils.randomAlphanumeric(20).toUpperCase();
	}

}
