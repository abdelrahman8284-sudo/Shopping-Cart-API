package com.abdelrahman.shoppingcart.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abdelrahman.shoppingcart.models.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

	@Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.products")
	List<Category> findAllWithProductCount();
	
	Optional<Category> findByNameIgnoreCase(String name);

	boolean existsByName(String name);
}
