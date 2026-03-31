package com.abdelrahman.shoppingcart.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdelrahman.shoppingcart.dtos.requests.AddItemToCart;
import com.abdelrahman.shoppingcart.dtos.responses.ApiResponse;
import com.abdelrahman.shoppingcart.dtos.responses.CartResponse;
import com.abdelrahman.shoppingcart.mappers.CartMapper;
import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.services.CartItemService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart-items")
public class CarItemController {

	
	
	private final CartItemService itemService;
	private final CartMapper mapper;

	@PostMapping("/items/add") 
	public ResponseEntity<ApiResponse> addItemToCart(
	        @RequestParam(required = false) Long cartId, 
	        @RequestBody AddItemToCart itemRequest) {
	    
		Cart cart = itemService.addItemToCart(
	            itemRequest.getProductId(), 
	            cartId,                     
	            itemRequest.getQuantity()   
	    );
	    
	    CartResponse response = mapper.toCartResponse(cart);
	    return ResponseEntity.ok(new ApiResponse("Added Successfully", response));
	}
	
	@PutMapping("/{cartId}/items/{itemId}/remove") 
	public ResponseEntity<ApiResponse> removeItemFromCart(
			@PathVariable Long cartId,
			@PathVariable Long itemId,
			@RequestParam int removedCount) {
	    
	    Cart cart = itemService.removeItemFromCart(
	    		cartId,
	    		itemId,
	    		removedCount);
	    
	    CartResponse response = mapper.toCartResponse(cart);
	    return ResponseEntity.ok(new ApiResponse("Item updated/removed successfully", response));
	}
	
	@PutMapping("/{cartId}/items/{itemId}/update") 
	public ResponseEntity<ApiResponse> updateItemQuantity(
			@PathVariable Long cartId,
			@PathVariable Long itemId,
			@RequestParam int newQuantity) {
	    
	    Cart cart = itemService.updateItemQuantity(
	    		cartId,
	    		itemId,
	    		newQuantity);
	    
	    CartResponse response = mapper.toCartResponse(cart);
	    return ResponseEntity.ok(new ApiResponse("Item quantity updated successfully", response));
	}

}
