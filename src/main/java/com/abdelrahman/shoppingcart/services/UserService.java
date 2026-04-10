package com.abdelrahman.shoppingcart.services;

import java.nio.file.AccessDeniedException;
import java.util.List;

import com.abdelrahman.shoppingcart.dtos.UserUpdateRequest;
import com.abdelrahman.shoppingcart.dtos.responses.UserResponse;

public interface UserService {

	UserResponse update(Long id,UserUpdateRequest dto) throws AccessDeniedException;
	List<UserResponse> findAll();
	UserResponse findById(Long id);
	UserResponse findByEmail(String email);
	void delete(Long id);
}
