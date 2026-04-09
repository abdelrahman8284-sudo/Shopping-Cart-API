package com.abdelrahman.shoppingcart.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class UserLogin {

	private String email;
	
	private String password;
}
