package com.coffeeShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeeShop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	public List<Order> findAllByOrderByOrderIdDesc();

	public List<Order> findAllByUserEmail(String email);
}
