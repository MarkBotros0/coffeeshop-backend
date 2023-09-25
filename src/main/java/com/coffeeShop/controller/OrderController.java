package com.coffeeShop.controller;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coffeeShop.DTO.OrderRequestDTO;
import com.coffeeShop.model.Order;
import com.coffeeShop.model.User;
import com.coffeeShop.repository.UserRepository;
import com.coffeeShop.security.config.JwtService;
import com.coffeeShop.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@ComponentScan("com.coffeeShop")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	private final JwtService jwtService;
	private final UserRepository userRepository;

	@GetMapping("/all")
	public ResponseEntity<List<Order>> findAll() {
		return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Order>> findAllByUserEmail(@RequestHeader("Authorization") String authHeader) {
		String jwt = authHeader.substring(7);
		String userEmail = jwtService.extractUsername(jwt);
		return new ResponseEntity<>(orderService.findAllByUserEmail(userEmail), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Order> save(@RequestBody OrderRequestDTO orderDTO,
			@RequestHeader("Authorization") String authHeader) {
	
			String jwt = authHeader.substring(7);
			String userEmail = jwtService.extractUsername(jwt);
			User user = userRepository.findByEmail(userEmail).get();

			Order order = orderService.changeMapToOrder(orderDTO);
			order.setUser(user);

			return ResponseEntity.ok(orderService.save(order));
	
	}

	@DeleteMapping
	private Boolean deleteOrder(int id) {
		return orderService.deleteOrderById(id);
	}

	@GetMapping("/{id}")
	private ResponseEntity<Order> findOrderById(@PathVariable("id") int id) {
		return ResponseEntity.ok(orderService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Order> createOrUpdate(@PathVariable("id") int id, @RequestBody Order order) {
		return ResponseEntity.ok(orderService.updateOrder(id, order));

	}

}
