package com.abdelrahman.shoppingcart.security.dtos;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.abdelrahman.shoppingcart.models.User;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class UserPrinciple implements UserDetails{

	private final User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(new SimpleGrantedAuthority(user.getRole().name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword()  ;
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}


	public Long getId() {
		return user.getId();
	}
	
	public String getUsernameValue() {
		return user.getUsername();
	}
	
	public LocalDateTime getCreatedAt() {
		return user.getCreatedAt();
	}
}
