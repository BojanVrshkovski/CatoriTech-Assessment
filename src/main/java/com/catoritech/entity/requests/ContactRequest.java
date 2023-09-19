package com.catoritech.entity.requests;

public class ContactRequest {
	private String firstName;

	private String lastName;

	private String address;

	private String phone;

	private String VAT;

	private Long userId;
	private Long businessId;;

	public ContactRequest() {
	}

	public ContactRequest(String firstName, String lastName, String address, String phone, String VAT, Long userId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.VAT = VAT;
		this.userId = userId;
	}

	public ContactRequest(
		String firstName, String lastName, String address, String phone, String VAT, Long userId, Long businessId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.VAT = VAT;
		this.userId = userId;
		this.businessId = businessId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getVAT() {
		return VAT;
	}

	public void setVAT(String VAT) {
		this.VAT = VAT;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
