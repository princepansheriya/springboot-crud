package com.example.customercrud.customexception;

import java.io.Serializable;

/**
 * Exception class for custom validation errors, extending RuntimeException.
 */
public class CustomValidationException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a custom validation exception with the specified message.
	 */
	public CustomValidationException(String message) {
		super(message);
	}

}