package com.abdelrahman.shoppingcart.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.abdelrahman.shoppingcart.dtos.requests.AddItemToCart;
import com.abdelrahman.shoppingcart.dtos.responses.CartItemResponse;
import com.abdelrahman.shoppingcart.dtos.responses.CartResponse;
import com.abdelrahman.shoppingcart.models.Cart;
import com.abdelrahman.shoppingcart.models.CartItem;
@Mapper(componentModel = "spring",uses = CartItemMapper.class)
public interface CartMapper {

	CartResponse toCartResponse(Cart cart);
	
	//Cart toCartResponse(AddItemToCart addItemToCart);


	
	
	List<CartResponse> toListCartResponse(List<Cart> carts);
}
