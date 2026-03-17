package com.abdelrahman.shoppingcart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abdelrahman.shoppingcart.models.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
