package com.catoritech.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
	UserDetails loadUserByUsername(String username);
}
