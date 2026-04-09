package com.abdelrahman.shoppingcart.services;

import java.util.List;

import com.abdelrahman.shoppingcart.dtos.requests.UserRequestDto;
import com.abdelrahman.shoppingcart.dtos.responses.UserResponse;

public interface UserService {

	UserResponse update(Long id,UserRequestDto dto);
	List<UserResponse> findAll();
	UserResponse findById(Long id);
	UserResponse findByEmail(String email);
	void delete(Long id);
}
