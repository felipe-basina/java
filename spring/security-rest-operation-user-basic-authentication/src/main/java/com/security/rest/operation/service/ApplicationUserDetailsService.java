package com.security.rest.operation.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.rest.operation.dto.ApplicationUserDetails;
import com.security.rest.operation.model.User;
import com.security.rest.operation.repository.UserRepository;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = this.userRepository.findByUserName(username);
		
		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		
		return new ApplicationUserDetails(optionalUser.get());
	}

}
