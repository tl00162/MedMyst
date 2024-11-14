package edu.westga.medmyst.project.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.westga.medmyst.project.model.AppointmentTest;

public class AppointmentTestDAL {

    public List<AppointmentTest> getTestsByAppointmentId(int appointmentId) throws SQLException {
        String query = "SELECT lab_test_id FROM AppointmentLabTest WHERE appointment_id = ?";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointmentId);
            ResultSet resultSet = stmt.executeQuery();

            List<AppointmentTest> appointmentTests = new ArrayList<>();
            while (resultSet.next()) {
                appointmentTests.add(new AppointmentTest(appointmentId, resultSet.getInt("lab_test_id")));
            }
            return appointmentTests;
        }
    }

    public void addAppointmentLabTest(AppointmentTest appointmentTest) throws SQLException {
        String query = "INSERT INTO AppointmentLabTest (appointment_id, lab_test_id) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointmentTest.getAppointmentId());
            stmt.setInt(2, appointmentTest.getTestId());
            stmt.executeUpdate();
        }
    }

    public void updateAppointmentLabTest(AppointmentTest appointmentTest, int newAppointmentId) throws SQLException {
        String query = "UPDATE AppointmentLabTest SET appointment_id = ? WHERE appointment_id = ? AND lab_test_id = ?";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, newAppointmentId);
            stmt.setInt(2, appointmentTest.getAppointmentId());
            stmt.setInt(3, appointmentTest.getTestId());
            stmt.executeUpdate();
        }
    }

    public void deleteAppointmentLabTest(int appointmentId, int labTestId) throws SQLException {
        String query = "DELETE FROM AppointmentLabTest WHERE appointment_id = ? AND lab_test_id = ?";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, appointmentId);
            stmt.setInt(2, labTestId);
            stmt.executeUpdate();
        }
    }
}


