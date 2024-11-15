package edu.westga.medmyst.project.model;

import java.time.LocalDateTime;

/**
 * The Test class
 * @author demmons1
 * @version Fall 2024
 */
public class Test {
	private int testId;
    private int doctorId;
    private int patientId;
    private TestType testType;
    private double low;
    private double high;
    private String unitOfMeasurement;
    private String result;
    private LocalDateTime dateTime;
    
    /**
     * Constructor for Test. Creates a new test when all fields are present
     * @param testId
     * @param patientId
     * @param doctorId
     * @param testType
     * @param normality
     * @param low
     * @param high
     * @param unitOfMeasurement
     * @param result
     * @param dateTime
     */
    public Test(int testId, int doctorId, int patientId, TestType testType, 
                double low, double high, String unitOfMeasurement, String result, LocalDateTime dateTime) {
        this.testId = testId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.testType = testType;
        this.low = low;
        this.high = high;
        this.unitOfMeasurement = unitOfMeasurement;
        this.result = result;
        this.dateTime = dateTime;
    }
    
    /**
     * Gets the testId
     * @return the testId
     */
    public int getTestId() {
    	return this.testId;
    }
    
    /**
     * Sets the testId
     * @param testId the new testId
     */
    public void setTestId(int testId) {
    	this.testId = testId;
    }
    
    /**
     * Gets the patientId
     * @return the patientId
     */
    public int getPatientId() {
    	return this.patientId;
    }
    
    /**
     * Sets the patientId
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
     * Sets the doctorId
     * @param doctorId the new doctorId
     */
    public void setDoctorId(int doctorId) {
    	this.doctorId = doctorId;
    }
    
    /**
     * Gets the testType
     * @return the testType
     */
    public TestType getTestType() {
    	return this.testType;
    }
    
    /**
     * Sets the testType
     * @param testType the new testType.
     */
    public void setTestType(TestType testType) {
    	this.testType = testType;
    }
    
    /**
     * Gets the low
     * @return the low
     */
    public double getLow() {
    	return this.low;
    }
    
    /**
     * Sets the low value
     * @param low the new low
     */
    public void setLow(double low) {
    	this.low = low;
    }
    
    /**
     * Gets the high
     * @return the high
     */
    public double getHigh() {
    	return this.high;
    }
    
    /**
     * Sets the high value
     * @param high the new high
     */
    public void setHigh(double high) {
    	this.high = high;
    }
    
    /**
     * Gets the unitOfMeasurement
     * @return the unitOfMeasurement
     */
    public String getUnitOfMeasurement() {
    	return this.unitOfMeasurement;
    }
    
    /**
     * Sets the unit of measurement
     * @param unit the new unit
     */
    public void setUnitOfMeasurement(String unit) {
    	this.unitOfMeasurement = unit;
    }
    
    /**
     * Gets the result
     * @return the result
     */
    public String getResult() {
    	return this.result;
    }
    
    /**
     * Sets the result
     * @param result the new result
     */
    public void setResult(String result) {
    	this.result = result;
    }
    
    /**
     * Gets the datetime
     * @return the datetime
     */
    public LocalDateTime getDateTime() {
    	return this.dateTime;		
    }
    
    /**
     * Sets the datetime
     * @param dateTime the new dateTimee
     */
    public void setDateTime(LocalDateTime dateTime) {
    	this.dateTime = dateTime;
    }
    
}
