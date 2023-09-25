package com.coffeeShop.security.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coffeeShop.exception.UserEmailAlreadyFoundException;
import com.coffeeShop.model.JwtBlacklist;
import com.coffeeShop.model.Role;
import com.coffeeShop.model.User;
import com.coffeeShop.repository.JwtBlacklistRepository;
import com.coffeeShop.repository.UserRepository;
import com.coffeeShop.security.config.JwtService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AuthenticationServiceTest {
	@Autowired
	private AuthenticationService service;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private JwtBlacklistRepository jwtBlacklistRepository;

	@BeforeAll
	public void setUpBeforeAllTests() {
		userRepository.deleteAll();
	}

	@Test
	@Order(1)
	public void registerTest() {
		RegisterRequest request = RegisterRequest.builder().firstName("mark").lastName("botros").email("test@test.com")
				.password("testing").role(Role.USER).build();
		service.register(request);

		Optional<User> userOptional = userRepository.findByEmail("test@test.com");

		assertEquals(true, userOptional.isPresent());
		assertEquals("mark", userOptional.get().getFirstname());
		assertEquals("botros", userOptional.get().getLastname());
		assertEquals(false, userOptional.get().getPassword() == "testing");// decoded
		
		assertThrows(UserEmailAlreadyFoundException.class, () -> service.register(request));
	}

	@Test
	@Order(2)
	public void authenticateAndLogoutTest() {
		AuthenticationRequest request = AuthenticationRequest.builder().email("test@test.com").password("testing")
				.build();

		AuthenticationResponse authResponse = service.authenticate(request);
		Optional<User> userOptional = userRepository.findByEmail("test@test.com");

		assertEquals(true, userOptional.isPresent());
		assertEquals(authResponse.getUser(), userOptional.get().convertToUserDto());
		assertEquals("test@test.com", jwtService.extractUsername(authResponse.getToken()));
		assertEquals(true, jwtService.isTokenValid(authResponse.getToken(), userOptional.get()));

		service.logout("Bearer " + authResponse.getToken());

		Optional<JwtBlacklist> jwtBlacklist = jwtBlacklistRepository.findJwtBlacklistByToken(authResponse.getToken());

		assertEquals(true, jwtBlacklist.isPresent());
		assertEquals(false, jwtService.isTokenValid(authResponse.getToken(), userOptional.get()));
	}

}
