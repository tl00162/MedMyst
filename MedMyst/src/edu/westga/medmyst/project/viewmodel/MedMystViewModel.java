package edu.westga.medmyst.project.viewmodel;

import java.sql.SQLException;

import edu.westga.medmyst.project.dal.LoginDAL;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class TextTwistViewModel
 * 
 * TextTwistViewModel mediates between the view and the rest of the game.
 * 
 * @author Thomas Lamont
 * @version Fall 2024
 */
public class MedMystViewModel {

	private StringProperty username;
	private StringProperty password;
	private StringProperty loginSuccess;

	private LoginDAL loginDAL;

	/**
	 * Constructs a new MedMystViewModel and initializes its properties.
	 * 
	 */
	public MedMystViewModel() {
		this.username = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
		this.loginSuccess = new SimpleStringProperty();
		this.loginDAL = new LoginDAL();
	}

	/**
	 * Gets the username property that is bound to the username text field in the
	 * view.
	 * 
	 * @return the username StringProperty
	 */
	public StringProperty getUsername() {
		return this.username;
	}

	/**
	 * Gets the password property that is bound to the password text field in the
	 * view.
	 * 
	 * @return the password StringProperty
	 */
	public StringProperty getPassword() {
		return this.password;
	}

	/**
	 * Gets the login success message property that is bound to the login success
	 * label in the view.
	 * 
	 * This property is updated based on the outcome of the login attempt.
	 * 
	 * @return the login success StringProperty
	 */
	public StringProperty getLoginSuccess() {
		return this.loginSuccess;
	}

	/**
	 * Validates the login credentials by checking the username and password against
	 * the database using the LoginDAL.
	 * 
	 * If either the username or password is empty, an error message is set. If the
	 * login is valid, the method returns true, otherwise, it sets an error message
	 * and returns false.
	 * 
	 * @return true if the login is valid, false otherwise
	 * @throws SQLException if there is an issue with the database connection or
	 *                      query execution
	 */
	public boolean validateLogin() throws SQLException {
		String usernameValue = this.username.get();
		String passwordValue = this.password.get();
		System.out.print("" + usernameValue + passwordValue);
		if (usernameValue == null || usernameValue.isEmpty() || passwordValue == null || passwordValue.isEmpty()) {
			this.loginSuccess.set("Username and password must not be empty.");
			return false;
		}

		boolean isValidLogin = this.loginDAL.checkValidLogin(this.username.get(), this.password.get());

		if (isValidLogin) {
			return true;
		} else {
			this.loginSuccess.set("Invalid username or password.");
			return false;
		}
	}
}
