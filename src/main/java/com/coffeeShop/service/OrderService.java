package com.coffeeShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coffeeShop.DTO.OrderItemDTO;
import com.coffeeShop.DTO.OrderRequestDTO;
import com.coffeeShop.exception.NotEnoughProductsException;
import com.coffeeShop.exception.OrderNotFoundException;
import com.coffeeShop.model.CoffeeBean;
import com.coffeeShop.model.Goodie;
import com.coffeeShop.model.Order;
import com.coffeeShop.model.OrderItem;
import com.coffeeShop.model.Product;
import com.coffeeShop.repository.OrderRepository;
import com.coffeeShop.repository.ProductRepository;
import com.coffeeShop.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

//	@Transactional
//	public void init() {
//		User user = User.builder().firstname("mark").lastname("markos").email("mark.botros@gmail.com")
//				.password("testing").role(Role.USER).build();
//		userRepository.save(user);
//
//		Order order1 = new Order();
//		order1.addOrderItem(productRepository.findById(1).get(), 5);
//		order1.addOrderItem(productRepository.findById(6).get(), 6);
//		order1.addOrderItem(productRepository.findById(9).get(), 10);
//		order1.setUser(userRepository.findByEmail("mark.botros@gmail.com").get());
//		save(order1);
//		Order order2 = new Order();
//		order2.addOrderItem(productRepository.findById(2).get(), 20);
//		order2.addOrderItem(productRepository.findById(12).get(), 30);
//		order2.setUser(userRepository.findByEmail("mark.botros@gmail.com").get());
//		save(order2);
//	}

	public Order changeMapToOrder(OrderRequestDTO orderDto) {
		Order order = new Order();
		for (OrderItemDTO item : orderDto.getOrderItems()) {
			int quantity = item.getQuantity();
			int productId = item.getProductId();

			Product product = productRepository.findById(productId)
					.orElseThrow(() -> new IllegalArgumentException("not valid input"));

			order.addOrderItem(new OrderItem(product, quantity));
		}
		return order;
	}

	@Transactional
	public Order save(Order order) {

		List<OrderItem> itemsList = order.getOrderItems();

		for (OrderItem lineItem : itemsList) {
			int id = lineItem.getProduct().getId();
			int quantityToRemove = lineItem.getQuantity();

			Optional<Product> productOptional = productRepository.findById(id);

			if (productOptional.isPresent()) {
				Product product = productOptional.get();

				if (product instanceof CoffeeBean) {
					CoffeeBean coffeeBean = (CoffeeBean) product;
					if (coffeeBean.getQuantityLeft() >= quantityToRemove) {
						coffeeBean.decrement(quantityToRemove);
						productRepository.save(coffeeBean);
					} else {
						throw new NotEnoughProductsException("Error: Quantity Ordered (" + quantityToRemove
								+ ") is more than quantity left (" + coffeeBean.getQuantityLeft() + ")");
					}
				} else if (product instanceof Goodie) {
					Goodie goodie = (Goodie) product;
					if (goodie.getQuantityLeft() >= quantityToRemove) {
						goodie.decrement(quantityToRemove);
						productRepository.save(goodie);
					} else {
						throw new NotEnoughProductsException("Error: Quantity Ordered (" + quantityToRemove
								+ ") is more than quantity left (" + goodie.getQuantityLeft() + ")");
					}
				}
			}
		}
		orderRepository.save(order);
		return order;
	}

	public List<Order> findAll() {
		List<Order> orderList = orderRepository.findAllByOrderByOrderIdDesc();
		return orderList;
	}

	public Order updateOrder(int orderId, Order newOrder) {
		Optional<Order> oldOrderOptional = orderRepository.findById(orderId);
		if (oldOrderOptional.isPresent()) {
			Order oldOrder = oldOrderOptional.get();
			List<OrderItem> oldItemsList = oldOrder.getOrderItems();
			for (OrderItem lineItem : oldItemsList) {
				int id = lineItem.getProduct().getId();
				int quantityToAdd = lineItem.getQuantity();

				Product product = productRepository.findById(id).get();

				if (product instanceof CoffeeBean) {
					CoffeeBean bean = (CoffeeBean) product;
					bean.increment(quantityToAdd);
					productRepository.save(bean);
				} else if (product instanceof Goodie) {
					Goodie goodie = (Goodie) product;
					goodie.increment(quantityToAdd);
					productRepository.save(goodie);
				}
			}
			oldOrder.setOrderItems(newOrder.getOrderItems());
			return save(oldOrder);
		} else {
			throw new OrderNotFoundException("Order of id: " + orderId + " is not found");
		}
	}

	public Boolean deleteOrderById(int id) {
		orderRepository.deleteById(id);
		return true;
	}

	public Order findById(int id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order is not found for order id = " + id));
		return order;
	}

	public List<Order> findAllByUserEmail(String email) {
		List<Order> orderList = orderRepository.findAllByUserEmail(email);
//		List<OrderDTO> orderDtoList = orderList.stream().map(order -> order.convertToOrderDto())
//				.collect(Collectors.toList());
		return orderList;
	}
}
