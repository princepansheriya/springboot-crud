package com.example.customercrud.services;

import java.util.List;

import com.example.customercrud.dto.CustomerDto;
import com.example.customercrud.entity.CustomerEntity;

/**
 * Service interface for performing operations related to Customer entities.
 */
public interface CustomerService {

	/**
	 * Get a list of all customers.
	 *
	 * @return List of Customer entities.
	 */
	List<CustomerEntity> getAllCustomer();

	/**
	 * Get a customer by their unique ID.
	 *
	 * @param id ID of the customer.
	 * @return Customer entity if found, otherwise null.
	 */
	CustomerEntity getCustomerById(Long id);

	/**
	 * Save or update a customer.
	 *
	 * @param customer Customer entity to be saved or updated.
	 * @return Saved or updated Customer entity.
	 */
	CustomerEntity saveOrUpdateCustomer(CustomerDto customerDto);

	/**
	 * Delete a customer by their unique ID.
	 *
	 * @param id ID of the customer to be deleted.
	 */
	void deleteCustomer(Long id);

	/**
	 * Check if a customer with the given email exists (excluding the specified
	 * customerId).
	 *
	 * @param email      Email to check.
	 * @param customerId ID of the customer to exclude from the check, or null if
	 *                   not specified.
	 * @return True if a customer with the email exists (excluding the specified
	 *         customerId), false otherwise.
	 */
	boolean existsByEmailAndNotId(String email, Long customerId);

	/**
	 * Check if a customer with the given mobile number exists (excluding the
	 * specified customerId).
	 *
	 * @param mobile     Mobile number to check.
	 * @param customerId ID of the customer to exclude from the check, or null if
	 *                   not specified.
	 * @return True if a customer with the mobile number exists (excluding the
	 *         specified customerId), false otherwise.
	 */
	boolean existsByMobileAndNotId(String mobile, Long customerId);

}