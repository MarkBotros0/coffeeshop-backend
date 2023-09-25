package com.coffeeShop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeeShop.model.CoffeeBean;

public interface CoffeeBeansRepository extends JpaRepository<CoffeeBean, Integer>{

	Optional<CoffeeBean> findByName(String name);

}
