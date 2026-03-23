package com.abdelrahman.shoppingcart.services;

import java.util.List;

import com.abdelrahman.shoppingcart.dtos.requests.ProductRequest;
import com.abdelrahman.shoppingcart.dtos.responses.ProductResponse;

public interface ProductService {

	ProductResponse addProduct(ProductRequest request);
	ProductResponse updateProduct(Long id,ProductRequest request);
	void deleteProduct(Long id);
	ProductResponse getProductById(Long id);
	List<ProductResponse> getProductsByName(String name);
	List<ProductResponse> getAllProducts();
	List<ProductResponse> getProductsByCategory(String category);
	List<ProductResponse> getProductsByBrand(String brand);
	List<ProductResponse> getProductsByCategoryAndBrand(String category,String brand);
	List<ProductResponse> getProductsByBrandAndName(String brand,String name);
	long countProductsByBrandAndName(String brand,String name);
}
