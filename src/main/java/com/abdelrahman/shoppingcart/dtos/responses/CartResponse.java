package com.abdelrahman.shoppingcart.dtos.responses;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;
@Data
public class CartResponse {
	private Long id;
	
	private BigDecimal totalAmount;
	
	private Set<CartItemResponse> items;

}
