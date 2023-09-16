package com.catoritech.service.impl;

import com.catoritech.entity.User;
import com.catoritech.entity.requests.UserRequest;
import com.catoritech.exceptions.UserAlreadyExistException;
import com.catoritech.repository.UserRepository;
import com.catoritech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Long registerUser(UserRequest userRequest) {
		User user = new User();
		try {
			user.setUsername(userRequest.getUsername());
			user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
			user.setUserRole(userRequest.getUserRole());
			user = userRepository.save(user);
		}catch (DataIntegrityViolationException e) {
			throw new UserAlreadyExistException("User with that username alredy exists");
		}
		return user.getId();
	}
}
