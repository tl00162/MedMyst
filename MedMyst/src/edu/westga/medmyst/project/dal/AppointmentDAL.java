package edu.westga.medmyst.project.dal;

import edu.westga.medmyst.project.model.Appointment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Layer for Appointment data in the database. Handles CRUD
 * operations for Appointment.
 * 
 * @author demmons1
 * @version Fall 2024
 */
public class AppointmentDAL {

	/**
	 * Inserts a new appointment into the database.
	 *
	 * @param appointment The appointment object to be added.
	 * @throws SQLException If an SQL error occurs.
	 * @return the generated appointmentId
	 */
	public int addAppointment(Appointment appointment) throws SQLException {
		String query = "INSERT INTO appointment (patient_id, doctor_id, reason, details, appointment_type, datetime, final_diagnosis) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, appointment.getPatientId());
			stmt.setInt(2, appointment.getDoctorId());
			stmt.setString(3, appointment.getReason());
			stmt.setString(4, appointment.getDetails());
			stmt.setString(5, appointment.getAppointmentType());
			stmt.setTimestamp(6, Timestamp.valueOf(appointment.getDateTime()));
			stmt.setString(7, appointment.getFinalDiagnosis());

			stmt.executeUpdate();

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					appointment.setAppointmentId(generatedKeys.getInt(1));
					return appointment.getAppointmentId();
				} else {
					throw new SQLException("Creating appointment failed, no ID obtained.");
				}
			}
		}
	}

	/**
	 * Updates an existing appointment in the database.
	 *
	 * @param appointment The appointment object containing updated data.
	 * @throws SQLException If an SQL error occurs.
	 */
	public void updateAppointment(Appointment appointment) throws SQLException {
		String query = "UPDATE appointment SET patient_id = ?, doctor_id = ?, reason = ?, details = ?, appointment_type = ?, datetime = ?, final_diagnosis = ? WHERE appointment_id = ?";

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, appointment.getPatientId());
			stmt.setInt(2, appointment.getDoctorId());
			stmt.setString(3, appointment.getReason());
			stmt.setString(4, appointment.getDetails());
			stmt.setString(5, appointment.getAppointmentType());
			stmt.setTimestamp(6, Timestamp.valueOf(appointment.getDateTime()));
			stmt.setString(7, appointment.getFinalDiagnosis());
			stmt.setInt(8, appointment.getAppointmentId());

			stmt.executeUpdate();
		}
	}

	/**
	 * Retrieves all appointments from the database.
	 * 
	 * @return a list of all appointments
	 * @throws SQLException if a database access error occurs
	 */
	public List<Appointment> getAllAppointments() throws SQLException {
		List<Appointment> appointments = new ArrayList<>();
		String query = "SELECT a.appointment_id, a.patient_id, a.doctor_id, a.reason, a.details, a.appointment_type, a.datetime, a.final_diagnosis, "
				+ "d.f_name AS doctor_first_name, d.l_name AS doctor_last_name, d.specialty AS doctor_specialty "
				+ "FROM appointment a " + "JOIN doctor d ON a.doctor_id = d.doctor_id";

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Appointment appointment = new Appointment(rs.getInt("appointment_id"), rs.getInt("patient_id"),
						rs.getInt("doctor_id"), rs.getString("doctor_first_name"), rs.getString("doctor_last_name"),
						rs.getString("doctor_specialty"), rs.getString("reason"), rs.getString("details"),
						rs.getString("appointment_type"), rs.getTimestamp("datetime").toLocalDateTime());
				appointment.setFinalDiagosis(rs.getString("final_diagnosis"));
				appointments.add(appointment);
			}
		}
		return appointments;
	}

	/**
	 * Retrieves appointments based on given criteria.
	 * 
	 * @param patientId The patient ID to filter by (use 0 if not filtering by
	 *                  patient).
	 * @param doctorId  The doctor ID to filter by (use 0 if not filtering by
	 *                  doctor).
	 * @return A list of matching appointments.
	 * @throws SQLException If a database access error occurs.
	 */
	public List<Appointment> getAppointmentsByCriteria(int patientId, int doctorId) throws SQLException {
		List<Appointment> appointments = new ArrayList<>();
		StringBuilder queryBuilder = new StringBuilder(
				"SELECT a.appointment_id, a.patient_id, a.doctor_id, a.reason, a.details, "
						+ "a.appointment_type, a.datetime, a.final_diagnosis, d.f_name AS doctor_first_name, d.l_name AS doctor_last_name, d.specialty AS doctor_specialty "
						+ "FROM appointment a JOIN doctor d ON a.doctor_id = d.doctor_id WHERE 1=1");

		if (patientId > 0) {
			queryBuilder.append(" AND a.patient_id = ").append(patientId);
		}
		if (doctorId > 0) {
			queryBuilder.append(" AND a.doctor_id = ").append(doctorId);
		}

		String query = queryBuilder.toString();

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Appointment appointment = new Appointment(rs.getInt("appointment_id"), rs.getInt("patient_id"),
						rs.getInt("doctor_id"), rs.getString("reason"), rs.getString("details"),
						rs.getString("appointment_type"), rs.getTimestamp("datetime").toLocalDateTime());
				appointment.setFinalDiagosis(rs.getString("final_diagnosis"));
				appointments.add(appointment);
			}
		}
		return appointments;
	}

	/**
	 * Retrieves all appointments for a specific doctor by their ID.
	 * 
	 * @param doctorId the ID of the doctor whose appointments to retrieve
	 * @return a list of Appointments for the specified doctor
	 * @throws SQLException if a database access error occurs
	 */
	public List<Appointment> getAppointmentsByDoctorId(int doctorId) throws SQLException {
		List<Appointment> appointments = new ArrayList<>();
		String query = "SELECT * FROM appointment WHERE doctor_id = ?";

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, doctorId);
			try (ResultSet resultSet = stmt.executeQuery()) {
				while (resultSet.next()) {
					Appointment appointment = new Appointment(resultSet.getInt("appointment_id"),
							resultSet.getInt("patient_id"), resultSet.getInt("doctor_id"),
							resultSet.getString("reason"), resultSet.getString("details"),
							resultSet.getString("appointment_type"),
							resultSet.getTimestamp("datetime").toLocalDateTime());
					appointment.setFinalDiagosis(resultSet.getString("final_diagnosis"));
					appointments.add(appointment);
				}
			}
		}
		return appointments;
	}

	/**
	 * Deletes an appointment by its ID.
	 *
	 * @param appointmentId The ID of the appointment to delete.
	 * @throws SQLException If an SQL error occurs.
	 */
	public void deleteAppointment(int appointmentId) throws SQLException {
		String query = "DELETE FROM appointment WHERE appointment_id = ?";

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, appointmentId);
			stmt.executeUpdate();
		}
	}

	/**
	 * Retrieves an appointment by its ID from the database. The method fetches
	 * appointment details including associated patient and doctor information.
	 * 
	 * @param appointmentId The ID of the appointment to retrieve.
	 * @return An {@link Appointment} object containing the appointment details if
	 *         found, or {@code null} if no appointment is found.
	 * @throws SQLException If a database access error occurs or the SQL query is
	 *                      invalid.
	 */
	public Appointment getAppointmentById(int appointmentId) throws SQLException {
		String query = "SELECT a.appointment_id, a.patient_id, a.doctor_id,"
				+ "d.f_name AS doctor_first_name, d.l_name AS doctor_last_name," + "d.specialty AS doctor_specialty,"
				+ "a.reason, a.details, a.appointment_type, a.datetime" + " FROM Appointment a " + ""
				+ "JOIN Doctor d ON a.doctor_id = d.doctor_id " + " WHERE a.appointment_id = ?";

		try (Connection connection = DriverManager.getConnection(ConnectionString.CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, appointmentId);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				int id = resultSet.getInt("appointment_id");
				int patientId = resultSet.getInt("patient_id");
				int doctorId = resultSet.getInt("doctor_id");
				String doctorFirstName = resultSet.getString("doctor_first_name");
				String doctorLastName = resultSet.getString("doctor_last_name");
				String doctorSpecialty = resultSet.getString("doctor_specialty");
				String reason = resultSet.getString("reason");
				String details = resultSet.getString("details");
				String appointmentType = resultSet.getString("appointment_type");
				LocalDateTime dateTime = resultSet.getTimestamp("datetime").toLocalDateTime();

				return new Appointment(id, patientId, doctorId, doctorFirstName, doctorLastName, doctorSpecialty,
						reason, details, appointmentType, dateTime);
			} else {
				return null;
			}
		}
	}

}
