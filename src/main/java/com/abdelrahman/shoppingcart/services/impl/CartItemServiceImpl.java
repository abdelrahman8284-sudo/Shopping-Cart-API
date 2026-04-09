package com.abdelrahman.shoppingcart.services.impl;

import java.nio.file.AccessDeniedException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.abdelrahman.shoppingcart.exceptions.InsufficientStockException;
import com.abdelrahman.shoppingcart.exceptions.RecordNotFoundException;
import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.models.CartItem;
import com.abdelrahman.shoppingcart.models.Product;
import com.abdelrahman.shoppingcart.repositories.CartItemRepo;
import com.abdelrahman.shoppingcart.repositories.CartRepo;
import com.abdelrahman.shoppingcart.repositories.ProductRepo;
import com.abdelrahman.shoppingcart.security.dtos.UserPrinciple;
import com.abdelrahman.shoppingcart.services.CartItemService;
import com.abdelrahman.shoppingcart.services.CartService;
import com.abdelrahman.shoppingcart.services.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
	
	private final CartItemRepo itemRepo;
	private final CartRepo cartRepo;
	private final ProductRepo productRepo;
	private final  ProductService productService;
	private final CartService cartService;
	
	@Override
	@Transactional
	public Cart addItemToCart(Long productId,Long cartId,int quantity) throws AccessDeniedException {
		// creating cart 
		Cart cart = cartService.getCart(cartId);
		
		if(!isCurrentUser(cart.getUser().getId())) {
			throw new AccessDeniedException("User not allowed to see this cart");
		}
		
		Product product = productService.getProduct(productId);
				
	
		if(quantity <= 0) {
		    throw new IllegalArgumentException("Quantity must be greater than 0");
		}
		
		if(quantity > product.getInventory()) {
			throw new InsufficientStockException("There is not enough inventory ,  available :"+product.getInventory()+" items");
		}
		
		Set<CartItem> items = cart.getItems();
		
		CartItem currentItem = items.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst().orElse(null);
		
		
		if(currentItem == null) {
			CartItem newItem = CartItem.builder()
					.quantity(quantity)					
					.cart(cart)
					.product(product)
					.unitPrice(product.getPrice())
					.build();
			cart.addItem(newItem);
		}else {
			int newQuantity = currentItem.getQuantity() + quantity;
	        
	        if (newQuantity > product.getInventory()) {
	            throw new RuntimeException("There is not enough inventory ,  available :"+product.getInventory()+" items");
	        }
	        currentItem.setQuantity(newQuantity);        
		}
		cart.calcTotalAmount();
		return  cartRepo.save(cart);
	}
	
	@Override
	@Transactional
	public Cart updateItemQuantity(Long cartId, Long productId, int newQuantity) throws AccessDeniedException {
		Cart cart = cartService.getCart(cartId);
		if(!isCurrentUser(cart.getUser().getId())) {
			throw new AccessDeniedException("User not allowed to see this cart");
		}
		
		Product product = productService.getProduct(productId);
		if(newQuantity <= 0) {
		    throw new IllegalArgumentException("Quantity must be greater than 0");
		}
		
		if(newQuantity > product.getInventory()) {
			throw new InsufficientStockException("There is not enough inventory ,  available :"+product.getInventory()+" items");
		}
		
		Set<CartItem> items = cart.getItems();

		CartItem currentItem =
				items.stream()
				.filter(item->item.getProduct().getId().equals(productId))
				.findFirst()
				.orElseThrow(()->new RecordNotFoundException("There no items with this product"));
        currentItem.setQuantity(newQuantity);    
        currentItem.setUnitPrice(product.getPrice());
		cart.calcTotalAmount();
//		cartRepo.save(cart);
		return cartRepo.save(cart);
	}
	@Override
	@Transactional
	public Cart removeItemFromCart(Long cartId, Long itemId, int removedCount) throws AccessDeniedException {
		Cart cart = cartRepo.findById(cartId).orElseThrow(()->new RecordNotFoundException("Cart not found"));
		if(!isCurrentUser(cart.getUser().getId())) {
			throw new AccessDeniedException("User not allowed to see this cart");
		}
		CartItem currentItem = cart.getItems().stream()
				.filter(item ->item.getId().equals(itemId))
				.findFirst()
				.orElseThrow(() -> new RecordNotFoundException("Item not found in this cart"));
		
		int currentQuantity = currentItem.getQuantity();
		
		if(removedCount <= 0) {
		    throw new IllegalArgumentException("Invalid remove count");
		}
		
		if(removedCount>currentQuantity) {
			throw new RuntimeException("Only "+currentQuantity +" can be removed!");
		}	
		
		if(removedCount == currentQuantity) {
			cart.removeItem(currentItem);
			
		}else {
			
			currentItem.setQuantity(currentQuantity - removedCount);			
		}

		cart.calcTotalAmount();
		return cartRepo.save(cart);
	}
	@Override
	public CartItem getCartItem(Long cartId, Long productId) throws AccessDeniedException {
		Cart cart = cartService.getCart(cartId);
		if(!isCurrentUser(cart.getUser().getId())) {
			throw new AccessDeniedException("User not allowed to see this cart");
		}
		return cart.getItems().stream()
				.filter(item->item.getProduct().getId().equals(productId))
				.findFirst()
				.orElseThrow(()-> new RecordNotFoundException("Item not found"));
	}
	
	private UserPrinciple getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (UserPrinciple) auth.getPrincipal();
	}
	
	private boolean isCurrentUser(Long userId) {
		UserPrinciple user = getCurrentUser();
		return user.getId().equals(userId);
	}

}
