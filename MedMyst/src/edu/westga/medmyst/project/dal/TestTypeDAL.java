package edu.westga.medmyst.project.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.westga.medmyst.project.model.TestType;

/**
 * Data Access Layer for managing Lab Test Types. Provides methods to interact
 * with the LabTestType table in the database.
 * 
 * @version Fall 2024
 */
public class TestTypeDAL {

	/**
	 * Retrieves all lab test types from the database.
	 * 
	 * @return a list of all lab test types
	 * @throws SQLException if a database access error occurs
	 */
	public List<TestType> getAllLabTestTypes() throws SQLException {
		String query = "SELECT lab_test_type, description, low, high, units FROM LabTestType";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			List<TestType> labTestTypes = new ArrayList<>();
			while (resultSet.next()) {
				labTestTypes.add(new TestType(resultSet.getString("lab_test_type"), resultSet.getString("description"),
						resultSet.getDouble("low"), resultSet.getDouble("high"), resultSet.getString("units")));
			}
			return labTestTypes;
		}
	}

	/**
	 * Adds a new lab test type to the database.
	 * 
	 * @param labTestType the lab test type to add
	 * @throws SQLException if a database access error occurs
	 */
	public void addLabTestType(TestType labTestType) throws SQLException {
		String query = "INSERT INTO LabTestType (lab_test_type, description) VALUES (?, ?)";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, labTestType.getTypeName());
			stmt.setString(2, labTestType.getDescription());
			stmt.executeUpdate();
		}
	}

	/**
	 * Updates an existing lab test type in the database.
	 * 
	 * @param labTestType the lab test type with updated details
	 * @throws SQLException if a database access error occurs
	 */
	public void updateLabTestType(TestType labTestType) throws SQLException {
		String query = "UPDATE LabTestType SET description = ? WHERE lab_test_type = ?";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, labTestType.getDescription());
			stmt.setString(2, labTestType.getTypeName());
			stmt.executeUpdate();
		}
	}

	/**
	 * Deletes a lab test type from the database by its name.
	 * 
	 * @param labTestTypeName the name of the lab test type to delete
	 * @throws SQLException if a database access error occurs
	 */
	public void deleteLabTestType(String labTestTypeName) throws SQLException {
		String query = "DELETE FROM LabTestType WHERE lab_test_type = ?";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, labTestTypeName);
			stmt.executeUpdate();
		}
	}
}
