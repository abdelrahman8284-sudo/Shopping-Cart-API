package com.abdelrahman.shoppingcart.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abdelrahman.shoppingcart.models.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long>{

	List<Image> findByProductId(Long productId);
}
