package edu.westga.medmyst.project.model;

import java.sql.SQLException;
import java.time.LocalDateTime;

import edu.westga.medmyst.project.dal.CheckupDAL;

/**
 * The Class Appointment.
 * Represents an appointment in the MedMyst application.
 * 
 * @author demmons1
 * @version Fall 2024
 */
public class Appointment {

    private int appointmentId;
    private int patientId;
    private int doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorSpecialty;
    private String reason;
    private String details;
    private String appointmentType;
    private LocalDateTime dateTime;
    private String finalDiagnosis;
    private CheckupDAL checkupDAL;
    private Checkup checkup;

    /**
     * Constructs an Appointment object.
     *
     * @param appointmentId   The ID of the appointment.
     * @param patientId       The ID of the patient.
     * @param doctorId        The ID of the doctor.
     * @param doctorFirstName The doctors first Name.
     * @param doctorLastName  The doctors last Name.
     * @param doctorSpecialty The doctors specialty.
     * @param reason          The reason for the appointment.
     * @param details         Additional details for the appointment.
     * @param appointmentType The type of the appointment.
     * @param dateTime        The date and time of the appointment.
     */
    public Appointment(int appointmentId, int patientId, int doctorId, String doctorFirstName, String doctorLastName, String doctorSpecialty, String reason, String details, 
    		String appointmentType, LocalDateTime dateTime) {
    	this.checkupDAL = new CheckupDAL();
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.doctorSpecialty = doctorSpecialty;
        this.reason = reason;
        this.details = details;
        this.appointmentType = appointmentType;
        this.dateTime = dateTime;
    }
    
    /**
     * Constructs an appointment object with just the information present in the appointment DB table.
     * @param appointmentId   The Id of the appointment.
     * @param patientId		  The ID of the patient.
     * @param doctorId		  The ID of the doctor.
     * @param reason		  The reason for the appointment.
     * @param details		  The details of the appointment.
     * @param appointmentType The type of the appointment.
     * @param dateTime		  The date and time of the appointment.
     */
    public Appointment(int appointmentId, int patientId, int doctorId, String reason, String details, String appointmentType, 
    		LocalDateTime dateTime) {
    	this.checkupDAL = new CheckupDAL();
    	this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.reason = reason;
        this.details = details;
        this.appointmentType = appointmentType;
        this.dateTime = dateTime;
    }


    /**
     * Gets the appointment Id
     * @return the appointmentId
     */
    public int getAppointmentId() {
        return this.appointmentId;
    }

    /**
     * Sets the appointment Id to the specified value
     * @param appointmentId the new appointmentId.
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * gets the patientId
     * @return the patientId.
     */
    public int getPatientId() {
        return this.patientId;
    }

    /**
     * sets the patientId to the specified value
     * @param patientId the new patientId
     */
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets the doctorId
     * @return the doctorId
     */
    public int getDoctorId() {
        return this.doctorId;
    }

    /**
     * sets the doctorId to the specified value.
     * @param doctorId the new doctorId.
     */
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    
    /**
     * Gets the doctor's first name.
     * @return the doctor's first name.
     */
    public String getDoctorFirstName() {
        return this.doctorFirstName;
    }

    /**
     * Sets the doctor's first name
     * @param doctorFirstName the new first name
     */
    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    /**
     * Gets the doctor's last name
     * @return the doctor's last name
     */
    public String getDoctorLastName() {
        return this.doctorLastName;
    }

    /**
     * sets the doctor's last name
     * @param doctorLastName the new last name
     */
    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    /**
     * Gets the doctor's specialty
     * @return the specialty
     */
    public String getDoctorSpecialty() {
        return this.doctorSpecialty;
    }

    /**
     * Sets the doctor's specialty.
     * @param doctorSpecialty the new specialty
     */
    public void setDoctorSpecialty(String doctorSpecialty) {
        this.doctorSpecialty = doctorSpecialty;
    }

    /**
     * Gets the reason for the appointment
     * @return the reason
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * Sets the reason for the appointment
     * @param reason the new reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets the appointment details
     * @return the details
     */
    public String getDetails() {
        return this.details;
    }

    /**
     * Sets the appointment details
     * @param details the new details
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Gets the appointment Type
     * @return the appointment Type
     */
    public String getAppointmentType() {
        return this.appointmentType;
    }

    /**
     * Sets the appointment Type
     * @param appointmentType the new type
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * Gets the dateTime of the appointment.
     * @return the dateTime.
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * sets the DateTime
     * @param dateTime the new dateTime
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    /**
     * gets the finalDiagnosis
     * @return the finalDiagnosis
     */
    public String getFinalDiagnosis() {
    	return this.finalDiagnosis;
    }
    
    /**
     * Sets the final diagnosis
     * @param diagnosis the final diagnosis
     */
    public void setFinalDiagosis(String diagnosis) {
    	this.finalDiagnosis = diagnosis;
    }
    
    /**
     * Gets the checkup
     * @return the checkup
     */
    public Checkup getCheckup() {
    	try {
			return this.checkupDAL.getCheckupByAppointmentId(this.appointmentId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * Sets the checkup
     * @param checkup the new checkup
     */
    public void setCheckup(Checkup checkup) {
    	this.checkup = checkup;
    }
    
    @Override
    public String toString() {
        return "Appointment [appointmentId=" + this.appointmentId + ", patientId=" + this.patientId + ", doctorId=" + this.doctorId
                + ", reason=" + this.reason + ", details=" + this.details + ", appointmentType=" + this.appointmentType + ", dateTime="
                + this.dateTime + "]";
    }
}
