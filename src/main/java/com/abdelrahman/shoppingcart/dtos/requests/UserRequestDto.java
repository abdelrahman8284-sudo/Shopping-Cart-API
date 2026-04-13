package com.abdelrahman.shoppingcart.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor@NoArgsConstructor@Builder
public class UserRequestDto {

	
	private String username;
	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String password;
}
