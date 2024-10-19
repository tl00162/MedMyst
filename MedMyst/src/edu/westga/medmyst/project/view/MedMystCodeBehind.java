package edu.westga.medmyst.project.view;

import java.sql.SQLException;

import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * The Class MedMystCodeBehind.
 * 
 * MedmystCodeBehind provides access to the GUI through code
 * 
 * @author CS 3152
 * @version Fall 2024
 */
public class MedMystCodeBehind {

	@FXML
	private Button loginButton;

	@FXML
	private Label loginSuccess;

	@FXML
	private TextField usernameTextField;

	@FXML
	private TextField passwordTextField;

	private MedMystViewModel viewmodel;
	
	
	/**
	 * initializes the codebehind
	 */
	public MedMystCodeBehind() {
		this.viewmodel = new MedMystViewModel();

	}

	@FXML
	private void initialize() {
		this.loginSuccess.textProperty().bindBidirectional(this.viewmodel.getLoginSuccess());
		this.usernameTextField.textProperty().bindBidirectional(this.viewmodel.getUsername());
		this.passwordTextField.textProperty().bindBidirectional(this.viewmodel.getPassword());
	}

	@FXML
	void loginButtonClick(ActionEvent event) {
		try {
			this.viewmodel.validateLogin();
		} catch (SQLException e) {
			e.printStackTrace();
			this.viewmodel.getLoginSuccess().set("Error accessing the database.");
		}
	}

}
