package dao;

import java.sql.Connection;
import java.sql.SQLException;

import model.Employee;

/**
 * The {EmployeeDao} interface provides methods for managing employee data in
 * the database. It includes operations to save employee information and check
 * for duplicate entries based on mobile numbers and email addresses.
 *
 * Implementing classes should handle the underlying database interactions.
 *
 * @author Prince
 * @version 1.0
 */
public interface EmployeeDao {

	/**
	 * Saves the employee information to the database.
	 *
	 * @param employee The employee object containing the details to be saved.
	 */
	void saveEmployee(Employee employee);

	/**
	 * Checks if there is a duplicate entry with the given mobile number in the
	 * database.
	 *
	 * @param connection The database connection.
	 * @param contactNo  The mobile number to check for duplication.
	 * @return {@code true} if a duplicate entry exists, otherwise {@code false}.
	 * @throws SQLException If a database access error occurs.
	 */
	boolean isDuplicateMobileNumber(Connection connection, String contactNo) throws SQLException;

	/**
	 * Checks if there is a duplicate entry with the given email address in the
	 * database.
	 *
	 * @param connection The database connection.
	 * @param email      The email address to check for duplication.
	 * @return {@code true} if a duplicate entry exists, otherwise {@code false}.
	 * @throws SQLException If a database access error occurs.
	 */
	boolean isDuplicateEmail(Connection connection, String email) throws SQLException;

}
