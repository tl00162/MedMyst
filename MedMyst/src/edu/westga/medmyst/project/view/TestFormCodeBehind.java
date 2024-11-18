package edu.westga.medmyst.project.view;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.westga.medmyst.project.model.Patient;
import edu.westga.medmyst.project.model.Test;
import edu.westga.medmyst.project.model.TestType;
import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private TextField unitsTextField;

	private MedMystViewModel viewModel;

	private Test currentTest = null;

	@FXML
	private void initialize() {

		this.dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
		this.typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("typeName"));

		this.resultTableColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
		this.resultTestTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("typeName"));
		this.resultDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

		this.orderedTestsTableView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue != null) {
						this.populateFields(newValue);
					}
				});

		this.testResultsTableView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue != null) {
						this.populateFields(newValue);
					}
				});

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
		List<TestType> testTypes = this.viewModel.getTestTypes();
		this.testTypeComboBox.getItems().addAll(testTypes);
	}

	private void populateTestTimeComboBox() {
		this.testTimeComboBox.getItems().addAll("09:00 AM", "09:20 AM", "09:40 AM", "10:00 AM", "10:20 AM", "10:40 AM",
				"11:00 AM", "11:20 AM", "11:40 AM", "01:00 PM", "01:20 PM", "01:40 PM", "02:00 PM", "02:20 PM",
				"02:40 PM", "03:00 PM", "03:20 PM", "03:40 PM");
	}

	public void initializeForm(Patient selectedPatient) {
		if (selectedPatient != null) {
			this.patientFirstNameLabel.setText(selectedPatient.getFName());
			this.patientLastNameLabel.setText(selectedPatient.getLName());
			this.patientDoBLabel.setValue(selectedPatient.getDateOfBirth());
			this.viewModel.patientIdProperty().set(selectedPatient.getPatientId());

			this.loadCompleteTests();
			this.loadIncompleteTests();
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

			if (this.currentTest != null) {
				this.currentTest.setDoctorId(this.viewModel.getDoctorIdByName(doctor));
				this.currentTest.setTestType(selectedTestType);
				this.currentTest.setLow(Double.parseDouble(lowValueText.isEmpty() ? "0.0" : lowValueText));
				this.currentTest.setHigh(Double.parseDouble(highValueText.isEmpty() ? "0.0" : highValueText));
				this.currentTest.setUnitOfMeasurement(unit);
				this.currentTest.setResult(result);
				this.currentTest.setDateTime(testDateTime);

				this.viewModel.updateLabTest(this.currentTest);

				this.loadCompleteTests();
				this.loadIncompleteTests();
				this.clearForm();

				System.out.println("Test updated successfully.");
			} else {
				if (this.viewModel.addTest()) {
					this.orderedTestsTableView.getItems()
							.add(new Test(-1, this.viewModel.doctorIdProperty().get(),
									this.viewModel.patientIdProperty().get(), selectedTestType,
									Double.parseDouble(lowValueText.isEmpty() ? "0.0" : lowValueText),
									Double.parseDouble(highValueText.isEmpty() ? "0.0" : highValueText), unit, result,
									testDateTime, false));
					this.clearForm();
					System.out.println("Test added successfully.");
				} else {
					System.err.println("Failed to add test. ViewModel returned false.");
					this.showErrorDialog("Failed to add test. Please check your inputs.");
				}
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

			this.viewModel.finalizeTest(selectedTest);

			this.loadCompleteTests();
			this.loadIncompleteTests();

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
			System.out.println("Test removed successfully.");
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

	private void loadCompleteTests() {
		try {
			List<Test> completeTests = this.viewModel.getCompleteTestsForPatient();
			this.testResultsTableView.getItems().setAll(completeTests);
		} catch (SQLException e) {
			System.err.println("Error loading complete tests: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void loadIncompleteTests() {
		try {
			List<Test> incompleteTests = this.viewModel.getIncompleteTestsForPatient();
			this.orderedTestsTableView.getItems().setAll(incompleteTests);
		} catch (SQLException e) {
			System.err.println("Error loading incomplete tests: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void populateFields(Test test) {
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
}