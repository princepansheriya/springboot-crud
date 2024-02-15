package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import model.Employee;
import util.DBUtils;

/**
 * Servlet implementation class EmployeeServlet
 * 
 * <p>
 * This servlet handles employee registration by processing POST requests.
 * </p>
 */
@WebServlet("/register")
public class EmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Handles POST requests to register a new employee.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Retrieve parameters from request
		String[] fields = { "firstName", "lastName", "username", "email", "password", "address", "contactNo",
				"birthdate" };
		String[] values = new String[fields.length];

		for (int i = 0; i < fields.length; i++) {
			values[i] = request.getParameter(fields[i]);
		}

		// Validate fields
		String validationError = validateFields(fields, values);
		if (validationError != null) {
			response.getWriter().write(validationError);
			return;
		}

		// All required fields are filled, proceed with saving the employee
		Employee employee = new Employee();
		setEmployeeFields(employee, fields, values);

		// Validate and format birthdate
		if (!formatAndSetBirthdate(employee, values[7], response)) {
			return;
		}

		// Database operations
		EmployeeDao employeeDao = new EmployeeDaoImpl();

		try {
			// Use DBUtils to get the connection
			try (Connection connection = DBUtils.getConnection()) {

				// Check for duplicates
				boolean isDuplicateEmail = employeeDao.isDuplicateEmail(connection, values[3]);
				boolean isDuplicateContactNo = employeeDao.isDuplicateMobileNumber(connection, values[6]);

				if (isDuplicateEmail && isDuplicateContactNo) {
					response.getWriter().write("Both Email and ContactNo are duplicate.");
				} else if (isDuplicateContactNo) {
					response.getWriter().write("The ContactNo is duplicate; please check and update.");
				} else if (isDuplicateEmail) {
					response.getWriter().write("The email address is duplicate; please check and update.");
				} else {
					// Save employee if no duplicates
					employeeDao.saveEmployee(employee);
					response.getWriter().write("success");
				}
			}

		} catch (SQLException e) {
			response.getWriter().write("Error: Database error occurred.");
		}

	}

	/**
	 * Validates the provided fields and values.
	 * 
	 * @param fields The array of field names.
	 * @param values The array of corresponding field values.
	 * @return An error message if validation fails, otherwise null.
	 */
	private String validateFields(String[] fields, String[] values) {

		for (int i = 0; i < fields.length; i++) {
			if (values[i] == null || values[i].trim().isEmpty()) {
				return "Error: Please fill in all required fields.";
			}
			if (values[i].length() > getFieldMaxLength(fields[i])) {
				return "Error: Maximum length exceeded for " + fields[i] + ".";
			}
		}

		if (!values[3].matches("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+") && !values[6].matches("\\d+")) {
			return "Error: Please enter a valid email address or Contact number.";
		}

		if (!values[3].matches("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+")) {
			return "Error: Please enter a valid email address.";
		}

		if (!values[6].matches("\\d+")) {
			return "Error: Contact number should contain only digits.";
		}
		if (values[6].length() != 10) {
			return "Error: Contact number should be exactly 10 digits.";
		}

		return null; // No validation error

	}

	/**
	 * Sets the fields of the provided Employee object based on the provided fields
	 * and values.
	 * 
	 * @param employee The Employee object to set the fields for.
	 * @param fields   The array of field names.
	 * @param values   The array of corresponding field values.
	 */
	private void setEmployeeFields(Employee employee, String[] fields, String[] values) {

		for (int i = 0; i < fields.length; i++) {
			switch (fields[i]) {
			case "firstName":
				employee.setFirstName(values[i]);
				break;
			case "lastName":
				employee.setLastName(values[i]);
				break;
			case "username":
				employee.setUsername(values[i]);
				break;
			case "email":
				employee.setEmail(values[i]);
				break;
			case "password":
				employee.setPassword(values[i]);
				break;
			case "address":
				employee.setAddress(values[i]);
				break;
			case "contactNo":
				employee.setContactNo(values[i]);
				break;
			case "birthdate":
				// Already handled separately
				break;
			default:
				// Handle unknown field
				break;
			}
		}

	}

	/**
	 * Formats and sets the birthdate of the provided Employee object.
	 * 
	 * @param employee  The Employee object to set the birthdate for.
	 * @param birthdate The birthdate string to format and set.
	 * @param response  The HttpServletResponse object for error responses.
	 * @return True if formatting and setting is successful, otherwise false.
	 */
	private boolean formatAndSetBirthdate(Employee employee, String birthdate, HttpServletResponse response) {

		SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			java.util.Date birthdateUtil = inputFormat.parse(birthdate);
			String formattedBirthdate = outputFormat.format(birthdateUtil);
			employee.setBirthdate(formattedBirthdate);
			return true;
		} catch (ParseException e) {
			try {
				response.getWriter().write("Error: Invalid birthdate format.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}

	}

	/**
	 * Returns the maximum length for a given field.
	 * 
	 * @param fieldName The name of the field.
	 * @return The maximum length for the given field.
	 */
	private int getFieldMaxLength(String fieldName) {

		switch (fieldName) {
		case "firstName":
		case "lastName":
		case "username":
			return 50;
		case "email":
			return 100;
		case "password":
			return 15;
		case "address":
			return 255;
		case "contactNo":
			return 10;
		case "birthdate":
			return 10;
		default:
			return 0; // Unknown field
		}

	}

}
