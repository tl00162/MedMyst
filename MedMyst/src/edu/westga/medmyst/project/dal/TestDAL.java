package edu.westga.medmyst.project.dal;

import java.sql.*;
import java.time.LocalDateTime;
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
                labTests.add(new Test(
                    resultSet.getInt("lab_test_id"),
                    patientId,
                    resultSet.getInt("doctor_id"),
                    testType,
                    resultSet.getBoolean("normality"),
                    resultSet.getDouble("low"),
                    resultSet.getDouble("high"),
                    resultSet.getString("unit_of_measurement"),
                    resultSet.getString("results"),
                    resultSet.getTimestamp("datetime").toLocalDateTime()
                ));
            }
            return labTests;
        }
    }

    public void addLabTest(Test labTest) throws SQLException {
        String query = "INSERT INTO LabTest (doctor_id, patient_id, test_type, normality, low, high, unit_of_measurement, results, datetime) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, labTest.getDoctorId());
            stmt.setInt(2, labTest.getPatientId());
            stmt.setString(3, labTest.getTestType().getTypeName());
            stmt.setBoolean(4, labTest.getNormality());
            stmt.setDouble(5, labTest.getLow());
            stmt.setDouble(6, labTest.getHigh());
            stmt.setString(7, labTest.getUnitOfMeasurement());
            stmt.setString(8, labTest.getResult());
            stmt.setTimestamp(9, Timestamp.valueOf(labTest.getDateTime()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding lab test failed, no rows affected.");
            }
        }
    }

    public void updateLabTest(Test labTest) throws SQLException {
        String query = "UPDATE LabTest SET doctor_id = ?, patient_id = ?, test_type = ?, normality = ?, low = ?, high = ?, " +
                       "unit_of_measurement = ?, results = ?, datetime = ? WHERE lab_test_id = ?";
        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, labTest.getDoctorId());
            stmt.setInt(2, labTest.getPatientId());
            stmt.setString(3, labTest.getTestType().getTypeName());
            stmt.setBoolean(4, labTest.getNormality());
            stmt.setDouble(5, labTest.getLow());
            stmt.setDouble(6, labTest.getHigh());
            stmt.setString(7, labTest.getUnitOfMeasurement());
            stmt.setString(8, labTest.getResult());
            stmt.setTimestamp(9, Timestamp.valueOf(labTest.getDateTime()));
            stmt.setInt(10, labTest.getTestId());

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
}


