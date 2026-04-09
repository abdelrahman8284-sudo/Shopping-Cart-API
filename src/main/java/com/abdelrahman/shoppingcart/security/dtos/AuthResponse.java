package com.abdelrahman.shoppingcart.security.dtos;

import com.abdelrahman.shoppingcart.dtos.responses.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class AuthResponse {

	private String token;
	private String type;
	private UserResponse userResponse;
}
