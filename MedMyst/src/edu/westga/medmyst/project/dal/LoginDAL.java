package edu.westga.medmyst.project.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.medmyst.project.model.Login;

/**
 * DAL for accessing login data from nurse and administrator tables.
 * 
 * @author tl00162
 */
public class LoginDAL {

	/**
	 * Checks if the provided username and password combination exists in either the
	 * nurseaccount or administratoraccount table, and retrieves the user's first
	 * and last name.
	 * 
	 * @param username the username to check
	 * @param password the password to check
	 * @return a Login object if the login is valid, null otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public Login checkValidLogin(String username, String password) throws SQLException {
		String nurseQuery = "SELECT n.f_name, n.l_name FROM nurseaccount na "
				+ "JOIN nurse n ON na.user_id = n.nurse_id WHERE na.user_id = ? AND na.password = ?";
		String adminQuery = "SELECT a.f_name, a.l_name FROM administratoraccount aa "
				+ "JOIN administrator a ON aa.user_id = a.admin_id WHERE aa.user_id = ? AND aa.password = ?";

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING)) {

			try (PreparedStatement nurseStmt = connection.prepareStatement(nurseQuery)) {
				nurseStmt.setString(1, username);
				nurseStmt.setString(2, password);

				ResultSet nurseRs = nurseStmt.executeQuery();
				if (nurseRs.next()) {
					String firstName = nurseRs.getString("f_name");
					String lastName = nurseRs.getString("l_name");
					return new Login(username, "nurse", firstName, lastName);
				}
			}
			try (PreparedStatement adminStmt = connection.prepareStatement(adminQuery)) {
				adminStmt.setString(1, username);
				adminStmt.setString(2, password);

				ResultSet adminRs = adminStmt.executeQuery();
				if (adminRs.next()) {
					String firstName = adminRs.getString("f_name");
					String lastName = adminRs.getString("l_name");
					return new Login(username, "admin", firstName, lastName);
				}
			}
		}

		return null;
	}
}
