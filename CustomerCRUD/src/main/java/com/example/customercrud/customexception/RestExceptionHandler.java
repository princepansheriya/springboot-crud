package com.example.customercrud.customexception;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.customercrud.entity.Gender;
import com.example.customercrud.util.Constants;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

/**
 * Controller advice class for RESTful services, handling exceptions globally
 * and providing consistent error responses throughout the application.
 */
@ControllerAdvice
public class RestExceptionHandler {

	/**
	 * Handles validation exceptions globally, creating a ResponseEntity with a
	 * standardized structure for validation error responses, including a message,
	 * HTTP status, and field-specific error details.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		Map<String, Object> response = new HashMap<>();
		response.put(Constants.MESSAGE_KEY, Constants.VALIDATION_FAILED);
		response.put(Constants.STATUS_KEY, HttpStatus.BAD_REQUEST.value());
		response.put(Constants.ERRORS_KEY, errors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles exceptions related to date parsing globally, constructing a
	 * ResponseEntity with a standardized structure for date parse error responses,
	 * including a message, HTTP status, and specific error details.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<Object> handleDateParseException(DateTimeParseException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put(Constants.MESSAGE_KEY, Constants.PLEASE_ENTER_VALID_DATE);
		response.put(Constants.STATUS_KEY, HttpStatus.BAD_REQUEST.value());
		response.put(Constants.ERRORS_KEY, ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles custom validation exceptions globally, generating a ResponseEntity
	 * with a standardized structure for custom validation error responses,
	 * including an error message and HTTP status.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(CustomValidationException.class)
	public ResponseEntity<Object> handleCustomValidationException(CustomValidationException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put(Constants.MESSAGE_KEY, ex.getMessage());
		response.put(Constants.STATUS_KEY, HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles exceptions related to unreadable HTTP messages globally, offering
	 * specific error responses for invalid gender and date fields. Provides a
	 * general error response for other cases, including a message and HTTP status.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		Throwable cause = ex.getCause();

		if (cause instanceof MismatchedInputException mismatchedInputException) {
			// Handle Gender field
			if (mismatchedInputException.getTargetType().equals(Gender.class)) {
				return ResponseHandler.generateErrorResponse(Constants.PLEASE_ENTER_VALID_GENDER,
						HttpStatus.BAD_REQUEST);
			}

			// Handle LocalDate field
			if (mismatchedInputException.getTargetType().equals(LocalDate.class)) {
				return ResponseHandler.generateErrorResponse(Constants.PLEASE_ENTER_VALID_DATE, HttpStatus.BAD_REQUEST);
			}
		}
		return ResponseHandler.generateErrorResponse(Constants.AN_ERROR_OCCURRED + ex.getMessage(),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles general exceptions globally, generating a ResponseEntity with a
	 * standardized structure for internal server errors, including an error message
	 * and HTTP status.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return new ResponseEntity<>(Constants.AN_ERROR_OCCURRED + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}