package com.abdelrahman.shoppingcart.dtos.requests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class ProductRequest {

	@NotBlank
	private String name;
	//@NotBlank
	private String brand;
	@NotNull
	@DecimalMin("0.0")
	private BigDecimal price;
	@Min(1)
	private int inventory;
	private String description;
	private Long categoryId;
}
