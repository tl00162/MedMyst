USE cs3230f24i;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `CheckUp`;
DROP TABLE IF EXISTS `LabTest`;
DROP TABLE IF EXISTS `AppointmentLabTest`;
DROP TABLE IF EXISTS `LabTestType`;
DROP TABLE IF EXISTS `Appointment`;
DROP TABLE IF EXISTS `AppointmentType`;
DROP TABLE IF EXISTS `Doctor`;
DROP TABLE IF EXISTS `Patient`;
DROP TABLE IF EXISTS `AdministratorAccount`;
DROP TABLE IF EXISTS `NurseAccount`;
DROP TABLE IF EXISTS `Administrator`;
DROP TABLE IF EXISTS `Nurse`;

CREATE TABLE Nurse (
    nurse_id INT AUTO_INCREMENT PRIMARY KEY,
    f_name VARCHAR(50),
    l_name VARCHAR(50),
    date_of_birth DATE,
	gender CHAR(1),
    phone_number CHAR(10),
    address VARCHAR(100),
    address_2 VARCHAR(100),
    state VARCHAR(20),
    zip CHAR(5)
);

CREATE TABLE Administrator (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    f_name VARCHAR(50),
    l_name VARCHAR(50),
    date_of_birth DATE,
	gender CHAR(1),
    phone_number CHAR(10),
    address VARCHAR(100),
    address_2 VARCHAR(100),
    state VARCHAR(20),
    zip CHAR(5)
);

CREATE TABLE NurseAccount (
    user_id INT PRIMARY KEY,
    password VARCHAR(20),
    user_logs TEXT,
    CONSTRAINT fk_nurse_user FOREIGN KEY (user_id) REFERENCES Nurse(nurse_id)
);

CREATE TABLE AdministratorAccount (
    user_id INT PRIMARY KEY,
    password VARCHAR(20),
    user_logs TEXT,
    CONSTRAINT fk_admin_user FOREIGN KEY (user_id) REFERENCES Administrator(admin_id)
);

CREATE TABLE Patient (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    f_name VARCHAR(50),
    l_name VARCHAR(50),
    date_of_birth DATE,
	gender CHAR(1),
    phone_number CHAR(10),
	address VARCHAR(100),
    address_2 VARCHAR(100),
    state VARCHAR(20),
    zip CHAR(5),
    active_status BOOLEAN
);

CREATE TABLE Doctor (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    f_name VARCHAR(50),
    l_name VARCHAR(50),
    date_of_birth DATE,
	gender CHAR(1),
	specialty VARCHAR(50),
    phone_number CHAR(10),
	address VARCHAR(100),
    address_2 VARCHAR(100),
    state VARCHAR(20),
    zip CHAR(5)
);

CREATE TABLE AppointmentType (
    appointment_type VARCHAR(20) PRIMARY KEY,
    description TEXT
);

CREATE TABLE Appointment (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    reason TEXT,
    details TEXT,
    appointment_type VARCHAR(20),
    datetime DATETIME,
    CONSTRAINT fk_patient_id FOREIGN KEY (patient_id) REFERENCES Patient(patient_id),
    CONSTRAINT fk_doctor_id FOREIGN KEY (doctor_id) REFERENCES Doctor(doctor_id),
    CONSTRAINT fk_appointment_type FOREIGN KEY (appointment_type) REFERENCES AppointmentType(appointment_type),
	CONSTRAINT uq_patient_datetime UNIQUE (patient_id, datetime)
);

CREATE TABLE LabTest (
    lab_test_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT,
    patient_id INT,
    test_type VARCHAR(20),
    normality VARCHAR(20),
	low DECIMAL(9,2),
	high DECIMAL(9,2),
	unit_of_measurement VARCHAR(20),
    results TEXT,
    datetime DATETIME,
    CONSTRAINT fk_labtest_doctor_id FOREIGN KEY (doctor_id) REFERENCES Doctor(doctor_id),
    CONSTRAINT fk_labtest_patient_id FOREIGN KEY (patient_id) REFERENCES Patient(patient_id),
	CONSTRAINT uq_labtest_doctor_patient_datetime_test_type UNIQUE (doctor_id, patient_id, datetime, test_type)
);

CREATE TABLE AppointmentLabTest (
    appointment_id INT,
    lab_test_id INT,
    PRIMARY KEY (appointment_id, lab_test_id),
    CONSTRAINT fk_appointmentlabtest_patient_id FOREIGN KEY (appointment_id) REFERENCES Appointment(appointment_id),
    CONSTRAINT fk_appointmentlabtest_lab_test_id FOREIGN KEY (lab_test_id) REFERENCES LabTest(lab_test_id)
);

CREATE TABLE LabTestType (
    lab_test_type VARCHAR(50) PRIMARY KEY,
    description TEXT
);

ALTER TABLE LabTest
ADD CONSTRAINT fk_labtest_test_type FOREIGN KEY (test_type) REFERENCES LabTestType(lab_test_type);

CREATE TABLE CheckUp (
    appointment_id INT PRIMARY KEY,
    nurse_id INT,
    body_temperature DECIMAL(5,2),
    diastolic_blood_pressure INT,
    systolic_blood_pressure INT,
    pulse INT,
    symptoms TEXT,
    CONSTRAINT fk_checkup_appointment_id FOREIGN KEY (appointment_id) REFERENCES Appointment(appointment_id),
    CONSTRAINT fk_checkup_nurse_id FOREIGN KEY (nurse_id) REFERENCES Nurse(nurse_id)
);

SET FOREIGN_KEY_CHECKS=1;


