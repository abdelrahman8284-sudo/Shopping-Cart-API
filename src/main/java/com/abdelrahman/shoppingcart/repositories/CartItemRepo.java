package com.abdelrahman.shoppingcart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abdelrahman.shoppingcart.models.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
