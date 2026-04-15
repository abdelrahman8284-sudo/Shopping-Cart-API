package com.abdelrahman.shoppingcart.services;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;

import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.models.User;

public interface CartService {

	//Cart createCartForUser(User user);
	Cart getOrCreateCart(Long id);
//	Cart addItemToCart(Long productId,Long cartId,int quantity);
//	Cart removeItemFromCart(Long cartId,Long itemId,int removedCount);
	Cart clearCart(Long cartId) throws AccessDeniedException;
	BigDecimal getTotalAmount(Long cartId) throws AccessDeniedException;
	Cart getCart(Long cartId) throws AccessDeniedException;
	Cart getCartByUserId(Long userId);
}
