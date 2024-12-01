package edu.westga.medmyst.project.dal;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.westga.medmyst.project.model.Test;
import edu.westga.medmyst.project.model.TestType;

public class TestDAL {

	/**
	 * Retrieves all tests associated with a specific patient.
	 * 
	 * @param patientId the ID of the patient
	 * @return a list of tests associated with the patient
	 * @throws SQLException if a database access error occurs
	 */
	public List<Test> getTestsByPatientId(int patientId) throws SQLException {
		String query = "SELECT * FROM LabTest WHERE patient_id = ?";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, patientId);
			ResultSet resultSet = stmt.executeQuery();

			List<Test> labTests = new ArrayList<>();
			while (resultSet.next()) {
				TestType testType = new TestType(resultSet.getString("test_type"), null, 0, 0, null);
				boolean finalized = resultSet.getShort("finalized") == 1;
				boolean normality = resultSet.getShort("normality") == 1;
				labTests.add(new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"), patientId,
						testType, resultSet.getDouble("low"), resultSet.getDouble("high"),
						resultSet.getString("unit_of_measurement"), resultSet.getString("results"),
						resultSet.getTimestamp("datetime").toLocalDateTime(), finalized, normality));
			}
			return labTests;
		}
	}

	/**
	 * Retrieves all completed tests for a specific patient.
	 * 
	 * @param patientId the ID of the patient
	 * @return a list of completed tests for the patient
	 * @throws SQLException if a database access error occurs
	 */
	public List<Test> getCompleteTestsByPatientId(int patientId) throws SQLException {
		String query = "SELECT * FROM LabTest WHERE patient_id = ? AND results IS NOT NULL AND results <> ''";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, patientId);
			ResultSet resultSet = stmt.executeQuery();

			List<Test> completeTests = new ArrayList<>();
			while (resultSet.next()) {
				TestType testType = new TestType(resultSet.getString("test_type"), null, 0, 0, null);
				boolean finalized = resultSet.getShort("finalized") == 1;
				boolean normality = resultSet.getShort("normality") == 1; // Add normality here
				completeTests.add(new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"), patientId,
						testType, resultSet.getDouble("low"), resultSet.getDouble("high"),
						resultSet.getString("unit_of_measurement"), resultSet.getString("results"),
						resultSet.getTimestamp("datetime").toLocalDateTime(), finalized, normality));
			}
			return completeTests;
		}
	}

	/**
	 * Adds a new test to the LabTest table as part of a transactional operation.
	 * 
	 * This method inserts a new lab test into the LabTest table and ensures that
	 * the operation is atomic by utilizing transactions. If any part of the
	 * operation fails, the transaction will be rolled back to maintain data
	 * integrity.
	 * 
	 * @param labTest   the test to add, including details such as doctor, patient,
	 *                  test type, and results
	 * @param normality the normality status of the test (true for normal, false for
	 *                  abnormal)
	 * @return the generated ID of the newly added test
	 * @throws SQLException if a database access error occurs, such as a failure
	 *                      during the insert operation or while retrieving the
	 *                      generated key
	 */
	public int addLabTest(Test labTest, boolean normality) throws SQLException {
		String insertQuery = "INSERT INTO LabTest (doctor_id, patient_id, test_type, low, high, unit_of_measurement, results, datetime, normality) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet generatedKeys = null;

		try {
			connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
			connection.setAutoCommit(false);

			insertStmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, labTest.getDoctorId());
			insertStmt.setInt(2, labTest.getPatientId());
			insertStmt.setString(3, labTest.getTestType().getTypeName());
			insertStmt.setDouble(4, labTest.getLow());
			insertStmt.setDouble(5, labTest.getHigh());
			insertStmt.setString(6, labTest.getUnitOfMeasurement());
			insertStmt.setString(7, labTest.getResult());
			insertStmt.setTimestamp(8, Timestamp.valueOf(labTest.getDateTime()));
			insertStmt.setBoolean(9, normality);

			int affectedRows = insertStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Adding lab test failed, no rows affected.");
			}

			generatedKeys = insertStmt.getGeneratedKeys();
			int labTestId;
			if (generatedKeys.next()) {
				labTestId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Adding lab test failed, no ID obtained.");
			}

			connection.commit();
			return labTestId;

		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException rollbackEx) {
					System.err.println("Rollback failed: " + rollbackEx.getMessage());
				}
			}
			throw e;
		} finally {
			if (generatedKeys != null)
				generatedKeys.close();
			if (insertStmt != null)
				insertStmt.close();
			if (connection != null)
				connection.close();
		}
	}

	/**
	 * Updates an existing test in the LabTest table.
	 * 
	 * @param labTest   the test to update
	 * @param normality the normality of the test (true for normal, false for
	 *                  abnormal)
	 * @throws SQLException if a database access error occurs
	 */
	public void updateLabTest(Test labTest, boolean normality) throws SQLException {
		String query = "UPDATE LabTest SET doctor_id = ?, patient_id = ?, test_type = ?, low = ?, high = ?, "
				+ "unit_of_measurement = ?, results = ?, datetime = ?, normality = ? WHERE lab_test_id = ?";
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
			stmt.setBoolean(9, normality);
			stmt.setInt(10, labTest.getTestId());

			stmt.executeUpdate();
		}
	}

	/**
	 * Deletes a test from the LabTest table by its ID.
	 * 
	 * @param labTestId the ID of the test to delete
	 * @throws SQLException if a database access error occurs
	 */
	public void deleteLabTest(int labTestId) throws SQLException {
		String query = "DELETE FROM LabTest WHERE lab_test_id = ?";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, labTestId);
			stmt.executeUpdate();
		}
	}

	/**
	 * Retrieves all incomplete tests for a specific patient.
	 * 
	 * @param patientId the ID of the patient
	 * @return a list of incomplete tests for the patient
	 * @throws SQLException if a database access error occurs
	 */
	public List<Test> getIncompleteTestsByPatientId(int patientId) throws SQLException {
		String query = "SELECT * FROM LabTest WHERE patient_id = ? AND (results IS NULL OR results = '')";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, patientId);
			ResultSet resultSet = stmt.executeQuery();

			List<Test> incompleteTests = new ArrayList<>();
			while (resultSet.next()) {
				TestType testType = new TestType(resultSet.getString("test_type"), null, 0, 0, null);
				boolean finalized = resultSet.getShort("finalized") == 1;
				boolean normality = resultSet.getShort("normality") == 1; // Add normality here

				incompleteTests.add(new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"), patientId,
						testType, resultSet.getDouble("low"), resultSet.getDouble("high"),
						resultSet.getString("unit_of_measurement"), resultSet.getString("results"),
						resultSet.getTimestamp("datetime").toLocalDateTime(), finalized, normality));
			}
			return incompleteTests;
		}
	}

	/**
	 * Removes a test from the LabTest table by its ID.
	 * 
	 * @param testId the ID of the test to remove
	 * @throws SQLException if a database access error occurs
	 */
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

	/**
	 * Finalizes a test in the LabTest table by updating its finalized status.
	 * 
	 * @param testId the ID of the test to be finalized
	 * @throws SQLException if a database access error occurs
	 */
	public void finalizeTest(int testId) throws SQLException {
		String query = "UPDATE LabTest SET finalized = 1 WHERE lab_test_id = ?";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, testId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException("Failed to finalize test: No rows affected.");
			}
		}
	}

	/**
	 * Retrieves all tests from the LabTest table.
	 * 
	 * @return a list of all tests
	 * @throws SQLException if a database access error occurs
	 */
	public List<Test> getTests() throws SQLException {
		String query = "SELECT * FROM LabTest";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet resultSet = stmt.executeQuery()) {

			List<Test> allTests = new ArrayList<>();
			while (resultSet.next()) {
				TestType testType = new TestType(resultSet.getString("test_type"), null, 0, 0, null);
				boolean finalized = resultSet.getShort("finalized") == 1;
				boolean normality = resultSet.getShort("normality") == 1; // Add normality here

				allTests.add(new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"),
						resultSet.getInt("patient_id"), testType, resultSet.getDouble("low"),
						resultSet.getDouble("high"), resultSet.getString("unit_of_measurement"),
						resultSet.getString("results"), resultSet.getTimestamp("datetime").toLocalDateTime(), finalized,
						normality));
			}
			return allTests;
		}
	}

	/**
	 * Retrieves all completed tests for a specific appointment.
	 * 
	 * @param appointmentId the ID of the appointment
	 * @return a list of completed tests for the appointment
	 * @throws SQLException if a database access error occurs
	 */
	public List<Test> getCompleteTestsForAppointment(int appointmentId) throws SQLException {
		String query = "SELECT lt.* " + "FROM LabTest lt "
				+ "JOIN AppointmentLabTest alt ON lt.lab_test_id = alt.lab_test_id "
				+ "WHERE alt.appointment_id = ? AND lt.results IS NOT NULL AND lt.results <> ''";
		return this.getTestsForQuery(query, appointmentId);
	}

	/**
	 * Retrieves all incomplete tests for a specific appointment.
	 * 
	 * @param appointmentId the ID of the appointment
	 * @return a list of incomplete tests for the appointment
	 * @throws SQLException if a database access error occurs
	 */
	public List<Test> getIncompleteTestsForAppointment(int appointmentId) throws SQLException {
		String query = "SELECT lt.* " + "FROM LabTest lt "
				+ "JOIN AppointmentLabTest alt ON lt.lab_test_id = alt.lab_test_id "
				+ "WHERE alt.appointment_id = ? AND (lt.results IS NULL OR lt.results = '')";
		return this.getTestsForQuery(query, appointmentId);
	}

	private List<Test> getTestsForQuery(String query, int appointmentId) throws SQLException {
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, appointmentId);
			ResultSet resultSet = stmt.executeQuery();

			List<Test> tests = new ArrayList<>();
			while (resultSet.next()) {
				TestType testType = new TestType(resultSet.getString("test_type"), null, 0, 0, null);
				boolean finalized = resultSet.getShort("finalized") == 1;
				boolean normality = resultSet.getShort("normality") == 1; // Add normality here
				tests.add(new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"),
						resultSet.getInt("patient_id"), testType, resultSet.getDouble("low"),
						resultSet.getDouble("high"), resultSet.getString("unit_of_measurement"),
						resultSet.getString("results"), resultSet.getTimestamp("datetime").toLocalDateTime(), finalized,
						normality));
			}
			return tests;
		}
	}

	/**
	 * Searches for tests by patient information.
	 * 
	 * @param firstName the first name of the patient
	 * @param lastName  the last name of the patient
	 * @param dob       the date of birth of the patient
	 * @return a list of tests matching the patient information
	 * @throws SQLException if a database access error occurs
	 */
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
				TestType testType = new TestType(resultSet.getString("test_type"), null, 0, 0, null);
				boolean finalized = resultSet.getShort("finalized") == 1;
				boolean normality = resultSet.getShort("normality") == 1; // Add normality here

				filteredTests.add(new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"),
						resultSet.getInt("patient_id"), testType, resultSet.getDouble("low"),
						resultSet.getDouble("high"), resultSet.getString("unit_of_measurement"),
						resultSet.getString("results"), resultSet.getTimestamp("datetime").toLocalDateTime(), finalized,
						normality));
			}
			return filteredTests;
		}
	}

	/**
	 * Links a test to an appointment in the AppointmentLabTest table with normality
	 * status.
	 * 
	 * @param testId        the ID of the test to link
	 * @param appointmentId the ID of the appointment
	 * @param normality     the normality of the test (1 for normal, 0 for abnormal)
	 * @throws SQLException if a database access error occurs
	 */
	public void linkTestToAppointment(int testId, int appointmentId, int normality) throws SQLException {
		String query = "INSERT INTO AppointmentLabTest (appointment_id, lab_test_id, normality) VALUES (?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, appointmentId);
			stmt.setInt(2, testId);
			stmt.setInt(3, normality);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException("Failed to link test to appointment: No rows affected.");
			}
		}
	}

	/**
	 * Retrieves the last inserted test ID.
	 * 
	 * @return the ID of the last inserted test
	 * @throws SQLException if a database access error occurs
	 */
	public int getLastInsertedTestId() throws SQLException {
		String query = "SELECT LAST_INSERT_ID() AS last_id";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				Statement stmt = connection.createStatement();
				ResultSet resultSet = stmt.executeQuery(query)) {

			if (resultSet.next()) {
				return resultSet.getInt("last_id");
			} else {
				throw new SQLException("No ID generated for the last inserted test.");
			}
		}
	}

	/**
	 * Retrieves all past tests for a patient that are not part of the current
	 * appointment.
	 * 
	 * @param patientId            the ID of the patient
	 * @param currentAppointmentId the ID of the current appointment
	 * @return a list of past tests for the patient
	 * @throws SQLException if a database access error occurs
	 */
	public List<Test> getPastTestsForPatient(int patientId, int currentAppointmentId) throws SQLException {
		String query = "SELECT * FROM LabTest lt " + "WHERE lt.patient_id = ? "
				+ "AND lt.lab_test_id NOT IN (SELECT lab_test_id FROM AppointmentLabTest WHERE appointment_id = ?)";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, patientId);
			stmt.setInt(2, currentAppointmentId);

			ResultSet resultSet = stmt.executeQuery();

			List<Test> pastTests = new ArrayList<>();
			while (resultSet.next()) {
				TestType testType = new TestType(resultSet.getString("test_type"), null, 0, 0, null);
				boolean finalized = resultSet.getShort("finalized") == 1;
				boolean normality = resultSet.getShort("normality") == 1; // Add normality here

				pastTests.add(new Test(resultSet.getInt("lab_test_id"), resultSet.getInt("doctor_id"), patientId,
						testType, resultSet.getDouble("low"), resultSet.getDouble("high"),
						resultSet.getString("unit_of_measurement"), resultSet.getString("results"),
						resultSet.getTimestamp("datetime").toLocalDateTime(), finalized, normality));
			}
			return pastTests;
		}
	}

	/**
	 * Updates the normality of a test in the AppointmentLabTest table.
	 * 
	 * @param testId    the ID of the test
	 * @param normality the normality of the test (1 for normal, 0 for abnormal)
	 * @throws SQLException if a database access error occurs
	 */
	public void updateNormalityForAppointmentLabTest(int testId, int normality) throws SQLException {
		String query = "UPDATE AppointmentLabTest SET normality = ? WHERE lab_test_id = ?";
		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, normality);
			stmt.setInt(2, testId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException("Failed to update normality: No rows affected.");
			}
		}
	}

}
