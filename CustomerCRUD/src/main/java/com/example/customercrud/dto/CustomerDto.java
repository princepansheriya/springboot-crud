package com.example.customercrud.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.customercrud.entity.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * DTO for customer information transfer between client and server. Includes
 * name,birth date,mobile,address,gender,and email.Utilizes Lombok for
 * getter/setter generation and validation annotations for data correctness.
 * Commonly employed in Spring MVC controllers for handling customer data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDto {

	Long id;

	@NotBlank(message = "FirstName is required")
	String firstName;

	@NotBlank(message = "LastName is required")
	String lastName;

	@Past(message = "Birth date must be in the past")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull(message = "Birth date is required.")
	LocalDate birthDate;

	@Size(min = 10, max = 10, message = "Mobile number must have exactly 10 digits")
	@Pattern(regexp = "\\d+", message = "Mobile number must contain only digits")
	@NotBlank(message = "Mobile number required")
	String mobile;

	@NotBlank(message = "Address1 is required")
	String address1;

	String address2;

	@NotNull(message = "Gender is required")
	Gender gender;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	String email;

}