package edu.westga.medmyst.project.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.westga.medmyst.project.dal.LoginDAL;
import edu.westga.medmyst.project.dal.PatientDAL;
import edu.westga.medmyst.project.model.Login;
import edu.westga.medmyst.project.model.Patient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class TextTwistViewModel
 * 
 * TextTwistViewModel mediates between the view and the rest of the game.
 * 
 * @author Thomas Lamont
 * @version Fall 2024
 */
public class MedMystViewModel {

	private StringProperty username;
	private StringProperty password;
	private StringProperty loginSuccess;
	
	private StringProperty firstName;
	private StringProperty lastName;
	private ObjectProperty<LocalDate> dateOfBirth;
	private StringProperty gender;
	private StringProperty phoneNumber;
	private StringProperty address1;
	private StringProperty address2;
	private StringProperty state;
	private StringProperty zip;


	private LoginDAL loginDAL;
	private Login currentUser;
	
	private PatientDAL patientDAL;

	/**
	 * Constructs a new MedMystViewModel and initializes its properties.
	 * 
	 */
	public MedMystViewModel() {
		this.username = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
		this.loginSuccess = new SimpleStringProperty();
		
		this.firstName = new SimpleStringProperty();
	    this.lastName = new SimpleStringProperty();
	    this.dateOfBirth = new SimpleObjectProperty<>();
	    this.gender = new SimpleStringProperty();
	    this.phoneNumber = new SimpleStringProperty();
	    this.address1 = new SimpleStringProperty();
	    this.address2 = new SimpleStringProperty();
	    this.state = new SimpleStringProperty();
	    this.zip = new SimpleStringProperty();
		
		this.loginDAL = new LoginDAL();
		this.currentUser = null;
		
		this.patientDAL = new PatientDAL();
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
    
    public StringProperty firstNameProperty() {
        return this.firstName;
    }

    public StringProperty lastNameProperty() {
        return this.lastName;
    }

    public ObjectProperty<LocalDate> dateOfBirthProperty() {
        return this.dateOfBirth;
    }

    public StringProperty genderProperty() {
        return this.gender;
    }

    public StringProperty phoneNumberProperty() {
        return this.phoneNumber;
    }

    public StringProperty address1Property() {
        return this.address1;
    }

    public StringProperty address2Property() {
        return this.address2;
    }

    public StringProperty stateProperty() {
        return this.state;
    }

    public StringProperty zipProperty() {
        return this.zip;
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

		boolean isValidLogin = this.loginDAL.checkValidLogin(this.username.get(), this.password.get());

		if (isValidLogin) {
	        this.loginSuccess.set("Login successful!");
	        this.currentUser = new Login(usernameValue);
	        return true;
	    } else {
	        this.loginSuccess.set("Invalid username or password.");
	        return false;
	    }
	}
	
	public boolean addPatient() {
	    if (this.firstName.get().isEmpty() || this.lastName.get().isEmpty() || this.dateOfBirth.get() == null || 
	        this.gender.get().isEmpty() || this.address1.get().isEmpty() || this.state.get().isEmpty() || 
	        this.zip.get().isEmpty()) {

	        return false;
	    }

	    Patient newPatient = new Patient(this.firstName.get(), this.lastName.get(), this.dateOfBirth.get(),
	                                     this.gender.get(), this.phoneNumber.get(), this.address1.get(), this.address2.get(), 
	                                     this.state.get(), this.zip.get());
	    try {

	        this.patientDAL.addPatient(newPatient);
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean updatePatient(Patient patientToUpdate) {
	    if (this.firstName.get().isEmpty() || this.lastName.get().isEmpty() || this.dateOfBirth.get() == null || 
	        this.gender.get().isEmpty() || this.address1.get().isEmpty() || this.state.get().isEmpty() || 
	        this.zip.get().isEmpty()) {

	        return false;
	    }

	    patientToUpdate.setFName(this.firstName.get());
	    patientToUpdate.setLName(this.lastName.get());
	    patientToUpdate.setDateOfBirth(this.dateOfBirth.get());
	    patientToUpdate.setGender(this.gender.get());
	    patientToUpdate.setAddress1(this.address1.get());
	    patientToUpdate.setAddress2(this.address2.get());
	    patientToUpdate.setState(this.state.get());
	    patientToUpdate.setZip(this.zip.get());

	    try {

	        this.patientDAL.updatePatient(patientToUpdate);
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public void loadPatientData(Patient patient) {
	    this.firstName.set(patient.getFName());
	    this.lastName.set(patient.getLName());
	    this.dateOfBirth.set(patient.getDateOfBirth());
	    this.gender.set(patient.getGender());
	    this.address1.set(patient.getAddress1());
	    this.address2.set(patient.getAddress2());
	    this.state.set(patient.getState());
	    this.zip.set(patient.getZip());
	}
	
	public List<Patient> getPatients() {
	    try {
	        return this.patientDAL.getAllPatients();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}
}
