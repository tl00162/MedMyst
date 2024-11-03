package edu.westga.medmyst.project.view;

import java.time.LocalDateTime;

import edu.westga.medmyst.project.model.Appointment;
import edu.westga.medmyst.project.model.Patient;
import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.converter.NumberStringConverter;

/**
 * The Class AppointmentFormCodeBehind.
 * AppointmentFormCodeBehind provides access to the Appointment Form GUI.
 * 
 * @author demmons1
 * @version Fall 2024
 */
public class AppointmentFormCodeBehind {

    @FXML
    private TextField fnameTextField;
    
    @FXML
    private TextField lnameTextField;
    
    @FXML
    private DatePicker dobDatePicker;
    
    @FXML
    private TextField appointmentTypeTextField;

    @FXML
    private TextField doctorTextField;

    @FXML
    private DatePicker dateDatePicker;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private TextField reasonTextField;

    @FXML
    private TextField detailsTextField;
    
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
        this.cancelButton.setOnAction(event -> closeWindow());
        this.createAppointmentButton.setOnAction(event -> saveAppointment());

        this.populateAppointmentTimeComboBox();
    }

    public void setViewModel(MedMystViewModel viewmodel) {
        this.viewmodel = viewmodel;
        
        this.fnameTextField.textProperty().bindBidirectional(this.viewmodel.firstNameProperty());
		this.lnameTextField.textProperty().bindBidirectional(this.viewmodel.lastNameProperty());
		this.dobDatePicker.valueProperty().bindBidirectional(this.viewmodel.dateOfBirthProperty());
		this.appointmentTypeTextField.textProperty().bindBidirectional(this.viewmodel.appointmentTypeProperty());
		this.dateDatePicker.valueProperty().bindBidirectional(this.viewmodel.appointmentDateProperty());
		this.timeComboBox.valueProperty().bindBidirectional(this.viewmodel.appointmentTimeProperty());
		this.reasonTextField.textProperty().bindBidirectional(this.viewmodel.reasonProperty());
		this.detailsTextField.textProperty().bindBidirectional(this.viewmodel.detailsProperty());
		this.systolicPressureTextField.textProperty().bindBidirectional(this.viewmodel.systolicPressureProperty(), new NumberStringConverter());
		this.diastolicPressureTextField.textProperty().bindBidirectional(this.viewmodel.diastolicPressureProperty(), new NumberStringConverter());
		this.pulseTextField.textProperty().bindBidirectional(this.viewmodel.pulseProperty(), new NumberStringConverter());
		this.heightTextField.textProperty().bindBidirectional(this.viewmodel.heightProperty(), new NumberStringConverter());
		this.weightTextField.textProperty().bindBidirectional(this.viewmodel.weightProperty(), new NumberStringConverter());
    }

    public void setCurrentPatient(Patient patient) {
        this.currentPatient = patient;
        this.fnameTextField.setText(patient.getFName());
        this.lnameTextField.setText(patient.getLName());
        this.dobDatePicker.setValue(patient.getDateOfBirth());
        this.fnameTextField.setEditable(false);
        this.lnameTextField.setEditable(false);
        this.dobDatePicker.setEditable(false);
    }

    public void setOnFormSubmit(Runnable onFormSubmit) {
        this.onFormSubmit = onFormSubmit;
    }

    private void populateAppointmentTimeComboBox() {
        this.timeComboBox.getItems().addAll(
            "09:00 AM", "09:20 AM", "09:40 AM", "10:00 AM", "10:20 AM", "10:40 AM",
            "11:00 AM", "11:20 AM", "11:40 AM", "01:00 PM", "01:20 PM", "01:40 PM",
            "02:00 PM", "02:20 PM", "02:40 PM", "03:00 PM", "03:20 PM", "03:40 PM"
        );
    }

    private void closeWindow() {
        Stage stage = (Stage) this.cancelButton.getScene().getWindow();
        stage.close();
    }

    private void saveAppointment() {
        String doctorId = this.doctorTextField.getText();
        LocalDateTime appointmentDateTime = null;
        if (this.dateDatePicker.getValue() != null && this.timeComboBox.getValue() != null) {
            appointmentDateTime = LocalDateTime.of(
                this.dateDatePicker.getValue(),
                java.time.LocalTime.parse(this.convertTo24HourFormat(this.timeComboBox.getValue()))
            );
        }
        String appointmentType = this.appointmentTypeTextField.getText();
        String reason = this.reasonTextField.getText();
        String details = this.detailsTextField.getText();

        StringBuilder errorMessage = new StringBuilder();

        if (doctorId == null || doctorId.isEmpty()) {
            errorMessage.append("Doctor ID must not be empty.\n");
        }
        if (appointmentDateTime == null) {
            errorMessage.append("Appointment date and time must be selected.\n");
        }
        if (appointmentType == null) {
            errorMessage.append("Appointment type must be selected.\n");
        }
        if (reason == null || reason.isEmpty()) {
            errorMessage.append("Appointment reason must not be empty.\n");
        }

        if (errorMessage.length() > 0) {
            showErrorDialog(errorMessage.toString());
            return;
        }

        boolean success = this.viewmodel.addAppointment();

        if (!success) {
            showErrorDialog("Failed to create appointment. Please try again.");
            return;
        }

        if (this.onFormSubmit != null) {
            this.onFormSubmit.run();
        }

        closeWindow();
    }

    private void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    private String convertTo24HourFormat(String time) {
        return java.time.format.DateTimeFormatter.ofPattern("hh:mm a")
            .parse(time, java.time.LocalTime::from)
            .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
    }
}
