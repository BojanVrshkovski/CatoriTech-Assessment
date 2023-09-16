package com.catoritech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@GetMapping("/public")
	public String publicGet(){
		return "Hello World";
	}

	@GetMapping("/user")
	public String publicUser(){
		return "Hello User";
	}

	@GetMapping("/business")
	public String publicBusiness(){
		return "Hello Business";
	}
}
