package com.security.rest.operation.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.security.rest.operation.model.User;
import com.security.rest.operation.repository.UserRepository;

@Component
public class UserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	public List<User> findAllUsers() {
		logger.info("Fetching all users from db");
		return this.userRepository.findAll();
	}
	
	public User findById(final Integer id) {
		logger.info("Fecthing user by id {}", id);
		return this.userRepository.findById(id).get();
	}
	
	public User findByUserName(final String userName) {
		logger.info("Fecthing user by user name {}", userName);
		return this.userRepository.findByUserName(userName).get();
	}
	
}
