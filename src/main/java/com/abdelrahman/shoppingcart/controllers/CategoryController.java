package com.abdelrahman.shoppingcart.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdelrahman.shoppingcart.dtos.requests.CategoryRequest;
import com.abdelrahman.shoppingcart.dtos.responses.CategoryResponse;
import com.abdelrahman.shoppingcart.services.CategoryService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {

	private final CategoryService categoryService;
	@PostMapping
	public ResponseEntity<CategoryResponse> addCategory(@RequestBody@Valid CategoryRequest request) {		
		return new ResponseEntity<>(categoryService.addCategory(request),HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCategory(@PathVariable Long id,@RequestBody@Valid CategoryRequest request) {
		categoryService.updateCategory(id,request);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
	    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse>  getCategoryById(@PathVariable Long id) {
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}

	@GetMapping("/search-name")
	public ResponseEntity<CategoryResponse> getCategoryByName(@RequestParam@NotBlank(message = "The name can't be empty.") String name) {
		return ResponseEntity.ok(categoryService.getCategoryByName(name));
	}

	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}


	@GetMapping("/count")
	public ResponseEntity<Long> countCategories() {

	    return ResponseEntity.ok(categoryService.countCategories());
	}
}
