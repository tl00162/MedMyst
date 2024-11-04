package edu.westga.medmyst.project.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import edu.westga.medmyst.project.model.Appointment;
import edu.westga.medmyst.project.model.Patient;
import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.util.converter.NumberStringConverter;

/**
 * The Class AppointmentFormCodeBehind. AppointmentFormCodeBehind provides
 * access to the Appointment Form GUI.
 * 
 * @version Fall 2024
 * @author demmons1
 */
public class AppointmentFormCodeBehind {

	@FXML
	private TextField fnameTextField;

	@FXML
	private TextField lnameTextField;

	@FXML
	private DatePicker dobDatePicker;

	@FXML
	private ComboBox<String> appointmentTypeComboBox;

	@FXML
	private ComboBox<String> doctorComboBox;

	@FXML
	private DatePicker dateDatePicker;

	@FXML
	private ComboBox<String> timeComboBox;

	@FXML
	private TextArea reasonTextArea;

	@FXML
	private TextArea detailsTextArea;

	@FXML
	private TextField systolicPressureTextField;

	@FXML
	private TextField diastolicPressureTextField;

	@FXML
	private TextField pulseTextField;

	@FXML
	private TextField heightTextField;

	@FXML
	private TextField weightTextField;

	@FXML
	private Button createAppointmentButton;

	@FXML
	private Button cancelButton;

	private MedMystViewModel viewmodel;

	private Patient currentPatient;

	private Appointment currentAppointment;

	private Runnable onFormSubmit;

	@FXML
	private void initialize() {
		this.cancelButton.setOnAction(event -> this.closeWindow());
		this.createAppointmentButton.setOnAction(event -> this.saveAppointment());

		this.populateAppointmentTimeComboBox();

		this.dateDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> this.updateAppointmentDateTime());
		this.timeComboBox.valueProperty().addListener((obs, oldTime, newTime) -> this.updateAppointmentDateTime());

