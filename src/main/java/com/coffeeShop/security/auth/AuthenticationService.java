package com.coffeeShop.security.auth;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coffeeShop.exception.UserEmailAlreadyFoundException;
import com.coffeeShop.model.JwtBlacklist;
import com.coffeeShop.model.Role;
import com.coffeeShop.model.User;
import com.coffeeShop.repository.JwtBlacklistRepository;
import com.coffeeShop.repository.UserRepository;
import com.coffeeShop.security.config.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final JwtBlacklistRepository jwtBlacklistRepository;

	public AuthenticationResponse register(RegisterRequest request) {

		Optional<User> userOptional = repository.findByEmail(request.getEmail());

		if (userOptional.isEmpty()) {
			var user = User.builder().firstname(request.getFirstName()).lastname(request.getLastName())
					.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER)
					.build();
			repository.save(user);
			var jwtToken = jwtService.generateToken(user);
			return AuthenticationResponse.builder().token(jwtToken).user(user.convertToUserDto()).build();
		} else {
			throw new UserEmailAlreadyFoundException("User Email : " + request.getEmail() + " already exists");
		}
	}

	public void logout(String authHeader) {
		String jwt = authHeader.substring(7);
		JwtBlacklist jwtBlacklist = new JwtBlacklist(jwt);
		jwtBlacklistRepository.save(jwtBlacklist);
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = repository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).user(user.convertToUserDto()).build();
	}
}
