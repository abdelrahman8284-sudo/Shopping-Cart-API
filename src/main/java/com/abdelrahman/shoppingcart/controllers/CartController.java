package com.abdelrahman.shoppingcart.controllers;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdelrahman.shoppingcart.dtos.responses.ApiResponse;
import com.abdelrahman.shoppingcart.dtos.responses.CartResponse;
import com.abdelrahman.shoppingcart.exceptions.RecordNotFoundException;
import com.abdelrahman.shoppingcart.mappers.CartMapper;
import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.security.dtos.UserPrinciple;
import com.abdelrahman.shoppingcart.services.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "Cart Management")
public class CartController {

	private final CartService cartService;
	private final CartMapper mapper;
	
	private Long getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrinciple user = (UserPrinciple) auth.getPrincipal();
		return  user.getId();
	}
	@GetMapping("/{cartId}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get Cart by Id (Only Admin)")
    public ResponseEntity<ApiResponse> getCart( @PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            CartResponse response = mapper.toCartResponse(cart);
            return ResponseEntity.ok(new ApiResponse("Success", response));
        } catch (RecordNotFoundException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
          }
    }
	 
	@DeleteMapping("/{cartId}") 
	@Operation(summary = "Clear Cart by Id")
	public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
	    try {
		    Cart cart = cartService.clearCart(cartId);
		    CartResponse response = mapper.toCartResponse(cart);
		    return ResponseEntity.ok(new ApiResponse("The cart has been successfully cleared", response));
	
	    }catch(AccessDeniedException e) {
	    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
	    }
	}
	@GetMapping("/{cartId}/total-price")
	@Operation(summary = "Get total amount of Cart by cartId")
	public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
	    
		 try {
	            BigDecimal totalPrice = cartService.getTotalAmount(cartId);
	            return ResponseEntity.ok(new ApiResponse("Total Price", totalPrice));
	        } catch (RecordNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
	        }catch (AccessDeniedException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
	          }
	}
	
	@GetMapping("/my-cart")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@Operation(summary = "Get Cart (my cart)")
    public ResponseEntity<ApiResponse> getCurrentCart() {
        try {
            Cart cart = cartService.getCartByUserId(getCurrentUserId());
            CartResponse response = mapper.toCartResponse(cart);
            return ResponseEntity.ok(new ApiResponse("Success", response));
        } catch (RecordNotFoundException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
}
