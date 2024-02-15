package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Employee;
import util.DBUtils;

public class EmployeeDaoImpl implements EmployeeDao {

	/**
	 * Saves the employee data to the database after checking for duplicates.
	 *
	 * @param employee The employee object to be saved.
	 */
	@Override
	public void saveEmployee(Employee employee) {

		try {
			try (Connection connection = DBUtils.getConnection()) {
				// Check for duplicate entry based on mobile number and email
				if (!isDuplicateMobileNumber(connection, employee.getContactNo())
						&& !isDuplicateEmail(connection, employee.getEmail())) {

					String sql = "INSERT INTO employee (first_name, last_name, username, email, password, address, contact_no, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

					try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
						preparedStatement.setString(1, employee.getFirstName());
						preparedStatement.setString(2, employee.getLastName());
						preparedStatement.setString(3, employee.getUsername());
						preparedStatement.setString(4, employee.getEmail());
						preparedStatement.setString(5, employee.getPassword());
						preparedStatement.setString(6, employee.getAddress());
						preparedStatement.setString(7, employee.getContactNo());
						preparedStatement.setString(8, employee.getBirthdate());

						preparedStatement.executeUpdate();
					}
				} else {
					if (isDuplicateMobileNumber(connection, employee.getContactNo())
							&& isDuplicateEmail(connection, employee.getEmail())) {
						System.out.println("Both email and mobile number are duplicate.");
					} else if (isDuplicateMobileNumber(connection, employee.getContactNo())) {
						System.out.println("Duplicate entry for mobile number.");
					} else if (isDuplicateEmail(connection, employee.getEmail())) {
						System.out.println("Duplicate entry for email address.");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Checks if there is a duplicate entry with the given mobile number in the
	 * database.
	 *
	 * @param connection The database connection.
	 * @param contactNo  The mobile number to check for duplicates.
	 * @return true if a duplicate entry exists, false otherwise.
	 * @throws SQLException If a database access error occurs.
	 */
	@Override
	public boolean isDuplicateMobileNumber(Connection connection, String contactNo) throws SQLException {

		String query = "SELECT * FROM employee WHERE contact_no = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, contactNo);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		}

	}

	/**
	 * Checks if there is a duplicate entry with the given email in the database.
	 *
	 * @param connection The database connection.
	 * @param email      The email to check for duplicates.
	 * @return true if a duplicate entry exists, false otherwise.
	 * @throws SQLException If a database access error occurs.
	 */
	@Override
	public boolean isDuplicateEmail(Connection connection, String email) throws SQLException {

		String query = "SELECT * FROM employee WHERE email = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, email);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		}

	}

}
