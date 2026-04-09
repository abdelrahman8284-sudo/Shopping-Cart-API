package com.abdelrahman.shoppingcart.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.abdelrahman.shoppingcart.dtos.requests.ProductRequest;
import com.abdelrahman.shoppingcart.dtos.responses.ProductResponse;
import com.abdelrahman.shoppingcart.services.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

	private final ProductService productService;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductResponse> addProduct(@RequestBody@Valid ProductRequest request) {		
		return new ResponseEntity<>(productService.addProduct(request),HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> updateProduct(@PathVariable Long id,@RequestBody@Valid ProductRequest request) {
		productService.updateProduct(id,request);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
	    productService.deleteProduct(id);
	    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse>  getProductById(@PathVariable Long id) {
		return ResponseEntity.ok(productService.getProductById(id));
	}

	@GetMapping("/search-name")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<ProductResponse>> getProductsByName(@RequestParam@NotBlank(message = "The name can't be empty.") String name) {
		return ResponseEntity.ok(productService.getProductsByName(name));
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@GetMapping("/search-category")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<ProductResponse>> getProductsByCategory(@RequestParam String category) {
		return ResponseEntity.ok(productService.getProductsByCategory(category));
	}

	@GetMapping("/search-brand")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<ProductResponse>> getProductsByBrand(@RequestParam String brand) {
		return ResponseEntity.ok(productService.getProductsByBrand(brand));
	}

	@GetMapping("/search-category-brand")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<ProductResponse>> getProductsByCategoryAndBrand(@RequestParam String category,@RequestParam String brand) {
		return ResponseEntity.ok(productService.getProductsByCategoryAndBrand(category,brand));
	}

	@GetMapping("/search-brand-name")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<ProductResponse>> getProductsByBrandAndName(@RequestParam String brand,@RequestParam String name) {
		return ResponseEntity.ok(productService.getProductsByBrandAndName(brand,name));
	}

	@GetMapping("/count-brand-name")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Long> countProductsByBrandAndName(
	        @RequestParam String brand,
	        @RequestParam String name) {

	    return ResponseEntity.ok(productService.countProductsByBrandAndName(brand, name));
	}
}
