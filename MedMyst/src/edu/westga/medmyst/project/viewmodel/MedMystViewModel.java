package edu.westga.medmyst.project.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.westga.medmyst.project.dal.AdminDAL;
import edu.westga.medmyst.project.dal.AppointmentDAL;
import edu.westga.medmyst.project.dal.AppointmentTestDAL;
import edu.westga.medmyst.project.dal.AppointmentTypeDAL;
import edu.westga.medmyst.project.dal.CheckupDAL;
import edu.westga.medmyst.project.dal.DoctorDAL;
import edu.westga.medmyst.project.dal.LoginDAL;
import edu.westga.medmyst.project.dal.PatientDAL;
import edu.westga.medmyst.project.dal.TestDAL;
import edu.westga.medmyst.project.dal.TestTypeDAL;
import edu.westga.medmyst.project.model.Appointment;
import edu.westga.medmyst.project.model.AppointmentType;
import edu.westga.medmyst.project.model.Checkup;
import edu.westga.medmyst.project.model.Doctor;
import edu.westga.medmyst.project.model.Login;
import edu.westga.medmyst.project.model.Patient;
import edu.westga.medmyst.project.model.Test;
import edu.westga.medmyst.project.model.TestType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class MedMystViewModel
 * 
 * MedMystViewModel mediates between the view and the rest of the application.
 * 
 * @author tl00162
 * @version Fall 2024
 */
public class MedMystViewModel {

	private StringProperty username;
	private StringProperty password;
	private StringProperty loginSuccess;

	private IntegerProperty patientId;
	private StringProperty firstName;
	private StringProperty lastName;
	private ObjectProperty<LocalDate> dateOfBirth;
	private StringProperty gender;
	private StringProperty phoneNumber;
	private StringProperty address1;
	private StringProperty address2;
	private StringProperty state;
	private StringProperty zip;
	private BooleanProperty isActive;
	private BooleanProperty canAddAppointment;

	private IntegerProperty appointmentId;
	private IntegerProperty doctorId;
	private StringProperty doctorFirstName;
	private StringProperty doctorLastName;
	private StringProperty doctorSpecialty;
	private StringProperty reason;
	private StringProperty details;
	private StringProperty appointmentType;
	private ObjectProperty<LocalDate> appointmentDate;
	private StringProperty appointmentTime;
	private ObjectProperty<LocalDateTime> appointmentDateTime;
	private DoubleProperty bodyTemperature;
	private IntegerProperty systolicPressure;
	private IntegerProperty diastolicPressure;
	private IntegerProperty pulse;
	private StringProperty symptoms;
	private DoubleProperty height;
	private DoubleProperty weight;
	private StringProperty initialDiagnosis;
	private StringProperty finalDiagnosis;

	private StringProperty testType;
	private DoubleProperty testHighValue;
	private DoubleProperty testLowValue;
	private StringProperty testUnit;
	private StringProperty testResult;
	private ObjectProperty<LocalDateTime> testDateTime;

	private LoginDAL loginDAL;
	private Login currentUser;

	private PatientDAL patientDAL;
	private AppointmentDAL appointmentDAL;
	private DoctorDAL doctorDAL;
	private AppointmentTypeDAL appointmentTypeDAL;
	private TestDAL testDAL;
	private TestTypeDAL testTypeDAL;
	private AppointmentTestDAL appointmentTestDAL;
	private CheckupDAL checkupDAL;
	private AdminDAL adminDAL;

