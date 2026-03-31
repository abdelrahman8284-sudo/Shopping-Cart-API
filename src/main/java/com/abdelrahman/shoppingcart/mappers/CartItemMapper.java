package com.abdelrahman.shoppingcart.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.abdelrahman.shoppingcart.dtos.requests.AddItemToCart;
import com.abdelrahman.shoppingcart.dtos.responses.CartItemResponse;
import com.abdelrahman.shoppingcart.models.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
	@Mappings({
		@Mapping(source ="product.id",target="productId"),
		@Mapping(source ="product.name",target="name")
		})
	CartItemResponse toCartItemResponse(CartItem item);
	
	
	@Mapping(source ="productId",target ="product.id")
	CartItem toCartItem(AddItemToCart addItemToCart);
}
