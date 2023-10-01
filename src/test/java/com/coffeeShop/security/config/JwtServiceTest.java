package com.coffeeShop.security.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coffeeShop.model.Role;
import com.coffeeShop.model.User;
import com.coffeeShop.repository.UserRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class JwtServiceTest {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Test
//	public void generateTokenAndIsTokenValidTest() {
//		User user1 = User.builder().firstname("Mark").lastname("Botros").email("test@test.com")
//				.password(passwordEncoder.encode("testing")).role(Role.USER).build();
//
//		User user2 = User.builder().firstname("test").lastname("test").email("test@test.com11")
//				.password(passwordEncoder.encode("testing")).role(Role.USER).build();
//
//		repository.save(user1);
//		repository.save(user2);
//
//		String jwtToken1 = jwtService.generateToken(user1);
//		String jwtToken2 = jwtService.generateToken(user2);
//
//		assertEquals(true, jwtService.isTokenValid(jwtToken1, user1));
//		assertEquals(false, jwtService.isTokenValid(jwtToken1, user2));
//		assertEquals(true, jwtService.isTokenValid(jwtToken2, user2));
//		assertEquals(false, jwtService.isTokenValid(jwtToken2, user1));
//	}

	@Test
	public void extractUsernameTest() {
		User user1 = User.builder().firstname("Mark").lastname("Botros").email("test@test.com")
				.password(passwordEncoder.encode("testing")).role(Role.USER).build();

		repository.save(user1);

		String jwtToken1 = jwtService.generateToken(user1);

		assertEquals(user1.getEmail(), jwtService.extractUsername(jwtToken1));
	}

//	@Test
//	@DirtiesContext
//	public void isTokenExpiredTest() throws InterruptedException {
//		User user1 = User.builder().firstname("Mark").lastname("Botros").email("test@test.com")
//				.password(passwordEncoder.encode("testing")).role(Role.USER).build();
//		repository.save(user1);
//		String jwtToken1 = jwtService.generateToken(user1);
//		assertEquals(true, jwtService.isTokenValid(jwtToken1, user1));
//
//		TimeUnit.SECONDS.sleep(1500);
//		assertEquals(false, jwtService.isTokenValid(jwtToken1, user1));
//	}
}
