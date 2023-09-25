package com.coffeeShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coffeeShop.exception.ProductNotFoundException;
import com.coffeeShop.model.Product;
import com.coffeeShop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service("productService")
@RequiredArgsConstructor
public class ProductService {
	private final DrinksService drinksService;
	private final CoffeeBeansService coffeeBeansService;
	private final GoodiesService goodiesService;
	private final ProductRepository productRepository;

	public void init() {
		drinksService.init();
		coffeeBeansService.init();
		goodiesService.init();
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findById(int id) {
		Optional<Product> productOptional = productRepository.findById(id);
		if (productOptional.isPresent()) {
			return productOptional.get();
		} else {
			throw new ProductNotFoundException("Product is not found for product id: " + id);
		}
	}
	
	public Product findByName(String name) {
		Optional<Product> productOptional = productRepository.findProductByName(name);
		if (productOptional.isPresent()) {
			return productOptional.get();
		} else {
			throw new ProductNotFoundException("Product is not found for product name: " + name);
		}
	}
	
	public Boolean deleteAll() {
		productRepository.deleteAll();
		return true;
	}

}
