package com.abdelrahman.shoppingcart.dtos.responses;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor@NoArgsConstructor@Setter@Getter@Builder
public class ProductResponse {

	private Long id;
	
	private String name;
	
	private String brand;
	
	private BigDecimal price;
	
	private String description;
	
	private int inventory;
	
	private Long categoryId;
	
	private List<Long> imagesIds;
}
