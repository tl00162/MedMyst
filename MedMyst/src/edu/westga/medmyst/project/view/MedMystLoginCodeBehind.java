package edu.westga.medmyst.project.view;

import java.io.IOException;
import java.sql.SQLException;

import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The Class MedMystLoginCodeBehind.
 * 
 * MedmystLoginCodeBehind provides access to the Login GUI
 * 
 * @author CS 3152
 * @version Fall 2024
 */
public class MedMystLoginCodeBehind {

	@FXML
	private Button loginButton;

	@FXML
	private Label loginSuccess;

	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField passwordTextField;

	private MedMystViewModel viewmodel;

	/**
	 * initializes the codebehind
	 */
	public MedMystLoginCodeBehind() {
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
			if (this.viewmodel.validateLogin()) {
				this.loadNurseScene();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			this.viewmodel.getLoginSuccess().set("Error accessing the database.");
		}
	}

	private void loadNurseScene() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/edu/westga/medmyst/project/view/MedMystNursePage.fxml"));
			Pane dashboardPane = loader.load();

			MedMystNurseCodeBehind nurseController = loader.getController();

			nurseController.setViewModel(this.viewmodel);

			Stage stage = (Stage) this.loginButton.getScene().getWindow();
			Scene scene = new Scene(dashboardPane);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
