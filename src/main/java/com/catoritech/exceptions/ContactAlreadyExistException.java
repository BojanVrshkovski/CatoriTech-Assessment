package com.catoritech.exceptions;

public class ContactAlreadyExistException extends RuntimeException{
	public ContactAlreadyExistException(String message) {
		super(message);
	}
}
