package edu.westga.medmyst.project.dal;

import edu.westga.medmyst.project.model.Checkup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The DAL class for Checkup
 * @author demmons1
 * @version Fall 2024
 */
public class CheckupDAL {

    /**
     * Adds a new checkup record to the database.
     * 
     * @param checkup the CheckUp object to add
     * @throws SQLException if a database access error occurs
     */
    public void addCheckUp(Checkup checkup) throws SQLException {
        String query = "INSERT INTO CheckUp (appointment_id, nurse_id, body_temperature, diastolic_blood_pressure, systolic_blood_pressure, pulse, symptoms) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, checkup.getAppointmentId());
            stmt.setInt(2, checkup.getNurseId());
            stmt.setDouble(3, checkup.getBodyTemperature());
            stmt.setInt(4, checkup.getDiastolicBloodPressure());
            stmt.setInt(5, checkup.getSystolicBloodPressure());
            stmt.setInt(6, checkup.getPulse());
            stmt.setString(7, checkup.getSymptoms());

            stmt.executeUpdate();
        }
    }

    /**
     * Updates an existing checkup record in the database.
     * 
     * @param checkup the CheckUp object to update
     * @throws SQLException if a database access error occurs
     */
    public void updateCheckUp(Checkup checkup) throws SQLException {
        String query = "UPDATE CheckUp SET nurse_id = ?, body_temperature = ?, diastolic_blood_pressure = ?, systolic_blood_pressure = ?, pulse = ?, symptoms = ? WHERE appointment_id = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, checkup.getNurseId());
            stmt.setDouble(2, checkup.getBodyTemperature());
            stmt.setInt(3, checkup.getDiastolicBloodPressure());
            stmt.setInt(4, checkup.getSystolicBloodPressure());
            stmt.setInt(5, checkup.getPulse());
            stmt.setString(6, checkup.getSymptoms());
            stmt.setInt(7, checkup.getAppointmentId());

            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a checkup record from the database.
     * 
     * @param appointmentId the ID of the appointment associated with the checkup to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteCheckUp(int appointmentId) throws SQLException {
        String query = "DELETE FROM CheckUp WHERE appointment_id = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointmentId);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a checkup record by appointment ID.
     * 
     * @param appointmentId the ID of the appointment associated with the checkup
     * @return a CheckUp object, or null if no checkup is found
     * @throws SQLException if a database access error occurs
     */
    public Checkup getCheckUpByAppointmentId(int appointmentId) throws SQLException {
        String query = "SELECT * FROM CheckUp WHERE appointment_id = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointmentId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return this.extractCheckupFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all checkup records from the database.
     * 
     * @return a list of CheckUp objects
     * @throws SQLException if a database access error occurs
     */
    public List<Checkup> getAllCheckUps() throws SQLException {
        String query = "SELECT * FROM CheckUp";
        List<Checkup> checkUps = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            while (resultSet.next()) {
                Checkup checkup = this.extractCheckupFromResultSet(resultSet);
                checkUps.add(checkup);
            }
        }
        return checkUps;
    }

    /**
     * Helper method to extract a CheckUp object from a ResultSet.
     * 
     * @param resultSet the ResultSet containing checkup data
     * @return a CheckUp object
     * @throws SQLException if a database access error occurs
     */
    private Checkup extractCheckupFromResultSet(ResultSet resultSet) throws SQLException {
        int appointmentId = resultSet.getInt("appointment_id");
        int nurseId = resultSet.getInt("nurse_id");
        double bodyTemperature = resultSet.getDouble("body_temperature");
        int diastolicBloodPressure = resultSet.getInt("diastolic_blood_pressure");
        int systolicBloodPressure = resultSet.getInt("systolic_blood_pressure");
        int pulse = resultSet.getInt("pulse");
        String symptoms = resultSet.getString("symptoms");

        return new Checkup(appointmentId, nurseId, bodyTemperature, diastolicBloodPressure, systolicBloodPressure, pulse, symptoms);
    }
}

