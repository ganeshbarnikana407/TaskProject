package com.ganeshtech.taskproject.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {
	private String email;
	private String password;

}
