package com.catoritech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "businesses")
public class Business {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "business_name", nullable = false)
	private String businessName;

	public Business() {
	}

	public Business(Long id, String businessName) {
		this.id = id;
		this.businessName = businessName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
}
