package edu.westga.medmyst.project.model;

/**
 * The Class AppointmentType.
 * Represents an appointment type in the MedMyst application.
 * 
 * @version Fall 2024
 */
public class AppointmentType {
    private String typeName;
    private String description;

    public AppointmentType(String typeName, String description) {
        this.typeName = typeName;
        this.description = description;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.typeName; // Will be used to display in ComboBox
    }
}