	/**
	 * Constructs a new MedMystViewModel and initializes its properties.
	 * 
	 */
	public MedMystViewModel() {
		this.username = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
		this.loginSuccess = new SimpleStringProperty();
		this.patientId = new SimpleIntegerProperty();
		this.firstName = new SimpleStringProperty();
		this.lastName = new SimpleStringProperty();
		this.dateOfBirth = new SimpleObjectProperty<>();
		this.gender = new SimpleStringProperty();
		this.phoneNumber = new SimpleStringProperty();
		this.address1 = new SimpleStringProperty();
		this.address2 = new SimpleStringProperty();
		this.state = new SimpleStringProperty();
		this.zip = new SimpleStringProperty();
		this.appointmentId = new SimpleIntegerProperty();
		this.doctorId = new SimpleIntegerProperty();
		this.doctorFirstName = new SimpleStringProperty();
		this.doctorLastName = new SimpleStringProperty();
		this.doctorSpecialty = new SimpleStringProperty();
		this.reason = new SimpleStringProperty();
		this.details = new SimpleStringProperty();
		this.appointmentType = new SimpleStringProperty();
		this.appointmentDate = new SimpleObjectProperty<>();
		this.appointmentTime = new SimpleStringProperty();
		this.appointmentDateTime = new SimpleObjectProperty<>();
		this.bodyTemperature = new SimpleDoubleProperty();
		this.systolicPressure = new SimpleIntegerProperty();
		this.diastolicPressure = new SimpleIntegerProperty();
		this.pulse = new SimpleIntegerProperty();
		this.symptoms = new SimpleStringProperty();
		this.height = new SimpleDoubleProperty();
		this.weight = new SimpleDoubleProperty();
		this.initialDiagnosis = new SimpleStringProperty();
		this.finalDiagnosis = new SimpleStringProperty();
		this.loginDAL = new LoginDAL();
		this.currentUser = null;
		this.patientDAL = new PatientDAL();
		this.appointmentDAL = new AppointmentDAL();
		this.doctorDAL = new DoctorDAL();
		this.appointmentTypeDAL = new AppointmentTypeDAL();
		this.testDAL = new TestDAL();
		this.testTypeDAL = new TestTypeDAL();
		this.adminDAL = new AdminDAL();
		this.appointmentTestDAL = new AppointmentTestDAL();
		this.checkupDAL = new CheckupDAL();
		this.isActive = new SimpleBooleanProperty();
		this.canAddAppointment = new SimpleBooleanProperty(false);

		this.testType = new SimpleStringProperty();
		this.testHighValue = new SimpleDoubleProperty();
		this.testLowValue = new SimpleDoubleProperty();
		this.testUnit = new SimpleStringProperty();
		this.testResult = new SimpleStringProperty();
		this.testDateTime = new SimpleObjectProperty<>();
	}

	/**
	 * Gets the username property that is bound to the username text field in the
	 * view.
	 * 
	 * @return the username StringProperty
	 */
	public StringProperty getUsername() {
		return this.username;
	}

	/**
	 * Gets the isActive property that is bound to the radio buttons in the view.
	 * 
	 * @return the isActive property
	 */
	public BooleanProperty getIsActive() {
		return this.isActive;
	}

	/**
	 * Gets the password property that is bound to the password text field in the
	 * view.
	 * 
	 * @return the password StringProperty
	 */
	public StringProperty getPassword() {
		return this.password;
	}

	/**
	 * Gets the login success message property that is bound to the login success
	 * label in the view.
	 * 
	 * This property is updated based on the outcome of the login attempt.
	 * 
	 * @return the login success StringProperty
	 */
	public StringProperty getLoginSuccess() {
		return this.loginSuccess;
	}

	/**
	 * Returns the current logged-in user.
	 * 
	 * @return the current AppUser if logged in, or null if no user is logged in
	 */
	public Login getCurrentUser() {
		return this.currentUser;
	}

	/**
	 * Returns the patientIdProperty
	 * 
	 * @return the patientIdProperty
	 */
	public IntegerProperty patientIdProperty() {
		return this.patientId;
	}

	/**
	 * Returns the firstNameProperty
	 * 
	 * @return the firstNameProperty
	 */
	public StringProperty firstNameProperty() {
		return this.firstName;
	}

	/**
	 * Returns the lastNameProperty
	 * 
	 * @return the lastNameProperty
	 */
	public StringProperty lastNameProperty() {
		return this.lastName;
	}

	/**
	 * Returns the dateOfBirthProperty
	 * 
	 * @return the dateOfBirthProperty
	 */
	public ObjectProperty<LocalDate> dateOfBirthProperty() {
		return this.dateOfBirth;
	}

	/**
	 * Returns the genderProperty
	 * 
	 * @return the genderProperty
	 */
	public StringProperty genderProperty() {
		return this.gender;
	}

	/**
	 * Returns the phoneNumberProperty
	 * 
	 * @return the phoneNumberProperty
	 */
	public StringProperty phoneNumberProperty() {
		return this.phoneNumber;
	}

	/**
	 * Returns the address1Property
	 * 
	 * @return the address1Property
	 */
	public StringProperty address1Property() {
		return this.address1;
	}

	/**
	 * Returns the address2Property
	 * 
	 * @return the address2Property
	 */
	public StringProperty address2Property() {
		return this.address2;
	}

	/**
	 * Returns the stateProperty
	 * 
	 * @return the stateProperty
	 */
	public StringProperty stateProperty() {
		return this.state;
	}

	/**
	 * Returns the zipProperty
	 * 
	 * @return the zipProperty
	 */
	public StringProperty zipProperty() {
		return this.zip;
	}

	/**
	 * Returns the appointmentIdProperty
	 * 
	 * @return the appointmentIdProperty
	 */
	public IntegerProperty appointmentIdProperty() {
		return this.appointmentId;
	}

	/**
	 * Returns the doctorIdProperty
	 * 
	 * @return the doctorIdProperty
	 */
	public IntegerProperty doctorIdProperty() {
		return this.doctorId;
	}

