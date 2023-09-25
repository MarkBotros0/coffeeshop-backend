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

import com.coffeeShop.model.Goodie;
import com.coffeeShop.service.GoodiesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/goodies")
@CrossOrigin(origins = "http://localhost:3000")
@ComponentScan("com.coffeeShop")
@RequiredArgsConstructor
public class GoodiesController {
	private final GoodiesService goodiesService;

	@GetMapping
	private List<Goodie> findAll() {
		return goodiesService.findAll();
	}

	@PostMapping
	public Goodie addGoodies(@RequestBody Goodie goodie) {
		return goodiesService.save(goodie);
	}

	@GetMapping("/{id}")
	private ResponseEntity<Goodie> findById(@PathVariable("id") int id) {
		return ResponseEntity.ok(goodiesService.findById(id));
	}

}
