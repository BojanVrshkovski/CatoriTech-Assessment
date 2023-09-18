package com.catoritech.exceptions;

public class ContactForUserNotFoundException extends RuntimeException{
	public ContactForUserNotFoundException(String message) {
		super(message);
	}
}
