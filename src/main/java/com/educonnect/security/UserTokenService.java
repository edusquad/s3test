package com.educonnect.security;

import com.educonnect.security.model.SecuritySocialProvider;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;
import java.util.Map;

public class UserTokenService extends UserInfoTokenServices {

	public UserTokenService(ClientResources resources, SecuritySocialProvider provider) {
		super(resources.getResource().getUserInfoUri(), resources.getClient().getClientId());
		setAuthoritiesExtractor(new OAuth2AuthoritiesExtractor(provider));
	}

	public static class OAuth2AuthoritiesExtractor implements AuthoritiesExtractor {

		private String provider;

		public OAuth2AuthoritiesExtractor(SecuritySocialProvider provider) {
			this.provider = provider.getProviderId();
		}

		@Override
		public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
			return AuthorityUtils.createAuthorityList(this.provider);
		}
	}
}