package com.abdelrahman.shoppingcart.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdelrahman.shoppingcart.dtos.requests.UserRequestDto;
import com.abdelrahman.shoppingcart.dtos.responses.UserResponse;
import com.abdelrahman.shoppingcart.mappers.UserMapper;
import com.abdelrahman.shoppingcart.models.User;
import com.abdelrahman.shoppingcart.security.dtos.AuthResponse;
import com.abdelrahman.shoppingcart.security.dtos.UserLogin;
import com.abdelrahman.shoppingcart.security.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final UserMapper mapper;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@RequestBody UserRequestDto dto) {
		User user = mapper.toUser(dto);	
		UserResponse response = mapper.toUserResponse(authService.register(user));
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody UserLogin dto){
		return ResponseEntity.ok(authService.login(dto));
	}
//	@PatchMapping("/reset/{id}")
//	public ResponseEntity<?> resetPassword(@PathVariable Long id, @RequestParam String password) {
//		return ResponseEntity.ok(authService.resetPassword(id, password));
//	}
}
