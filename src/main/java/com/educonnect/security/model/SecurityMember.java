package com.educonnect.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SecurityMember extends User {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityMember.class);

	private static final long serialVersionUID = -6992062813198540016L;

	private static final String ROLE_PREFIX = "ROLE_";

	private Member member;

	public SecurityMember(Member member) {
		super(member.getUid(), member.getUpw(), makeGrantedAuthority(member.getMemberRoles()));
		this.member = member;
	}

	public SecurityMember(String uemail, String upw, Collection<? extends GrantedAuthority> authorities) {
		super(uemail, upw, authorities);
	}

	private static Collection<? extends GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
		return authorities;
	}
}
