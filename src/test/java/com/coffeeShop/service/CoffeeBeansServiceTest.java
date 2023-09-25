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
import com.coffeeShop.model.CoffeeBean;
import com.coffeeShop.repository.CoffeeBeansRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class CoffeeBeansServiceTest {
	@Autowired
	private CoffeeBeansService service;
	@Autowired
	private CoffeeBeansRepository repository;

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
		CoffeeBean bean1 = new CoffeeBean("Tanzania Peaberry Coffee", 4.5f, "01/09/2023", "30/12/2023", "");
		service.save(bean1);

		CoffeeBean coffeeBeanRetrieved = service.findById(bean1.getId());
		assertEquals(bean1, coffeeBeanRetrieved);
	}

	@Test
	public void findAllTest() {
		CoffeeBean bean1 = new CoffeeBean("Tanzania Peaberry Coffee", 4.5f, "01/09/2023", "30/12/2023", "");
		CoffeeBean bean2 = new CoffeeBean("Hawaii Knoa Coffee", 6.4f, "01/01/2023", "30/03/2023", "");
		CoffeeBean bean3 = new CoffeeBean("Nicaragoan Coffee", 5.3f, "01/03/2023", "30/06/2023", "");
		CoffeeBean bean4 = new CoffeeBean("Sumatra Mandhelling Coffee", 7.3f, "01/06/2023", "30/09/2023", "");
		repository.save(bean1);
		repository.save(bean2);
		repository.save(bean3);
		repository.save(bean4);

		List<CoffeeBean> coffeeBeansList = service.findAll();
		assertEquals(4, coffeeBeansList.size());
	}

	@Test
	public void findByIdTest() {
		CoffeeBean bean1 = new CoffeeBean("Tanzania Peaberry Coffee", 4.5f, "01/09/2023", "30/12/2023", "");
		service.save(bean1);
		
		CoffeeBean coffeeBeanRetrieved = service.findById(bean1.getId());
		
		assertEquals(bean1.getId(), coffeeBeanRetrieved.getId());
		assertThrows(ProductNotFoundException.class, () -> service.findById(0));
	}
	
	
	@Test
	public void findByNameTest() {
		CoffeeBean bean1 = new CoffeeBean("Tanzania Peaberry Coffee", 4.5f, "01/09/2023", "30/12/2023", "");
		service.save(bean1);

		CoffeeBean coffeeBeanRetrieved = service.findByName(bean1.getName());

		assertEquals(bean1.getName(), coffeeBeanRetrieved.getName());
		assertThrows(ProductNotFoundException.class, () -> service.findByName("test"));
	}

	@Test
	public void deleteDrinkTest() {
		CoffeeBean bean1 = new CoffeeBean("Tanzania Peaberry Coffee", 4.5f, "01/09/2023", "30/12/2023", "");
		service.save(bean1);

		assertEquals(true, service.deleteById(bean1.getId()));

		assertThrows(ProductNotFoundException.class, () -> service.findById(bean1.getId()));
	}

}
