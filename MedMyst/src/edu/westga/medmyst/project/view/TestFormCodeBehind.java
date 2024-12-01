package edu.westga.medmyst.project.view;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.westga.medmyst.project.model.Appointment;
import edu.westga.medmyst.project.model.Test;
import edu.westga.medmyst.project.model.TestType;
import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The Class TestFormCodeBehind. TestFormCodeBehind provides access to the Test
 * Form GUI.
 * 
 * @version Fall 2024
 * @author tl00162
 */
public class TestFormCodeBehind {

	@FXML
	private Button addTestButton;

	@FXML
	private ComboBox<String> doctorComboBox;

	@FXML
	private Button finalizeDiagnosisButton;

	@FXML
	private TextField highValueTextField;

	@FXML
	private TextField lowValueTextField;

	@FXML
	private TableView<Test> orderedTestsTableView;

	@FXML
	private DatePicker patientDoBLabel;

	@FXML
	private TextField patientFirstNameLabel;

	@FXML
	private TextField patientLastNameLabel;

	@FXML
	private Button removeTestButton;

	@FXML
	private TableColumn<?, ?> resultTableColumn;

	@FXML
	private TextArea resultsTextArea;

	@FXML
	private Tab resultsTypeTableColumn;

	@FXML
	private DatePicker testDatePicker;

	@FXML
	private TableView<Test> testResultsTableView;

	@FXML
	private ComboBox<String> testTimeComboBox;

	@FXML
	private ComboBox<TestType> testTypeComboBox;

	@FXML
	private TableColumn<?, ?> typeTableColumn;

	@FXML
	private TableColumn<?, ?> resultTestTypeTableColumn;

	@FXML
	private TableColumn<?, ?> resultDateTableColumn;

	@FXML
	private TableColumn<?, ?> dateTableColumn;

	@FXML
	private TableColumn<?, ?> c;

	@FXML
	private TableColumn<?, ?> pastTestDateColumn;

	@FXML
	private TableColumn<?, ?> pastTestResultsColumn;

	@FXML
	private TableColumn<?, ?> pastTestTypeColumn;

	@FXML
	private TableView<Test> pastTestsTableView;

	@FXML
	private TextField unitsTextField;

	@FXML
	private RadioButton normalRadioButton;

	@FXML
	private RadioButton abnormalRadioButton;

	private MedMystViewModel viewModel;

	private Test currentTest = null;

	@FXML
	private void initialize() {

		this.dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
		this.typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("typeName"));

