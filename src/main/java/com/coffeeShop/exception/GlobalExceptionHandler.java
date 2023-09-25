package com.coffeeShop.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coffeeShop.response.error.NotEnoughProductsResponse;
import com.coffeeShop.response.error.OrderNotFoundResponse;
import com.coffeeShop.response.error.ProductNotFoundResponse;
import com.coffeeShop.response.error.UserEmailAlreadyFoundResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<OrderNotFoundResponse> handleOrderNotFoundException(OrderNotFoundException e) {

		OrderNotFoundResponse response = new OrderNotFoundResponse();

		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setMessage(e.getMessage());
		response.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ProductNotFoundResponse> handleProductNotFoundException(ProductNotFoundException e) {

		ProductNotFoundResponse response = new ProductNotFoundResponse();

		response.setStatus(HttpStatus.BAD_GATEWAY.value());
		response.setMessage(e.getMessage());
		response.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotEnoughProductsException.class)
	public ResponseEntity<NotEnoughProductsResponse> handleNotEnoughProductsException(NotEnoughProductsException e) {

		NotEnoughProductsResponse response = new NotEnoughProductsResponse();

		response.setStatus(HttpStatus.BAD_GATEWAY.value());
		response.setMessage(e.getMessage());
		response.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserEmailAlreadyFoundException.class)
	public ResponseEntity<UserEmailAlreadyFoundResponse> handleUserEmailAlreadyExistsException(
			UserEmailAlreadyFoundException e) {

		UserEmailAlreadyFoundResponse response = new UserEmailAlreadyFoundResponse();

		response.setStatus(HttpStatus.CONFLICT.value());
		response.setMessage(e.getMessage());
		response.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Illegal Arguments");
		body.put("status", HttpStatus.NOT_ACCEPTABLE.value());

		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}
}
