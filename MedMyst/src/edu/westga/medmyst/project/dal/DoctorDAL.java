package edu.westga.medmyst.project.dal;

import edu.westga.medmyst.project.model.Doctor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAL for accessing doctor data in the database.
 * 
 * @author demmons1
 * @version Fall 2024
 */
public class DoctorDAL {

    /**
     * Inserts a new doctor into the database.
     * 
     * @param doctor The doctor object containing the doctor information to insert.
     * @throws SQLException If a database access error occurs.
     */
    public void addDoctor(Doctor doctor) throws SQLException {
        String query = "INSERT INTO Doctor (f_name, l_name, date_of_birth, gender, specialty, phone_number, address, address_2, state, zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, doctor.getFirstName());
            stmt.setString(2, doctor.getLastName());
            stmt.setDate(3, java.sql.Date.valueOf(doctor.getDateOfBirth()));
            stmt.setString(4, doctor.getGender());
            stmt.setString(5, doctor.getSpecialty());
            stmt.setString(6, doctor.getPhoneNumber());
            stmt.setString(7, doctor.getAddress1());
            stmt.setString(8, doctor.getAddress2());
            stmt.setString(9, doctor.getState());
            stmt.setString(10, doctor.getZip());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int doctorId = generatedKeys.getInt(1);
                        doctor.setDoctorId(doctorId);
                    } else {
                        throw new SQLException("Creating doctor failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating doctor failed, no rows affected.");
            }
        }
    }

    /**
     * Updates an existing doctor in the database.
     * 
     * @param doctor The doctor object containing updated information.
     * @throws SQLException If a database access error occurs.
     */
    public void updateDoctor(Doctor doctor) throws SQLException {
        String query = "UPDATE Doctor SET f_name = ?, l_name = ?, date_of_birth = ?, gender = ?, specialty = ?, phone_number = ?, address = ?, address_2 = ?, state = ?, zip = ? WHERE doctor_id = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, doctor.getFirstName());
            stmt.setString(2, doctor.getLastName());
            stmt.setDate(3, java.sql.Date.valueOf(doctor.getDateOfBirth()));
            stmt.setString(4, doctor.getGender());
            stmt.setString(5, doctor.getSpecialty());
            stmt.setString(6, doctor.getPhoneNumber());
            stmt.setString(7, doctor.getAddress1());
            stmt.setString(8, doctor.getAddress2());
            stmt.setString(9, doctor.getState());
            stmt.setString(10, doctor.getZip());
            stmt.setInt(11, doctor.getDoctorId());

            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all doctors from the database.
     * 
     * @return A list of all doctors.
     * @throws SQLException If a database access error occurs.
     */
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM Doctor";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Doctor doctor = new Doctor(
                    rs.getInt("doctor_id"),
                    rs.getString("f_name"),
                    rs.getString("l_name"),
                    rs.getDate("date_of_birth").toLocalDate(),
                    rs.getString("gender"),
                    rs.getString("specialty"),
                    rs.getString("phone_number"),
                    rs.getString("address"),
                    rs.getString("address_2"),
                    rs.getString("state"),
                    rs.getString("zip")
                );
                doctors.add(doctor);
            }
        }

        return doctors;
    }
    
    /**
     * Retrieves a doctor by their ID.
     * 
     * @param doctorId The ID of the doctor to retrieve.
     * @return The Doctor object if found, null otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public Doctor getDoctorById(int doctorId) throws SQLException {
        String query = "SELECT * FROM Doctor WHERE doctor_id = ?";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, doctorId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                        rs.getInt("doctor_id"),
                        rs.getString("f_name"),
                        rs.getString("l_name"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("gender"),
                        rs.getString("specialty"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getString("address_2"),
                        rs.getString("state"),
                        rs.getString("zip")
                    );
                }
            }
        }

        return null;
    }
    
    /**
     * Retrieves a doctor ID by their full name.
     * 
     * @param fullName The full name of the doctor (e.g., "John Doe").
     * @return The doctor ID if found, -1 otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public int getDoctorIdByName(String fullName) throws SQLException {
        String query = "SELECT doctor_id, f_name, l_name FROM Doctor";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String firstName = rs.getString("f_name");
                String lastName = rs.getString("l_name");
                int doctorId = rs.getInt("doctor_id");

                if ((firstName + " " + lastName).equalsIgnoreCase(fullName)) {
                    return doctorId;
                }
            }
        }

        return -1;
    }
    
    /**
     * Retrieves a list of all doctors' full names.
     * 
     * @return A list of full names (e.g., "John Doe") of all doctors.
     * @throws SQLException If a database access error occurs.
     */
    public List<String> getAllDoctorNames() throws SQLException {
        List<String> doctorNames = new ArrayList<>();
        String query = "SELECT f_name, l_name FROM Doctor";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String fullName = rs.getString("f_name") + " " + rs.getString("l_name");
                doctorNames.add(fullName);
            }
        }

        return doctorNames;
    }
}
