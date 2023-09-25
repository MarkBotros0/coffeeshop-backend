package com.coffeeShop.response.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductNotFoundResponse extends ErrorResponse{
	
	public ProductNotFoundResponse(int status, String message, long timeStamp) {
		super(status, message, timeStamp);
	}
}
