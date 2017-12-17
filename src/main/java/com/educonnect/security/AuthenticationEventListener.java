package com.educonnect.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Component
public class AuthenticationEventListener {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationEventListener.class);

	private final PrincipalExtractor principalExtractor = new FixedPrincipalExtractor();

	@Resource(name = "memberDetailsService")
	private MemberDetailService memberDetailsService;

	@EventListener
	public void handleAuthenticationSuccess(InteractiveAuthenticationSuccessEvent event) throws IOException {
		if (!(event.getAuthentication() instanceof OAuth2Authentication)) {
			return;
		}

		OAuth2Authentication authentication = (OAuth2Authentication) event.getAuthentication();
		authentication.getPrincipal();

		Map<String, Object> map = (Map<String, Object>) authentication.getUserAuthentication().getDetails();
		log.debug("authentication details : {}", map);
		Collection<? extends GrantedAuthority> provider = authentication.getUserAuthentication().getAuthorities();
		UserDetails userDetails = getUser(map, provider);
	}

	private UserDetails getUser(Map<String, Object> map, Collection<? extends GrantedAuthority> provider) {
		map = unifyPropertiesForm(map);
		String username = principalExtractor.extractPrincipal(map).toString();
		log.debug("loaded username : {}", username);

		return isUser(map, provider, username);
	}

	private UserDetails isUser(Map<String, Object> map, Collection<? extends GrantedAuthority> provider, String username) {
		try {
			return this.memberDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			this.memberDetailsService.saveNewMember(map, provider);
		}
		return null;
	}

	private Map<String, Object> unifyPropertiesForm(Map<String, Object> map) {
		if (isNaver(map)) {
			map = setMapForNaver(map);
		}
		return map;
	}

	private Map<String, Object> setMapForNaver(Map<String, Object> map) {
		return (Map<String, Object>) map.get("response");
	}

	private boolean isNaver(Map<String, Object> map) {
		return map.keySet().stream().filter(m -> m.equals("resultcode")).findFirst().isPresent();
	}

}
