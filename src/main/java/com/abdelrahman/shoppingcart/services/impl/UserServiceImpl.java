package com.abdelrahman.shoppingcart.services.impl;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abdelrahman.shoppingcart.dtos.UserUpdateRequest;
import com.abdelrahman.shoppingcart.dtos.requests.UserRequestDto;
import com.abdelrahman.shoppingcart.dtos.responses.UserResponse;
import com.abdelrahman.shoppingcart.exceptions.RecordNotFoundException;
import com.abdelrahman.shoppingcart.mappers.UserMapper;
import com.abdelrahman.shoppingcart.models.User;
import com.abdelrahman.shoppingcart.repositories.UserRepo;
import com.abdelrahman.shoppingcart.security.dtos.UserPrinciple;
import com.abdelrahman.shoppingcart.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	
	private final UserRepo userRepo;
	private final UserMapper mapper;
	private final PasswordEncoder encoder;
	
	
	public UserResponse update(Long id,UserUpdateRequest dto) throws AccessDeniedException {
		User currentUser = userRepo.findById(id).orElseThrow(()->new RecordNotFoundException("User Not found"));

		if(dto.getUsername()!=null && !dto.getUsername().isBlank()) {
			currentUser.setUsername(dto.getUsername());
		}
		if(dto.getEmail()!=null && !dto.getEmail().isBlank()) {
			currentUser.setEmail(dto.getEmail());
		}
		User user = userRepo.save(currentUser);
		
		return mapper.toUserResponse(user);
	}
	
	
	
	public List<UserResponse> findAll(){
		
		return mapper.toListDto(userRepo.findAll());
	}
	
	public UserResponse findById(Long id) {
		return mapper.toUserResponse(userRepo.findById(id).orElseThrow(()->new RuntimeException("User Not Found")));
	}
	
	public UserResponse findByEmail(String email) {
		return mapper.toUserResponse(userRepo.findByEmail(email).orElseThrow(()->new RecordNotFoundException("User not Found")));
	}
	
	public void delete(Long id) {
		if(!userRepo.existsById(id))
			throw new RuntimeException("User Not Found");
		userRepo.deleteById(id);
	}
}