package com.catoritech.exceptions;

public class EmptyContactListException extends RuntimeException{
	public EmptyContactListException(String message) {
		super(message);
	}
}
