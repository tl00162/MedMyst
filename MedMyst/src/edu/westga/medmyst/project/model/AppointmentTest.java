package edu.westga.medmyst.project.model;

public class AppointmentTest {
    private int appointmentId;
    private int testId;

    public AppointmentTest(int appointmentId, int testId) {
        this.appointmentId = appointmentId;
        this.testId = testId;
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
}

