package com.example.customercrud.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
 * Annotations: * - @Data: Lombok annotation to automatically generate getter,
 * setter, equals, hashCode, and toString methods. - @NoArgsConstructor: Lombok
 * annotation to generate a default constructor. - @AllArgsConstructor: Lombok
 * annotation to generate a constructor with all fields. - @Entity: JPA
 * annotation indicating that this class is a JPA entity and will be mapped to a
 * database table.
 *
 * Usage: This class is used to store and retrieve customer information from the
 * database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Customer")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Gender is required")
	Gender gender;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	String email;

}