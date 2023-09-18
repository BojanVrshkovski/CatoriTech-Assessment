package com.catoritech.exceptions;

public class ContactInvalidIdException extends RuntimeException{
	public ContactInvalidIdException() {
		super("Invalid contact ID");
	}
}
