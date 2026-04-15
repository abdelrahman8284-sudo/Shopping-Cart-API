package com.abdelrahman.shoppingcart.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor@NoArgsConstructor@Builder
public class AddItemToCart {

	@NotNull
	private Long productId;
	@Min(value = 1)
	private int quantity;
}
