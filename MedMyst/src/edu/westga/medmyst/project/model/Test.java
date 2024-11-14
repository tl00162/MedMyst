package edu.westga.medmyst.project.model;

import java.time.LocalDateTime;

/**
 * The Test class
 * @author demmons1
 * @version Fall 2024
 */
public class Test {
	private int testId;
    private int patientId;
    private int doctorId;
    private TestType testType;
    private boolean normality;
    private double low;
    private double high;
    private String unitOfMeasurement;
    private double result;
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
    public Test(int testId, int patientId, int doctorId, TestType testType, boolean normality, 
                double low, double high, String unitOfMeasurement, double result, LocalDateTime dateTime) {
        this.testId = testId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.testType = testType;
        this.normality = normality;
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
    public int doctorId() {
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
    
}
