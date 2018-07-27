package com.security.rest.operation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.rest.operation.model.User;
import com.security.rest.operation.service.UserService;

@RestController
@RequestMapping(path = "${rest.endpoint.user}")
public class UserRest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	private UserService userService;

	@Autowired
	public UserRest(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllUsers() {
		try {
			
			List<User> users = this.userService.findAllUsers();
			logger.info("Total of users {}", users.size());
			
			return ResponseEntity.status(HttpStatus.OK).body(users);
			
		} catch (Exception ex) {
			logger.error("Error when fetching all users {}", ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getUserById(@PathVariable(name = "id") Integer id) {
		try {
			
			User user = this.userService.findById(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(user);
			
		} catch (Exception ex) {
			logger.error("Error when fetching user by id {}: {}", id, 
					ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(path = "${rest.endpoint.user.byname}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getUserByUserName(@PathVariable(name = "user_name") String userName) {
		try {
			
			User user = this.userService.findByUserName(userName);
			
			return ResponseEntity.status(HttpStatus.OK).body(user);
			
		} catch (Exception ex) {
			logger.error("Error when fetching user by user name {}: {}", userName, 
					ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
}
