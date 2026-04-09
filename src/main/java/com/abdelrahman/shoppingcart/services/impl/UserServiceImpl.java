package com.abdelrahman.shoppingcart.services.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abdelrahman.shoppingcart.dtos.requests.UserRequestDto;
import com.abdelrahman.shoppingcart.dtos.responses.UserResponse;
import com.abdelrahman.shoppingcart.exceptions.RecordNotFoundException;
import com.abdelrahman.shoppingcart.mappers.UserMapper;
import com.abdelrahman.shoppingcart.models.User;
import com.abdelrahman.shoppingcart.repositories.UserRepo;
import com.abdelrahman.shoppingcart.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	
	private final UserRepo userRepo;
	private final UserMapper mapper;
	private final PasswordEncoder encoder;
	
	
	public UserResponse update(Long id,UserRequestDto dto) {
		User currentUser = userRepo.findById(id).orElseThrow(()->new RecordNotFoundException("User Not found"));
		User user = mapper.toUser(dto);
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		if(dto.getPassword() != null && !dto.getPassword().isBlank()) {
		    currentUser.setPassword(encoder.encode(dto.getPassword()));
		}
		return mapper.toUserResponse(userRepo.save(currentUser));
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