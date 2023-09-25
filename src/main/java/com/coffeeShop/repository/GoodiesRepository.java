package com.coffeeShop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeeShop.model.Goodie;

public interface GoodiesRepository extends JpaRepository<Goodie, Integer> {

	Optional<Goodie> findByName(String name);

}
