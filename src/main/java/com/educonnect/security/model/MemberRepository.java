package com.educonnect.security.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {
	
	Optional<Member> findByUid(String uid);
	
	
}
