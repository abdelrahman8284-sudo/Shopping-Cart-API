package com.abdelrahman.shoppingcart.controllers;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdelrahman.shoppingcart.dtos.responses.ApiResponse;
import com.abdelrahman.shoppingcart.dtos.responses.OrderResponse;
import com.abdelrahman.shoppingcart.mappers.OrderMapper;
import com.abdelrahman.shoppingcart.models.Order;
import com.abdelrahman.shoppingcart.security.dtos.UserPrinciple;
import com.abdelrahman.shoppingcart.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final OrderMapper mapper;
	
	private Long getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple user = (UserPrinciple) auth.getPrincipal();
		return  user.getId();
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ApiResponse> createOrder() {
        try {
            Order order = orderService.placeOrder(getCurrentUserId());
            OrderResponse response = mapper.toOrderResponse(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Item order Success!", response));
        }
        catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
        catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured!", e.getMessage()));
        }
    }
	
	@GetMapping("/{orderId}") 
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<ApiResponse> getOrder(@PathVariable Long orderId){
		try {
			Order order = orderService.getOrderById(orderId);
			OrderResponse response = mapper.toOrderResponse(order);
			return ResponseEntity.ok(new ApiResponse("Order found successfully", response));
		}catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
	}
	
	@GetMapping("/my") 
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
		try {
			List<Order> orders = orderService.getUserOrders(userId);
			List<OrderResponse> response = mapper.toListOrderResponse(orders);
			return ResponseEntity.ok(new ApiResponse("Orders found successfully", response));
		}catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
	}
	@PatchMapping("/{orderId}/ship")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> shipOrder(@PathVariable Long orderId){
		orderService.shipOrder(orderId);
		return ResponseEntity.ok(new ApiResponse("Shipping order successfully", null));
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{orderId}/deliver") 
	public ResponseEntity<ApiResponse> deliverOrder(@PathVariable Long orderId){
		orderService.deliverOrder(orderId);
		return ResponseEntity.ok(new ApiResponse("Order deliveres successfully", null));
	}
	@PatchMapping("/{orderId}/cancel") 
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long orderId){
		try {
			orderService.cancelOrder(orderId);
			return ResponseEntity.ok(new ApiResponse("Order cancelled successfully", null));
		}catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
	}
	
}
