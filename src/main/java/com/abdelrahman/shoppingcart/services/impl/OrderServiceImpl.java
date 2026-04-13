package com.abdelrahman.shoppingcart.services.impl;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.abdelrahman.shoppingcart.enums.OrderStatus;
import com.abdelrahman.shoppingcart.exceptions.IllegalOrderStateException;
import com.abdelrahman.shoppingcart.exceptions.InsufficientStockException;
import com.abdelrahman.shoppingcart.exceptions.RecordNotFoundException;
import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.models.CartItem;
import com.abdelrahman.shoppingcart.models.Order;
import com.abdelrahman.shoppingcart.models.OrderItem;
import com.abdelrahman.shoppingcart.models.Product;
import com.abdelrahman.shoppingcart.repositories.OrderRepo;
import com.abdelrahman.shoppingcart.repositories.ProductRepo;
import com.abdelrahman.shoppingcart.security.dtos.UserPrinciple;
import com.abdelrahman.shoppingcart.services.CartService;
import com.abdelrahman.shoppingcart.services.OrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderRepo orderRepo;
	private final ProductRepo productRepo;
	private final CartService cartService;
	
	@Override
	public Order placeOrder(Long userId) throws AccessDeniedException {
		if(!isCurrentUser(userId)) {
			throw new AccessDeniedException("You cannot place an order for another user!");
		}
		Cart cart = cartService.getCartByUserId(userId);
		
		if (cart.getItems() == null || cart.getItems().isEmpty()) {
	        throw new IllegalOrderStateException("Your cart is empty. You cannot place an empty order!");
	    }
		Order order = createOrder(cart);
		cartService.clearCart(cart.getId());
		processOrder(order.getId());
		return order;
	}
	
	private Order createOrder(Cart cart) {
		Order order = new Order();
		//order.setTotalAmount(cart.getTotalAmount());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setUser(cart.getUser());
		Set<CartItem> cartItems = cart.getItems();
		for (CartItem cartItem : cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(cartItem.getUnitPrice());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());		
			order.addOrderItem(orderItem);
		}
		
		order.calculateTotalAmount();
		return orderRepo.save(order);
	}
	
	@Override
	public void processOrder(Long orderId) {

	    Order order = orderRepo.findById(orderId)
	            .orElseThrow(() -> new RecordNotFoundException("Order not found"));

	    if (order.getOrderStatus() != OrderStatus.PENDING) {
	        throw new IllegalOrderStateException("Only pending orders can be processed");
	    }

	    for (OrderItem item : order.getOrderItems()) {
	        Product product = item.getProduct();
	        if (product.getInventory() < item.getQuantity()) {
	            throw new InsufficientStockException(
	                "Not enough stock for product: " + product.getName() +
	                " (Available: " + product.getInventory() + ")"
	            );
	        }

	        product.setInventory(product.getInventory() - item.getQuantity());
	    }

	    order.setOrderStatus(OrderStatus.PROCESSING);

	    orderRepo.save(order);
	}

	@Override
	public void shipOrder(Long orderId) {
		Order order = orderRepo.findById(orderId).orElseThrow();
	
		if(!order.getOrderStatus().equals(OrderStatus.PROCESSING)) {
			throw new IllegalOrderStateException("Can't ship order not processded");
		}
		
		order.setOrderStatus(OrderStatus.SHIPPED);
		orderRepo.save(order);
	}

	@Override
	public void deliverOrder(Long orderId) {
		Order order = orderRepo.findById(orderId).orElseThrow();
		
		if(!order.getOrderStatus().equals(OrderStatus.SHIPPED)) {
			throw new IllegalOrderStateException("Can't deliver order not shiped");
		}
		order.setOrderStatus(OrderStatus.DELIVERED);
		orderRepo.save(order);
		
	}

	@Override
	public void cancelOrder(Long orderId) throws AccessDeniedException{	
		Order order = orderRepo.findById(orderId).orElseThrow();
		
		if(!isCurrentUser(order.getUser().getId())) {
			throw new AccessDeniedException("You cannot cancel an order for another user!");
		}
		
		if(order.getOrderStatus().equals(OrderStatus.SHIPPED) || 
			    order.getOrderStatus().equals(OrderStatus.DELIVERED) || 
			    order.getOrderStatus().equals(OrderStatus.CANCELLED)) {
			throw new IllegalOrderStateException("Order can't cancelled");
		}
		if (order.getOrderStatus().equals(OrderStatus.PROCESSING)) {
			Set<OrderItem> items = order.getOrderItems();
			for (OrderItem item : items) {
				Product product = item.getProduct();
				product.setInventory(product.getInventory()+item.getQuantity());
				productRepo.save(product);
			}
		}
		order.setOrderStatus(OrderStatus.CANCELLED);
		orderRepo.save(order);
		
	}
	
	private UserPrinciple getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (UserPrinciple) auth.getPrincipal();
	}
	
	private boolean isCurrentUser(Long userId) {
		UserPrinciple user = getCurrentUser();
		return user.getId().equals(userId);
	}

	@Override
	public Order getOrderById(Long orderId) throws AccessDeniedException {
		Order order = orderRepo.findById(orderId).orElseThrow(()->new RecordNotFoundException("Order not found"));
		if(!isCurrentUser(order.getUser().getId())) {
			throw new AccessDeniedException("You cannot cancel an order for another user!");
		}
		return order;
	}

	@Override
	public List<Order> getUserOrders(Long userId) throws AccessDeniedException {
		if(!isCurrentUser(userId)) {
			throw new AccessDeniedException("You cannot get an orders for another user!");
		}
		return orderRepo.findByUserId(userId);
	}

}
