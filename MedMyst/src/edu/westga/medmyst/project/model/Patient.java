package edu.westga.medmyst.project.model;

import java.time.LocalDate;

/**
 * Represents a patient within the system.
 * 
 * @author demmons1
 * @version Fall 2024
 */
public class Patient {

	private int patientID;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String gender;
	private String phoneNumber;
	private String address1;
	private String address2;
	private String state;
	private String zip;
	private Boolean activeStatus;

	/**
	 * Instantiates a new patient.
	 * 
	 * @param patientId   the patient's Id
	 * @param fname       the patient's first name
	 * @param lname       the patient's last name
	 * @param birthDate   the patient's dob
	 * @param gender      the patient's gender
	 * @param phoneNumber the patient's phone number
	 * @param address1    the patient's address1
	 * @param address2    the patient's address2
	 * @param state       the patient's state
	 * @param zip         the patient's zip code
	 */
	public Patient(int patientId, String fname, String lname, LocalDate birthDate, String gender, String phoneNumber,
			String address1, String address2, String state, String zip) {
		this.patientID = patientId;
		this.firstName = fname;
		this.lastName = lname;
		this.dateOfBirth = birthDate;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.address1 = address1;
		this.address2 = address2;
		this.state = state;
		this.zip = zip;
		this.activeStatus = true;
	}

	/**
	 * Gets the patientId
	 * 
	 * @return the patientId
	 */
	public int getPatientId() {
		return this.patientID;
	}

	/**
	 * Sets the patientId
	 * 
	 * @param patient_id the new patientId
	 */
	public void setPatientId(int patient_id) {
		this.patientID = patient_id;
	}

	/**
	 * Gets the fName
	 * 
	 * @return the fName
	 */
	public String getFName() {
		return this.firstName;
	}

	/**
	 * Sets the fName
	 * 
	 * @param fistName the new fName
	 */
	public void setFName(String fistName) {
		this.firstName = fistName;
	}

	/**
	 * Gets the lName
	 * 
	 * @return the lName
	 */
	public String getLName() {
		return this.lastName;
	}

	/**
	 * Sets the lname
	 * 
	 * @param lastName the new lName
	 */
	public void setLName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * gets the dob.
	 * 
	 * @return the dob
	 */
	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}

	/**
	 * Sets the dob
	 * 
	 * @param dateOfBirth the new dob
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * gets the gender
	 * 
	 * @return the gender
	 */
	public String getGender() {
		return this.gender;
	}

	/**
	 * sets the gender
	 * 
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the phone number
	 * 
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * sets the phone number
	 * 
	 * @param phoneNumber the new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the address1
	 * 
	 * @return the address1
	 */
	public String getAddress1() {
		return this.address1;
	}

	/**
	 * Sets the address1
	 * 
	 * @param address the new address1
	 */
	public void setAddress1(String address) {
		this.address1 = address;
	}

	/**
	 * gets the address2
	 * 
	 * @return the address2
	 */
	public String getAddress2() {
		return this.address2;
	}

	/**
	 * Sets the address2
	 * 
	 * @param address the new address2
	 */
	public void setAddress2(String address) {
		this.address2 = address;
	}

	/**
	 * Gets the state
	 * 
	 * @return the state
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * sets the state
	 * 
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the zip
	 * 
	 * @return the zip
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * sets the zip
	 * 
	 * @param zip the new zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Gets the active status
	 * 
	 * @return the active status
	 */
	public Boolean getActiveStatus() {
		return this.activeStatus;
	}

	/**
	 * Sets the active status
	 * 
	 * @param activeStatus the new active_status
	 */
	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Override
	public String toString() {
		return (this.firstName + " " + this.lastName + " " + this.gender);

	}
}