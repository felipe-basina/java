package com.security.rest.operation.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.security.rest.operation.model.User;

public class UserResponseWrapper {

	private List<UserResponse> users = new ArrayList<>();

	public List<UserResponse> getUsers() {
		return users;
	}

	public UserResponseWrapper(List<User> users) {
		this.users = users.stream()
				.map(user -> new UserResponse(user))
					.collect(Collectors.toList());
	}
	
}
