package com.abdelrahman.shoppingcart.services;

import java.nio.file.AccessDeniedException;
import java.util.List;

import com.abdelrahman.shoppingcart.models.Order;

public interface OrderService {

	Order placeOrder(Long userId) throws AccessDeniedException;
	Order getOrderById(Long orderId) throws AccessDeniedException;
	List<Order> getUserOrders(Long userId)throws AccessDeniedException;
	void processOrder(Long orderId);
	void shipOrder(Long orderId);
	void deliverOrder(Long orderId);
	void cancelOrder(Long orderId) throws AccessDeniedException;
}
