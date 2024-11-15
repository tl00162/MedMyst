package edu.westga.medmyst.project.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import edu.westga.medmyst.project.model.Appointment;
import edu.westga.medmyst.project.model.AppointmentTest;
import edu.westga.medmyst.project.model.Patient;
import edu.westga.medmyst.project.model.Test;
import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private Button addTestButton;

	@FXML
	private Button editPatientButton;

	@FXML
	private Button logoutButton;

	@FXML
	private ListView<Patient> patientsListView;

	@FXML
	private TextField searchFirstNameTextFieldPatient;

	@FXML
	private TextField searchLastNameTextFieldPatient;

	@FXML
	private DatePicker searchDOBPickerPatient;

	@FXML
	private Button searchButtonPatient;

	@FXML
	private Button addAppointmentButton;

	@FXML
	private Button editAppointmentButton;
	
	@FXML
    private Button editTestButton;

	@FXML
	private Button clearAppoinmentButton;

	@FXML
	private Button clearPatientButton;

	@FXML
	private ListView<Appointment> appointmentsListView;
	
	@FXML
    private ListView<AppointmentTest> testsListView;

	@FXML
	private TextField searchFirstNameTextFieldAppointment;

	@FXML
	private TextField searchLastNameTextFieldAppointment;

	@FXML
	private DatePicker searchDOBPickerAppointment;

	@FXML
	private Button searchButtonAppointment;

	@FXML
	private Button viewAppointmentButton;
	
	@FXML
    private Button viewTestButton;

	@FXML
	private Label usernameLabel;

	private MedMystViewModel viewmodel;

	private Patient selectedPatient;

	private Appointment selectedAppointment;
	
	private AppointmentTest selectedAppointmentTest;

	@FXML
	private void initialize() {
	    this.editPatientButton.disableProperty()
	            .bind(this.patientsListView.getSelectionModel().selectedItemProperty().isNull());
	    
	    this.patientsListView.getSelectionModel().selectedItemProperty()
	            .addListener((observable, oldValue, newValue) -> {
	                this.selectedPatient = newValue;
	                
	                this.viewmodel.updateCanAddAppointment(newValue);
	            });

	    this.editAppointmentButton.disableProperty()
	            .bind(this.appointmentsListView.getSelectionModel().selectedItemProperty().isNull());

	    this.viewAppointmentButton.disableProperty()
	            .bind(this.appointmentsListView.getSelectionModel().selectedItemProperty().isNull());

	    this.appointmentsListView.getSelectionModel().selectedItemProperty()
	            .addListener((observable, oldValue, newValue) -> {
	                this.selectedAppointment = newValue;
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
	 * Refreshes the appointment list in the ListView.
	 */
	private void refreshAppointmentList() {
		List<Appointment> appointmentList = this.viewmodel.getAppointments();
		ObservableList<Appointment> observableAppointmentList = FXCollections.observableArrayList(appointmentList);
		this.appointmentsListView.setItems(observableAppointmentList);

		this.appointmentsListView.setCellFactory(appointmentListView -> new ListCell<Appointment>() {
			@Override
			protected void updateItem(Appointment appointment, boolean empty) {
				super.updateItem(appointment, empty);
				if (empty || appointment == null) {
					setText(null);
				} else {
					Patient patient = viewmodel.getPatientById(appointment.getPatientId());
					String patientLastName = (patient != null) ? patient.getLName() : "Unknown";
					String doctorName = viewmodel.getDoctorNameById(appointment.getDoctorId());
					String appointmentType = appointment.getAppointmentType();
					LocalDateTime dateTime = appointment.getDateTime();

					String displayText = String.format("%s - %s | %s | %s", patientLastName,
							dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
							doctorName, appointmentType);

					setText(displayText);
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
			String userId = this.viewmodel.getCurrentUser().getUserID();
			String lastName = this.viewmodel.getCurrentUser().getLastName();

			this.usernameLabel.setText(String.format("Welcome, %s, %s %s!", userId, firstName, lastName));
		}
	    this.addAppointmentButton.disableProperty().bind(this.viewmodel.canAddAppointmentProperty().not());
	    this.addTestButton.disableProperty().bind(this.viewmodel.canAddAppointmentProperty().not());
		this.refreshPatientList();
		this.refreshAppointmentList();
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
	private void createAppointment() {
		if (this.selectedPatient != null) {
			try {
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/edu/westga/medmyst/project/view/AppointmentForm.fxml"));
				Pane appointmentFormPane = loader.load();

				Stage appointmentFormStage = new Stage();
				appointmentFormStage.setTitle("Create Appointment");
				appointmentFormStage.setScene(new Scene(appointmentFormPane));

				AppointmentFormCodeBehind appointmentFormCodeBehind = loader.getController();
				appointmentFormCodeBehind.setViewModel(this.viewmodel);
				appointmentFormCodeBehind.setCurrentPatient(this.selectedPatient);

				appointmentFormCodeBehind.setOnFormSubmit(() -> {
					this.refreshAppointmentList();
					appointmentFormStage.close();
				});

				appointmentFormStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No patient selected.");
		}
	}

	@FXML
	private void viewAppointment() {
		if (this.selectedAppointment != null) {
			try {
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/edu/westga/medmyst/project/view/AppointmentForm.fxml"));
				Pane appointmentFormPane = loader.load();

				Stage appointmentFormStage = new Stage();
				appointmentFormStage.setTitle("View Appointment");
				appointmentFormStage.setScene(new Scene(appointmentFormPane));

				AppointmentFormCodeBehind appointmentFormCodeBehind = loader.getController();
				appointmentFormCodeBehind.setViewModel(this.viewmodel);
				appointmentFormCodeBehind.setCurrentAppointment(this.selectedAppointment);

				Patient patient = this.viewmodel.getPatientById(this.selectedAppointment.getPatientId());
				if (patient != null) {
					appointmentFormCodeBehind.setCurrentPatient(patient);
				}
				if (this.selectedAppointment.getDateTime().isBefore(LocalDateTime.now())) {
					appointmentFormCodeBehind.getCreateAppointmentButton().setVisible(false);
				}

				appointmentFormCodeBehind.setOnFormSubmit(() -> {
					this.refreshAppointmentList();
					this.selectedAppointment = null;
					appointmentFormStage.close();
				});

				appointmentFormStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No appointment selected.");
		}
	}

	@FXML
	private void editAppointment() {
		if (this.selectedAppointment != null) {

			if (this.selectedAppointment.getDateTime().isBefore(LocalDateTime.now())) {
				this.showAlert("Cannot Edit Appointment", "This appointment is in the past and cannot be edited.");
				return;
			}
			try {
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/edu/westga/medmyst/project/view/AppointmentForm.fxml"));
				Pane appointmentFormPane = loader.load();
				Stage appointmentFormStage = new Stage();
				appointmentFormStage.setTitle("Edit Appointment");
				appointmentFormStage.setScene(new Scene(appointmentFormPane));
				AppointmentFormCodeBehind appointmentFormCodeBehind = loader.getController();
				appointmentFormCodeBehind.setViewModel(this.viewmodel);
				appointmentFormCodeBehind.setCurrentAppointment(this.selectedAppointment);

				appointmentFormCodeBehind.setCreateAppointmentButtonText("Edit Appointment");

				Patient patient = this.viewmodel.getPatientById(this.selectedAppointment.getPatientId());
				if (patient != null) {
					appointmentFormCodeBehind.setCurrentPatient(patient);
				}
				appointmentFormCodeBehind.setOnFormSubmit(() -> {
					this.refreshAppointmentList();
					this.selectedAppointment = null;
					appointmentFormStage.close();
				});
				appointmentFormStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No appointment selected.");
		}
	}

	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
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

	@FXML
	private void searchPatients() {
		String firstName = this.searchFirstNameTextFieldPatient.getText();
		String lastName = this.searchLastNameTextFieldPatient.getText();
		LocalDate dob = this.searchDOBPickerPatient.getValue();

		List<Patient> filteredPatients = this.viewmodel.searchPatients(firstName, lastName, dob);
		ObservableList<Patient> observablePatientList = FXCollections.observableArrayList(filteredPatients);
		this.patientsListView.setItems(observablePatientList);
	}

	@FXML
	private void searchAppointments() {
		String firstName = this.searchFirstNameTextFieldAppointment.getText().trim();
		String lastName = this.searchLastNameTextFieldAppointment.getText().trim();
		LocalDate dob = this.searchDOBPickerAppointment.getValue();

		List<Appointment> filteredAppointments = this.viewmodel.searchAppointmentsByPatientInfo(firstName, lastName,
				dob);
		ObservableList<Appointment> observableAppointmentList = FXCollections.observableArrayList(filteredAppointments);
		this.appointmentsListView.setItems(observableAppointmentList);
	}

	@FXML
	void clearAppointments() {

	    this.searchFirstNameTextFieldAppointment.clear();
	    this.searchLastNameTextFieldAppointment.clear();
	    this.searchDOBPickerAppointment.setValue(null);

	    this.refreshAppointmentList();
	}

	@FXML
	void clearPatientSearch() {
	    this.searchFirstNameTextFieldPatient.clear();
	    this.searchLastNameTextFieldPatient.clear();
	    this.searchDOBPickerPatient.setValue(null);

	    this.refreshPatientList();
	}
	
	@FXML
    void createTest() {
		//TODO unimplemented
    }
	
	@FXML
    void editTest() {
		//TODO unimplemented
    }
	
	@FXML
    void viewTest() {
		//TODO unimplemented
    }

}
