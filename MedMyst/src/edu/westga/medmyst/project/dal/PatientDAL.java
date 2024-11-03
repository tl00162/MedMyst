package edu.westga.medmyst.project.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
	 * @param patient The patient object containing the patient information to
	 *                insert.
	 * @throws SQLException If a database access error occurs.
	 */
	public void addPatient(Patient patient) throws SQLException {
		String query = "INSERT INTO Patient (f_name, l_name, date_of_birth, gender, phone_number, address, address_2, state, zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, patient.getFName());
			stmt.setString(2, patient.getLName());
			stmt.setDate(3, java.sql.Date.valueOf(patient.getDateOfBirth()));
			stmt.setString(4, patient.getGender());
			stmt.setString(5, patient.getPhoneNumber());
			stmt.setString(6, patient.getAddress1());
			stmt.setString(7, patient.getAddress2());
			stmt.setString(8, patient.getState());
			stmt.setString(9, patient.getZip());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows > 0) {

				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int patientId = generatedKeys.getInt(1);
						patient.setPatientId(patientId);
					} else {
						throw new SQLException("Creating patient failed, no ID obtained.");
					}
				}
			} else {
				throw new SQLException("Creating patient failed, no rows affected.");
			}
		}
	}

	/**
	 * Updates an existing patient in the database.
	 * 
	 * @param patient The patient object containing updated information.
	 * @throws SQLException If a database access error occurs.
	 */
	public void updatePatient(Patient patient) throws SQLException {
		String query = "UPDATE patient SET f_name = ?, l_name = ?, date_of_birth = ?, gender = ?, phone_number = ?, address = ?, address_2 = ?, state = ?, zip = ? WHERE patient_id = ?";

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
			stmt.setInt(10, patient.getPatientId());

			System.out.println("Executing update for patient ID: " + patient.getPatientId());
			int rowsAffected = stmt.executeUpdate();
			System.out.println("Update complete. Rows affected: " + rowsAffected);
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
				Patient patient = new Patient(rs.getInt("patient_id"), rs.getString("f_name"), rs.getString("l_name"),
						rs.getDate("date_of_birth").toLocalDate(), rs.getString("gender"), rs.getString("phone_number"),
						rs.getString("address"), rs.getString("address_2"), rs.getString("state"), rs.getString("zip"));
				patients.add(patient);
			}
		}

		return patients;
	}
	
	/**
     * Searches for patients by first name, last name, and date of birth.
     * 
     * @param firstName The first name filter (nullable)
     * @param lastName  The last name filter (nullable)
     * @param dob       The date of birth filter (nullable)
     * @return List of matching patients
     * @throws SQLException If there is an error accessing the database
     */
	public List<Patient> searchPatients(String firstName, String lastName, LocalDate dob) throws SQLException {
	    StringBuilder queryBuilder = new StringBuilder("SELECT * FROM patient WHERE 1=1");

	    if (firstName != null && !firstName.isEmpty()) {
	        queryBuilder.append(" AND f_name = ?");
	    }
	    if (lastName != null && !lastName.isEmpty()) {
	        queryBuilder.append(" AND l_name = ?");
	    }
	    if (dob != null) {
	        queryBuilder.append(" AND date_of_birth = ?");
	    }

	    String query = queryBuilder.toString();

	    try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
	         PreparedStatement stmt = connection.prepareStatement(query)) {

	        int paramIndex = 1;
	        if (firstName != null && !firstName.isEmpty()) {
	            stmt.setString(paramIndex++, firstName);
	        }
	        if (lastName != null && !lastName.isEmpty()) {
	            stmt.setString(paramIndex++, lastName);
	        }
	        if (dob != null) {
	            stmt.setDate(paramIndex++, java.sql.Date.valueOf(dob));
	        }

	        ResultSet rs = stmt.executeQuery();
	        return this.buildPatientListFromResultSet(rs);
	    }
	}
    
    private List<Patient> buildPatientListFromResultSet(ResultSet rs) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        while (rs.next()) {
            Patient patient = new Patient(
                rs.getInt("patient_id"),
                rs.getString("f_name"),
                rs.getString("l_name"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getString("gender"),
                rs.getString("phone_number"),
                rs.getString("address"),
                rs.getString("address_2"),
                rs.getString("state"),
                rs.getString("zip")
            );
            patients.add(patient);
        }
        return patients;
    }
    
    public Patient getPatientById(int patientId) throws SQLException {
        String query = "SELECT * FROM Patient WHERE patient_id = ?";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
        		PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Patient(
                    resultSet.getInt("patient_id"),
                    resultSet.getString("f_name"),
                    resultSet.getString("l_name"),
                    resultSet.getDate("date_of_birth").toLocalDate(),
                    resultSet.getString("gender"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("address"),
                    resultSet.getString("address_2"),
                    resultSet.getString("state"),
                    resultSet.getString("zip")
                );
            }
        }
        return null; // Return null if no patient is found
    }

}
