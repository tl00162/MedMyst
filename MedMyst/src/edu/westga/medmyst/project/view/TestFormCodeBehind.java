package edu.westga.medmyst.project.view;

import java.util.List;

import edu.westga.medmyst.project.model.Patient;
import edu.westga.medmyst.project.model.TestType;
import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
	private TableColumn<?, ?> descriptionTableColumn;

	@FXML
	private ComboBox<String> doctorComboBox;

	@FXML
	private Button finalizeDiagnosisButton;

	@FXML
	private TextField highValueTextField;

	@FXML
	private TextField lowValueTextField;

	@FXML
	private TableView<?> orderedTestsTableView;

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
	private TextField resultsTextField;

	@FXML
	private Tab resultsTypeTableColumn;

	@FXML
	private DatePicker testDatePicker;

	@FXML
	private TableView<?> testResultsTableView;

	@FXML
	private ComboBox<String> testTimeComboBox;

	@FXML
	private ComboBox<TestType> testTypeComboBox;

	@FXML
	private TableColumn<?, ?> typeTableColumn;

	@FXML
	private TextField unitsTextField;

	private MedMystViewModel viewModel;

	@FXML
	private void initialize() {

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

		this.highValueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				double value = Double.parseDouble(newValue);
				this.viewModel.testHighValueProperty().set(value);
			} catch (NumberFormatException e) {
				this.highValueTextField.setText(oldValue);
			}
		});
		this.viewModel.testHighValueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.highValueTextField.setText(newValue.toString());
			}
		});

		this.lowValueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				double value = Double.parseDouble(newValue);
				this.viewModel.testLowValueProperty().set(value);
			} catch (NumberFormatException e) {
				this.lowValueTextField.setText(oldValue);
			}
		});
		this.viewModel.testLowValueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.lowValueTextField.setText(newValue.toString());
			}
		});

		this.unitsTextField.textProperty().bindBidirectional(this.viewModel.testUnitProperty());
		this.resultsTextField.textProperty().bindBidirectional(this.viewModel.testResultProperty());

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
		}
	}

	@FXML
	void addTest() {
		//TODO
	}

	@FXML
	void finalizeDiagnosis() {
		//TODO
	}

	@FXML
	void removeTest() {
		//TODO
	}

}