package com.abdelrahman.shoppingcart.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.abdelrahman.shoppingcart.dtos.requests.UserRequestDto;
import com.abdelrahman.shoppingcart.dtos.responses.UserResponse;
import com.abdelrahman.shoppingcart.models.User;

@Mapper(componentModel = "spring",uses = CartMapper.class)
public interface UserMapper {


	UserResponse toUserResponse(User user);
	
	User toUser(UserRequestDto dto);
	
	List<UserResponse> toListDto(List<User> users);
}
