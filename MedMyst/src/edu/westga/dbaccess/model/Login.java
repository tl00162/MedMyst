package edu.westga.dbaccess.model;

/**
 * Login class
 * @author lyang
 * @version 1.0
 */
public class Login {

	private String username;
	private String password;

	public Login(String username, String password) {
		if (username == null || username.length()==0) {
			throw new IllegalArgumentException("first name cannot be null");
		}
		if (password == null || password.length()==0) {
			throw new IllegalArgumentException("first name cannot be null");
		}
		this.username = username;
		this.password = password;
		
	}

	public String getUsername() {
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
}
