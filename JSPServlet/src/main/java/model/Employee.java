package model;

/**
 * The {Employee} class represents an employee entity with basic information. It
 * includes details such as first name, last name, username, email, password,
 * address, contact number, and birthdate.
 *
 * This class provides a convenient way to encapsulate and manage employee data.
 *
 * @author Prince
 * @version 1.0
 */
public class Employee {

	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
	private String address;
	private String contactNo;
	private String birthdate;

	// Constructors
	public Employee() {
	}

	public Employee(String firstName, String lastName, String username, String email, String password, String address,
			String contactNo, String birthdate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.address = address;
		this.contactNo = contactNo;
		this.birthdate = birthdate;
	}

	// Getters and Setters

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

}
