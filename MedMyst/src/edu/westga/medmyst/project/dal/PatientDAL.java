package edu.westga.medmyst.project.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.westga.medmyst.project.model.Patient;

/**
 * DAL for accessing patient data in the database.
 * 
 * @author demmons1
 * @version Fall 2024
 */
public class PatientDAL {

    /**
     * Inserts a new patient into the database.
     * 
     * @param patient The patient object containing the patient information to insert.
     * @throws SQLException If a database access error occurs.
     */
    public void addPatient(Patient patient) throws SQLException {
        String query = "INSERT INTO patient (f_name, l_name, date_of_birth, gender, phone_number, address, address_2, state, zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, patient.getFName());
            stmt.setString(2, patient.getLName());
            stmt.setDate(3, java.sql.Date.valueOf(patient.getDateOfBirth()));
            stmt.setString(4, patient.getGender());
            stmt.setString(5, patient.getPhoneNumber());
            stmt.setString(6, patient.getAddress1());
            stmt.setString(7, patient.getAddress2());
            stmt.setString(8, patient.getState());
            stmt.setString(9, patient.getZip());

            stmt.executeUpdate();
        }
    }

    /**
     * Updates an existing patient in the database.
     * 
     * @param patient The patient object containing updated information.
     * @throws SQLException If a database access error occurs.
     */
    public void updatePatient(Patient patient) throws SQLException {
        String query = "UPDATE patient SET f_name = ?, l_name = ?, date_of_birth = ?, gender = ?, phone_number = ?, address = ?, address_2 = ?, state = ?, zip = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, patient.getFName());
            stmt.setString(2, patient.getLName());
            stmt.setDate(3, java.sql.Date.valueOf(patient.getDateOfBirth()));
            stmt.setString(4, patient.getGender());
            stmt.setString(5, patient.getPhoneNumber());
            stmt.setString(6, patient.getAddress1());
            stmt.setString(7, patient.getAddress2());
            stmt.setString(8, patient.getState());
            stmt.setString(9, patient.getZip());

            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all patients from the database.
     * 
     * @return A list of all patients.
     * @throws SQLException If a database access error occurs.
     */
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patient";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("f_name"),
                    rs.getString("l_name"),
                    rs.getDate("date_of_birth").toLocalDate(),
                    rs.getString("phone_number"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("address_2"),
                    rs.getString("state"),
                    rs.getString("zip")
                );
                patients.add(patient);
            }
        }

        return patients;
    }
}
