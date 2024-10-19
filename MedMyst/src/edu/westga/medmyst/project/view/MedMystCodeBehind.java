package edu.westga.medmyst.project.view;

import java.io.IOException;
import java.sql.SQLException;

import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

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
		
		this.viewmodel.getLoginSuccess().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Login successful!")) {
                this.openPatientManagement();
            }
        });
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
	
	private void openPatientManagement() {
        try {
            // Create a new Stage for the patient management window
            Stage patientStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PatientManagement.fxml"));
            Pane pane = loader.load();

            Scene scene = new Scene(pane);
            patientStage.setScene(scene);
            patientStage.setTitle("Patient Management");
            patientStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
