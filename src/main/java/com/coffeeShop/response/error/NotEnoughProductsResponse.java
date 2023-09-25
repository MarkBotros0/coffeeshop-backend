package com.coffeeShop.response.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotEnoughProductsResponse extends ErrorResponse {
	
	public NotEnoughProductsResponse(int status, String message, long timeStamp) {
		super(status, message, timeStamp);
	}
}
