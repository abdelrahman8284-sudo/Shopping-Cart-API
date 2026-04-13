package com.abdelrahman.shoppingcart.dtos.responses;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter@Getter@AllArgsConstructor@NoArgsConstructor@Builder
public class OrderItemResponse {
	private Long id;
	private BigDecimal price;
	private int quantity;
	private Long productId;
	private String productName;
	private String productBrand;
}
