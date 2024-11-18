package edu.westga.medmyst.project.dal;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.westga.medmyst.project.model.Test;
import edu.westga.medmyst.project.model.TestType;

public class TestDAL {

	public List<Test> getTestsByPatientId(int patientId) throws SQLException {
		String query = "SELECT * FROM LabTest WHERE patient_id = ?";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, patientId);
			ResultSet resultSet = stmt.executeQuery();

			List<Test> labTests = new ArrayList<>();
			while (resultSet.next()) {
				TestType testType = new TestType(resultSet.getString("test_type"), null);
				boolean finalized = resultSet.getShort("finalized") == 1;
				labTests.add(new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"), patientId,
						testType, resultSet.getDouble("low"), resultSet.getDouble("high"),
						resultSet.getString("unit_of_measurement"), resultSet.getString("results"),
						resultSet.getTimestamp("datetime").toLocalDateTime(), finalized));
			}
			return labTests;
		}
	}

	public List<Test> getCompleteTestsByPatientId(int patientId) throws SQLException {
		String query = "SELECT * FROM LabTest WHERE patient_id = ? AND results IS NOT NULL AND results <> ''";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, patientId);
			ResultSet resultSet = stmt.executeQuery();

			List<Test> completeTests = new ArrayList<>();
			while (resultSet.next()) {
				TestType testType = new TestType(resultSet.getString("test_type"), null);
				boolean finalized = resultSet.getShort("finalized") == 1;
				completeTests.add(new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"), patientId,
						testType, resultSet.getDouble("low"), resultSet.getDouble("high"),
						resultSet.getString("unit_of_measurement"), resultSet.getString("results"),
						resultSet.getTimestamp("datetime").toLocalDateTime(), finalized));
			}
			return completeTests;
		}
	}

	public void addLabTest(Test labTest) throws SQLException {
		String query = "INSERT INTO LabTest (doctor_id, patient_id, test_type, low, high, unit_of_measurement, results, datetime) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, labTest.getDoctorId());
			stmt.setInt(2, labTest.getPatientId());
			stmt.setString(3, labTest.getTestType().getTypeName());
			stmt.setDouble(4, labTest.getLow());
			stmt.setDouble(5, labTest.getHigh());
			stmt.setString(6, labTest.getUnitOfMeasurement());
			stmt.setString(7, labTest.getResult());
			stmt.setTimestamp(8, Timestamp.valueOf(labTest.getDateTime()));

			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Adding lab test failed, no rows affected.");
			}
		}
	}

	public void updateLabTest(Test labTest) throws SQLException {
		String query = "UPDATE LabTest SET doctor_id = ?, patient_id = ?, test_type = ?, low = ?, high = ?, "
				+ "unit_of_measurement = ?, results = ?, datetime = ? WHERE lab_test_id = ?";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, labTest.getDoctorId());
			stmt.setInt(2, labTest.getPatientId());
			stmt.setString(3, labTest.getTestType().getTypeName());
			stmt.setDouble(4, labTest.getLow());
			stmt.setDouble(5, labTest.getHigh());
			stmt.setString(6, labTest.getUnitOfMeasurement());
			stmt.setString(7, labTest.getResult());
			stmt.setTimestamp(8, Timestamp.valueOf(labTest.getDateTime()));
			stmt.setInt(9, labTest.getTestId());

			stmt.executeUpdate();
		}
	}

	public void deleteLabTest(int labTestId) throws SQLException {
		String query = "DELETE FROM LabTest WHERE lab_test_id = ?";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, labTestId);
			stmt.executeUpdate();
		}
	}

	public List<Test> getIncompleteTestsByPatientId(int patientId) throws SQLException {
		String query = "SELECT * FROM LabTest WHERE patient_id = ? AND "
				+ "(results IS NULL OR results = '' OR unit_of_measurement IS NULL OR low = 0 OR high = 0)";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, patientId);
			ResultSet resultSet = stmt.executeQuery();

			List<Test> incompleteTests = new ArrayList<>();
			while (resultSet.next()) {
				TestType testType = new TestType(resultSet.getString("test_type"), null);
				boolean finalized = resultSet.getShort("finalized") == 1;

				Test test = new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"), patientId,
						testType, resultSet.getDouble("low"), resultSet.getDouble("high"),
						resultSet.getString("unit_of_measurement"), resultSet.getString("results"),
						resultSet.getTimestamp("datetime").toLocalDateTime(), finalized);
				incompleteTests.add(test);

			}
			return incompleteTests;
		}
	}

	public void removeTestById(int testId) throws SQLException {
		String query = "DELETE FROM LabTest WHERE lab_test_id = ?";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, testId);
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException("No rows affected. Test may not exist.");
			}
		}
	}

	public void finalizeTest(int testId) throws SQLException {
	    String query = "{ CALL FinalizeTest(?) }";

	    try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
	         CallableStatement stmt = connection.prepareCall(query)) {

	        stmt.setInt(1, testId);

	        stmt.execute();
	    } catch (SQLException e) {
	        System.err.println("Error finalizing test: " + e.getMessage());
	        throw e;
	    }
	}

	public List<Test> getTests() throws SQLException {
		String query = "SELECT * FROM LabTest";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet resultSet = stmt.executeQuery()) {

			List<Test> allTests = new ArrayList<>();
			while (resultSet.next()) {
				TestType testType = new TestType(resultSet.getString("test_type"), null);
				boolean finalized = resultSet.getShort("finalized") == 1;

				allTests.add(new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"),
						resultSet.getInt("patient_id"), testType, resultSet.getDouble("low"),
						resultSet.getDouble("high"), resultSet.getString("unit_of_measurement"),
						resultSet.getString("results"), resultSet.getTimestamp("datetime").toLocalDateTime(),
						finalized));
			}
			return allTests;
		}
	}

	public List<Test> searchTestsByPatientInfo(String firstName, String lastName, LocalDate dob) throws SQLException {
		String query = "SELECT lt.* FROM LabTest lt " + "JOIN Patient p ON lt.patient_id = p.patient_id "
				+ "WHERE (p.f_name LIKE ? OR ? IS NULL) " + "AND (p.l_name LIKE ? OR ? IS NULL) "
				+ "AND (p.date_of_birth = ? OR ? IS NULL)";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, firstName != null ? firstName + "%" : null);
			stmt.setString(2, firstName);
			stmt.setString(3, lastName != null ? lastName + "%" : null);
			stmt.setString(4, lastName);
			stmt.setDate(5, dob != null ? Date.valueOf(dob) : null);
			stmt.setDate(6, dob != null ? Date.valueOf(dob) : null);

			ResultSet resultSet = stmt.executeQuery();

			List<Test> filteredTests = new ArrayList<>();
			while (resultSet.next()) {
				TestType testType = new TestType(resultSet.getString("test_type"), null);
				boolean finalized = resultSet.getShort("finalized") == 1;

				filteredTests.add(new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"),
						resultSet.getInt("patient_id"), testType, resultSet.getDouble("low"),
						resultSet.getDouble("high"), resultSet.getString("unit_of_measurement"),
						resultSet.getString("results"), resultSet.getTimestamp("datetime").toLocalDateTime(),
						finalized));
			}
			return filteredTests;
		}
	}
}
