package com.catoritech.service.impl;

import com.catoritech.entity.User;
import com.catoritech.entity.requests.UserRequest;
import com.catoritech.exceptions.UserAlreadyExistException;
import com.catoritech.repository.UserRepository;
import com.catoritech.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.catoritech.constants.LoggerAndExceptionConstants.ALREADY_EXIST_USER_DB_MESSAGE;
import static com.catoritech.constants.LoggerAndExceptionConstants.SUCCESSFULLY_REGISTERED_USER_MESSAGE;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
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
			log.info(String.format(SUCCESSFULLY_REGISTERED_USER_MESSAGE,userRequest.getUsername()));
		}catch (DataIntegrityViolationException e) {
			log.error(String.format(ALREADY_EXIST_USER_DB_MESSAGE,user.getUsername()));
			throw new UserAlreadyExistException(String.format(ALREADY_EXIST_USER_DB_MESSAGE,user.getUsername()));
		}
		return user.getId();
	}
}
