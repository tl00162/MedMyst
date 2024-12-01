package edu.westga.medmyst.project.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The AdminDAL class handles database operations for admin functionality.
 * 
 * @version Fall 2024
 * @author CS
 */
public class AdminDAL {

	/**
	 * Executes a given SQL query and returns the results as a list of maps.
	 *
	 * @param query The SQL query to execute.
	 * @return A list of maps, where each map represents a row of the result set
	 *         with column names as keys.
	 * @throws SQLException If an error occurs while executing the query.
	 */
	public List<Map<String, Object>> executeQuery(String query) throws SQLException {
		if (query == null || query.trim().isEmpty()) {
			throw new IllegalArgumentException("Query cannot be null or empty.");
		}

		List<Map<String, Object>> results = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				Statement stmt = connection.createStatement();
				ResultSet resultSet = stmt.executeQuery(query)) {

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (resultSet.next()) {
				Map<String, Object> row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					row.put(metaData.getColumnName(i), resultSet.getObject(i));
				}
				results.add(row);
			}
		}

		return results;
	}

	/**
	 * Retrieves a report of visits within the specified date range.
	 * 
	 * @param startDate the start date of the report range (inclusive)
	 * @param endDate   the end date of the report range (inclusive)
	 * @return a list of maps, where each map represents a visit and contains
	 *         key-value pairs of visit details
	 * @throws SQLException if a database access error occurs
	 */
	public List<Map<String, Object>> getVisitReport(LocalDate startDate, LocalDate endDate) throws SQLException {
		String query = "SELECT a.datetime AS 'Visit Date', " + "p.patient_id AS 'Patient ID', "
				+ "CONCAT(p.f_name, ' ', p.l_name) AS 'Patient Name', "
				+ "CONCAT(n.f_name, ' ', n.l_name) AS 'Nurse Name', "
				+ "CONCAT(d.f_name, ' ', d.l_name) AS 'Doctor Name', " + "a.reason AS 'Diagnosis', "
				+ "t.test_type AS 'Test Type', " + "t.datetime AS 'Test Perform Date', " + "CASE "
				+ "    WHEN t.lab_test_id IS NULL THEN '' " + "    WHEN alt.normality = 1 THEN 'Normal' "
				+ "    WHEN alt.normality = 0 THEN 'Abnormal' " + "    ELSE '' " + "END AS 'Test Normality' "
				+ "FROM Appointment a " + "JOIN Patient p ON a.patient_id = p.patient_id "
				+ "LEFT JOIN Doctor d ON a.doctor_id = d.doctor_id "
				+ "LEFT JOIN Checkup c ON a.appointment_id = c.appointment_id "
				+ "LEFT JOIN Nurse n ON c.nurse_id = n.nurse_id "
				+ "LEFT JOIN AppointmentLabTest alt ON a.appointment_id = alt.appointment_id "
				+ "LEFT JOIN LabTest t ON alt.lab_test_id = t.lab_test_id " + "WHERE a.datetime BETWEEN ? AND ? "
				+ "ORDER BY a.datetime, p.l_name";

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setTimestamp(1, java.sql.Timestamp.valueOf(startDate.atStartOfDay()));
			stmt.setTimestamp(2, java.sql.Timestamp.valueOf(endDate.plusDays(1).atStartOfDay()));

			ResultSet resultSet = stmt.executeQuery();
			return this.resultSetToList(resultSet);
		}
	}

	private List<Map<String, Object>> resultSetToList(ResultSet resultSet) throws SQLException {
		List<Map<String, Object>> results = new ArrayList<>();
		List<String> columnOrder = List.of("Visit Date", "Patient ID", "Patient Name", "Nurse Name", "Doctor Name",
				"Diagnosis", "Test Type", "Test Perform Date", "Test Normality");

		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();

		while (resultSet.next()) {
			Map<String, Object> row = new LinkedHashMap<>();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				row.put(columnName, resultSet.getObject(columnName));
			}

			Map<String, Object> orderedRow = new LinkedHashMap<>();
			for (String columnName : columnOrder) {
				orderedRow.put(columnName, row.getOrDefault(columnName, null));
			}
			results.add(orderedRow);
		}

		return results;
	}
}