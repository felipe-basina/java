package com.security.rest.operation.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.security.rest.operation.model.User;

public class ApplicationUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2383463572047290140L;

	private static final String ROLE_ = "ROLE_";
	
	private User user;
	
	public ApplicationUserDetails(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.user.getUserRoles()
			.stream()
			.map(userRole -> new SimpleGrantedAuthority(ROLE_.concat(userRole.getRole().toUpperCase())))
		.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.user.getIsActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.user.getIsActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.user.getIsActive();
	}

	@Override
	public boolean isEnabled() {
		return this.user.getIsActive();
	}

}
