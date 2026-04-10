package com.abdelrahman.shoppingcart.dtos;

import lombok.Data;

@Data
public class UserUpdateRequest {

	private String username;
	
	private String email;
}
