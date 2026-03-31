package com.abdelrahman.shoppingcart.services.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.abdelrahman.shoppingcart.exceptions.RecordNotFoundException;
import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.models.CartItem;
import com.abdelrahman.shoppingcart.models.Product;
import com.abdelrahman.shoppingcart.repositories.CartRepo;
import com.abdelrahman.shoppingcart.repositories.ProductRepo;
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

	    return cartRepo.findById(id)
	            .orElseGet(() -> cartRepo.save(new Cart()));
	}
	
	@Override
	public Cart getCart(Long cartId) {
		Cart cart = cartRepo.findById(cartId).orElseThrow(()->new RecordNotFoundException("Cart not found"));
		cart.calcTotalAmount();
		return cartRepo.save(cart);
	}

	
	@Override
	@Transactional
	public Cart clearCart(Long cartId) {
	    Cart cart = cartRepo.findById(cartId)
	            .orElseThrow(() -> new RecordNotFoundException("Cart Not found!"));
	    
	    if (cart.getItems() != null && !cart.getItems().isEmpty()) {
	        for (CartItem item : cart.getItems()) {
	            Product product = item.getProduct();
	            product.setInventory(product.getInventory() + item.getQuantity());
	            productRepo.save(product);
	        }
	        
	        cart.getItems().clear();
	    }
	    
	    cart.calcTotalAmount();
	    return cartRepo.save(cart); 
	}

	@Override
	public BigDecimal getTotalAmount(Long cartId) {
		return  getCart(cartId).getTotalAmount();
	}
	
	

}
