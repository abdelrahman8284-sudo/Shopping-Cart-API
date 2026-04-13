package com.abdelrahman.shoppingcart.mappers;



import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.abdelrahman.shoppingcart.dtos.responses.OrderItemResponse;
import com.abdelrahman.shoppingcart.models.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

	@Mapping(source="product.name",target="productName")
	@Mapping(source="product.id",target="productId")
	@Mapping(source="product.brand",target="productBrand")
	OrderItemResponse toOrderItemResponse(OrderItem item);
	
	List<OrderItemResponse> toListOrderItemResponse(List<OrderItem> items);
}
