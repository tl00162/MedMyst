package edu.westga.medmyst.project.view;

import java.io.IOException;
import java.util.List;

import edu.westga.medmyst.project.model.Patient;
import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The Class MedMystNurseCodeBehind.
 * 
 * MedmystNurseCodeBehind provides access to the Nurse dashboard GUI
 * 
 * @author tl00162
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
	private ListView<Patient> patientsListView;

	@FXML
	private Label usernameLabel;

	private MedMystViewModel viewmodel;

	private Patient selectedPatient;

	@FXML
	private void initialize() {
		this.editPatientButton.disableProperty()
				.bind(this.patientsListView.getSelectionModel().selectedItemProperty().isNull());

		this.patientsListView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					this.selectedPatient = newValue;
				});
	}

	/**
	 * Refreshes the patient list in the ListView.
	 */
	private void refreshPatientList() {
		List<Patient> patientList = this.viewmodel.getPatients();
		ObservableList<Patient> observablePatientList = FXCollections.observableArrayList(patientList);
		this.patientsListView.setItems(observablePatientList);

		this.patientsListView.setCellFactory(patientListView -> new ListCell<Patient>() {
			@Override
			protected void updateItem(Patient patient, boolean empty) {
				super.updateItem(patient, empty);
				if (empty || patient == null) {
					setText(null);
				} else {
					setText(patient.getFName() + " " + patient.getLName());
				}
			}
		});
	}

	/**
	 * Sets the ViewModel for this controller and updates the username label.
	 *
	 * @param viewmodel The ViewModel to use in this controller.
	 */
	public void setViewModel(MedMystViewModel viewmodel) {
		this.viewmodel = viewmodel;

		if (this.viewmodel.getCurrentUser() != null) {
			String firstName = this.viewmodel.getCurrentUser().getFirstName();
			String role = this.viewmodel.getCurrentUser().getRole();

			if (role.equalsIgnoreCase("nurse")) {
				this.usernameLabel.setText("Welcome, Nurse " + firstName + "!");
			} else if (role.equalsIgnoreCase("admin")) {
				this.usernameLabel.setText("Welcome, Admin " + firstName + "!");
			}
		}

		this.refreshPatientList();
	}

	@FXML
	private void addPatient() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/edu/westga/medmyst/project/view/PatientForm.fxml"));
			Pane patientFormPane = loader.load();

			Stage patientFormStage = new Stage();
			patientFormStage.setTitle("Add Patient");
			patientFormStage.setScene(new Scene(patientFormPane));

			PatientFormCodeBehind patientFormCodeBehind = loader.getController();
			patientFormCodeBehind.setViewModel(this.viewmodel);

			patientFormCodeBehind.clearForm();

			patientFormCodeBehind.setOnFormSubmit(() -> {
				this.refreshPatientList();
				patientFormStage.close();
			});

			patientFormStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void editPatient() {
		if (this.selectedPatient != null) {
			try {
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/edu/westga/medmyst/project/view/PatientForm.fxml"));
				Pane patientFormPane = loader.load();

				Stage patientFormStage = new Stage();
				patientFormStage.setTitle("Edit Patient");
				patientFormStage.setScene(new Scene(patientFormPane));

				PatientFormCodeBehind patientFormCodeBehind = loader.getController();
				patientFormCodeBehind.setCurrentPatient(this.selectedPatient);
				patientFormCodeBehind.setViewModel(this.viewmodel);
				patientFormCodeBehind.populateForm();

				patientFormCodeBehind.setOnFormSubmit(() -> {
					this.refreshPatientList();
					this.selectedPatient = null;
					patientFormStage.close();
				});

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

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/edu/westga/medmyst/project/view/MedMystLogin.fxml"));
			Pane loginPane = loader.load();
			Stage stage = (Stage) this.logoutButton.getScene().getWindow();
			Scene scene = new Scene(loginPane);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
