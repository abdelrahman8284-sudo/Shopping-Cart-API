package com.abdelrahman.shoppingcart.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder@Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class CategoryResponse {

	private Long id;
	
	private String name;

	private long productCount;
}
