package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDaoImpl;
import util.DBUtils;

/**
 * Servlet implementation class CheckDuplicateServlet
 * 
 * <p>
 * This servlet checks for duplicate entries in the database for email and
 * contact number.
 * </p>
 */
@WebServlet("/checkDuplicateServlet")
public class CheckDuplicateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to check duplicate email or contact number.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String contactNo = request.getParameter("contactNo");
		EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

		try {
			try (Connection connection = DBUtils.getConnection()) {
				boolean isDuplicateEmail = employeeDao.isDuplicateEmail(connection, email);
				boolean isDuplicateContactNo = employeeDao.isDuplicateMobileNumber(connection, contactNo);

				if (isDuplicateEmail || isDuplicateContactNo) {
					response.setContentType("text/plain");
					response.getWriter().write("true");
				} else {
					response.setContentType("text/plain");
					response.getWriter().write("false");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setContentType("text/plain");
			response.getWriter().write("error");
		}
	}

}
