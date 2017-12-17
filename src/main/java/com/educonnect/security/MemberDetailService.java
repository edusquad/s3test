package com.educonnect.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.educonnect.security.model.MemberRepository;
import com.educonnect.security.model.SecurityMember;

@Service("memberDetailsService")
public class MemberDetailService implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(MemberDetailService.class);

	@Autowired
	MemberRepository repo;

	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		log.debug("requested username is : {}", uid);
		return repo.findByUid(uid).filter(m -> m != null).map(m -> new SecurityMember(m))
				.orElseThrow(() -> new UsernameNotFoundException("no user matches your request!"));
	}

	public void saveNewMember(Map<String, Object> map, Collection<? extends GrantedAuthority> provider) {
		List<GrantedAuthority> authority = new ArrayList<>();
		authority.add(provider.stream().findFirst().get());
//		authority.add(new SimpleGrantedAuthority("PARENT"));
		repo.save(new NewMember(authority).set(map));
	}

}
