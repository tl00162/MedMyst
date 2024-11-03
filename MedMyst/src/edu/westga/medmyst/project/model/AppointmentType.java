package edu.westga.medmyst.project.model;

/**
 * The Class AppointmentType.
 * Represents an appointment type in the MedMyst application.
 * @author demmons1
 * @version Fall 2024
 */
public class AppointmentType {
    private String typeName;
    private String description;

    /**
     * Creates a new appointment Type
     * @param typeName the type
     * @param description the description
     */
    public AppointmentType(String typeName, String description) {
        this.typeName = typeName;
        this.description = description;
    }

    /**
     * Gets the typeName
     * @return the typeName
     */
    public String getTypeName() {
        return this.typeName;
    }

    /**
     * Sets the type name
     * @param typeName the new type name
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Gets the description
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.typeName;
    }
}

