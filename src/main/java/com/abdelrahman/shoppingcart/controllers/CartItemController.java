package com.abdelrahman.shoppingcart.controllers;

import java.nio.file.AccessDeniedException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdelrahman.shoppingcart.dtos.requests.AddItemToCart;
import com.abdelrahman.shoppingcart.dtos.responses.ApiResponse;
import com.abdelrahman.shoppingcart.dtos.responses.CartItemResponse;
import com.abdelrahman.shoppingcart.dtos.responses.CartResponse;
import com.abdelrahman.shoppingcart.mappers.CartItemMapper;
import com.abdelrahman.shoppingcart.mappers.CartMapper;
import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.models.CartItem;
import com.abdelrahman.shoppingcart.services.CartItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart-items")
@Tag(name = "CartItem Management")
public class CartItemController {	
	
	private final CartItemService itemService;
	private final CartItemMapper itemMapper;
	private final CartMapper mapper;

	@PostMapping("/items/add") 
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Add Item to cart")
	public ResponseEntity<ApiResponse> addItemToCart(
	        @RequestParam(required = false) Long cartId, 
	        @RequestBody@Valid AddItemToCart itemRequest) throws AccessDeniedException {
	    
		Cart cart = itemService.addItemToCart(
	            itemRequest.getProductId(), 
	            cartId,                     
	            itemRequest.getQuantity()   
	    );
	    
	    CartResponse response = mapper.toCartResponse(cart);
	    return ResponseEntity.ok(new ApiResponse("Added Successfully", response));
	}
	
	@PutMapping("/{cartId}/items/{itemId}/remove") 
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Remove item from cart")
	public ResponseEntity<ApiResponse> removeItemFromCart(
			@PathVariable Long cartId,
			@PathVariable Long itemId,
			@RequestParam int removedCount) throws AccessDeniedException {
	    
	    Cart cart = itemService.removeItemFromCart(
	    		cartId,
	    		itemId,
	    		removedCount);
	    
	    CartResponse response = mapper.toCartResponse(cart);
	    return ResponseEntity.ok(new ApiResponse("Item updated/removed successfully", response));
	}
	
	@PutMapping("/{cartId}/items/{itemId}/update") 
	@PreAuthorize("hasRole('USER')")
	@Operation(summary = "Update item quantity")
	public ResponseEntity<ApiResponse> updateItemQuantity(
			@PathVariable Long cartId,
			@PathVariable Long itemId,
			@RequestParam int newQuantity) throws AccessDeniedException {
	    
	    Cart cart = itemService.updateItemQuantity(
	    		cartId,
	    		itemId,
	    		newQuantity);
	    
	    CartResponse response = mapper.toCartResponse(cart);
	    return ResponseEntity.ok(new ApiResponse("Item quantity updated successfully", response));
	}
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/cart/{cartId}/product/{productId}")
	@Operation(summary = "Get Cart item")
	public ResponseEntity<?> getCartItem(
			@PathVariable Long cartId,
			@PathVariable Long productId) throws AccessDeniedException{
		CartItem item = itemService.getCartItem(cartId, productId);
		CartItemResponse response = itemMapper.toCartItemResponse(item);
		return ResponseEntity.ok(new ApiResponse("Item ", response));
	}

}
