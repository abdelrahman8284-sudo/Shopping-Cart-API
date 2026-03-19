package com.abdelrahman.shoppingcart.services;

import java.util.List;

import com.abdelrahman.shoppingcart.dtos.requests.CategoryRequest;
import com.abdelrahman.shoppingcart.dtos.responses.CategoryResponse;

public interface ImageService {

	CategoryResponse addCategory(CategoryRequest request);
	void updateCategory(Long id,CategoryRequest request);
	void deleteCategory(Long id);
	CategoryResponse getCategoryById(Long id);
	CategoryResponse getCategoryByName(String name);
	List<CategoryResponse> getAllCategories();
	long countCategories();
}
