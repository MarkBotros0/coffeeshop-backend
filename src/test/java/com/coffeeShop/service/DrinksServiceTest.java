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
import com.coffeeShop.repository.DrinksRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class DrinksServiceTest {
	@Autowired
	private DrinksService service;
	@Autowired
	private DrinksRepository repository;

	@BeforeEach
	public void setUpBeforeEachTests() {
		repository.deleteAll();
	}
	
	@AfterAll
	public void setUpAfterAllTests() {
		repository.deleteAll();
		
	}

	@Test
	public void saveTest() {
		Drink drink = new Drink("Mocha", 3.9f, "01/06/2023", "30/09/2023", "");
		service.save(drink);

		Drink drinkRetrieved = service.findById(drink.getId());
		assertEquals(drink, drinkRetrieved);
	}

	@Test
	public void findAllTest() {
		Drink drink1 = new Drink("Cappoccino", 3.2f, "01/09/2023", "30/12/2023", "");
		Drink drink2 = new Drink("Espresso", 2.5f, "01/01/2023", "30/03/2023", "");
		Drink drink3 = new Drink("Latte", 4.4f, "01/03/2023", "30/06/2023", "");
		Drink drink4 = new Drink("Mocha", 3.9f, "01/06/2023", "30/09/2023", "");
		service.save(drink1);
		service.save(drink2);
		service.save(drink3);
		service.save(drink4);

		List<Drink> drinksList = service.findAll();
		assertEquals(4, drinksList.size());
	}

	@Test
	public void findByIdTest() {
		Drink drink = new Drink("Cappoccino", 3.2f, "01/09/2023", "30/12/2023", "");
		service.save(drink);

		Drink drinkRetrieved = service.findById(drink.getId());

		assertEquals(drink.getId(), drinkRetrieved.getId());
		assertThrows(ProductNotFoundException.class, () -> service.findById(0));
	}
	
	@Test
	public void findByNameTest() {
		Drink drink = new Drink("Cappoccino", 3.2f, "01/09/2023", "30/12/2023", "");
		service.save(drink);
		
		Drink drinkRetrieved = service.findByName(drink.getName());
		
		assertEquals(drink.getName(), drinkRetrieved.getName());
		assertThrows(ProductNotFoundException.class, () -> service.findByName("test"));
	}

	@Test
	public void deleteDrinkTest() {
		Drink drink = new Drink("Cappoccino", 3.2f, "01/09/2023", "30/12/2023", "");
		service.save(drink);

		assertEquals(true, service.deleteDrinkById(drink.getId()));

		assertThrows(ProductNotFoundException.class, () -> service.findById(drink.getId()));
	}

}
