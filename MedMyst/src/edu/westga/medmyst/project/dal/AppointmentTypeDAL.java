package edu.westga.medmyst.project.dal;

import edu.westga.medmyst.project.model.AppointmentType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Layer for AppointmentType.
 * Provides methods to retrieve appointment types from the database.
 * @author demmons1
 * @version Fall 2024
 */
public class AppointmentTypeDAL {

	/**
	 * gets the list of all appointmentTypes
	 * @return the list of appointmentTypes
	 * @throws SQLException
	 */
    public List<AppointmentType> getAllAppointmentTypes() throws SQLException {
        List<AppointmentType> appointmentTypes = new ArrayList<>();
        String query = "SELECT * FROM AppointmentType";

        try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String typeName = rs.getString("appointment_type");
                String description = rs.getString("description");
                AppointmentType appointmentType = new AppointmentType(typeName, description);
                appointmentTypes.add(appointmentType);
            }
        }
        return appointmentTypes;
    }
}
