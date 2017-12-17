package com.educonnect.security.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface MemberRoleRepository extends CrudRepository<MemberRole, String> {
	Optional<MemberRole> findById(String id);
}
