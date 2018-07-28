package com.security.rest.operation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.rest.operation.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public Optional<User> findByUserName(final String userName);
	
}
