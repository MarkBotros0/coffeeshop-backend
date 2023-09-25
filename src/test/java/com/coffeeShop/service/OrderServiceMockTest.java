package com.coffeeShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeeShop.model.CoffeeBean;
import com.coffeeShop.model.Drink;
import com.coffeeShop.model.Goodie;
import com.coffeeShop.model.Order;
import com.coffeeShop.repository.OrderRepository;
import com.coffeeShop.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceMockTest {
	@InjectMocks
	private OrderService orderService;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private ProductRepository productRepository;

	@Test
	public void testSave() {

		Drink drink1 = new Drink("Cappoccino", 3.2f, "01/09/2023", "30/12/2023", "");
		Goodie goodie1 = new Goodie("Cookies", 3.6f, "01/01/2023", "30/03/2023", "");
	
		Order order = new Order();
		order.addOrderItem(drink1, 2);
		order.addOrderItem(goodie1, 6);

		when(productRepository.findById(drink1.getId())).thenReturn(Optional.of(drink1));
		when(productRepository.findById(goodie1.getId())).thenReturn(Optional.of(goodie1));
		when(orderRepository.save(order)).thenReturn(order);

		Order savedOrder = orderService.save(order);
		
		assertEquals(order, savedOrder);
		
		verify(orderRepository, times(1)).save(order);
		verify(productRepository, times(order.getOrderItems().size())).save(drink1);
	}
	
	@Test
	public void testFindAll() {

		CoffeeBean bean1 = new CoffeeBean("Tanzania Peaberry Coffee", 4.5f, "01/09/2023", "30/12/2023", "");

		Order order1 = new Order();
		order1.addOrderItem(bean1, 2);
		Order order2 = new Order();
		order2.addOrderItem(bean1, 4);
		Order order3 = new Order();
		order3.addOrderItem(bean1, 3);

		when(orderRepository.findAllByOrderByOrderIdDesc()).thenReturn(List.of(order3, order2, order1));

		List<Order> orders = orderService.findAll();

		assertEquals(3, orders.size());
	}
	
	
	
}
