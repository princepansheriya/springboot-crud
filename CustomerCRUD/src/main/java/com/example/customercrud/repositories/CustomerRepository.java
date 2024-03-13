package com.example.customercrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.customercrud.entity.CustomerEntity;

/**
 * Repository interface for performing CRUD operations on Customer entities.
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

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
	boolean existsByIdIsNotAndMobile(Long id, String mobileNumber);

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
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.email = :email AND (:customerId IS NULL OR c.id <> :customerId)")
	boolean existsByEmailAndNotId(@Param("email") String email, @Param("customerId") Long customerId);

}