package com.abdelrahman.shoppingcart.security.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abdelrahman.shoppingcart.dtos.responses.UserResponse;
import com.abdelrahman.shoppingcart.enums.Role;
import com.abdelrahman.shoppingcart.exceptions.AlreadyExistsException;
import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.models.User;
import com.abdelrahman.shoppingcart.repositories.UserRepo;
import com.abdelrahman.shoppingcart.security.dtos.AuthResponse;
import com.abdelrahman.shoppingcart.security.dtos.UserLogin;
import com.abdelrahman.shoppingcart.security.dtos.UserPrinciple;
import com.abdelrahman.shoppingcart.security.jwt.JwtService;
import com.abdelrahman.shoppingcart.services.CartService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager manager;
	private final PasswordEncoder encoder;
	private final UserRepo userRepo;
	private final JwtService jwtService;
	private final CartService cartService;
	@Transactional
	public User register(User user) {
		if(userRepo.existsByEmail(user.getEmail())) {
			throw new AlreadyExistsException("Oops!"+user.getEmail()+" already exists!");
		}
		user.setPassword(encoder.encode(user.getPassword()));
		if(user.getRole()==null || user.getRole()!=Role.ROLE_USER)
			user.setRole(Role.ROLE_USER);
		Cart cart =cartService.getOrCreateCart(null);
		user.setCart(cart); cart.setUser(user);
		return userRepo.save(user);
	}
	
	
	public AuthResponse login(UserLogin userLogin) {
//		System.out.println("LOGIN EMAIL: " + userLogin.getEmail());
//		System.out.println("LOGIN PASS: " + userLogin.getPassword());
		return  verify(userLogin);
	}


	private AuthResponse verify(UserLogin userLogin) {
		Authentication authentication = manager.authenticate(
				new UsernamePasswordAuthenticationToken(
						userLogin.getEmail(),userLogin.getPassword()));
//		Authentication auth = manager.authenticate(
//			    new UsernamePasswordAuthenticationToken(
//			        userLogin.getEmail(),
//			        userLogin.getPassword()
//			    )
//			);

//			System.out.println("AUTH SUCCESS: " + authentication.isAuthenticated());
//			System.out.println("PRINCIPAL: " + authentication.getPrincipal());
		if(authentication.isAuthenticated()) {
			var user = (UserPrinciple)authentication.getPrincipal();
			String authority = user.getAuthorities().iterator().next().getAuthority();
			String token = jwtService.generateToken(user.getUsername(),authority,user.getId());
			
			UserResponse response = UserResponse.builder()
					.id(user.getId())
					.email(user.getUsername())
					.role(Role.valueOf(authority))
					.createdAt(user.getCreatedAt())
					.username(user.getUsernameValue())
					.build();
			AuthResponse auth = new AuthResponse(token, "Bearer", response);

			return auth;
		}

		throw new BadCredentialsException("Invalid credentials");	}
}
