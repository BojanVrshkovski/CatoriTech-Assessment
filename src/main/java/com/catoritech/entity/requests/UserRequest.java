package com.catoritech.entity.requests;

import com.catoritech.entity.enums.UserRole;

public class UserRequest {
	private String username;
	private String password;
	private UserRole userRole;
	private Long businessId;

	public UserRequest() {
	}

	public UserRequest(String username, String password, UserRole userRole) {
		this.username = username;
		this.password = password;
		this.userRole = userRole;
	}

	public UserRequest(String username, String password, UserRole userRole, Long businessId) {
		this.username = username;
		this.password = password;
		this.userRole = userRole;
		this.businessId = businessId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
}
