package edu.westga.medmyst.project.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAL for accessing login table
 * 
 * @author Thomas Lamont
 */
public class LoginDAL {

	/**
	 * Checks if the provided username and password combination exists in the
	 * database
	 * 
	 * @param username the username to check
	 * @param password the password to check
	 * @return true if the login is valid, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	public boolean checkValidLogin(String username, String password) throws SQLException {
		String query = "SELECT COUNT(*) FROM login_test WHERE username = ? AND password = ?";

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, username);
			stmt.setString(2, password);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}
		}

		return false;
	}
}