		this.resultTableColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
		this.resultTestTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("typeName"));
		this.resultDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

		this.pastTestResultsColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
		this.pastTestTypeColumn.setCellValueFactory(new PropertyValueFactory<>("typeName"));
		this.pastTestDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

		this.orderedTestsTableView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue != null) {
						this.populateFields(newValue);
						this.enableButtonsForAppointmentTests();
					}
				});

		this.testResultsTableView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue != null) {
						this.populateFields(newValue);
						this.enableButtonsForAppointmentTests();
					}
				});

		this.pastTestsTableView.setOnMouseClicked(event -> {
			Test selectedTest = this.pastTestsTableView.getSelectionModel().getSelectedItem();
			if (selectedTest != null) {
				this.populateFields(selectedTest);
				this.disableButtonsForPastTests();
			}
		});

		this.testTypeComboBox.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue != null) {
						this.updateTestTypeFields(newValue);
					}
				});
		this.highValueTextField.setEditable(false);
		this.lowValueTextField.setEditable(false);
		this.unitsTextField.setEditable(false);

		ToggleGroup normalityGroup = new ToggleGroup();
		this.normalRadioButton.setToggleGroup(normalityGroup);
		this.abnormalRadioButton.setToggleGroup(normalityGroup);

		this.normalRadioButton.setSelected(false);
		this.abnormalRadioButton.setSelected(false);

	}

	private void updateTestTypeFields(TestType selectedTestType) {
		this.highValueTextField.setText(String.valueOf(selectedTestType.getHigh()));
		this.lowValueTextField.setText(String.valueOf(selectedTestType.getLow()));
		this.unitsTextField.setText(selectedTestType.getUnit());
	}

	public void setViewModel(MedMystViewModel viewModel) {
		if (viewModel == null) {
			System.err.println("ViewModel is null in TestFormCodeBehind.");
			return;
		}
		this.viewModel = viewModel;

		if (this.viewModel.testHighValueProperty() == null) {
			System.err.println("testHighValueProperty is not initialized.");
			return;
		}
		if (this.viewModel.testLowValueProperty() == null) {
			System.err.println("testLowValueProperty is not initialized.");
			return;
		}

		String decimalPattern = "^\\d{0,7}(\\.\\d{0,2})?$";

		this.highValueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches(decimalPattern) && !newValue.isEmpty()) {
				this.highValueTextField.setText(oldValue);
			}
		});

		this.viewModel.testHighValueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.highValueTextField.setText(newValue.toString());
			}
		});

		this.lowValueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches(decimalPattern) && !newValue.isEmpty()) {
				this.lowValueTextField.setText(oldValue);
			}
		});

		this.viewModel.testLowValueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.lowValueTextField.setText(newValue.toString());
			}
		});

		this.unitsTextField.textProperty().bindBidirectional(this.viewModel.testUnitProperty());
		this.resultsTextArea.textProperty().bindBidirectional(this.viewModel.testResultProperty());

		this.populateDoctorComboBox();
		this.populateTestTypeComboBox();
		this.populateTestTimeComboBox();
	}

	private void populateDoctorComboBox() {
		List<String> doctorNames = this.viewModel.getDoctorNames();
		this.doctorComboBox.getItems().addAll(doctorNames);
	}

	private void populateTestTypeComboBox() {
		try {
			List<TestType> testTypes = this.viewModel.getAllTestTypes();
			this.testTypeComboBox.getItems().addAll(testTypes);
		} catch (SQLException e) {
			System.err.println("Error loading test types: " + e.getMessage());
		}
	}

	private void populateTestTimeComboBox() {
		this.testTimeComboBox.getItems().addAll("09:00 AM", "09:20 AM", "09:40 AM", "10:00 AM", "10:20 AM", "10:40 AM",
				"11:00 AM", "11:20 AM", "11:40 AM", "01:00 PM", "01:20 PM", "01:40 PM", "02:00 PM", "02:20 PM",
				"02:40 PM", "03:00 PM", "03:20 PM", "03:40 PM");
	}

	public void initializeForm(Appointment selectedAppointment) {
		if (selectedAppointment != null) {
			this.viewModel.appointmentIdProperty().set(selectedAppointment.getAppointmentId());
			this.viewModel.patientIdProperty().set(selectedAppointment.getPatientId());

			this.patientFirstNameLabel.setText(selectedAppointment.getPatient().getFName());
			this.patientLastNameLabel.setText(selectedAppointment.getPatient().getLName());
			this.patientDoBLabel.setValue(selectedAppointment.getPatient().getDateOfBirth());

			int appointmentId = selectedAppointment.getAppointmentId();
			int patientId = selectedAppointment.getPatientId();
			this.loadPastTestsForPatient(patientId, appointmentId);

			this.loadTestsForAppointment(selectedAppointment.getAppointmentId());
		}
	}

	private void loadTestsForAppointment(int appointmentId) {
		try {
			List<Test> orderedTests = this.viewModel.getTestsForAppointment(appointmentId, false);
			List<Test> completedTests = this.viewModel.getTestsForAppointment(appointmentId, true);

			this.orderedTestsTableView.getItems().setAll(orderedTests);
			this.testResultsTableView.getItems().setAll(completedTests);
		} catch (SQLException e) {
			System.err.println("Error loading tests for appointment: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	void addTest() {
		try {
			String doctor = this.doctorComboBox.getValue();
			TestType selectedTestType = this.testTypeComboBox.getValue();
			String highValueText = this.highValueTextField.getText();
			String lowValueText = this.lowValueTextField.getText();
			String unit = this.unitsTextField.getText();
			String result = this.resultsTextArea.getText();
			LocalDate testDate = this.testDatePicker.getValue();

			if (testDate == null) {
				this.showErrorDialog("Test date must be selected.");
				return;
			}

			String selectedTime = this.testTimeComboBox.getValue();
			if (selectedTime == null || selectedTime.isEmpty()) {
				this.showErrorDialog("Test time must be selected.");
				return;
			}

			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
			LocalTime testTime = LocalTime.parse(selectedTime, timeFormatter);
			LocalDateTime testDateTime = LocalDateTime.of(testDate, testTime);

			if (doctor == null || doctor.isEmpty()) {
				this.showErrorDialog("Doctor must be selected.");
				return;
			}

			if (selectedTestType == null) {
				this.showErrorDialog("Test type must be selected.");
				return;
			}

			this.viewModel.doctorIdProperty().set(this.viewModel.getDoctorIdByName(doctor));
			this.viewModel.testTypeProperty().set(selectedTestType.getTypeName());
			this.viewModel.testHighValueProperty()
					.set(!highValueText.isEmpty() ? Double.parseDouble(highValueText) : 0.0);
			this.viewModel.testLowValueProperty().set(!lowValueText.isEmpty() ? Double.parseDouble(lowValueText) : 0.0);
			this.viewModel.testUnitProperty().set(unit != null ? unit : "");
			this.viewModel.testResultProperty().set(result != null ? result : "");
			this.viewModel.testDateTimeProperty().set(testDateTime);

			boolean isNormal = this.normalRadioButton.isSelected();
			int normality = this.normalRadioButton.isSelected() ? 1 : 0;

			if (this.currentTest != null) {
				this.currentTest.setDoctorId(this.viewModel.getDoctorIdByName(doctor));
				this.currentTest.setTestType(selectedTestType);
				this.currentTest.setLow(Double.parseDouble(lowValueText.isEmpty() ? "0.0" : lowValueText));
				this.currentTest.setHigh(Double.parseDouble(highValueText.isEmpty() ? "0.0" : highValueText));
				this.currentTest.setUnitOfMeasurement(unit);
				this.currentTest.setResult(result);
				this.currentTest.setDateTime(testDateTime);

				this.viewModel.updateLabTest(this.currentTest, isNormal);

				this.loadTestsForAppointment(this.viewModel.appointmentIdProperty().get());
				this.clearForm();

				System.out.println("Test updated successfully.");
			} else {
				Test newTest = new Test(-1, this.viewModel.doctorIdProperty().get(),
						this.viewModel.patientIdProperty().get(), selectedTestType,
						Double.parseDouble(lowValueText.isEmpty() ? "0.0" : lowValueText),
						Double.parseDouble(highValueText.isEmpty() ? "0.0" : highValueText), unit, result, testDateTime,
						false, isNormal);

				int newTestId = this.viewModel.addTest(newTest, isNormal);
				System.out.println("New Test ID: " + newTestId);

				System.out.println("Linking Test to Appointment ID: " + this.viewModel.appointmentIdProperty().get());
				this.viewModel.linkTestToAppointment(newTestId, this.viewModel.appointmentIdProperty().get(),
						normality);
				this.loadTestsForAppointment(this.viewModel.appointmentIdProperty().get());
				this.clearForm();

				System.out.println("Test added successfully.");
			}
		} catch (NumberFormatException e) {
			System.err.println("Error: High and Low values must be numeric.");
			this.showErrorDialog("High and Low values must be numeric.");
		} catch (SQLException e) {
			System.err.println("Error: Failed to update or add the test.");
			this.showErrorDialog("Failed to update or add the test. Please check your inputs.");
		}
	}

	private void showErrorDialog(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	void finalizeDiagnosis() {
		Test selectedTest = this.orderedTestsTableView.getSelectionModel().getSelectedItem();
		if (selectedTest == null) {
			selectedTest = this.testResultsTableView.getSelectionModel().getSelectedItem();
		}

		if (selectedTest == null) {
			this.showErrorDialog("No test selected. Please select a test to finalize.");
			return;
		}

		try {
			if (selectedTest.isFinalized()) {
				this.showErrorDialog("This test is already finalized and cannot be modified.");
				return;
			}

			boolean confirmation = this
					.showConfirmationDialog("Finalizing a test is permanent and cannot be undone. Are you sure?");
			if (!confirmation) {
				return;
			}

			int normality = this.normalRadioButton.isSelected() ? 1 : 0;

			this.viewModel.finalizeTest(selectedTest, normality);

			this.loadTestsForAppointment(this.viewModel.appointmentIdProperty().get());

			System.out.println("Test finalized successfully.");
		} catch (SQLException e) {
			System.err.println("Error finalizing test: " + e.getMessage());
			e.printStackTrace();
			this.showErrorDialog("An error occurred while trying to finalize the test.");
		}
	}

	@FXML
	void removeTest() {
		Test selectedTest = this.orderedTestsTableView.getSelectionModel().getSelectedItem();
		if (selectedTest == null) {
			selectedTest = this.testResultsTableView.getSelectionModel().getSelectedItem();
		}

		if (selectedTest == null) {
			this.showErrorDialog("No test selected. Please select a test to remove.");
			return;
		}

		try {
			if (selectedTest.isFinalized()) {
				this.showErrorDialog("Cannot remove a finalized test.");
				return;
			}

			boolean confirmation = this.showConfirmationDialog("Are you sure you want to remove the selected test?");
			if (!confirmation) {
				return;
			}

			this.viewModel.removeTest(selectedTest);

			if (this.orderedTestsTableView.getItems().contains(selectedTest)) {
				this.orderedTestsTableView.getItems().remove(selectedTest);
			} else if (this.testResultsTableView.getItems().contains(selectedTest)) {
				this.testResultsTableView.getItems().remove(selectedTest);
			}
			this.clearForm();
		} catch (SQLException e) {
			System.err.println("Error removing test: " + e.getMessage());
			e.printStackTrace();
			this.showErrorDialog("An error occurred while trying to remove the test.");
		}
	}

	private boolean showConfirmationDialog(String message) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText(message);
		return alert.showAndWait().orElse(null) == ButtonType.OK;
	}

	public void populateFields(Test test) {
		try {
			this.currentTest = test;
			this.addTestButton.setText("Update");

			this.doctorComboBox.setValue(this.viewModel.getDoctorNameById(test.getDoctorId()));
			this.testTypeComboBox.setValue(test.getTestType());
			this.highValueTextField.setText(String.valueOf(test.getHigh()));
			this.lowValueTextField.setText(String.valueOf(test.getLow()));
			this.unitsTextField.setText(test.getUnitOfMeasurement());
			this.resultsTextArea.setText(test.getResult());
			this.testDatePicker.setValue(test.getDateTime().toLocalDate());

			LocalTime testTime = test.getDateTime().toLocalTime();
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
			String formattedTime = testTime.format(timeFormatter);
			this.testTimeComboBox.setValue(formattedTime);

			boolean isFinalized = test.isFinalized();
			this.addTestButton.setDisable(isFinalized);
			this.removeTestButton.setDisable(isFinalized);
			this.finalizeDiagnosisButton.setDisable(isFinalized);

			if (test.getNormality() == null) {
				this.normalRadioButton.setSelected(false);
				this.abnormalRadioButton.setSelected(false);
			} else if (test.getNormality()) {
				this.normalRadioButton.setSelected(true);
				this.abnormalRadioButton.setSelected(false);
			} else {
				this.normalRadioButton.setSelected(false);
				this.abnormalRadioButton.setSelected(true);
			}
		} catch (Exception e) {
			System.err.println("Error populating fields: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void clearForm() {
		this.currentTest = null;
		this.addTestButton.setText("Add Test");

		this.doctorComboBox.setValue(null);
		this.testTypeComboBox.setValue(null);
		this.highValueTextField.clear();
		this.lowValueTextField.clear();
		this.unitsTextField.clear();
		this.resultsTextArea.clear();
		this.testDatePicker.setValue(null);
		this.testTimeComboBox.setValue(null);

		this.finalizeDiagnosisButton.setDisable(false);
		this.addTestButton.setDisable(false);
		this.removeTestButton.setDisable(false);
	}

	private void loadPastTestsForPatient(int patientId, int currentAppointmentId) {
		try {
			List<Test> pastTests = this.viewModel.getPastTestsForPatient(patientId, currentAppointmentId);
			this.pastTestsTableView.getItems().setAll(pastTests);
		} catch (SQLException e) {
			System.err.println("Error loading past tests: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void enableButtonsForAppointmentTests() {
		if (this.currentTest != null && this.currentTest.isFinalized()) {
			this.addTestButton.setDisable(true);
			this.removeTestButton.setDisable(true);
			this.finalizeDiagnosisButton.setDisable(true);
		} else {
			this.addTestButton.setDisable(false);
			this.removeTestButton.setDisable(false);
			this.finalizeDiagnosisButton.setDisable(false);
		}
	}

	private void disableButtonsForPastTests() {
		this.addTestButton.setDisable(true);
		this.removeTestButton.setDisable(true);
		this.finalizeDiagnosisButton.setDisable(true);
	}
}