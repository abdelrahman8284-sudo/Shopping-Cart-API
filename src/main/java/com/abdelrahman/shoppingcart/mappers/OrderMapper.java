package com.abdelrahman.shoppingcart.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.abdelrahman.shoppingcart.dtos.responses.OrderResponse;
import com.abdelrahman.shoppingcart.models.Order;

@Mapper(componentModel = "spring",uses = {OrderItemMapper.class,UserMapper.class})
public interface OrderMapper {

	OrderResponse toOrderResponse(Order order);
	
	List<OrderResponse> toListOrderResponse(List<Order> orders);
}
