package com.catoritech.exceptions;

public class NoRowsUpdatedException extends RuntimeException{
	public NoRowsUpdatedException(String message) {
		super(message);
	}
}
