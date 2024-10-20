package edu.westga.medmyst.project.model;

import java.sql.Date;
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

	public int getPatientId() {
		return this.patient_id;
	}

	public void setPatientId(int patient_id) {
		this.patient_id = patient_id;
	}

	public String getFName() {
		return this.f_name;
	}

	public void setFName(String f_name) {
		this.f_name = f_name;
	}

	public String getLName() {
		return this.l_name;
	}

	public void setLName(String l_name) {
		this.l_name = l_name;
	}

	public LocalDate getDateOfBirth() {
		return this.date_of_birth;
	}

	public void setDateOfBirth(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return this.phone_number;
	}

	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}
	
	public String getAddress1() {
		return this.address1;
	}
	
	public void setAddress1(String address) {
		this.address1 = address;
	}
	
	public String getAddress2() {
		return this.address2;
	}
	
	public void setAddress2(String address) {
		this.address2 = address;
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZip() {
		return this.zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}

	public Boolean getActiveStatus() {
		return this.active_status;
	}

	public void setActiveStatus(Boolean active_status) {
		this.active_status = active_status;
	}
}