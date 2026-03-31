package com.abdelrahman.shoppingcart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abdelrahman.shoppingcart.models.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

}
