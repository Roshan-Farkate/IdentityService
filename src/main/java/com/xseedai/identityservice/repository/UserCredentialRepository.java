package com.xseedai.identityservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xseedai.identityservice.dto.UserDetailsDTO;
import com.xseedai.identityservice.entity.UserCredential;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> {
//Optional<UserCredential> findByEmail(String username);
Optional<UserCredential> findByEmail(String email);
//	UserCredential findByEmail(String username);

	UserCredential findByResetToken(String token);


	Optional<UserCredential> findByName(String name);

	
	
	

}
