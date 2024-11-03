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

    public int getDoctorId() {
        return this.doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
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

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " (" + this.specialty + ")";
    }
}
