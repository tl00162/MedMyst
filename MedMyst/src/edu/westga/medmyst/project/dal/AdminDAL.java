package edu.westga.medmyst.project.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
}