package edu.westga.medmyst.project.model;

import java.time.LocalDate;

/**
 * Represents a doctor within the system.
 * @author demmons1
 * @version Fall 2024
 */
public class Doctor {

    private int doctorId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String specialty;
    private String phoneNumber;
    private String address1;
    private String address2;
    private String state;
    private String zip;

    /**
     * Instantiates a new doctor.
     * 
     * @param doctorId    the doctor's ID
     * @param firstName   the doctor's first name
     * @param lastName    the doctor's last name
     * @param dateOfBirth the doctor's date of birth
     * @param gender      the doctor's gender
     * @param specialty   the doctor's specialty
     * @param phoneNumber the doctor's phone number
     * @param address1    the doctor's primary address
     * @param address2    the doctor's secondary address
     * @param state       the doctor's state
     * @param zip         the doctor's ZIP code
     */
    public Doctor(int doctorId, String firstName, String lastName, LocalDate dateOfBirth, String gender, String specialty,
                  String phoneNumber, String address1, String address2, String state, String zip) {
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.specialty = specialty;
        this.phoneNumber = phoneNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.state = state;
        this.zip = zip;
    }

    /**
     * Gets the doctor ID.
     * 
     * @return the doctor's unique ID
     */
    public int getDoctorId() {
        return this.doctorId;
    }

    /**
     * Sets the doctor ID.
     * 
     * @param doctorId the ID to set for the doctor
     */
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Gets the doctor's first name.
     * 
     * @return the first name of the doctor
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the doctor's first name.
     * 
     * @param firstName the first name to set for the doctor
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the doctor's last name.
     * 
     * @return the last name of the doctor
     */
    public String getLastName() {
        return this.lastName;
    }
    
    /**
     * Sets the doctor's last name.
     * 
     * @param lastName the last name to set for the doctor
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the doctor's date of birth.
     * 
     * @return the date of birth of the doctor
     */
    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * Sets the doctor's date of birth.
     * 
     * @param dateOfBirth the date of birth to set for the doctor
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the doctor's gender.
     * 
     * @return the gender of the doctor
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Sets the doctor's gender.
     * 
     * @param gender the gender to set for the doctor
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the doctor's specialty.
     * 
     * @return the specialty of the doctor
     */
    public String getSpecialty() {
        return this.specialty;
    }

    /**
     * Sets the doctor's specialty.
     * 
     * @param specialty the specialty to set for the doctor
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Gets the doctor's phone number.
     * 
     * @return the phone number of the doctor
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Sets the doctor's phone number.
     * 
     * @param phoneNumber the phone number to set for the doctor
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the doctor's primary address.
     * 
     * @return the primary address of the doctor
     */
    public String getAddress1() {
        return this.address1;
    }

    /**
     * Sets the doctor's primary address.
     * 
     * @param address1 the primary address to set for the doctor
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Gets the doctor's secondary address.
     * 
     * @return the secondary address of the doctor
     */
    public String getAddress2() {
        return this.address2;
    }

    /**
     * Sets the doctor's secondary address.
     * 
     * @param address2 the secondary address to set for the doctor
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Gets the doctor's state.
     * 
     * @return the state where the doctor resides
     */
    public String getState() {
        return this.state;
    }

    /**
     * Sets the doctor's state.
     * 
     * @param state the state to set for the doctor
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the doctor's zip code.
     * 
     * @return the zip code of the doctor
     */
    public String getZip() {
        return this.zip;
    }

    /**
     * Sets the doctor's zip code.
     * 
     * @param zip the zip code to set for the doctor
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " (" + this.specialty + ")";
    }
}
