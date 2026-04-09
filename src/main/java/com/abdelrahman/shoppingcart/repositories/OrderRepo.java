package com.abdelrahman.shoppingcart.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abdelrahman.shoppingcart.models.Order;

@Repository
public interface OrderRepo  extends JpaRepository<Order, Long>{

	List<Order> findByUserId(Long userId);
}
