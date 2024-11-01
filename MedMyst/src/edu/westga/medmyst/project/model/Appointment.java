package edu.westga.medmyst.project.model;

import java.time.LocalDateTime;

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
    private String reason;
    private String details;
    private String appointmentType;
    private LocalDateTime dateTime;

    /**
     * Constructs an Appointment object.
     *
     * @param appointmentId   The ID of the appointment.
     * @param patientId       The ID of the patient.
     * @param doctorId        The ID of the doctor.
     * @param reason          The reason for the appointment.
     * @param details         Additional details for the appointment.
     * @param appointmentType The type of the appointment.
     * @param dateTime        The date and time of the appointment.
     */
    public Appointment(int appointmentId, int patientId, int doctorId, String reason, String details, 
    		String appointmentType, LocalDateTime dateTime) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.reason = reason;
        this.details = details;
        this.appointmentType = appointmentType;
        this.dateTime = dateTime;
    }

    /**
     * Constructs an Appointment object without an appointmentId (for new appointments).
     *
     * @param patientId       The ID of the patient.
     * @param doctorId        The ID of the doctor.
     * @param reason          The reason for the appointment.
     * @param details         Additional details for the appointment.
     * @param appointmentType The type of the appointment.
     * @param dateTime        The date and time of the appointment.
     */
    public Appointment(int patientId, int doctorId, String reason, String details, String appointmentType, LocalDateTime dateTime) {
        this(0, patientId, doctorId, reason, details, appointmentType, dateTime);
    }

    // Getters and setters

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Appointment [appointmentId=" + appointmentId + ", patientId=" + patientId + ", doctorId=" + doctorId
                + ", reason=" + reason + ", details=" + details + ", appointmentType=" + appointmentType + ", dateTime="
                + dateTime + "]";
    }
}