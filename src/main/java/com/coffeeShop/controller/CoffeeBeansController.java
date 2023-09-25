package com.coffeeShop.controller;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coffeeShop.model.CoffeeBean;
import com.coffeeShop.service.CoffeeBeansService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/coffeebeans")
@CrossOrigin(origins = "http://localhost:3000")
@ComponentScan("com.coffeeShop")
@RequiredArgsConstructor
public class CoffeeBeansController {
	private final CoffeeBeansService coffeeBeansService;

	@GetMapping
	private List<CoffeeBean> findAll() {
		return coffeeBeansService.findAll();
	}

	@PostMapping
	public CoffeeBean addCoffeeBeans(@RequestBody CoffeeBean bean) {
		return coffeeBeansService.save(bean);
	}

	@GetMapping("/{id}")
	private ResponseEntity<CoffeeBean> findById(@PathVariable("id") int id) {
			return ResponseEntity.ok(coffeeBeansService.findById(id));
	}
	
}
