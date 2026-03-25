package com.abdelrahman.shoppingcart.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.abdelrahman.shoppingcart.dtos.requests.ProductRequest;
import com.abdelrahman.shoppingcart.dtos.responses.ProductResponse;
import com.abdelrahman.shoppingcart.exceptions.RecordNotFoundException;
import com.abdelrahman.shoppingcart.mappers.ProductMapper;
import com.abdelrahman.shoppingcart.models.Category;
import com.abdelrahman.shoppingcart.models.Product;
import com.abdelrahman.shoppingcart.repositories.CategoryRepo;
import com.abdelrahman.shoppingcart.repositories.ProductRepo;
import com.abdelrahman.shoppingcart.services.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepo productRepo;
	private final CategoryRepo categoryRepo;
	private final ProductMapper mapper;
	

	@Override
	@Transactional
	public ProductResponse addProduct(ProductRequest request) {
		
		// check if category is found in database
		// if yes then create product and set it
		// if no then save it as a new category
		// then set as a new product category
		
		//Category category = categoryRepo.findById(request.getCategoryId()).orElseThrow(()->new RecordNotFoundException("Not found category"));
		Category category = categoryRepo.findByNameIgnoreCase(request.getCategoryName())
		        .orElseGet(() -> {
		            Category newCategory = new Category(request.getCategoryName());
		            return categoryRepo.save(newCategory);
		        });
			
		
		Product product = mapper.toEntity(request);
		product.setCategory(category);
		ProductResponse response = mapper.toDto(productRepo.save(product));
		return response;
	}
	
//	private Product createProduct(ProductRequest request,Category category) {
//		return new Product(
//				request.getName(),
//				request.getBrand(),
//				request.getPrice(),
//				request.getInventory(),
//				request.getDescription(),
//				category
//				);
//	}

	@Override
	@Transactional
	public ProductResponse updateProduct(Long id, ProductRequest request) {
		Product current = productRepo.findById(id).orElseThrow(()->new RecordNotFoundException("Not found product with id : "+id));
		Category category = categoryRepo.findByNameIgnoreCase(request.getCategoryName())
	            .orElseGet(() -> categoryRepo.save(Category.builder()
	                    .name(request.getCategoryName())
	                    .build()));
		current.setCategory(category);
		mapper.toEntity(request, current);
		ProductResponse response = mapper.toDto(productRepo.save(current));
		return response;
	}

	@Override
	@Transactional
	public void deleteProduct(Long id) {
	    if (!productRepo.existsById(id)) {
	        throw new RecordNotFoundException("Product Not found");
	    }
	    productRepo.deleteById(id);
	}

	@Override
	public ProductResponse getProductById(Long id) {
		Product product = productRepo.findById(id)
				.orElseThrow(()->new RecordNotFoundException("Product with id:" +id+" not found"));	
		
		return mapper.toDto(product);
	}

	@Override
	public List<ProductResponse> getProductsByName(String name) {
		List<Product> products = productRepo.findByNameContainingIgnoreCase(name);
		return mapper.toListDto(products);
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepo.findAll();
		return mapper.toListDto(products);
	}

	@Override
	public List<ProductResponse> getProductsByCategory(String category) {
		List<Product> products = productRepo.findByCategoryName(category);
		return mapper.toListDto(products);
	}

	@Override
	public List<ProductResponse> getProductsByBrand(String brand) {
		List<Product> products = productRepo.findByBrand(brand);
		return mapper.toListDto(products);
	}

	@Override
	public List<ProductResponse> getProductsByCategoryAndBrand(String category, String brand) {
		List<Product> products = productRepo.findByCategoryNameAndBrand(category,brand);
		return mapper.toListDto(products);
	}

	@Override
	public List<ProductResponse> getProductsByBrandAndName(String brand, String name) {
		List<Product> products = productRepo.findByBrandAndName(brand,name);
		return mapper.toListDto(products);
	}

	@Override
	public long countProductsByBrandAndName(String brand, String name) {
		return productRepo.countByBrandAndName(brand, name);
	}

}
