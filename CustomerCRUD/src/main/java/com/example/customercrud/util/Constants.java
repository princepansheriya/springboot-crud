package com.example.customercrud.util;

import lombok.experimental.FieldDefaults;

/**
 * Utility class containing constants used throughout the application. Provides
 * standardized keys and messages for responses, validation, and errors.
 */
@FieldDefaults(makeFinal = true)
public class Constants {

	public static String MESSAGE_KEY = "message";
	public static String STATUS_KEY = "status";
	public static String ERRORS_KEY = "errors";
	public static String DATA_KEY = "data";
	public static String AN_ERROR_OCCURRED = "An error occurred: ";
	public static String CUSTOMER_RETRIEVED_SUCCESSFULLY = "Customer retrieved successfully";
	public static String CUSTOMER_NOT_FOUND_WITH_ID = "Customer not found with ID: ";
	public static String CUSTOMER_SAVED_UPDATED_SUCCESSFULLY = "Customer saved/updated successfully";
	public static String CUSTOMER_DELETED_SUCCESSFULLY = "Customer deleted successfully";
	public static String CUSTOMER_UPDATED_SUCCESSFULLY = "Customer updated successfully";
	public static String PLEASE_ENTER_VALID_DATE = "Please enter a valid date";
	public static String PLEASE_ENTER_VALID_GENDER = "Please enter a valid gender";
	public static String VALIDATION_FAILED = "Validation failed";
	public static String EMAIL_ALREADY_IN_USE = "Email is already in use by another customer";
	public static String MOBILE_NUMBER_ALREADY_IN_USE = "Mobile number is already in use by another customer";

}