package com.coffeeShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coffeeShop.exception.ProductNotFoundException;
import com.coffeeShop.model.Drink;
import com.coffeeShop.model.Product;
import com.coffeeShop.repository.ProductRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ProductServiceTest {
	@Autowired
	private ProductService productService;
	@Autowired
	private DrinksService drinksService;
	@Autowired
	private ProductRepository productRepository;

	@BeforeEach
	public void setUpBeforeEachTests() {
		productService.deleteAll();

	}

	@AfterAll
	public void setUpAfterAllTests() {
		productService.deleteAll();

	}

	@Test
	public void testFindAll() {
		productService.init();
		assertEquals(12, productService.findAll().size());
	}

	@Test
	public void testFindByName() {
		Drink drink = new Drink("Cappoccino", 3.2f, "01/09/2023", "30/12/2023", "");
		drinksService.save(drink);

		Product productRetrieved = productService.findByName(drink.getName());

		assertEquals(drink.getId(), productRetrieved.getId());
		assertThrows(ProductNotFoundException.class, () -> productService.findById(0));
	}

	@Test
	public void testFindById() {
		Drink drink = new Drink("Cappoccino", 3.2f, "01/09/2023", "30/12/2023", "");
		drinksService.save(drink);

		Product productRetrieved = productService.findById(drink.getId());

		assertEquals(drink.getId(), productRetrieved.getId());
		assertThrows(ProductNotFoundException.class, () -> productService.findById(0));
	}

	@Test
	public void testDeleteAll() {
		Drink drink = new Drink("Cappoccino", 3.2f, "01/09/2023", "30/12/2023", "");
		drinksService.save(drink);

		productService.deleteAll();

		List<Product> productsList = productService.findAll();

		assertEquals(0, productsList.size());
		assertThrows(ProductNotFoundException.class, () -> productService.findByName(drink.getName()));
	}
}
