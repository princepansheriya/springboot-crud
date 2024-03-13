package com.example.customercrud.customexception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.customercrud.util.Constants;

/**
 * Utility class for managing HTTP responses, offering methods to generate
 * consistent and structured success and error responses.
 */
public class ResponseHandler {

	/**
	 * Generates a ResponseEntity with a standardized structure for successful
	 * responses, including a message, HTTP status, and associated data.
	 * 
	 * @param message
	 * @param status
	 * @param responseObj
	 * @return
	 */
	public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
		Map<String, Object> map = new HashMap<>();
		map.put(Constants.MESSAGE_KEY, message);
		map.put(Constants.STATUS_KEY, status.value());
		map.put(Constants.DATA_KEY, responseObj);
		return new ResponseEntity<>(map, status);
	}

	/**
	 * Generates a ResponseEntity with a standardized structure for error responses,
	 * including an error message, HTTP status, and additional details.
	 * 
	 * @param message
	 * @param status
	 * @return
	 */
	public static ResponseEntity<Object> generateErrorResponse(String message, HttpStatus status) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put(Constants.ERRORS_KEY, status.getReasonPhrase());
		errorResponse.put(Constants.MESSAGE_KEY, message);
		errorResponse.put(Constants.STATUS_KEY, status.value());
		return new ResponseEntity<>(errorResponse, status);
	}

}