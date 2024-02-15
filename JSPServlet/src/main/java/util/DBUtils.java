package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/employeejsp";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "asdfg";

	/**
	 * Get a database connection.
	 *
	 * @return The database connection.
	 * @throws SQLException If a database access error occurs.
	 */
	public static Connection getConnection() throws SQLException {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new SQLException("JDBC Driver not found", e);
		}

	}

}
