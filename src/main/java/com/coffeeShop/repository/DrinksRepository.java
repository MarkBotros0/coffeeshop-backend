package com.coffeeShop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeeShop.model.Drink;

public interface DrinksRepository extends JpaRepository<Drink, Integer>{

	Optional<Drink> findByName(String name);

}
