package com.abdelrahman.shoppingcart.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abdelrahman.shoppingcart.models.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

	List<Product> findByNameContainingIgnoreCase(String name);
	
	List<Product> findByCategoryName(String categoryName);
	
	List<Product> findByBrand(String brand);
	
	List<Product> findByCategoryNameAndBrand(String categoryName,String brand);
	
	List<Product> findByBrandAndName(String brand,String name);
	
	long countByBrandAndName(String brand,String name);
	
	boolean existsByNameAndBrand(String name,String brand);
}
