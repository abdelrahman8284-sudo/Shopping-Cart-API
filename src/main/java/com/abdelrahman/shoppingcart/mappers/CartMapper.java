package com.abdelrahman.shoppingcart.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.abdelrahman.shoppingcart.dtos.responses.CartResponse;
import com.abdelrahman.shoppingcart.models.Cart;
@Mapper(componentModel = "spring",uses = CartItemMapper.class)
public interface CartMapper {

	CartResponse toCartResponse(Cart cart);
	
	//Cart toCartResponse(AddItemToCart addItemToCart);


	
	
	List<CartResponse> toListCartResponse(List<Cart> carts);
}
