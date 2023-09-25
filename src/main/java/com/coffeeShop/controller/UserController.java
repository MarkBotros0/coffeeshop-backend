package com.coffeeShop.controller;

import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.coffeeShop.DTO.UserDTO;
import com.coffeeShop.model.User;
import com.coffeeShop.repository.UserRepository;
import com.coffeeShop.security.config.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
@ComponentScan("com.coffeeShop")
@RequiredArgsConstructor
public class UserController {
	private final UserRepository userRepository;
	private final JwtService jwtService;

	@GetMapping
	private ResponseEntity<UserDTO> findUser(@RequestHeader("Authorization") String authHeader) {
		String jwt = authHeader.substring(7);
		String userEmail = jwtService.extractUsername(jwt);
		Optional<User> userOptional = userRepository.findByEmail(userEmail);
		if (userOptional.isPresent()) {
			return new ResponseEntity<>(userRepository.findByEmail(userEmail).get().convertToUserDto(), HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

}
