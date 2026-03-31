package com.abdelrahman.shoppingcart.dtos.responses;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor@NoArgsConstructor@Setter@Getter@Builder
public class CartItemResponse {

	private Long id;
	
	private int quantity;
	
	private BigDecimal unitPrice;
	
	private BigDecimal totalPrice;
	
	private Long productId;
	
	private String name;
}
