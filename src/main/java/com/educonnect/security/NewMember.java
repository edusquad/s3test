package com.educonnect.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.educonnect.security.model.Member;
import com.educonnect.security.model.MemberRole;
import com.educonnect.security.model.SecuritySocialProvider;

public class NewMember {

private static final Logger log = LoggerFactory.getLogger(NewMember.class);

	Member member = new Member();
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	public NewMember(Collection<? extends GrantedAuthority> provider) {
		this.authorities.add(new SimpleGrantedAuthority(provider.stream().findFirst().get().toString()));
	}

	public Member set(Map<String, Object> map) {
		this.authorities.add(new SimpleGrantedAuthority("PARENT"));
		member.setMemberRoles((List<MemberRole>) authorities.get(1));
		if (checkProviderType(SecuritySocialProvider.KAKAO)) {
			return kakaoMember(map);
		}
		if (checkProviderType(SecuritySocialProvider.NAVER)) {
			return naverMember(map);
		}
		return facebookMember(map);
	}

	private boolean checkProviderType(SecuritySocialProvider type) {
		return authorities.get(0).toString().toUpperCase().equals(type);
	}

	private Member kakaoMember(Map<String, Object> map) {
		HashMap<String, String> propertyMap = (HashMap<String, String>) (Object) map.get("properties");
		member.setUid(Integer.toString((int) map.get("id")));
		member.setAlias((String) propertyMap.get("nickname"));
		member.setUpw("1");
		member.setProvider(SecuritySocialProvider.KAKAO);
		return member;
	}

	private Member facebookMember(Map<String, Object> map) {
		member.setUid((String) (map.get("id")));
		member.setAlias((String) map.get("name"));
		member.setUpw("1");
		member.setProvider(SecuritySocialProvider.FACEBOOK);
		return member;
	}

	private Member naverMember(Map<String, Object> map) {
		member.setUid((map.get("id").toString()));
		member.setAlias((String) map.get("nickname"));
		member.setUpw("1");
		member.setProvider(SecuritySocialProvider.NAVER);
		return member;
	}

}
