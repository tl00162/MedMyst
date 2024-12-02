Final submission

To build and run this program, ensure you have the following installed:

Java Development Kit (JDK): Version 17 or later.

MySQL Database Server: Version 8.0 or later.

nurse log ins

username: nurse1, password: password


admin log ins

username: administrator1, password: testpassword1


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
