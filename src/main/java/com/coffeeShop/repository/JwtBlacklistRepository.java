package com.coffeeShop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeeShop.model.JwtBlacklist;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, String> {
	
	public Optional<JwtBlacklist> findJwtBlacklistByToken(String token);
	
}