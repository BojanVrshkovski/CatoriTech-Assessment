package com.catoritech.exceptions;

public class BusinessCanNotAccessContactException extends RuntimeException{
	public BusinessCanNotAccessContactException(String message) {
		super(message);
	}
}
