Iteration 3 submission
nurse log ins

id: nurse1, password: password
id: nurse2, password: password2

admin log ins

id: administrator1, password: testpassword1
id: administrator2, password: testpassword2

Stored Procedure for adding an appointment:

use 3230f24i;

DELIMITER $$

CREATE PROCEDURE AddAppointment(
    IN p_patient_id INT,
    IN p_doctor_id INT,
    IN p_reason TEXT,
    IN p_details TEXT,
    IN p_appointment_type VARCHAR(20),
    IN p_datetime DATETIME,
    OUT p_appointment_id INT
)
BEGIN
    INSERT INTO appointment (patient_id, doctor_id, reason, details, appointment_type, datetime)
    VALUES (p_patient_id, p_doctor_id, p_reason, p_details, p_appointment_type, p_datetime);

    SET p_appointment_id = LAST_INSERT_ID();
END$$

DELIMITER ;