	/**
	 * Returns the doctorFirstNameProperty
	 * 
	 * @return the doctorFirstNameProperty
	 */
	public StringProperty doctorFirstNameProperty() {
		return this.doctorFirstName;
	}

	/**
	 * Returns the doctorLastNameProperty.
	 *
	 * @return the doctorLastNameProperty
	 */
	public StringProperty doctorLastNameProperty() {
		return this.doctorLastName;
	}

	/**
	 * Returns the doctorSpecialtyProperty.
	 *
	 * @return the doctorSpecialtyProperty
	 */
	public StringProperty doctorSpecialtyProperty() {
		return this.doctorSpecialty;
	}

	/**
	 * Returns the reasonProperty
	 * 
	 * @return the reasonProperty
	 */
	public StringProperty reasonProperty() {
		return this.reason;
	}

	/**
	 * Returns the detailsProperty
	 * 
	 * @return the detailsProperty
	 */
	public StringProperty detailsProperty() {
		return this.details;
	}

	/**
	 * Returns the appointmentTypeProperty
	 * 
	 * @return the appointmentTypeProperty
	 */
	public StringProperty appointmentTypeProperty() {
		return this.appointmentType;
	}

	/**
	 * Returns the appointmentDateProperty.
	 *
	 * @return the appointmentDateProperty
	 */
	public ObjectProperty<LocalDate> appointmentDateProperty() {
		return this.appointmentDate;
	}

	/**
	 * Returns the appointmentTimeProperty.
	 *
	 * @return the appointmentTimeProperty
	 */
	public StringProperty appointmentTimeProperty() {
		return this.appointmentTime;
	}

	/**
	 * Returns the appointmentDateTimeProperty
	 * 
	 * @return the appointmentDateTimeProperty
	 */
	public ObjectProperty<LocalDateTime> appointmentDateTimeProperty() {
		return this.appointmentDateTime;
	}

	/**
	 * Returns the bodyTemperatureProperty
	 * 
	 * @return the bodyTemperatureProperty
	 */
	public DoubleProperty bodyTemperatureProperty() {
		return this.bodyTemperature;
	}

	/**
	 * Returns the systolicPressureProperty.
	 *
	 * @return the systolicPressureProperty
	 */
	public IntegerProperty systolicPressureProperty() {
		return this.systolicPressure;
	}

	/**
	 * Returns the diastolicPressureProperty.
	 *
	 * @return the diastolicPressureProperty
	 */
	public IntegerProperty diastolicPressureProperty() {
		return this.diastolicPressure;
	}

	/**
	 * Returns the pulseProperty.
	 *
	 * @return the pulseProperty
	 */
	public IntegerProperty pulseProperty() {
		return this.pulse;
	}

	/**
	 * Returns the symptomsProperty
	 * 
	 * @return the symptomsProperty
	 */
	public StringProperty symptomsProperty() {
		return this.symptoms;
	}

	/**
	 * Returns the heightProperty.
	 *
	 * @return the heightProperty
	 */
	public DoubleProperty heightProperty() {
		return this.height;
	}

	/**
	 * Returns the weightProperty.
	 *
	 * @return the weightProperty
	 */
	public DoubleProperty weightProperty() {
		return this.weight;
	}

	/**
	 * Returns the initialDiagnosisProperty
	 * 
	 * @return the initialDiagnosisProperty
	 */
	public StringProperty initialDiagnosisProperty() {
		return this.initialDiagnosis;
	}

	/**
	 * Returns the finalDiagnosisProperty
	 * 
	 * @return the finalDiagnosisProperty
	 */
	public StringProperty finalDiagnosisProperty() {
		return this.finalDiagnosis;
	}

	/**
	 * Logs the user out by clearing the current user.
	 */
	public void logout() {
		this.currentUser = null;
		this.loginSuccess.set("You have been logged out.");
	}

	/**
	 * Gets the test type property.
	 * 
	 * @return a StringProperty representing the type of the test
	 */
	public StringProperty testTypeProperty() {
		return this.testType;
	}

	/**
	 * Gets the high value property for the test.
	 * 
	 * @return a DoubleProperty representing the high threshold value for the test
	 */
	public DoubleProperty testHighValueProperty() {
		return this.testHighValue;
	}

	/**
	 * Gets the low value property for the test.
	 * 
	 * @return a DoubleProperty representing the low threshold value for the test
	 */
	public DoubleProperty testLowValueProperty() {
		return this.testLowValue;
	}

	/**
	 * Gets the unit property for the test.
	 * 
	 * @return a StringProperty representing the unit of measurement for the test
	 */
	public StringProperty testUnitProperty() {
		return this.testUnit;
	}

