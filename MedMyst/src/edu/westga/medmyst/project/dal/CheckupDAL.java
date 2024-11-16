package edu.westga.medmyst.project.dal;

import java.sql.*;
import edu.westga.medmyst.project.model.Checkup;

/**
 * Data Access Layer for Checkup.
 * Handles interactions with the Checkup table in the database.
 * 
 * @author demmons1
 */
public class CheckupDAL {

	/**
	 * Gets the checkup related to the appointmentId
	 * @param appointmentId the specified appointmentId
	 * @return the related checkup
	 * @throws SQLException
	 */
    public Checkup getCheckupByAppointmentId(int appointmentId) throws SQLException {
        String query = "SELECT * FROM CheckUp WHERE appointment_id = ?";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointmentId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return new Checkup(
                    resultSet.getInt("appointment_id"),
                    resultSet.getInt("nurse_id"),
                    resultSet.getDouble("body_temperature"),
                    resultSet.getInt("diastolic_blood_pressure"),
                    resultSet.getInt("systolic_blood_pressure"),
                    resultSet.getInt("pulse"),
                    resultSet.getString("symptoms"),
                    resultSet.getDouble("height"),
                    resultSet.getDouble("weight"),
                    resultSet.getString("initial_diagnosis")
                );
            }
            return null;
        }
    }

    /**
     * Adds a checkup to the DB
     * @param checkup the checkup to add
     * @throws SQLException
     */
    public void addCheckup(Checkup checkup) throws SQLException {
        String query = "INSERT INTO CheckUp (appointment_id, nurse_id, body_temperature, diastolic_blood_pressure, " +
                       "systolic_blood_pressure, pulse, symptoms, height, weight, initial_diagnosis) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, checkup.getAppointmentId());
            stmt.setInt(2, checkup.getNurseId());
            stmt.setDouble(3, checkup.getBodyTemperature());
            stmt.setInt(4, checkup.getDiastolicBloodPressure());
            stmt.setInt(5, checkup.getSystolicBloodPressure());
            stmt.setInt(6, checkup.getPulse());
            stmt.setString(7, checkup.getSymptoms());
            stmt.setDouble(8, checkup.getHeight());
            stmt.setDouble(9, checkup.getWeight());
            stmt.setString(10, checkup.getInitialDiagnosis());

            stmt.executeUpdate();
        }
    }

    /**
     * Updates the selected checkup
     * @param checkup the checkup to update
     * @throws SQLException
     */
    public void updateCheckup(Checkup checkup) throws SQLException {
        String query = "UPDATE CheckUp SET nurse_id = ?, body_temperature = ?, diastolic_blood_pressure = ?, " +
                       "systolic_blood_pressure = ?, pulse = ?, symptoms =?, height = ?, weight = ?, initial_diagnosis = ? " +
                       "WHERE appointment_id = ?";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, checkup.getNurseId());
            stmt.setDouble(2, checkup.getBodyTemperature());
            stmt.setInt(3, checkup.getDiastolicBloodPressure());
            stmt.setInt(4, checkup.getSystolicBloodPressure());
            stmt.setInt(5, checkup.getPulse());
            stmt.setString(6, checkup.getSymptoms());
            stmt.setDouble(7, checkup.getHeight());
            stmt.setDouble(8, checkup.getWeight());
            stmt.setString(9, checkup.getInitialDiagnosis());
            stmt.setInt(10, checkup.getAppointmentId());

            stmt.executeUpdate();
        }
    }

    /**
     * Deletes the selected checkup
     * @param appointmentId the Id of the checkup to delete
     * @throws SQLException
     */
    public void deleteCheckup(int appointmentId) throws SQLException {
        String query = "DELETE FROM CheckUp WHERE appointment_id = ?";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointmentId);
            stmt.executeUpdate();
        }
    }
}
