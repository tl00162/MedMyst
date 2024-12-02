CREATE DEFINER=`cs3230f24i`@`%` PROCEDURE `AddAppointment`(
    IN p_patient_id INT,
    IN p_doctor_id INT,
    IN p_reason TEXT,
    IN p_details TEXT,
    IN p_appointment_type VARCHAR(20),
    IN p_datetime DATETIME,
    IN p_final_diagnosis TEXT,
    OUT p_appointment_id INT
)
BEGIN
    INSERT INTO appointment (patient_id, doctor_id, reason, details, appointment_type, datetime, final_diagnosis)
    VALUES (p_patient_id, p_doctor_id, p_reason, p_details, p_appointment_type, p_datetime, p_final_diagnosis);

    SET p_appointment_id = LAST_INSERT_ID();
END

CREATE DEFINER=`cs3230f24i`@`%` PROCEDURE `FinalizeTest`(
    IN p_test_id INT
)
BEGIN
    UPDATE LabTest
    SET finalized = 1
    WHERE lab_test_id = p_test_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Finalizing test failed. Test may not exist.';
    END IF;
END