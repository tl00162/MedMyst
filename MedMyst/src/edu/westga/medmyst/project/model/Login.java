package edu.westga.medmyst.project.model;

/**
 * Represents a login with a username, password, first name, last name, and user
 * role (nurse or admin).
 * 
 * @author tl00162
 */
public class Login {

	private String userID;
	private String role;
	private String firstName;
	private String lastName;

	/**
	 * Constructs a new Login instance with the given username, role, first name,
	 * and last name.
	 *
	 * @param username  the username for this login, must not be null or empty
	 * @param role      the role of the user (either "nurse" or "admin"), must not
	 *                  be null or empty
	 * @param firstName the first name of the user
	 * @param lastName  the last name of the user
	 * @throws IllegalArgumentException if the username or role is null or empty
	 */
	public Login(String username, String role, String firstName, String lastName) {
		if (username == null || username.length() == 0) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}
		if (role == null || role.length() == 0) {
			throw new IllegalArgumentException("Role cannot be null or empty");
		}
		this.userID = username;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Returns the userID for this login.
	 *
	 * @return the userID
	 */
	public String getUserID() {
		return this.userID;
	}

	/**
	 * Gets the role of the user.
	 *
	 * @return the role
	 */
	public String getRole() {
		return this.role;
	}

	/**
	 * Gets the first name of the user.
	 *
	 * @return the first name of the logged-in user.
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Gets the last name of the user.
	 *
	 * @return the last name of the logged-in user.
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the userID (username) for this login.
	 *
	 * @param username the new username to set for the logged-in user.
	 */
	public void setUsername(String username) {
		this.userID = username;
	}

	/**
	 * Sets the role of the user.
	 *
	 * @param role the role to set for the logged-in user (either "nurse" or
	 *             "admin").
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Sets the first name of the user.
	 *
	 * @param firstName the new first name to set for the logged-in user.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets the last name of the user.
	 *
	 * @param lastName the new last name to set for the logged-in user.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
