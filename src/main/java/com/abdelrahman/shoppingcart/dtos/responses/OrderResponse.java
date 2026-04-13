package com.abdelrahman.shoppingcart.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.abdelrahman.shoppingcart.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter@Getter@AllArgsConstructor@NoArgsConstructor@Builder
public class OrderResponse {
	private Long id;
	private LocalDateTime orderDate;
	private BigDecimal totalAmount;
	private OrderStatus orderStatus;
	
	private String username;
	private Long userId;
	private String email;
	
	private Set<OrderItemResponse> orderItems;
}
