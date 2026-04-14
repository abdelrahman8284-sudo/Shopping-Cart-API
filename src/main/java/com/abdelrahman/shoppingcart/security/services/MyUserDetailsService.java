package com.abdelrahman.shoppingcart.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abdelrahman.shoppingcart.models.User;
import com.abdelrahman.shoppingcart.repositories.UserRepo;
import com.abdelrahman.shoppingcart.security.dtos.UserPrinciple;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

	private final UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User with email :"+email +" not found !"));
//		System.out.println("FOUND USER: " + user.getEmail());
//		System.out.println("DB PASSWORD: " + user.getPassword());
		return new UserPrinciple(user);
	}

}
