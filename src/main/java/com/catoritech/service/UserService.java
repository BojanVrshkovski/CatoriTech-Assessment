package com.catoritech.service;

import com.catoritech.entity.requests.UserRequest;

public interface UserService {
	Long registerUser(UserRequest userRequest);
}
