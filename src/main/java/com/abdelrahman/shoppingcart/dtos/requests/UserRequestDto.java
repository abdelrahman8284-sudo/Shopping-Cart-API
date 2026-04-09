package com.abdelrahman.shoppingcart.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor@NoArgsConstructor@Builder
public class UserRequestDto {

	private String username;
	
	private String email;
	
	private String password;
}
