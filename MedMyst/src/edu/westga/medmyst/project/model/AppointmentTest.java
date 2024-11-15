package edu.westga.medmyst.project.model;

public class AppointmentTest {
    private int appointmentId;
    private int testId;
    private boolean normality;

    public AppointmentTest(int appointmentId, int testId, boolean normality) {
        this.appointmentId = appointmentId;
        this.testId = testId;
        this.normality = normality;
    }

    public int getAppointmentId() {
    	return appointmentId; 
    }
    public void setAppointmentId(int appointmentId) {
    	this.appointmentId = appointmentId; 
    }

    public int getTestId() {
    	return testId; 
    }
    public void setTestId(int testId) {
    	this.testId = testId; 
    }
    
    public boolean getNormality() {
    	return this.normality;
    }
    
    public void setNormality(boolean normality) {
    	this.normality = normality;
    }
}

