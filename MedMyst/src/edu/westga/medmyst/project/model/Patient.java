package edu.westga.medmyst.project.model;

import java.time.LocalDate;

/**
 * Represents a patient within the system.
 * @author demmons1
 * @version Fall 2024
 */
public class Patient {
	
	private int patient_id;
	private String f_name;
	private String l_name;
	private LocalDate date_of_birth;
	private String gender;
	private String phone_number;
	private String address1;
	private String address2;
	private String state;
	private String zip;
	private Boolean active_status;
	
	/**
	 * Instantiates a new patient.
	 * @param patientId the patient's Id
	 * @param fname the patient's first name
	 * @param lname the patient's last name
	 * @param birthDate the patient's dob
	 * @param gender the patient's gender
	 * @param phoneNumber the patient's phone number
	 * @param address1 the patient's address1
	 * @param address2 the patient's address2
	 * @param state the patient's state
	 * @param zip the patient's zip code
	 */
	public Patient(int patientId, String fname, String lname, LocalDate birthDate, String gender, String phoneNumber, String address1, String address2, String state, String zip) {
		this.patient_id = patientId;
		this.f_name = fname;
		this.l_name = lname;
		this.date_of_birth = birthDate;
		this.gender = gender;
		this.phone_number = phoneNumber;
		this.address1 = address1;
		this.address2 = address2;
		this.state = state;
		this.zip = zip;
		this.active_status = true;
	}

	/**
	 * Gets the patientId
	 * @return the patientId
	 */
	public int getPatientId() {
		return this.patient_id;
	}

	/**
	 * Sets the patientId
	 * @param patient_id the new patientId
	 */
	public void setPatientId(int patient_id) {
		this.patient_id = patient_id;
	}

	/**
	 * Gets the fName
	 * @return the fName
	 */
	public String getFName() {
		return this.f_name;
	}

	/**
	 * Sets the fName
	 * @param f_name the new fName
	 */
	public void setFName(String f_name) {
		this.f_name = f_name;
	}

	/**
	 * Gets the lName
	 * @return the lName
	 */
	public String getLName() {
		return this.l_name;
	}

	/**
	 * Sets the lname
	 * @param l_name the new lName
	 */
	public void setLName(String l_name) {
		this.l_name = l_name;
	}

	/**
	 * gets the dob.
	 * @return the dob
	 */
	public LocalDate getDateOfBirth() {
		return this.date_of_birth;
	}

	/**
	 * Sets the dob
	 * @param date_of_birth the new dob
	 */
	public void setDateOfBirth(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	
	/**
	 * gets the gender
	 * @return the gender
	 */
	public String getGender() {
		return this.gender;
	}
	
	/**
	 * sets the gender
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the phone number
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return this.phone_number;
	}

	/**
	 * sets the phone number
	 * @param phone_number the new phone number
	 */
	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}
	
	/**
	 * Gets the address1
	 * @return the address1
	 */
	public String getAddress1() {
		return this.address1;
	}
	
	/**
	 * Sets the address1
	 * @param address the new address1
	 */
	public void setAddress1(String address) {
		this.address1 = address;
	}
	
	/**
	 * gets the address2
	 * @return the address2
	 */
	public String getAddress2() {
		return this.address2;
	}
	
	/**
	 * Sets the address2
	 * @param address the new address2
	 */
	public void setAddress2(String address) {
		this.address2 = address;
	}
	
	/**
	 * Gets the state
	 * @return the state
	 */
	public String getState() {
		return this.state;
	}
	
	/**
	 * sets the state
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Gets the zip
	 * @return the zip
	 */
	public String getZip() {
		return this.zip;
	}
	
	/**
	 * sets the zip
	 * @param zip the new zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Gets the active status
	 * @return the active status
	 */
	public Boolean getActiveStatus() {
		return this.active_status;
	}

	/**
	 * Sets the active status
	 * @param active_status the new active_status
	 */
	public void setActiveStatus(Boolean active_status) {
		this.active_status = active_status;
	}
}