package com.abdelrahman.shoppingcart.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.abdelrahman.shoppingcart.dtos.requests.UserRequestDto;
import com.abdelrahman.shoppingcart.dtos.responses.UserResponse;
import com.abdelrahman.shoppingcart.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {


	@Mapping(source="cart.id",target="cartId")
	UserResponse toUserResponse(User user);
	
	User toUser(UserRequestDto dto);
	
	List<UserResponse> toListDto(List<User> users);
}
