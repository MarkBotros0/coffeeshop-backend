package com.coffeeShop.response.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderNotFoundResponse extends ErrorResponse {

	public OrderNotFoundResponse(int status, String message, long timeStamp) {
		super(status, message, timeStamp);
	}

}
