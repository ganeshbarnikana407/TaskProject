package com.ganeshtech.taskproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ganeshtech.taskproject.payload.JWTAuthResponse;
import com.ganeshtech.taskproject.payload.LoginDto;
import com.ganeshtech.taskproject.payload.UserDto;
import com.ganeshtech.taskproject.security.JwtTokenProvider;
import com.ganeshtech.taskproject.service.UserService;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto usersDto) {

		return new ResponseEntity<>(userService.createUser(usersDto), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> loginUser(@RequestBody LoginDto loginDto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		
		
		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		return ResponseEntity.ok(new JWTAuthResponse(token));
	}

}
