package edu.westga.medmyst.project.view;

import java.io.IOException;

import edu.westga.medmyst.project.model.Patient;
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
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/westga/medmyst/project/view/PatientForm.fxml"));
            Pane patientFormPane = loader.load();

            Stage patientFormStage = new Stage();
            patientFormStage.setTitle("Add Patient");
            patientFormStage.setScene(new Scene(patientFormPane));

            // Get the controller for the patient form and pass the view model
            PatientFormCodeBehind patientFormCodeBehind = loader.getController();
            patientFormCodeBehind.setViewModel(this.viewmodel); // Pass the viewmodel for handling patient logic

            patientFormStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editPatient() {
    	Patient selectedPatient = (Patient) this.patientsListView.getSelectionModel().getSelectedItem();
        
        if (selectedPatient != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/westga/medmyst/project/view/PatientForm.fxml"));
                Pane patientFormPane = loader.load();

                // Create a new stage for the Patient Form
                Stage patientFormStage = new Stage();
                patientFormStage.setTitle("Edit Patient");
                patientFormStage.setScene(new Scene(patientFormPane));

                // Get the controller for the patient form and pass the view model and selected patient
                PatientFormCodeBehind patientFormCodeBehind = loader.getController();
                patientFormCodeBehind.setViewModel(this.viewmodel);
                patientFormCodeBehind.populateForm(selectedPatient); // Populate the form with the patient's details

                patientFormStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No patient selected.");
        }
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
