package edu.westga.medmyst.project.view;

import java.io.IOException;

import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The Class MedMystNurseCodeBehind.
 * 
 * MedmystNurseCodeBehind provides access to the Nurse dashboard GUI
 * 
 * @author CS 3152
 * @version Fall 2024
 */
public class MedMystNurseCodeBehind {

    @FXML
    private Button addPatientButton;

    @FXML
    private Button editPatientButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ListView<?> patientsListView;

    @FXML
    private Label usernameLabel;
    
    private MedMystViewModel viewmodel;
    
    @FXML
    private void initialize() {    	
    	//TODO GET LIST OF PATIENTS AND ADD TO LIST VIEW
    }
    
    /**
     * Sets the ViewModel for this controller and updates the username label.
     *
     * @param viewmodel The ViewModel to use in this controller.
     */
    public void setViewModel(MedMystViewModel viewmodel) {
        this.viewmodel = viewmodel;

        if (this.viewmodel.getCurrentUser() != null) {
            this.usernameLabel.setText("Welcome, " + this.viewmodel.getCurrentUser().getUsername() + "!");
        }
    }
    
    @FXML
    private void addPatient() {
        System.out.println("Add Patient button clicked");
    }

    @FXML
    private void editPatient() {
        System.out.println("Edit Patient button clicked");
    }

    @FXML
    private void logout() {
        this.viewmodel.logout();
        System.out.println("Logged out");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/westga/medmyst/project/view/MedMystLogin.fxml"));
            Pane loginPane = loader.load();
            Stage stage = (Stage) this.logoutButton.getScene().getWindow();
            Scene scene = new Scene(loginPane);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
