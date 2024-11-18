package edu.westga.medmyst.project.model;

/**
 * The Checkup class.
 * @author demmons1
 * @version Fall 2024
 */
public class Checkup {

    private int appointmentId;
    private int nurseId;
    private double bodyTemperature;
    private int diastolicBloodPressure;
    private int systolicBloodPressure;
    private int pulse;
    private String symptoms;
    private double height;
    private double weight;
    private String initialDiagnosis;

    /**
     * Creates a CheckUp instance with all fields
     * 
     * @param appointmentId the appointment ID associated with the checkup
     * @param nurseId the nurse ID who conducted the checkup
     * @param bodyTemperature the body temperature recorded in the checkup
     * @param diastolicBloodPressure the diastolic blood pressure recorded
     * @param systolicBloodPressure the systolic blood pressure recorded
     * @param pulse the pulse recorded during the checkup
     * @param symptoms the symptoms described in the checkup
     * @param height the height
     * @param weight the weight
     * @param initialDiagnosis the initialDiagnosis
     */
    public Checkup(int appointmentId, int nurseId, double bodyTemperature, int diastolicBloodPressure, int systolicBloodPressure, int pulse, String symptoms, double height, double weight, String initialDiagnosis) {
        this.appointmentId = appointmentId;
        this.nurseId = nurseId;
        this.bodyTemperature = bodyTemperature;
        this.height = height;
        this.weight = weight;
        this.diastolicBloodPressure = diastolicBloodPressure;
        this.systolicBloodPressure = systolicBloodPressure;
        this.pulse = pulse;
        this.symptoms = symptoms;
        this.initialDiagnosis = initialDiagnosis;
    }

    /**
     * Gets the appointmentId
     * @return the appointmentId
     */
    public int getAppointmentId() {
        return this.appointmentId;
    }

    /**
     * Sets the appointmentId
     * @param appointmentId the new Id
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the nurseId
     * @return the nurseId
     */
    public int getNurseId() {
        return this.nurseId;
    }

    /**
     * Sets the nurseId
     * @param nurseId the new Id
     */
    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    /**
     * Gets the body temperature.
     * @return the body temperature
     */
    public double getBodyTemperature() {
        return this.bodyTemperature;
    }

    /**
     * Sets the bodyTemperature
     * @param bodyTemperature the new temperature
     */
    public void setBodyTemperature(double bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }
    
    /**
     * Gets the height
     * @return the height
     */
    public double getHeight() {
    	return this.height;
    }
    
    /**
     * Sets the height
     * @param height the new height
     */
    public void setHeight(double height) {
    	this.height = height;
    }
    
    /**
     * Gets the weight
     * @return the weight
     */
    public double getWeight() {
    	return this.weight;
    }
    
    /**
     * Sets the weight
     * @param weight the new weight
     */
    public void setWeight(double weight) {
    	this.weight = weight;
    }

    /**
     * Gets the diastolic pressure
     * @return the diastolic pressure
     */
    public int getDiastolicBloodPressure() {
        return this.diastolicBloodPressure;
    }

    /**
     * Sets the diastolic pressure
     * @param diastolicBloodPressure the new pressure
     */
    public void setDiastolicBloodPressure(int diastolicBloodPressure) {
        this.diastolicBloodPressure = diastolicBloodPressure;
    }

    /**
     * Gets the systolic pressure
     * @return the systolic pressure
     */
    public int getSystolicBloodPressure() {
        return this.systolicBloodPressure;
    }

    /**
     * Sets the systolic pressure
     * @param systolicBloodPressure the new pressure
     */
    public void setSystolicBloodPressure(int systolicBloodPressure) {
        this.systolicBloodPressure = systolicBloodPressure;
    }

    /**
     * Gets the pulse
     * @return the pulse
     */
    public int getPulse() {
        return this.pulse;
    }

    /**
     * Sets the pulse
     * @param pulse the new pulse
     */
    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    /**
     * Gets the symptoms
     * @return the symptoms
     */
    public String getSymptoms() {
        return this.symptoms;
    }

    /**
     * Sets the symptoms
     * @param symptoms the new symptoms
     */
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    
    /**
     * Gets the initialDiagnosis
     * @return the initialDiagnosis
     */
    public String getInitialDiagnosis() {
    	return this.initialDiagnosis;
    }
    
    /**
     * Sets the initialDiagnosis
     * @param initialDiagnosis the updated diagnosis
     */
    public void setInitialDiagnosis(String initialDiagnosis) {
    	this.initialDiagnosis = initialDiagnosis;
    }

    @Override
    public String toString() {
        return "CheckUp [appointmentId=" + this.appointmentId + ", nurseId=" + this.nurseId + ", bodyTemperature=" + this.bodyTemperature
                + ", diastolicBloodPressure=" + this.diastolicBloodPressure + ", systolicBloodPressure=" + this.systolicBloodPressure
                + ", pulse=" + this.pulse + ", symptoms=" + this.symptoms + "]";
    }
}
