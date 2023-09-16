package com.catoritech.entity;

import com.catoritech.entity.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(name = "user_role", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole contactRole;

	public User() {
	}

	public User(Long id, String username, String password, UserRole contactRole) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.contactRole = contactRole;
	}

	public User(String username, String password, UserRole contactRole) {
		this.username = username;
		this.password = password;
		this.contactRole = contactRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public UserRole getContactRole() {
		return contactRole;
	}

	public void setContactRole(UserRole contactRole) {
		this.contactRole = contactRole;
	}
}
