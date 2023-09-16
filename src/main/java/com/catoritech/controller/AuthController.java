package com.catoritech.controller;

import com.catoritech.entity.requests.UserRequest;
import com.catoritech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
public class AuthController {
	private final UserService userService;

	@Autowired
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	@PreAuthorize("hasAuthority('BUSINESS')")
	public ResponseEntity<Void> registerUser(@RequestBody UserRequest userRequest) {
		Long id = userService.registerUser(userRequest);

		URI location = UriComponentsBuilder.fromUriString("/user/{id}")
		                                   .buildAndExpand(id)
		                                   .toUri();

		return ResponseEntity.created(location).build();
	}
}
