package com.abdelrahman.shoppingcart.services.impl;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.abdelrahman.shoppingcart.exceptions.RecordNotFoundException;
import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.models.CartItem;
import com.abdelrahman.shoppingcart.models.Product;
import com.abdelrahman.shoppingcart.models.User;
import com.abdelrahman.shoppingcart.repositories.CartRepo;
import com.abdelrahman.shoppingcart.repositories.ProductRepo;
import com.abdelrahman.shoppingcart.security.dtos.UserPrinciple;
import com.abdelrahman.shoppingcart.services.CartService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	
	private final CartRepo cartRepo;
	private final ProductRepo productRepo;
	
	
	@Override 
	public Cart getOrCreateCart(Long id) { 
		if (id == null) {
			return cartRepo.save(new Cart());
			
		} 
		return cartRepo.findById(id) .orElseGet(() -> cartRepo.save(new Cart()));	
	}

//	@Override
//	public Cart createCartForUser(User user) {
//	    Cart cart = new Cart();
//	    cart.setUser(user);
//	    return cartRepo.save(cart);
//	}
	
	@Override
	public Cart getCart(Long cartId) throws AccessDeniedException{
		Cart cart = cartRepo.findById(cartId).orElseGet(()->getCartByUserId(getCurrentUser().getId()));
		if(!isCurrentUser(cart.getUser().getId())) {
			throw new AccessDeniedException("User not allowed to see this cart");
		}
		cart.calcTotalAmount();
		return cartRepo.save(cart);
	}

	
	@Override
	@Transactional
	public Cart clearCart(Long cartId) throws AccessDeniedException {
	    Cart cart = cartRepo.findById(cartId)
	            .orElseThrow(() -> new RecordNotFoundException("Cart Not found!"));
	    
	    if(!isCurrentUser(cart.getUser().getId())){
	    	throw new AccessDeniedException("Current user not allowed to clear this cart");
	    }
	    if (cart.getItems() != null && !cart.getItems().isEmpty()) {    
	        cart.getItems().clear();
	    }
	    
	    cart.calcTotalAmount();
	    return cartRepo.save(cart); 
	}

	@Override
	public BigDecimal getTotalAmount(Long cartId) throws AccessDeniedException {
		return  getCart(cartId).getTotalAmount();
	}
	
	private UserPrinciple getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (UserPrinciple) auth.getPrincipal();
	}
	@Override
	public Cart getCartByUserId(Long userId) {
		return cartRepo.findByUserId(userId);
	}
	
	private boolean isCurrentUser(Long userId) {
		UserPrinciple user = getCurrentUser();
		return user.getId().equals(userId);
	}

}
