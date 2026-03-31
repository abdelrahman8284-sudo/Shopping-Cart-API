package com.abdelrahman.shoppingcart.services;

import java.math.BigDecimal;

import com.abdelrahman.shoppingcart.models.Cart;

public interface CartService {

	Cart getOrCreateCart(Long id);
//	Cart addItemToCart(Long productId,Long cartId,int quantity);
//	Cart removeItemFromCart(Long cartId,Long itemId,int removedCount);
	Cart clearCart(Long cartId);
	BigDecimal getTotalAmount(Long cartId);
	Cart getCart(Long cartId);
}
