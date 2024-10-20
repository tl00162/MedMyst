package edu.westga.medmyst.project.view;

import java.time.LocalDate;

import edu.westga.medmyst.project.model.Patient;
import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The Class PatientFormCodeBehind. PatientFormCodeBehind provides access to the
 * Patient Form GUI
 * 
 * @author demmons1
 * @version Fall 2024
 */
public class PatientFormCodeBehind {

	@FXML
	private TextField fnameTextField;

	@FXML
	private TextField lnameTextField;

	@FXML
	private DatePicker dobDatePicker;

	@FXML
	private ComboBox<String> genderComboBox;

	@FXML
	private TextField phoneNumberTextField;

	@FXML
	private TextField address1TextField;

	@FXML
	private TextField address2TextField;

	@FXML
	private ComboBox<String> stateComboBox;

	@FXML
	private TextField zipTextField;

	@FXML
	private Button addPatientButton;

	@FXML
	private Button cancelButton;

	private MedMystViewModel viewmodel;

	private Patient currentPatient;

	private Runnable onFormSubmit;

	@FXML
	private void initialize() {

		this.cancelButton.setOnAction(event -> this.closeWindow());
		this.addPatientButton.setOnAction(event -> this.savePatient());
	}

	/**
	 * Sets the view model for this instance.
	 * 
	 * @param viewmodel the specified view model
	 */
	public void setViewModel(MedMystViewModel viewmodel) {
		this.viewmodel = viewmodel;

		this.fnameTextField.textProperty().bindBidirectional(this.viewmodel.firstNameProperty());
		this.lnameTextField.textProperty().bindBidirectional(this.viewmodel.lastNameProperty());
		this.dobDatePicker.valueProperty().bindBidirectional(this.viewmodel.dateOfBirthProperty());
		this.genderComboBox.valueProperty().bindBidirectional(this.viewmodel.genderProperty());
		this.phoneNumberTextField.textProperty().bindBidirectional(this.viewmodel.phoneNumberProperty());
		this.address1TextField.textProperty().bindBidirectional(this.viewmodel.address1Property());
		this.address2TextField.textProperty().bindBidirectional(this.viewmodel.address2Property());
		this.stateComboBox.valueProperty().bindBidirectional(this.viewmodel.stateProperty());
		this.zipTextField.textProperty().bindBidirectional(this.viewmodel.zipProperty());

		this.populateGenderComboBox();
		this.populateStateComboBox();

		if (this.currentPatient != null) {
			this.addPatientButton.setText("Edit Patient");
		}
	}

	/**
	 * Sets the currentPatient to the specified patient.
	 * 
	 * @param selectedPatient the patient to set.
	 */
	public void setCurrentPatient(Patient selectedPatient) {
		this.currentPatient = selectedPatient;
	}

	private void populateGenderComboBox() {

		this.genderComboBox.getItems().addAll("M", "F");
	}

	private void populateStateComboBox() {

		this.stateComboBox.getItems().addAll("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID",
				"IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
				"NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA",
				"WA", "WV", "WI", "WY");
	}

	/**
	 * Returns user to the nurse page.
	 * 
	 * @param onFormSubmit
	 */
	public void setOnFormSubmit(Runnable onFormSubmit) {
		this.onFormSubmit = onFormSubmit;
	}

	/**
	 * Populates the Patient Form with the current patient's information.
	 */
	public void populateForm() {
		this.fnameTextField.setText(this.currentPatient.getFName());
		this.lnameTextField.setText(this.currentPatient.getLName());
		this.dobDatePicker.setValue(this.currentPatient.getDateOfBirth());
		this.genderComboBox.setValue(this.currentPatient.getGender());
		this.phoneNumberTextField.setText(this.currentPatient.getPhoneNumber());
		this.address1TextField.setText(this.currentPatient.getAddress1());
		this.address2TextField.setText(this.currentPatient.getAddress2());
		this.stateComboBox.setValue(this.currentPatient.getState());
		this.zipTextField.setText(this.currentPatient.getZip());
	}

	private void closeWindow() {

		this.currentPatient = null;

		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Creates a new patient or updates the selected current patient.
	 */
	private void savePatient() {

		String firstName = this.fnameTextField.getText();
		String lastName = this.lnameTextField.getText();
		LocalDate dob = this.dobDatePicker.getValue();
		String gender = this.genderComboBox.getValue();
		String phoneNumber = this.phoneNumberTextField.getText();
		String address1 = this.address1TextField.getText();
		String address2 = this.address2TextField.getText();
		String state = this.stateComboBox.getValue();
		String zip = this.zipTextField.getText();

		StringBuilder errorMessage = new StringBuilder();

		if (firstName == null || firstName.isEmpty() || firstName.length() > 50) {
			errorMessage.append("First name must not be empty and cannot exceed 50 characters.\n");
		}
		if (lastName == null || lastName.isEmpty() || lastName.length() > 50) {
			errorMessage.append("Last name must not be empty and cannot exceed 50 characters.\n");
		}
		if (dob == null) {
			errorMessage.append("Date of birth must be selected.\n");
		}

		if (gender == null || (!gender.equals("M") && !gender.equals("F"))) {
			errorMessage.append("Gender must be selected and should be either 'M' or 'F'.\n");
		}

		if (phoneNumber == null || phoneNumber.length() != 10 || !phoneNumber.matches("\\d+")) {
			errorMessage.append("Phone number must be exactly 10 digits long and contain only numbers.\n");
		}

		if (address1 == null || address1.isEmpty()) {
			errorMessage.append("Address1 must not be empty.\n");
		}

		if (state == null) {
			errorMessage.append("State must be selected.\n");
		}

		if (zip == null || zip.length() != 5 || !zip.matches("\\d+")) {
			errorMessage.append("Zip code must be exactly 5 digits long and contain only numbers.\n");
		}

		if (errorMessage.length() > 0) {
			this.showErrorDialog(errorMessage.toString());
			return;
		}

		if (this.currentPatient == null) {
			boolean success = this.viewmodel.addPatient();
			if (!success) {
				this.showErrorDialog("Failed to add patient. Please try again.");
				return;
			}
		} else {

			this.currentPatient.setFName(firstName);
			this.currentPatient.setLName(lastName);
			this.currentPatient.setDateOfBirth(dob);
			this.currentPatient.setGender(gender);
			this.currentPatient.setPhoneNumber(phoneNumber);
			this.currentPatient.setAddress1(address1);
			this.currentPatient.setAddress2(address2);
			this.currentPatient.setState(state);
			this.currentPatient.setZip(zip);
			boolean success = this.viewmodel.updatePatient(this.currentPatient);
			if (!success) {
				this.showErrorDialog("Failed to update patient. Please try again.");
				return;
			}
		}

		this.currentPatient = null;

		if (this.onFormSubmit != null) {
			this.onFormSubmit.run();
		}

		this.closeWindow();
	}

	private void showErrorDialog(String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText(null);
		alert.setContentText(errorMessage);
		alert.showAndWait();
	}
}
