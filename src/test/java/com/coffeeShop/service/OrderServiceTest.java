package com.coffeeShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coffeeShop.exception.OrderNotFoundException;
import com.coffeeShop.model.CoffeeBean;
import com.coffeeShop.model.Order;
import com.coffeeShop.repository.OrderRepository;
import com.coffeeShop.repository.ProductRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class OrderServiceTest {
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;

	@BeforeAll
	public void setUpBeforeAllTests() {
		productService.init();
	}

	@BeforeEach
	public void setUpBeforeEachTests() {
		orderRepository.deleteAll();
	}

	@Test
	public void testFindAll() {

		CoffeeBean bean1 = new CoffeeBean("Tanzania Peaberry Coffee", 4.5f, "01/09/2023", "30/12/2023", "");

		Order order1 = new Order();
		order1.addOrderItem(productRepository.findProductByName("Tanzania Peaberry Coffee").get(), 2);
		Order order2 = new Order();
		order2.addOrderItem(productRepository.findProductByName("Tanzania Peaberry Coffee").get(), 4);
		Order order3 = new Order();
		order3.addOrderItem(productRepository.findProductByName("Tanzania Peaberry Coffee").get(), 3);

		orderRepository.save(order1);
		orderRepository.save(order2);
		orderRepository.save(order3);

		List<Order> orders = orderService.findAll();

		assertEquals(3, orders.size());
	}

	@Test
	public void testSave() {
		int productQunantityLeft = ((CoffeeBean) productService.findByName("Tanzania Peaberry Coffee"))
				.getQuantityLeft();

		Order order = new Order();
		order.addOrderItem(productService.findByName("Cappoccino"), 2);// price:3.2
		order.addOrderItem(productService.findByName("Tanzania Peaberry Coffee"), 6);// price:4.5

		orderService.save(order);

		Order orderRetrieved = orderRepository.findById(order.getOrderId()).get();

		assertEquals("Cappoccino", orderRetrieved.getOrderItems().get(0).getProduct().getName());
		assertEquals(2, orderRetrieved.getOrderItems().get(0).getQuantity());
		assertEquals("Tanzania Peaberry Coffee", orderRetrieved.getOrderItems().get(1).getProduct().getName());
		assertEquals(6, orderRetrieved.getOrderItems().get(1).getQuantity());
		assertEquals(productQunantityLeft - 6,
				((CoffeeBean) productService.findByName("Tanzania Peaberry Coffee")).getQuantityLeft());
		assertEquals((3.2f * 2f + 4.5f * 6f), orderRetrieved.getTotalPrice());
	}

	@Test
	public void testUpdateOrder() {
		Order order = new Order();
		order.addOrderItem(productService.findByName("Cappoccino"), 2);
		order.addOrderItem(productService.findByName("Tanzania Peaberry Coffee"), 6);

		orderService.save(order);

		Order modifiedOrder = new Order();
		modifiedOrder.addOrderItem(productService.findByName("Cappoccino"), 5);
		modifiedOrder.addOrderItem(productService.findByName("Hawaii Knoa Coffee"), 3);

		orderService.updateOrder(order.getOrderId(), modifiedOrder);

		Order orderRetrieved = orderService.findById(order.getOrderId());

		assertEquals("Cappoccino", orderRetrieved.getOrderItems().get(0).getProduct().getName());
		assertEquals("Hawaii Knoa Coffee", orderRetrieved.getOrderItems().get(1).getProduct().getName());
		assertEquals(5, orderRetrieved.getOrderItems().get(0).getQuantity());
		assertEquals(3, orderRetrieved.getOrderItems().get(1).getQuantity());
	}

	@Test
	public void testFindById() {
		Order order = new Order();
		orderService.save(order);

		Order orderRetrieved = orderService.findById(order.getOrderId());

		assertEquals(order.getOrderId(), orderRetrieved.getOrderId());
		assertThrows(OrderNotFoundException.class, () -> orderService.findById(0));
	}

	@Test
	public void testDeleteOrderByIdAndCheckForException() {
		orderRepository.deleteAll();

		Order order = new Order();
		order.addOrderItem(productService.findByName("Cappoccino"), 2);
		order.addOrderItem(productService.findByName("Tanzania Peaberry Coffee"), 6);

		orderService.save(order);

		assertEquals(true, orderService.deleteOrderById(order.getOrderId()));

		assertThrows(OrderNotFoundException.class, () -> orderService.findById(order.getOrderId()));
	}

}
