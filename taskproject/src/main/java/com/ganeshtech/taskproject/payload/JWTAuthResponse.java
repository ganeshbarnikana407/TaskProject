package com.ganeshtech.taskproject.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JWTAuthResponse {

	private String token;
	private String tokenType = "Bearer";

	public JWTAuthResponse(String token) {
		this.token = token;
	}

}
