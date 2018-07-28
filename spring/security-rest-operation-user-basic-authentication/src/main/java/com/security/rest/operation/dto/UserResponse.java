package com.security.rest.operation.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.security.rest.operation.model.User;
import com.security.rest.operation.model.UserRole;

public class UserResponse {

	@JsonProperty(value = "user_id")
	private Integer id;
	
	@JsonProperty(value = "user_name")
	private String userName;
	
	@JsonProperty(value = "email")
	private String email;
	
	private List<String> roles = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public UserResponse(User user) {
		this.id = user.getId();
		this.userName = user.getUserName();
		this.email = user.getEmail();
		for (UserRole userRole : user.getUserRoles()) {
			this.roles.add(userRole.getRole());	
		}
	}
	
}
