package com.abdelrahman.shoppingcart.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor@NoArgsConstructor@Builder@Setter@Getter
public class CategoryRequest {

	@NotBlank(message="Category name required")
	private String name;
	
}
