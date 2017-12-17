package com.educonnect.domain.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource(collectionResourceRel = "tokens")
public interface TokenRepository extends CrudRepository<Token, String> {
	Token findByUemail(String uemail);
	Token findByToken(String token);
}
