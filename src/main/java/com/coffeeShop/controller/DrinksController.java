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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coffeeShop.model.Drink;
import com.coffeeShop.service.DrinksService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/drinks")
@CrossOrigin(origins = "http://localhost:3000")
@ComponentScan("com.coffeeShop")
@RequiredArgsConstructor
public class DrinksController {
	private final DrinksService drinksService;

	@GetMapping
	private List<Drink> findAll() {
		return drinksService.findAll();
	}

	@PostMapping
	private Drink addDrink(@RequestBody Drink drink) {
		return drinksService.save(drink);
	}

	@GetMapping("/{id}")
	private ResponseEntity<Drink> findById(@PathVariable("id") int id) {
			return  ResponseEntity.ok(drinksService.findById(id));
	
	}

	@GetMapping("/drink")
	private ResponseEntity<Drink> findByName(@RequestParam("name") String name) {
			return ResponseEntity.ok(drinksService.findByName(name));
	}
}
