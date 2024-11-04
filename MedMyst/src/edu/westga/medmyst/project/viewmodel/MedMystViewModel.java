package edu.westga.medmyst.project.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.westga.medmyst.project.dal.AppointmentDAL;
import edu.westga.medmyst.project.dal.AppointmentTypeDAL;
import edu.westga.medmyst.project.dal.DoctorDAL;
import edu.westga.medmyst.project.dal.LoginDAL;
import edu.westga.medmyst.project.dal.PatientDAL;
import edu.westga.medmyst.project.model.Appointment;
import edu.westga.medmyst.project.model.AppointmentType;
import edu.westga.medmyst.project.model.Doctor;
import edu.westga.medmyst.project.model.Login;
import edu.westga.medmyst.project.model.Patient;
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
    private IntegerProperty systolicPressure;
    private IntegerProperty diastolicPressure;
    private IntegerProperty pulse;
    private DoubleProperty height;
    private DoubleProperty weight;

	private LoginDAL loginDAL;
	private Login currentUser;

	private PatientDAL patientDAL;
	private AppointmentDAL appointmentDAL;
	private DoctorDAL doctorDAL;
	private AppointmentTypeDAL appointmentTypeDAL;

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
	    this.systolicPressure = new SimpleIntegerProperty();
	    this.diastolicPressure = new SimpleIntegerProperty();
	    this.pulse = new SimpleIntegerProperty();
	    this.height = new SimpleDoubleProperty();
	    this.weight = new SimpleDoubleProperty();
		this.loginDAL = new LoginDAL();
		this.currentUser = null;
		this.patientDAL = new PatientDAL();
		this.appointmentDAL = new AppointmentDAL();
		this.doctorDAL = new DoctorDAL();
		this.appointmentTypeDAL = new AppointmentTypeDAL();
		this.isActive = new SimpleBooleanProperty();
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
	 * Gets the isActive property that is bound to the radio buttons in the
	 * view.
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
    
    public StringProperty doctorFirstNameProperty() {
        return this.doctorFirstName;
    }

    public StringProperty doctorLastNameProperty() {
        return this.doctorLastName;
    }

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
    
    public ObjectProperty<LocalDate> appointmentDateProperty() {
    	return this.appointmentDate;
    }
    
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
    
    public IntegerProperty systolicPressureProperty() {
    	return this.systolicPressure;
    }
    
    public IntegerProperty diastolicPressureProperty() {
    	return this.diastolicPressure;
    }
    
    public IntegerProperty pulseProperty() {
    	return this.pulse;
    }
    
    public DoubleProperty heightProperty() {
    	return this.height;
    }
    
    public DoubleProperty weightProperty() {
    	return this.weight;
    }

	/**
	 * Logs the user out by clearing the current user.
	 */
	public void logout() {
		this.currentUser = null;
		this.loginSuccess.set("You have been logged out.");
	}

	/**
	 * Validates the login credentials by checking the username and password against
	 * the database using the LoginDAL.
	 * 
	 * If either the username or password is empty, an error message is set. If the
	 * login is valid, the method returns true, otherwise, it sets an error message
	 * and returns false.
	 * 
	 * @return true if the login is valid, false otherwise
	 * @throws SQLException if there is an issue with the database connection or
	 *                      query execution
	 */
	public boolean validateLogin() throws SQLException {
		String usernameValue = this.username.get();
		String passwordValue = this.password.get();

		if (usernameValue == null || usernameValue.isEmpty() || passwordValue == null || passwordValue.isEmpty()) {
			this.loginSuccess.set("Username and password must not be empty.");
			return false;
		}

		Login login = this.loginDAL.checkValidLogin(usernameValue, passwordValue);

		if (login != null) {
			this.loginSuccess.set("Login successful! Welcome, " + login.getFirstName() + " " + login.getLastName());
			this.currentUser = login;
			return true;
		} else {
			this.loginSuccess.set("Invalid username or password.");
			return false;
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
		System.out.println(patient);
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
	        System.out.println("Error: Missing required fields for appointment creation.");
	        return false;
	    }
	    if (!this.isDoctorAvailable(this.doctorId.get(), this.appointmentDateTime.get())) {
	        System.out.println("Error: Doctor is already booked at this time.");
	        return false;
	    }
	    Appointment newAppointment = new Appointment(
	            this.appointmentId.get(),
	            this.patientId.get(),
	            this.doctorId.get(),
	            this.doctorFirstName.get(),
	            this.doctorLastName.get(),
	            this.doctorSpecialty.get(),
	            this.reason.get(),
	            this.details.get(),
	            this.appointmentType.get(),
	            this.appointmentDateTime.get());
	    try {
	        this.appointmentDAL.addAppointment(newAppointment);
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
	    if (this.appointmentDateTime.get() == null || this.reason.get().isEmpty() || this.appointmentType.get().isEmpty()) {
	        return false;
	    }
	    appointmentToUpdate.setDoctorId(this.doctorId.get());
	    appointmentToUpdate.setDoctorFirstName(this.doctorFirstName.get());
	    appointmentToUpdate.setDoctorLastName(this.doctorLastName.get());
	    appointmentToUpdate.setDoctorSpecialty(this.doctorSpecialty.get());
	    appointmentToUpdate.setReason(this.reason.get());
	    appointmentToUpdate.setDetails(this.details.get());
	    appointmentToUpdate.setAppointmentType(this.appointmentType.get());
	    appointmentToUpdate.setDateTime(this.appointmentDateTime.get());
	    if (this.systolicPressure.get() >= 0) {
	        appointmentToUpdate.setSystolicPressure(this.systolicPressure.get());
	    }
	    if (this.diastolicPressure.get() >= 0) {
	        appointmentToUpdate.setDiastolicPressure(this.diastolicPressure.get());
	    }
	    if (this.pulse.get() >= 0) {
	        appointmentToUpdate.setPulse(this.pulse.get());
	    }
	    if (this.height.get() >= 0) {
	        appointmentToUpdate.setHeight(this.height.get());
	    }
	    if (this.weight.get() >= 0) {
	        appointmentToUpdate.setWeight(this.weight.get());
	    }
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
	 * @param firstName patient's first name
	 * @param lastName patient's last name
	 * @param dob patient's dob
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

	    this.systolicPressure.set(appointment.getSystolicPressure());
	    this.diastolicPressure.set(appointment.getDiastolicPressure());
	    this.pulse.set(appointment.getPulse());
	    this.height.set(appointment.getHeight());
	    this.weight.set(appointment.getWeight());
	}

	/**
	 * searches for the specified patient based on provided criteria.
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param dob the patients dob
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
}
