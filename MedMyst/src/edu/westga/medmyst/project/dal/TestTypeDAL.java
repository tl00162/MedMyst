package edu.westga.medmyst.project.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.westga.medmyst.project.model.TestType;

public class TestTypeDAL {

    public List<TestType> getAllLabTestTypes() throws SQLException {
        String query = "SELECT lab_test_type, description FROM LabTestType";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            List<TestType> labTestTypes = new ArrayList<>();
            while (resultSet.next()) {
                labTestTypes.add(new TestType(
                    resultSet.getString("lab_test_type"),
                    resultSet.getString("description")
                ));
            }
            return labTestTypes;
        }
    }

    public void addLabTestType(TestType labTestType) throws SQLException {
        String query = "INSERT INTO LabTestType (lab_test_type, description) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, labTestType.getTypeName());
            stmt.setString(2, labTestType.getDescription());
            stmt.executeUpdate();
        }
    }

    public void updateLabTestType(TestType labTestType) throws SQLException {
        String query = "UPDATE LabTestType SET description = ? WHERE lab_test_type = ?";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, labTestType.getDescription());
            stmt.setString(2, labTestType.getTypeName());
            stmt.executeUpdate();
        }
    }

    public void deleteLabTestType(String labTestTypeName) throws SQLException {
        String query = "DELETE FROM LabTestType WHERE lab_test_type = ?";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, labTestTypeName);
            stmt.executeUpdate();
        }
    }
}


