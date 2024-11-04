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
		String query = "SELECT accounttype, f_name, l_name " + "FROM useraccount "
				+ "WHERE username = ? AND password = ?";

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, password);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					String accountType = rs.getString("accounttype");
					String firstName = rs.getString("f_name");
					String lastName = rs.getString("l_name");
					return new Login(username, accountType, firstName, lastName);
				}
			}
		}
		return null;
	}
}
