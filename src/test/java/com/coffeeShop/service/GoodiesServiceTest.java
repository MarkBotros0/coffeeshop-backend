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
import com.coffeeShop.model.Goodie;
import com.coffeeShop.repository.GoodiesRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class GoodiesServiceTest {
	@Autowired
	private GoodiesService service;
	@Autowired
	private GoodiesRepository repository;

	@BeforeEach
	public void setUpBeforeEachTests() {
		repository.deleteAll();
	}
	
	@AfterAll
	public void setUpAfterAllTests() {
		repository.deleteAll();
		
	}

	@Test
	public void saveGoodieTest() {
		Goodie goodie = new Goodie("Mocha", 3.9f, "01/06/2023", "30/09/2023", "");
		service.save(goodie);

		Goodie goodieRetrieved = service.findById(goodie.getId());
		assertEquals(goodie, goodieRetrieved);
	}

	@Test
	public void findAllGoodiesTest() {
		Goodie goodie1 = new Goodie("Cookies", 3.6f, "01/01/2023", "30/03/2023", "");
		Goodie goodie2 = new Goodie("Muffins", 2.8f, "01/03/2023", "30/06/2023", "");
		Goodie goodie3 = new Goodie("Croissant", 5.3f, "06/09/2023", "30/09/2023", "");
		Goodie goodie4 = new Goodie("Biscuits", 4.5f, "09/12/2023", "30/12/2023", "");
		repository.save(goodie1);
		repository.save(goodie2);
		repository.save(goodie3);
		repository.save(goodie4);

		List<Goodie> goodiesList = service.findAll();
		assertEquals(4, goodiesList.size());
	}

	@Test
	public void findGoodieByIdTest() {
		Goodie goodie = new Goodie("Cookies", 3.6f, "01/01/2023", "30/03/2023", "");
		service.save(goodie);

		Goodie goodieRetrieved = service.findById(goodie.getId());

		assertEquals(goodie.getId(), goodieRetrieved.getId());
		assertThrows(ProductNotFoundException.class, () -> service.findById(0));
	}
	
	@Test
	public void findGoodieByNameTest() {
		Goodie goodie = new Goodie("Cookies", 3.6f, "01/01/2023", "30/03/2023", "");
		service.save(goodie);
		
		Goodie goodieRetrieved = service.findByName(goodie.getName());
		
		assertEquals(goodie.getName(), goodieRetrieved.getName());
		assertThrows(ProductNotFoundException.class, () -> service.findByName("test"));
	}

	@Test
	public void deleteGoodieByIdTest() {
		Goodie goodie = new Goodie("Cookies", 3.6f, "01/01/2023", "30/03/2023", "");
		service.save(goodie);

		assertEquals(true, service.deleteGoodieById(goodie.getId()));

		assertThrows(ProductNotFoundException.class, () -> service.findById(goodie.getId()));
	}

}