		this.addIntegerInputRestriction(this.systolicPressureTextField);
		this.addIntegerInputRestriction(this.diastolicPressureTextField);
		this.addIntegerInputRestriction(this.pulseTextField);
		this.addDoubleInputRestriction(this.heightTextField);
		this.addDoubleInputRestriction(this.weightTextField);
	}

	/**
	 * Sets the viewmodel
	 * 
	 * @param viewmodel the specified viewmodel
	 */
	public void setViewModel(MedMystViewModel viewmodel) {
		this.viewmodel = viewmodel;
		this.populateDoctorComboBox();
		this.populateAppointmentTypeComboBox();
		this.bindDoctorComboBox();

		this.fnameTextField.textProperty().bindBidirectional(this.viewmodel.firstNameProperty());
		this.lnameTextField.textProperty().bindBidirectional(this.viewmodel.lastNameProperty());
		this.dobDatePicker.valueProperty().bindBidirectional(this.viewmodel.dateOfBirthProperty());
		this.dateDatePicker.valueProperty().bindBidirectional(this.viewmodel.appointmentDateProperty());
		this.timeComboBox.valueProperty().bindBidirectional(this.viewmodel.appointmentTimeProperty());
		this.reasonTextArea.textProperty().bindBidirectional(this.viewmodel.reasonProperty());
		this.detailsTextArea.textProperty().bindBidirectional(this.viewmodel.detailsProperty());
		this.systolicPressureTextField.textProperty().bindBidirectional(this.viewmodel.systolicPressureProperty(),
				new NumberStringConverter());
		this.diastolicPressureTextField.textProperty().bindBidirectional(this.viewmodel.diastolicPressureProperty(),
				new NumberStringConverter());
		this.pulseTextField.textProperty().bindBidirectional(this.viewmodel.pulseProperty(),
				new NumberStringConverter());
		this.heightTextField.textProperty().bindBidirectional(this.viewmodel.heightProperty(),
				new NumberStringConverter());
		this.weightTextField.textProperty().bindBidirectional(this.viewmodel.weightProperty(),
				new NumberStringConverter());

		this.appointmentTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				this.viewmodel.appointmentTypeProperty().set(newVal);
			}
		});
	}

	private void bindDoctorComboBox() {
		this.doctorComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				int doctorId = this.viewmodel.getDoctorIdByName(newValue);
				this.viewmodel.doctorIdProperty().set(doctorId);

				String[] nameParts = newValue.split(" ");
				if (nameParts.length >= 2) {
					this.viewmodel.doctorFirstNameProperty().set(nameParts[0]);
					this.viewmodel.doctorLastNameProperty().set(nameParts[1]);
				}
			}
		});
	}

	/**
	 * Sets the current patient
	 * 
	 * @param patient the selected patient
	 */
	public void setCurrentPatient(Patient patient) {
		this.currentPatient = patient;
		this.fnameTextField.setText(patient.getFName());
		this.lnameTextField.setText(patient.getLName());
		this.dobDatePicker.setValue(patient.getDateOfBirth());
		this.fnameTextField.setEditable(false);
		this.lnameTextField.setEditable(false);
		this.dobDatePicker.setEditable(false);

		this.viewmodel.patientIdProperty().set(patient.getPatientId());
	}

	/**
	 * Sets the current appointment
	 * 
	 * @param appointment the selected appointment
	 */
	public void setCurrentAppointment(Appointment appointment) {
		this.currentAppointment = appointment;

		this.populateAppointmentTypeComboBox();
		this.populateDoctorComboBox();

		if (appointment != null) {
			this.viewmodel.loadAppointmentData(appointment);
			String doctorName = this.viewmodel.getDoctorNameById(appointment.getDoctorId());
			this.doctorComboBox.setValue(doctorName);
			this.createAppointmentButton.setText("Update Appointment");
			this.appointmentTypeComboBox.setValue(appointment.getAppointmentType());
		}
	}

	/**
	 * Sets up what to run on submit
	 * 
	 * @param onFormSubmit
	 */
	public void setOnFormSubmit(Runnable onFormSubmit) {
		this.onFormSubmit = onFormSubmit;
	}
	
	/**
	 * Gets the createAppointmentButton
	 * 
	 * @return createAppointment Button
	 */
	public Button getCreateAppointmentButton() {
		return this.createAppointmentButton;
	}

	/**
	 * Gets the createAppointmentButton text
	 * 
	 * @param text for createAppointment Button
	 */
	public void setCreateAppointmentButtonText(String text) {
		this.createAppointmentButton.setText(text);
	}

	private void populateAppointmentTimeComboBox() {
		this.timeComboBox.getItems().addAll("09:00 AM", "09:20 AM", "09:40 AM", "10:00 AM", "10:20 AM", "10:40 AM",
				"11:00 AM", "11:20 AM", "11:40 AM", "01:00 PM", "01:20 PM", "01:40 PM", "02:00 PM", "02:20 PM",
				"02:40 PM", "03:00 PM", "03:20 PM", "03:40 PM");
	}

	private void populateDoctorComboBox() {
		List<String> doctors = this.viewmodel.getDoctorNames();
		this.doctorComboBox.getItems().addAll(doctors);
	}

	private void populateAppointmentTypeComboBox() {
		List<String> appointmentTypes = this.viewmodel.getAppointmentTypes();
		this.appointmentTypeComboBox.getItems().addAll(appointmentTypes);
	}

	private void closeWindow() {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}

	private void saveAppointment() {
		StringBuilder errorMessage = this.validateAppointmentInput();
		if (errorMessage.length() > 0) {
			this.showErrorDialog(errorMessage.toString());
			return;
		}

		int doctorId = this.getSelectedDoctorId();
		LocalDateTime appointmentDateTime = this.parseAppointmentDateTime();

		this.setViewModelProperties(doctorId, appointmentDateTime);

		boolean success;
		if (this.currentAppointment == null) {
			success = this.viewmodel.addAppointment();
		} else {
			this.updateCurrentAppointmentProperties();
			success = this.viewmodel.updateAppointment(this.currentAppointment);
		}
		if (!success) {
			this.showErrorDialog("Failed to save appointment. Please try again.");
			return;
		}
		if (this.onFormSubmit != null) {
			this.onFormSubmit.run();
		}

		this.closeWindow();
	}

	private void updateCurrentAppointmentProperties() {
		this.currentAppointment.setDoctorId(this.viewmodel.doctorIdProperty().get());
		this.currentAppointment.setDateTime(this.viewmodel.appointmentDateTimeProperty().get());
		this.currentAppointment.setAppointmentType(this.viewmodel.appointmentTypeProperty().get());
		this.currentAppointment.setReason(this.viewmodel.reasonProperty().get());
		this.currentAppointment.setDetails(this.viewmodel.detailsProperty().get());
		this.currentAppointment.setSystolicPressure(this.viewmodel.systolicPressureProperty().get());
		this.currentAppointment.setDiastolicPressure(this.viewmodel.diastolicPressureProperty().get());
		this.currentAppointment.setPulse(this.viewmodel.pulseProperty().get());
		this.currentAppointment.setHeight(this.viewmodel.heightProperty().get());
		this.currentAppointment.setWeight(this.viewmodel.weightProperty().get());
	}

	private StringBuilder validateAppointmentInput() {
		StringBuilder errorMessage = new StringBuilder();

		if (this.doctorComboBox.getValue() == null || this.doctorComboBox.getValue().isEmpty()) {
			errorMessage.append("Doctor must be selected.\n");
		}
		LocalDateTime appointmentDateTime = this.parseAppointmentDateTime();
		if (appointmentDateTime == null) {
			errorMessage.append("Appointment date and time must be selected.\n");
		} else if (appointmentDateTime.isBefore(LocalDateTime.now())) {
			errorMessage.append("Appointment cannot be scheduled in the past.\n");
		}
		if (this.appointmentTypeComboBox.getValue() == null) {
			errorMessage.append("Appointment type must be selected.\n");
		}
		if (this.reasonTextArea.getText() == null || this.reasonTextArea.getText().isEmpty()) {
			errorMessage.append("Appointment reason must not be empty.\n");
		}
		if (!this.isNumeric(this.systolicPressureTextField.getText())) {
			errorMessage.append("Systolic Pressure must be a numeric value.\n");
		}
		if (!this.isNumeric(this.diastolicPressureTextField.getText())) {
			errorMessage.append("Diastolic Pressure must be a numeric value.\n");
		}
		if (!this.isNumeric(this.pulseTextField.getText())) {
			errorMessage.append("Pulse must be a numeric value.\n");
		}
		if (!this.isNumeric(this.heightTextField.getText())) {
			errorMessage.append("Height must be a numeric value.\n");
		}
		if (!this.isNumeric(this.weightTextField.getText())) {
			errorMessage.append("Weight must be a numeric value.\n");
		}
		return errorMessage;
	}

	/**
	 * Helper method to check if a given string is numeric.
	 * 
	 * @param text The string to check.
	 * @return true if the string is numeric, false otherwise.
	 */
	private boolean isNumeric(String text) {
		if (text == null || text.isEmpty()) {
			return true;
		}
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Parses and formats the appointment date and time.
	 * 
	 * @return The LocalDateTime for the appointment, or null if date or time is not
	 *         selected.
	 */
	private LocalDateTime parseAppointmentDateTime() {
		if (this.dateDatePicker.getValue() != null && this.timeComboBox.getValue() != null) {
			return LocalDateTime.of(this.dateDatePicker.getValue(),
					java.time.LocalTime.parse(this.convertTo24HourFormat(this.timeComboBox.getValue())));
		}
		return null;
	}

	/**
	 * Gets the selected doctor's ID from the viewmodel using the doctor's name.
	 * 
	 * @return the doctor's ID
	 */
	private int getSelectedDoctorId() {
		String selectedDoctor = this.doctorComboBox.getValue();
		return this.viewmodel.getDoctorIdByName(selectedDoctor);
	}

	/**
	 * Sets the necessary properties in the ViewModel for creating an appointment.
	 * 
	 * @param doctorId            The selected doctor's ID.
	 * @param appointmentDateTime The selected appointment date and time.
	 */
	private void setViewModelProperties(int doctorId, LocalDateTime appointmentDateTime) {
		this.viewmodel.doctorIdProperty().set(doctorId);
		this.viewmodel.appointmentDateTimeProperty().set(appointmentDateTime);
		this.viewmodel.appointmentTypeProperty().set(this.appointmentTypeComboBox.getValue());
		this.viewmodel.reasonProperty().set(this.reasonTextArea.getText());
		this.viewmodel.detailsProperty().set(this.detailsTextArea.getText());
	}

	private void showErrorDialog(String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText(null);
		alert.setContentText(errorMessage);
		alert.showAndWait();
	}

	private String convertTo24HourFormat(String time) {
		if (time.matches("\\d{2}:\\d{2}")) {
			return time;
		}
		return java.time.format.DateTimeFormatter.ofPattern("hh:mm a").parse(time, java.time.LocalTime::from)
				.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
	}

	private void updateAppointmentDateTime() {
		LocalDate date = this.dateDatePicker.getValue();
		String time = this.timeComboBox.getValue();

		if (date != null && time != null) {
			LocalDateTime dateTime = LocalDateTime.of(date,
					java.time.LocalTime.parse(this.convertTo24HourFormat(time)));
			this.viewmodel.appointmentDateTimeProperty().set(dateTime);
		} else {
			this.viewmodel.appointmentDateTimeProperty().set(null);
		}
	}

	private void addIntegerInputRestriction(TextField textField) {
		textField.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
			String character = event.getCharacter();
			if (!character.matches("[0-9]")) {
				event.consume();
			}
		});
	}

	private void addDoubleInputRestriction(TextField textField) {
		textField.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
			String character = event.getCharacter();
			if (!character.matches("[0-9.]") || (character.equals(".") && textField.getText().contains("."))) {
				event.consume();
			}
		});
	}
}