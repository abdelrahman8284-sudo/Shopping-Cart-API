package com.abdelrahman.shoppingcart.controllers;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdelrahman.shoppingcart.dtos.UserUpdateRequest;
import com.abdelrahman.shoppingcart.dtos.requests.UserRequestDto;
import com.abdelrahman.shoppingcart.dtos.responses.ApiResponse;
import com.abdelrahman.shoppingcart.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User registration, authentication and profile management")
public class UserController {

	private final UserService userService;

	@PutMapping("/{id}")
	@Operation(summary = "Update User")
	@PreAuthorize("hasRole('ADMIN') or #id == principal.id")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserUpdateRequest dto) {
		try {
			return ResponseEntity.ok().body(new ApiResponse("Updated successfully",userService.update(id, dto)));
		} catch (AccessDeniedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(),null));
		}
	}


	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Find User By Id")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.findById(id));
	}

	@GetMapping("/search")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Find User by Email")
	public ResponseEntity<?> findByEmail(@RequestParam String email) {
		return ResponseEntity.ok(userService.findByEmail(email));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Find All Users")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(userService.findAll());
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Delete User by its id")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
