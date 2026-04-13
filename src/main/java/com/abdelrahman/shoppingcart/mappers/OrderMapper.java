package com.abdelrahman.shoppingcart.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.abdelrahman.shoppingcart.dtos.responses.OrderResponse;
import com.abdelrahman.shoppingcart.models.Order;

@Mapper(componentModel = "spring",uses = {OrderItemMapper.class})
public interface OrderMapper {
 
	@Mappings({
		@Mapping(source="user.id",target="userId"),
		@Mapping(source="user.username",target="username"),
		@Mapping(source="user.email",target="email")
		})
	OrderResponse toOrderResponse(Order order);
	
	List<OrderResponse> toListOrderResponse(List<Order> orders);
}
