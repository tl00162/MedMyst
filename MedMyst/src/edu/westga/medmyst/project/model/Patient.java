package edu.westga.medmyst.project.model;

import java.sql.Date;

/**
 * Represents a patient within the system.
 * @author demmons1
 * @version Fall 2024
 */
public class Patient {
	
	private int patient_id;
	private String f_name;
	private String l_name;
	private Date date_of_birth;
	private char gender;
	private String phone_number;
	private Boolean active_status;

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

	public Date getDateOfBirth() {
		return this.date_of_birth;
	}

	public void setDateOfBirth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	
	public char getGender() {
		return this.gender;
	}
	
	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return this.phone_number;
	}

	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}

	public Boolean getActiveStatus() {
		return this.active_status;
	}

	public void setActiveStatus(Boolean active_status) {
		this.active_status = active_status;
	}
}