	/**
	 * Gets the result property for the test.
	 * 
	 * @return a StringProperty representing the result of the test
	 */
	public StringProperty testResultProperty() {
		return this.testResult;
	}

	/**
	 * Gets the date and time property for the test.
	 * 
	 * @return an ObjectProperty representing the date and time of the test
	 */
	public ObjectProperty<LocalDateTime> testDateTimeProperty() {
		return this.testDateTime;
	}
	
	/**
	 * Attempts to create a new account via LoginDAL
	 * @param accountType the type of account
	 * @param password the password
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param username the username
	 * @return true if account created
	 * @throws SQLException
	 */
	public boolean createNewAccount(String accountType, String password, String firstName, String lastName, String username) throws SQLException {
	    return this.loginDAL.createAccount(accountType, password, firstName, lastName, username);
	}


	/**
	 * Validates the login credentials by checking the username and password against
	 * the database using the LoginDAL.
	 * 
	 * If either the username or password is empty, an error message is set. If the
	 * login is valid, the method returns the role of the user (e.g., "admin" or
	 * "nurse").
	 * 
	 * @return the role of the user if the login is valid, null otherwise
	 * @throws SQLException if there is an issue with the database connection or
	 *                      query execution
	 */
	public String validateLogin() throws SQLException {
		String usernameValue = this.username.get();
		String passwordValue = this.password.get();

		if (usernameValue == null || usernameValue.isEmpty() || passwordValue == null || passwordValue.isEmpty()) {
			this.loginSuccess.set("Username and password must not be empty.");
			return null;
		}

		Login login = this.loginDAL.checkValidLogin(usernameValue, passwordValue);

		if (login != null) {
			this.loginSuccess.set("Login successful! Welcome, " + login.getFirstName() + " " + login.getLastName());
			this.currentUser = login;
			return login.getRole();
		} else {
			this.loginSuccess.set("Invalid username or password.");
			return null;
		}
	}

