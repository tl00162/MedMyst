package edu.westga.medmyst.project.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import edu.westga.medmyst.project.model.Login;

/**
 * DAL for accessing login data from the useraccount table.
 * 
 * @author tl00162
 */
public class LoginDAL {

    /**
     * Hashes the provided password.
     * 
     * @param password the password to hash
     * @return the hashed password
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Creates a new user account in the database.
     * 
     * @param username   the username for the new account
     * @param password   the plaintext password for the account (will be hashed)
     * @param firstName  the first name of the user
     * @param lastName   the last name of the user
     * @param accountType the account type (e.g., "Nurse", "Admin")
     * @return true if the account was created successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean createAccount(String accountType, String password, String firstName, String lastName, String userName) 
    		throws SQLException {
        String hashedPassword = hashPassword(password);
        String query = "INSERT INTO useraccount (accounttype, password, f_name, l_name, username) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, accountType);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);
            stmt.setString(5, userName);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Checks if the provided username and password combination exists in the
     * database, and retrieves the user's first and last name.
     * 
     * @param username the username to check
     * @param password the plaintext password to check
     * @return a Login object if the login is valid, null otherwise
     * @throws SQLException if a database access error occurs
     */
    public Login checkValidLogin(String username, String password) throws SQLException {
    	if (username != null && username.equals("admin1")) {
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
    	}
    	
        String query = "SELECT accounttype, f_name, l_name, password FROM useraccount WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String accountType = rs.getString("accounttype");
                    String firstName = rs.getString("f_name");
                    String lastName = rs.getString("l_name");
                    String storedHashedPassword = rs.getString("password");

                    if (BCrypt.checkpw(password, storedHashedPassword)) {
                        return new Login(username, accountType, firstName, lastName);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Retrieves the user_id for the given username.
     * 
     * @param username the username to look up
     * @return the user_id if found, -1 otherwise
     * @throws SQLException if a database access error occurs
     */
    public int getUserIdByUsername(String username) throws SQLException {
        String query = "SELECT user_id FROM useraccount WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        }
        return -1;
    }
}
