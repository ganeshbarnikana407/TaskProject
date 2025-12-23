package com.ganeshtech.taskproject.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.ganeshtech.taskproject.entity.Users;
import com.ganeshtech.taskproject.exception.UserNotFound;
import com.ganeshtech.taskproject.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UserNotFound {

		Users user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFound(String.format("User with email : %s is not found", email)));
		Set<String> roles = new HashSet<String>();
		roles.add("ROLE_ADMIN");
		return new User(user.getEmail(), user.getPassword(), userAuthorities(roles));
	}

	private Collection<? extends GrantedAuthority> userAuthorities(Set<String> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}

}
