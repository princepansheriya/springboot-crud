package com.example.customercrud.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.customercrud.customexception.ResponseHandler;
import com.example.customercrud.dto.CustomerDto;
import com.example.customercrud.entity.CustomerEntity;
import com.example.customercrud.services.CustomerService;
import com.example.customercrud.util.Constants;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * `CustomerRestController` is a Spring REST controller managing
 * customer-related HTTP requests. It utilizes the base URI "/api/customers" for
 * mapping various endpoints.
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerRestController {

	CustomerService customerService;

	/**
	 * GET /api/customers Retrieve a list of all customers.
	 *
	 * @return List of Customer objects.
	 */
	@GetMapping
	public List<CustomerEntity> getAllCustomer() {
		return customerService.getAllCustomer();
	}

	/**
	 * GET /api/customers/{id} Retrieve a specific customer by ID.
	 *
	 * @param id The ID of the customer to retrieve.
	 * @return Customer object with the specified ID.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
		try {
			CustomerEntity customer = customerService.getCustomerById(id);
			if (customer != null) {
				return ResponseHandler.generateResponse(Constants.CUSTOMER_RETRIEVED_SUCCESSFULLY, HttpStatus.OK,
						customer);
			} else {
				return ResponseHandler.generateResponse(Constants.CUSTOMER_NOT_FOUND_WITH_ID + id, HttpStatus.NOT_FOUND,
						null);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}

	/**
	 * POST /api/customers Create or update a customer.
	 *
	 * @param customer Customer object to be created or updated.
	 * @return Created or updated Customer object.
	 */
	@PostMapping
	public ResponseEntity<Object> saveOrUpdateCustomer(@RequestBody @Valid CustomerDto customerDto) {
		CustomerEntity savedCustomer = customerService.saveOrUpdateCustomer(customerDto);
		return ResponseHandler.generateResponse(Constants.CUSTOMER_SAVED_UPDATED_SUCCESSFULLY, HttpStatus.CREATED,
				savedCustomer);
	}

	/**
	 * DELETE /api/customers/{id} Delete a customer by ID.
	 *
	 * @param id The ID of the customer to be deleted.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
		CustomerEntity existingCustomer = customerService.getCustomerById(id);
		if (existingCustomer != null) {
			customerService.deleteCustomer(id);
			return ResponseHandler.generateResponse(Constants.CUSTOMER_DELETED_SUCCESSFULLY, HttpStatus.OK, null);
		} else {
			return ResponseHandler.generateErrorResponse(Constants.CUSTOMER_NOT_FOUND_WITH_ID + id,
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * GET /api/customers/checkDuplicateEmail Check for duplicate email addresses.
	 *
	 * @param email      The email address check for duplicates.
	 * @param customerId The ID of the customer (optional for updates).
	 * @return True if the email is a duplicate, false otherwise.
	 */
	@GetMapping("/checkDuplicateEmail")
	public boolean checkDuplicateEmail(@RequestParam String email, @RequestParam(required = false) Long customerId) {
		return customerService.existsByEmailAndNotId(email, customerId);
	}

	/**
	 * GET /api/customers/checkDuplicateContact Check for duplicate mobile contacts.
	 *
	 * @param mobile     The mobile contact to check for duplicates.
	 * @param customerId The ID of the customer (optional for updates).
	 * @return True if the mobile contact is a duplicate, false otherwise.
	 */
	@GetMapping("/checkDuplicateContact")
	public boolean checkDuplicateContact(@RequestParam String mobile, @RequestParam(required = false) Long customerId) {
		return customerService.existsByMobileAndNotId(mobile, customerId);
	}

	/**
	 * PUT /api/customers/{id} - Update an existing customer by ID.
	 *
	 * @param id       The ID of the customer to be updated.
	 * @param customer The updated customer information.
	 * @return ResponseEntity with a success message or error message.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerDto customerDto) {
		CustomerEntity existingCustomer = customerService.getCustomerById(id);
		if (existingCustomer != null) {
			customerDto.setId(id);
			CustomerEntity updatedCustomer = customerService.saveOrUpdateCustomer(customerDto);
			return ResponseHandler.generateResponse(Constants.CUSTOMER_UPDATED_SUCCESSFULLY, HttpStatus.OK,
					updatedCustomer);
		} else {
			return ResponseHandler.generateErrorResponse(Constants.CUSTOMER_NOT_FOUND_WITH_ID + id,
					HttpStatus.NOT_FOUND);
		}
	}

}