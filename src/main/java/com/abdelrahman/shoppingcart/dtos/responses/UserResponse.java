package com.abdelrahman.shoppingcart.dtos.responses;

import java.time.LocalDateTime;

import com.abdelrahman.shoppingcart.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor@NoArgsConstructor@Builder
public class UserResponse {

	private Long id ;
	
	private String username;
	
	private String email;
	
	private Role role;
	
	private LocalDateTime createdAt;
	
	private CartResponse cart;
}
