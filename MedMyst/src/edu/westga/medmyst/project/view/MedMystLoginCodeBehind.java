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

		this.passwordTextField.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case ENTER:
					this.loginButtonClick(null);
					break;
				default:
					break;
			}
		});
	}

	@FXML
	void loginButtonClick(ActionEvent event) {
		try {
			String role = this.viewmodel.validateLogin();
			if (role != null) {
				if (role.equalsIgnoreCase("admin")) {
					this.loadAdminScene();
				} else if (role.equalsIgnoreCase("nurse")) {
					this.loadNurseScene();
				}
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
			javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
			stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
			stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadAdminScene() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/edu/westga/medmyst/project/view/MedMystAdminPage.fxml"));
			Pane adminPane = loader.load();

			MedMystAdminCodeBehind adminController = loader.getController();
			adminController.setViewModel(this.viewmodel);

			Stage stage = (Stage) this.loginButton.getScene().getWindow();
			Scene scene = new Scene(adminPane);
			stage.setScene(scene);

			javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
			stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
			stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
