package edu.westga.medmyst.project.dal;

import edu.westga.medmyst.project.model.Checkup;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The DAL for the Checkup class
 * @author demmons1
 * @version Fall 2024
 */
public class CheckupDAL {

	/**
	 * adds a Checkup to the DB
	 * @param checkup the checkup to add
	 * @throws SQLException
	 */
    public void addCheckup(Checkup checkup) throws SQLException {
        String query = "INSERT INTO CheckUp (appointment_id, nurse_id, body_temperature, diastolic_blood_pressure, systolic_blood_pressure, pulse, symptoms, height, weight) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
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

            stmt.executeUpdate();
        }
    }

    /**
     * Finds a checkup by the appointmentId
     * @param appointmentId the Id to search
     * @return the matching checkup
     * @throws SQLException
     */
    public Checkup getCheckupByAppointmentId(int appointmentId) throws SQLException {
        String query = "SELECT * FROM CheckUp WHERE appointment_id = ?";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setInt(1, appointmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Checkup(
                        rs.getInt("appointment_id"),
                        rs.getInt("nurse_id"),
                        rs.getDouble("body_temperature"),
                        rs.getInt("diastolic_blood_pressure"),
                        rs.getInt("systolic_blood_pressure"),
                        rs.getInt("pulse"),
                        rs.getString("symptoms"),
                        rs.getDouble("height"),
                        rs.getDouble("weight")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Updates the desired checkup
     * @param checkup the checkup to update
     * @throws SQLException
     */
    public void updateCheckup(Checkup checkup) throws SQLException {
        String query = "UPDATE CheckUp SET nurse_id = ?, body_temperature = ?, diastolic_blood_pressure = ?, " +
                       "systolic_blood_pressure = ?, pulse = ?, symptoms = ?, height = ?, weight = ? WHERE appointment_id = ?";
        
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
            stmt.setInt(9, checkup.getAppointmentId());

            stmt.executeUpdate();
        }
    }

    /**
     * Deletes the selected checkup
     * @param appointmentId the appointmentId of the selected checkup
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

    /**
     * Gets the list of all checkups
     * @return the list of checkups
     * @throws SQLException
     */
    public List<Checkup> getAllCheckups() throws SQLException {
        List<Checkup> checkups = new ArrayList<>();
        String query = "SELECT * FROM CheckUp";
        
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Checkup checkup = new Checkup(
                    rs.getInt("appointment_id"),
                    rs.getInt("nurse_id"),
                    rs.getDouble("body_temperature"),
                    rs.getInt("diastolic_blood_pressure"),
                    rs.getInt("systolic_blood_pressure"),
                    rs.getInt("pulse"),
                    rs.getString("symptoms"),
                    rs.getDouble("height"),
                    rs.getDouble("weight")
                );
                checkups.add(checkup);
            }
        }
        return checkups;
    }
}
