package edu.westga.medmyst.project.dal;

import edu.westga.medmyst.project.model.Appointment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Layer for Appointment data in the database.
 * Handles CRUD operations for Appointment.
 * 
 * @author demmons1
 * @version Fall 2024
 */
public class AppointmentDAL {

    /**
     * Inserts a new appointment into the database.
     *
     * @param appointment The appointment object to be added.
     * @throws SQLException If an SQL error occurs.
     */
    public void addAppointment(Appointment appointment) throws SQLException {
        String query = "INSERT INTO appointment (patient_id, doctor_id, reason, details, appointment_type, datetime) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setString(3, appointment.getReason());
            stmt.setString(4, appointment.getDetails());
            stmt.setString(5, appointment.getAppointmentType());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(appointment.getDateTime()));

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    appointment.setAppointmentId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating appointment failed, no ID obtained.");
                }
            }
        }
    }

    /**
     * Updates an existing appointment in the database.
     *
     * @param appointment The appointment object containing updated data.
     * @throws SQLException If an SQL error occurs.
     */
    public void updateAppointment(Appointment appointment) throws SQLException {
        String query = "UPDATE appointment SET patient_id = ?, doctor_id = ?, reason = ?, details = ?, appointment_type = ?, datetime = ? WHERE appointment_id = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setString(3, appointment.getReason());
            stmt.setString(4, appointment.getDetails());
            stmt.setString(5, appointment.getAppointmentType());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(appointment.getDateTime()));
            stmt.setInt(7, appointment.getAppointmentId());

            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param appointmentId The ID of the appointment.
     * @return The appointment object if found, otherwise null.
     * @throws SQLException If an SQL error occurs.
     */
    public Appointment getAppointmentById(int appointmentId) throws SQLException {
        String query = "SELECT * FROM appointment WHERE appointment_id = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Appointment(
                            rs.getInt("appointment_id"),
                            rs.getInt("patient_id"),
                            rs.getInt("doctor_id"),
                            rs.getString("reason"),
                            rs.getString("details"),
                            rs.getString("appointment_type"),
                            rs.getTimestamp("datetime").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all appointments for a specific patient.
     *
     * @param patientId The ID of the patient.
     * @return A list of appointments for the patient.
     * @throws SQLException If an SQL error occurs.
     */
    public List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointment WHERE patient_id = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment(
                            rs.getInt("appointment_id"),
                            rs.getInt("patient_id"),
                            rs.getInt("doctor_id"),
                            rs.getString("reason"),
                            rs.getString("details"),
                            rs.getString("appointment_type"),
                            rs.getTimestamp("datetime").toLocalDateTime()
                    );
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }

    /**
     * Deletes an appointment by its ID.
     *
     * @param appointmentId The ID of the appointment to delete.
     * @throws SQLException If an SQL error occurs.
     */
    public void deleteAppointment(int appointmentId) throws SQLException {
        String query = "DELETE FROM appointment WHERE appointment_id = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointmentId);
            stmt.executeUpdate();
        }
    }
}