	/**
	 * Tries to add new Patient to the DB
	 * 
	 * @return true if patient added else false
	 */
	public boolean addPatient() {
		if (this.firstName.get().isEmpty() || this.lastName.get().isEmpty() || this.dateOfBirth.get() == null
				|| this.gender.get().isEmpty() || this.address1.get().isEmpty() || this.state.get().isEmpty()
				|| this.zip.get().isEmpty()) {

			return false;
		}

		Patient newPatient = new Patient(this.patientId.get(), this.firstName.get(), this.lastName.get(),
				this.dateOfBirth.get(), this.gender.get(), this.phoneNumber.get(), this.address1.get(),
				this.address2.get(), this.state.get(), this.zip.get(), this.isActive.get());
		try {

			this.patientDAL.addPatient(newPatient);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Tries to update existing patient in the DB
	 * 
	 * @param patientToUpdate the patient to Update
	 * @return true if updated, else false
	 */
	public boolean updatePatient(Patient patientToUpdate) {
		if (this.firstName.get().isEmpty() || this.lastName.get().isEmpty() || this.dateOfBirth.get() == null
				|| this.gender.get().isEmpty() || this.address1.get().isEmpty() || this.state.get().isEmpty()
				|| this.zip.get().isEmpty()) {

			return false;
		}

		try {

			this.patientDAL.updatePatient(patientToUpdate);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Loads the specified patient's data
	 * 
	 * @param patient the specified patient
	 */
	public void loadPatientData(Patient patient) {
		this.patientId.set(patient.getPatientId());
		this.firstName.set(patient.getFName());
		this.lastName.set(patient.getLName());
		this.dateOfBirth.set(patient.getDateOfBirth());
		this.gender.set(patient.getGender());
		this.phoneNumber.set(patient.getPhoneNumber());
		this.address1.set(patient.getAddress1());
		this.address2.set(patient.getAddress2());
		this.state.set(patient.getState());
		this.zip.set(patient.getZip());

	}

	/**
	 * Gets the list of all patients
	 * 
	 * @return the list of patients
	 */
	public List<Patient> getPatients() {
		try {
			return this.patientDAL.getAllPatients();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Gets the patient by Id
	 * 
	 * @param patientId
	 * @return the found patient or null
	 */
	public Patient getPatientById(int patientId) {
		try {
			return this.patientDAL.getPatientById(patientId);
		} catch (SQLException e) {
			System.err.println("Error retrieving patient with ID: " + patientId);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the doctor names
	 * 
	 * @return the list of names
	 */
	public List<String> getDoctorNames() {
		List<String> doctorNames = new ArrayList<>();
		try {
			List<Doctor> doctors = this.doctorDAL.getAllDoctors();
			for (Doctor doctor : doctors) {
				doctorNames.add(doctor.getFirstName() + " " + doctor.getLastName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doctorNames;
	}

	/**
	 * Gets the doctor's Id
	 * 
	 * @param doctorName the doctor to find Id
	 * @return the id
	 */
	public int getDoctorIdByName(String doctorName) {
		try {
			List<Doctor> doctors = this.doctorDAL.getAllDoctors();
			for (Doctor doctor : doctors) {
				if ((doctor.getFirstName() + " " + doctor.getLastName()).equals(doctorName)) {
					return doctor.getDoctorId();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Gets the doctors name from their Id
	 * 
	 * @param doctorId the specified Id
	 * @return the matching name
	 */
	public String getDoctorNameById(int doctorId) {
		try {
			Doctor doctor = this.doctorDAL.getDoctorById(doctorId);
			return doctor.getFirstName() + " " + doctor.getLastName();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Tries to add a new Appointment to the DB.
	 * 
	 * @return true if the appointment is added, else false
	 */
	public boolean addAppointment() {
		if (this.patientId.get() == 0 || this.doctorId.get() == 0 || this.appointmentDateTime.get() == null
				|| this.reason.get().isEmpty() || this.appointmentType.get().isEmpty()) {
			return false;
		}
		if (!this.isDoctorAvailable(this.doctorId.get(), this.appointmentDateTime.get())) {
			return false;
		}
		Appointment newAppointment = new Appointment(this.appointmentId.get(), this.patientId.get(),
				this.doctorId.get(), this.doctorFirstName.get(), this.doctorLastName.get(), this.doctorSpecialty.get(),
				this.reason.get(), this.details.get(), this.appointmentType.get(), this.appointmentDateTime.get());
		newAppointment.setFinalDiagosis(this.finalDiagnosis.get());

		try {
			int generatedAppointmentId = this.appointmentDAL.addAppointment(newAppointment);

			Checkup newCheckup = new Checkup(generatedAppointmentId,
					this.loginDAL.getUserIdByUsername(this.username.get()), this.bodyTemperature.get(),
					this.diastolicPressure.get(), this.systolicPressure.get(), this.pulse.get(), this.symptoms.get(),
					this.height.get(), this.weight.get(), this.initialDiagnosis.get());
			newAppointment.setCheckup(newCheckup);
			this.checkupDAL.addCheckup(newCheckup);
			return true;
		} catch (SQLException e) {
			System.err.println("SQL Error during appointment creation:");
			System.err.println("Error Code: " + e.getErrorCode());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Message: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Tries to update an existing Appointment in the DB.
	 * 
	 * @param appointmentToUpdate the appointment to update
	 * @return true if updated, else false
	 */
	public boolean updateAppointment(Appointment appointmentToUpdate) {
		if (appointmentToUpdate.getDateTime().isBefore(LocalDateTime.now())) {
			System.out.println("Cannot update: Appointment time has passed.");
			return false;
		}

		appointmentToUpdate.setFinalDiagosis(this.finalDiagnosis.get());

		try {
			this.appointmentDAL.updateAppointment(appointmentToUpdate);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets the list of all appointments.
	 * 
	 * @return the list of appointments
	 */
	public List<Appointment> getAppointments() {
		try {
			List<Appointment> appointments = this.appointmentDAL.getAllAppointments();
			return appointments;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Retrieves all appointment types from the database as a list of strings.
	 * 
	 * @return a list of appointment type names as strings
	 */
	public List<String> getAppointmentTypes() {
		List<String> appointmentTypeNames = new ArrayList<>();

		try {
			List<AppointmentType> appointmentTypes = this.appointmentTypeDAL.getAllAppointmentTypes();
			for (AppointmentType type : appointmentTypes) {
				appointmentTypeNames.add(type.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return appointmentTypeNames;
	}

	/**
	 * Searches for appointments based on given criteria.
	 * 
	 * @param patientId the patient ID to filter by
	 * @param doctorId  the doctor ID to filter by
	 * @return a list of matching appointments
	 */
	public List<Appointment> searchAppointments(int patientId, int doctorId) {
		try {
			List<Appointment> appointments = this.appointmentDAL.getAppointmentsByCriteria(patientId, doctorId);
			return appointments;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Gets the list of appointments for a specified patient
	 * 
	 * @param patientId the specified patient
	 * @return the list of appointments
	 */
	public List<Appointment> getAppointmentsByPatientId(int patientId) {
		try {
			return this.appointmentDAL.getAppointmentsByCriteria(patientId, -1);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Gets a list of appointments based on the provided info
	 * 
	 * @param firstName patient's first name
	 * @param lastName  patient's last name
	 * @param dob       patient's dob
	 * @return the list of appointments
	 */
	public List<Appointment> searchAppointmentsByPatientInfo(String firstName, String lastName, LocalDate dob) {
		List<Appointment> matchingAppointments = new ArrayList<>();

		try {
			List<Patient> matchingPatients = this.patientDAL.searchPatients(firstName, lastName, dob);

			for (Patient patient : matchingPatients) {
				matchingAppointments.addAll(this.getAppointmentsByPatientId(patient.getPatientId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return matchingAppointments;
	}

	/**
	 * Loads the specified appointment's data into the ViewModel properties.
	 * 
	 * @param appointment The appointment to load data from.
	 */
	public void loadAppointmentData(Appointment appointment) {
		this.appointmentId.set(appointment.getAppointmentId());
		this.patientId.set(appointment.getPatientId());
		this.doctorId.set(appointment.getDoctorId());
		this.reason.set(appointment.getReason());
		this.details.set(appointment.getDetails());
		this.appointmentType.set(appointment.getAppointmentType());
		this.appointmentDate.set(appointment.getDateTime().toLocalDate());
		this.appointmentTime.set(appointment.getDateTime().toLocalTime().toString());
		this.appointmentDateTime.set(appointment.getDateTime());
		this.finalDiagnosis.set(appointment.getFinalDiagnosis());

		try {
			Doctor doctor = this.doctorDAL.getDoctorById(appointment.getDoctorId());
			if (doctor != null) {
				this.doctorFirstName.set(doctor.getFirstName());
				this.doctorLastName.set(doctor.getLastName());
				this.doctorSpecialty.set(doctor.getSpecialty());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Checkup checkup = appointment.getCheckup();

		this.systolicPressure.set(checkup.getSystolicBloodPressure());
		this.diastolicPressure.set(checkup.getDiastolicBloodPressure());
		this.bodyTemperature.set(checkup.getBodyTemperature());
		this.pulse.set(checkup.getPulse());
		this.height.set(checkup.getHeight());
		this.weight.set(checkup.getWeight());
		this.symptoms.set(checkup.getSymptoms());
		this.initialDiagnosis.set(checkup.getInitialDiagnosis());
	}

	/**
	 * searches for the specified patient based on provided criteria.
	 * 
	 * @param firstName the first name
	 * @param lastName  the last name
	 * @param dob       the patients dob
	 * @return the matching patient
	 */
	public List<Patient> searchPatients(String firstName, String lastName, LocalDate dob) {
		try {
			List<Patient> patients = this.patientDAL.searchPatients(firstName, lastName, dob);

			if (patients.isEmpty()) {
				return this.patientDAL.getAllPatients();
			}
			return patients;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Checks if a doctor is already booked for that DateTime
	 * 
	 * @param doctorId the specified doctor
	 * @param dateTime the specified time
	 * @return true if doctor is available, else false
	 */
	public boolean isDoctorAvailable(int doctorId, LocalDateTime dateTime) {
		try {
			List<Appointment> doctorAppointments = this.appointmentDAL.getAppointmentsByDoctorId(doctorId);
			for (Appointment appointment : doctorAppointments) {
				if (appointment.getDateTime().equals(dateTime)) {
					return false;
				}
			}
			return true;
		} catch (SQLException e) {
			System.err.println("SQL Error while checking doctor availability:");
			System.err.println("Error Code: " + e.getErrorCode());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Message: " + e.getMessage());
			e.printStackTrace();

			return false;
		}
	}

	/**
	 * Returns the BooleanProperty that indicates if the "Add Appointment" button
	 * should be enabled. This property is bound to the button's disable property in
	 * the view.
	 *
	 * @return the canAddAppointment BooleanProperty
	 */
	public BooleanProperty canAddAppointmentProperty() {
		return this.canAddAppointment;
	}

	/**
	 * Updates the canAddAppointment property based on the selected patient's active
	 * status. If a patient is selected and is active, the property will be set to
	 * true, enabling the "Add Appointment" button. If no patient is selected or if
	 * the patient is inactive, the property will be set to false, disabling the
	 * button.
	 *
	 * @param selectedPatient the currently selected patient, or null if no patient
	 *                        is selected
	 */
	public void updateCanAddAppointment(Patient selectedPatient) {
		if (selectedPatient == null) {
			this.canAddAppointment.set(false);
		} else {
			this.canAddAppointment.set(selectedPatient.getActiveStatus());
		}
	}

	/**
	 * Updates the specified checkup in the DBbbbbbbbbbbbbb
	 * 
	 * @param currentCheckup the checkup to update
	 * @return true if updated
	 */
	public boolean updateCheckup(Checkup currentCheckup) {
		try {
			this.checkupDAL.updateCheckup(currentCheckup);
			return true;
		} catch (SQLException e) {
			System.err.println("SQL Error while updating checkup:");
			System.err.println("Error Code: " + e.getErrorCode());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Message: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets the list of all available test types.
	 * 
	 * @return a List of TestType objects representing all available test types
	 */
	public List<TestType> getAllTestTypes() throws SQLException {
		try {
			return this.testTypeDAL.getAllLabTestTypes();
		} catch (SQLException e) {
			System.err.println("Error retrieving test types from database: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Adds a new test to the database and links it to the appointment with the
	 * provided normality.
	 * 
	 * @param newTest  the new test to add
	 * @param isNormal the normality of the test (true for normal, false for
	 *                 abnormal)
	 * @return the ID of the newly added test, or -1 if the operation fails
	 */
	public int addTest(Test newTest, boolean isNormal) {

		if (newTest.getPatientId() == 0 || newTest.getDoctorId() == 0 || newTest.getTestType().getTypeName() == null
				|| newTest.getDateTime() == null) {
			System.err.println("Error: Missing required fields for lab test creation.");
			return -1;
		}

		int newTestId = -1;
		try {
			newTestId = this.testDAL.addLabTest(newTest, isNormal);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newTestId;
	}

	/**
	 * Retrieves a list of completed tests for the currently selected patient.
	 * 
	 * @return a list of completed tests for the patient
	 * @throws SQLException if a database access error occurs
	 */
	public List<Test> getCompleteTestsForPatient() throws SQLException {
		int patientId = this.patientIdProperty().get();
		return this.testDAL.getCompleteTestsByPatientId(patientId);
	}

	/**
	 * Retrieves a list of incomplete tests for the currently selected patient.
	 * 
	 * @return a list of incomplete tests for the patient, or an empty list if no
	 *         patient is selected
	 * @throws SQLException if a database access error occurs
	 */
	public List<Test> getIncompleteTestsForPatient() throws SQLException {
		if (this.patientIdProperty().get() == 0) {
			return new ArrayList<>();
		}
		return this.testDAL.getIncompleteTestsByPatientId(this.patientIdProperty().get());
	}

	/**
	 * Removes the specified test from the database.
	 * 
	 * @param test the test to be removed
	 * @throws SQLException if a database access error occurs
	 */
	public void removeTest(Test test) throws SQLException {
		this.testDAL.removeTestById(test.getTestId());
	}

	/**
	 * Finalizes the specified test, marking it as completed in the database and
	 * updating its normality.
	 * 
	 * @param test      the test to be finalized
	 * @param normality the normality of the test (1 for normal, 0 for abnormal)
	 * @throws SQLException             if a database access error occurs
	 * @throws IllegalArgumentException if the test is null or the normality is
	 *                                  invalid
	 */
	public void finalizeTest(Test test, int normality) throws SQLException {
		if (test == null) {
			throw new IllegalArgumentException("Test cannot be null.");
		}

		if (normality != 0 && normality != 1) {
			throw new IllegalArgumentException("Normality must be 0 (abnormal) or 1 (normal).");
		}

		try {
			this.testDAL.finalizeTest(test.getTestId());
			this.testDAL.updateNormalityForAppointmentLabTest(test.getTestId(), normality);
			test.setFinalized(true);
		} catch (SQLException e) {
			System.err.println("Error finalizing test in database: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Updates the details of the specified test in the database, including its
	 * normality.
	 * 
	 * @param updatedTest the test with updated details
	 * @param isNormal    the normality of the test (true for normal, false for
	 *                    abnormal)
	 * @throws SQLException             if a database access error occurs
	 * @throws IllegalArgumentException if the test is null or contains invalid
	 *                                  details
	 */
	public void updateLabTest(Test updatedTest, boolean isNormal) throws SQLException {
		if (updatedTest == null) {
			throw new IllegalArgumentException("Updated test cannot be null.");
		}

		if (updatedTest.getDoctorId() <= 0 || updatedTest.getPatientId() <= 0 || updatedTest.getTestType() == null) {
			throw new IllegalArgumentException("Invalid test details provided.");
		}

		try {
			int normality = isNormal ? 1 : 0;
			this.testDAL.updateLabTest(updatedTest, isNormal);
			this.testDAL.updateNormalityForAppointmentLabTest(updatedTest.getTestId(), normality);

		} catch (SQLException e) {
			System.err.println("Error updating test in database: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Retrieves all tests from the database.
	 * 
	 * @return a list of all tests
	 */
	public List<Test> getTests() {
		try {
			return this.testDAL.getTests();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Searches for tests based on the provided patient information.
	 * 
	 * @param firstName the first name of the patient
	 * @param lastName  the last name of the patient
	 * @param dob       the date of birth of the patient
	 * @return a list of tests matching the provided patient information
	 */
	public List<Test> searchTestsByPatientInfo(String firstName, String lastName, LocalDate dob) {
		try {
			return this.testDAL.searchTestsByPatientInfo(firstName, lastName, dob);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Executes a query entered by the admin and returns the results as a list of
	 * maps.
	 *
	 * @param query The SQL query to execute.
	 * @return A list of maps, where each map represents a row of the result set
	 *         with column names as keys.
	 * @throws SQLException If an error occurs while executing the query.
	 */
	public List<Map<String, Object>> executeAdminQuery(String query) throws SQLException {
		if (query == null || query.trim().isEmpty()) {
			throw new IllegalArgumentException("Query cannot be null or empty.");
		}
		return this.adminDAL.executeQuery(query);
	}

	/**
	 * Generates a visit report for the specified date range.
	 * 
	 * @param startDate the start date of the report range (inclusive)
	 * @param endDate   the end date of the report range (inclusive)
	 * @return a list of maps, where each map represents a visit and contains
	 *         key-value pairs of visit details
	 * @throws SQLException if a database access error occurs
	 */
	public List<Map<String, Object>> generateVisitReport(LocalDate startDate, LocalDate endDate) throws SQLException {
		return this.adminDAL.getVisitReport(startDate, endDate);
	}

	/**
	 * Retrieves a list of tests for a specific appointment based on their
	 * completion status.
	 * 
	 * @param appointmentId the ID of the appointment
	 * @param isComplete    true to fetch completed tests, false to fetch incomplete
	 *                      tests
	 * @return a list of tests for the specified appointment and completion status
	 * @throws SQLException if a database access error occurs
	 */
	public List<Test> getTestsForAppointment(int appointmentId, boolean isComplete) throws SQLException {
		return isComplete ? this.testDAL.getCompleteTestsForAppointment(appointmentId)
				: this.testDAL.getIncompleteTestsForAppointment(appointmentId);
	}

	/**
	 * Retrieves a list of past tests for a specific patient that are not part of
	 * the current appointment.
	 * 
	 * @param patientId            the ID of the patient
	 * @param currentAppointmentId the ID of the current appointment
	 * @return a list of past tests for the specified patient
	 * @throws SQLException if a database access error occurs
	 */
	public List<Test> getPastTestsForPatient(int patientId, int currentAppointmentId) throws SQLException {
		return this.testDAL.getPastTestsForPatient(patientId, currentAppointmentId);
	}

	/**
	 * Links a test to an appointment in the AppointmentLabTest table with its
	 * normality status.
	 * 
	 * @param testId        the ID of the test to link
	 * @param appointmentId the ID of the appointment to link the test to
	 * @param normality     the normality of the test (1 for normal, 0 for abnormal)
	 * @throws SQLException             if a database access error occurs
	 * @throws IllegalArgumentException if normality is invalid
	 */
	public void linkTestToAppointment(int testId, int appointmentId, int normality) throws SQLException {
		if (normality != 0 && normality != 1) {
			throw new IllegalArgumentException("Normality must be 0 (abnormal) or 1 (normal).");
		}

		try {
			this.testDAL.linkTestToAppointment(testId, appointmentId, normality);
		} catch (SQLException e) {
			System.err.println("Error linking test to appointment in database: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Finalizes all complete tests for the specified appointment.
	 * 
	 * @param appointmentId the ID of the appointment
	 * @param tests         the list of complete tests to finalize
	 * @throws SQLException if a database access error occurs
	 */
	public void finalizeAllTestsForAppointment(int appointmentId, List<Test> tests) throws SQLException {
		try {
			this.testDAL.finalizeTests(tests);
			for (Test test : tests) {
				test.setFinalized(true);
			}
		} catch (SQLException e) {
			System.err.println("Error finalizing tests for appointment in database: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Retrieves the appointment ID associated with a specific test ID.
	 * 
	 * @param testId The ID of the test for which to retrieve the associated
	 *               appointment ID.
	 * @return The appointment ID associated with the specified test ID.
	 * @throws SQLException If a database access error occurs.
	 */
	public int getAppointmentIdForTest(int testId) throws SQLException {
		return this.testDAL.getAppointmentIdByTestId(testId);
	}

	/**
	 * Retrieves an appointment by its ID from the database. This method fetches
	 * detailed appointment information including associated doctor and patient
	 * details.
	 * 
	 * @param appointmentId The ID of the appointment to retrieve.
	 * @return An {@link Appointment} object containing the details of the
	 *         appointment if found, or {@code null} if no appointment with the
	 *         specified ID exists.
	 * @throws SQLException If a database access error occurs.
	 */
	public Appointment getAppointmentById(int appointmentId) throws SQLException {
		return this.appointmentDAL.getAppointmentById(appointmentId);
	}

}
