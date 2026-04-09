package com.abdelrahman.shoppingcart.services;

import java.nio.file.AccessDeniedException;

import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.models.CartItem;

public interface CartItemService {
	Cart addItemToCart(Long productId,Long cartId,int quantity) throws AccessDeniedException;
	Cart removeItemFromCart(Long cartId, Long itemId, int removedCount) throws AccessDeniedException;
	Cart updateItemQuantity(Long cartId,Long productId,int newQuantity) throws AccessDeniedException;
	CartItem getCartItem(Long cartId,Long productId) throws AccessDeniedException;
}
