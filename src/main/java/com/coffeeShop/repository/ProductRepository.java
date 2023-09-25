package com.coffeeShop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeeShop.model.Product;
;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	public List<Product> findAllByOrderByIdAsc();
	
	public Optional<Product> findProductByName(String name);
	
}