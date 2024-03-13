package com.example.customercrud.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.customercrud.customexception.CustomValidationException;
import com.example.customercrud.dto.CustomerDto;
import com.example.customercrud.entity.CustomerEntity;
import com.example.customercrud.repositories.CustomerRepository;
import com.example.customercrud.services.CustomerService;
import com.example.customercrud.util.Constants;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Implementation of the {@code CustomerService} interface for handling
 * customer-related operations.
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {

	CustomerRepository customerRepository;

	/**
	 * Retrieves a list of all customers from the database.
	 *
	 * @return A list of Customer entities representing all customers in the
	 *         database.
	 */
	@Override
	public List<CustomerEntity> getAllCustomer() {
		return customerRepository.findAll();
	}

	/**
	 * Retrieves a specific customer by their unique identifier.
	 *
	 * @param id The unique identifier of the customer to retrieve.
	 * @return A Customer entity representing the customer with the given ID, or
	 *         null if no customer with the provided ID exists.
	 */
	@Override
	public CustomerEntity getCustomerById(Long id) {
		return customerRepository.findById(id).orElse(null);
	}

	/**
	 * Saves or updates a customer based on the provided Customer entity. If the
	 * customer ID is provided and exists, the method updates the existing customer.
	 * If the customer ID is not provided or does not exist, the method creates a
	 * new customer.
	 *
	 * @param customer The Customer entity to be saved or updated.
	 * @return The saved or updated Customer entity.
	 * @throws RuntimeException If there is an attempt to update a non-existing
	 *                          customer or if the provided email or mobile number
	 *                          is already in use by another customer.
	 */
	@Override
	public CustomerEntity saveOrUpdateCustomer(CustomerDto customerDto) {
		ModelMapper modelMapper = new ModelMapper();
		CustomerEntity customerEntity = modelMapper.map(customerDto, CustomerEntity.class);

		validateEmailAndMobileUniqueness(customerEntity);
		if (customerEntity.getId() != null && customerEntity.getId() > 0) {
			return updateExistingCustomer(customerEntity);
		} else {
			return createNewCustomer(customerEntity);
		}
	}

	/**
	 * Saves or updates a customer based on the provided Customer entity. If the
	 * customer ID is provided and exists, the method updates the existing customer.
	 * If the customer ID is not provided or does not exist, the method creates a
	 * new customer.
	 *
	 * @param customer The Customer entity to be saved or updated.
	 * @return The saved or updated Customer entity.
	 * @throws RuntimeException If there is an attempt to update a non-existing
	 *                          customer or if the provided email or mobile number
	 *                          is already in use by another customer.
	 */
	private void validateEmailAndMobileUniqueness(CustomerEntity customer) {
		Long id = customer.getId();
		if (id == null) {
			id = 0L;
		}
		boolean emailExists = customerRepository.existsByEmailAndNotId(customer.getEmail(), id);
		boolean mobileExists = customerRepository.existsByIdIsNotAndMobile(id, customer.getMobile());

		if (emailExists) {
			throw new CustomValidationException(customer.getEmail() + " " + Constants.EMAIL_ALREADY_IN_USE);
		}
		if (mobileExists) {
			throw new CustomValidationException(customer.getMobile() + " " + Constants.MOBILE_NUMBER_ALREADY_IN_USE);
		}
	}

	/**
	 * Updates an existing customer with the provided customer details.
	 *
	 * @param customer The new Customer entity with updated details.
	 * @return The updated Customer entity.
	 * @throws IllegalArgumentException If the customer with the provided ID is not
	 *                                  found.
	 */
	private CustomerEntity updateExistingCustomer(CustomerEntity customer) {
		CustomerEntity existingCustomer = customerRepository.findById(customer.getId()).orElseThrow(
				() -> new CustomValidationException(Constants.CUSTOMER_NOT_FOUND_WITH_ID + customer.getId()));
		updateCustomerDetails(existingCustomer, customer);
		return customerRepository.save(existingCustomer);
	}

	/**
	 * Updates the details of an existing customer with the details from a new
	 * customer.
	 *
	 * @param existingCustomer The existing Customer entity to be updated.
	 * @param newCustomer      The new Customer entity with updated details.
	 */
	private void updateCustomerDetails(CustomerEntity existingCustomer, CustomerEntity newCustomer) {
		existingCustomer.setFirstName(newCustomer.getFirstName());
		existingCustomer.setLastName(newCustomer.getLastName());
		existingCustomer.setBirthDate(newCustomer.getBirthDate());
		existingCustomer.setMobile(newCustomer.getMobile());
		existingCustomer.setAddress1(newCustomer.getAddress1());
		existingCustomer.setAddress2(newCustomer.getAddress2());
		existingCustomer.setGender(newCustomer.getGender());
		existingCustomer.setEmail(newCustomer.getEmail());
	}

	/**
	 * Creates a new customer by saving the provided Customer entity to the
	 * database.
	 *
	 * @param customer The new Customer entity to be created.
	 * @return The created Customer entity.
	 */
	private CustomerEntity createNewCustomer(CustomerEntity customer) {
		return customerRepository.save(customer);
	}

	/**
	 * Deletes a customer from the database based on the provided customer ID.
	 *
	 * @param id The unique identifier of the customer to be deleted.
	 */
	@Override
	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}

	/**
	 * Checks if an email already exists for a customer with a different ID.
	 *
	 * @param email      The email to be checked.
	 * @param customerId The unique identifier of the customer to be excluded from
	 *                   the check.
	 * @return true if the email exists for a customer with a different ID, false
	 *         otherwise.
	 */
	@Override
	public boolean existsByEmailAndNotId(String email, Long customerId) {
		return customerRepository.existsByEmailAndNotId(email, customerId);
	}

	/**
	 * Checks if a mobile number already exists for a customer with a different ID.
	 *
	 * @param mobile     The mobile number to be checked.
	 * @param customerId The unique identifier of the customer to be excluded from
	 *                   the check.
	 * @return true if the mobile number exists for a customer with a different ID,
	 *         false otherwise.
	 */
	@Override
	public boolean existsByMobileAndNotId(String mobile, Long customerId) {

		if (customerId == null) {
			customerId = (long) 0;
		}

		return customerRepository.existsByIdIsNotAndMobile(customerId, mobile);
	}

}