package com.coffeeShop.response.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserEmailAlreadyFoundResponse extends ErrorResponse {

	public UserEmailAlreadyFoundResponse(int status, String message, long timeStamp) {
		super(status, message, timeStamp);
	}
	
}
