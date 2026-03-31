package com.abdelrahman.shoppingcart.services;

import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.models.CartItem;

public interface CartItemService {
	Cart addItemToCart(Long productId,Long cartId,int quantity);
	Cart removeItemFromCart(Long cartId, Long itemId, int removedCount);
	Cart updateItemQuantity(Long cartId,Long productId,int newQuantity);
	CartItem getCartItem(Long cartId,Long productId);
}
