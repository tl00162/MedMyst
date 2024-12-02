-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: cs-dblab01.uwg.westga.edu    Database: cs3230f24i
-- ------------------------------------------------------
-- Server version	8.4.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrator` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `f_name` varchar(50) DEFAULT NULL,
  `l_name` varchar(50) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `phone_number` char(10) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `address_2` varchar(100) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `zip` char(5) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1,'Bob','Johnson','1985-06-12','F','1234567890','123 Main St','Apt 2','California','90210'),(2,'Jane','Smith','1990-02-25','M','0987654321','456 Elm St',NULL,'New York','10001');
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administratoraccount`
--

DROP TABLE IF EXISTS `administratoraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administratoraccount` (
  `user_id` int NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `user_logs` text,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_admin_user` FOREIGN KEY (`user_id`) REFERENCES `administrator` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administratoraccount`
--

LOCK TABLES `administratoraccount` WRITE;
/*!40000 ALTER TABLE `administratoraccount` DISABLE KEYS */;
INSERT INTO `administratoraccount` VALUES (1,'testpassword1','Login logs for Alice'),(2,'testpassword2','Login logs for Bob');
/*!40000 ALTER TABLE `administratoraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `appointment_id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int DEFAULT NULL,
  `doctor_id` int DEFAULT NULL,
  `reason` text,
  `details` text,
  `appointment_type` varchar(20) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `final_diagnosis` text,
  PRIMARY KEY (`appointment_id`),
  UNIQUE KEY `uq_patient_datetime` (`patient_id`,`datetime`),
  KEY `fk_doctor_id` (`doctor_id`),
  KEY `fk_appointment_type` (`appointment_type`),
  CONSTRAINT `fk_appointment_type` FOREIGN KEY (`appointment_type`) REFERENCES `appointmenttype` (`appointment_type`),
  CONSTRAINT `fk_doctor_id` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `fk_patient_id` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,7,2,'na','na','Follow-up','2024-11-06 10:20:00','Ok'),(2,9,1,'follow up on surgery','na','Follow-up','2024-11-07 10:40:00','Ok'),(3,11,3,'na','na','Consultation','2024-11-01 09:40:00','OK'),(4,11,1,'na','na','Emergency','2024-11-21 13:00:00','OK'),(6,13,3,'follow up','na','Follow-up','2024-11-07 10:20:00','OK'),(7,10,1,'checkup	','na','Consultation','2024-11-21 15:00:00','OK'),(8,10,2,'basic visit','na','Consultation','2024-11-22 11:40:00','OK'),(9,13,4,'just following up','he ok','Follow-up','2024-11-23 09:00:00','OK'),(10,11,2,'just following up','na','Follow-up','2024-12-03 11:00:00',NULL),(11,13,2,'follow up on issues','na','Consultation','2024-12-05 09:00:00','He had the flu.'),(12,13,3,'a reason	','the deets','Follow-up','2024-12-18 11:40:00',NULL);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointmentlabtest`
--

DROP TABLE IF EXISTS `appointmentlabtest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointmentlabtest` (
  `appointment_id` int NOT NULL,
  `lab_test_id` int NOT NULL,
  `normality` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`appointment_id`,`lab_test_id`),
  KEY `fk_appointmentlabtest_lab_test_id` (`lab_test_id`),
  CONSTRAINT `fk_appointmentlabtest_lab_test_id` FOREIGN KEY (`lab_test_id`) REFERENCES `labtest` (`lab_test_id`),
  CONSTRAINT `fk_appointmentlabtest_patient_id` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`appointment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointmentlabtest`
--

LOCK TABLES `appointmentlabtest` WRITE;
/*!40000 ALTER TABLE `appointmentlabtest` DISABLE KEYS */;
INSERT INTO `appointmentlabtest` VALUES (3,25,0),(3,29,0),(4,13,1),(6,22,0),(6,23,0),(6,24,0),(8,16,1),(8,17,1),(9,10,1),(9,11,0),(9,14,1),(10,30,1),(10,31,0);
/*!40000 ALTER TABLE `appointmentlabtest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointmenttype`
--

DROP TABLE IF EXISTS `appointmenttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointmenttype` (
  `appointment_type` varchar(20) NOT NULL,
  `description` text,
  PRIMARY KEY (`appointment_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointmenttype`
--

LOCK TABLES `appointmenttype` WRITE;
/*!40000 ALTER TABLE `appointmenttype` DISABLE KEYS */;
INSERT INTO `appointmenttype` VALUES ('Consultation','General consultation and checkup'),('Emergency','Emergency appointment for urgent care'),('Follow-up','Follow-up after a previous appointment or treatment'),('Other','Any appointment type not covered by other categories'),('Routine Check-up','Scheduled routine check-up or preventive care');
/*!40000 ALTER TABLE `appointmenttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkup`
--

DROP TABLE IF EXISTS `checkup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkup` (
  `appointment_id` int NOT NULL,
  `nurse_id` int DEFAULT NULL,
  `body_temperature` decimal(5,2) DEFAULT NULL,
  `diastolic_blood_pressure` int DEFAULT NULL,
  `systolic_blood_pressure` int DEFAULT NULL,
  `pulse` int DEFAULT NULL,
  `symptoms` text,
  `height` decimal(5,2) DEFAULT NULL,
  `weight` decimal(5,2) DEFAULT NULL,
  `initial_diagnosis` text,
  PRIMARY KEY (`appointment_id`),
  KEY `fk_checkup_nurse_id` (`nurse_id`),
  CONSTRAINT `fk_checkup_appointment_id` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`appointment_id`),
  CONSTRAINT `fk_checkup_nurse_id` FOREIGN KEY (`nurse_id`) REFERENCES `nurse` (`nurse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkup`
--

LOCK TABLES `checkup` WRITE;
/*!40000 ALTER TABLE `checkup` DISABLE KEYS */;
INSERT INTO `checkup` VALUES (1,1,98.60,80,120,70,'No symptoms',5.80,150.00,'Routine checkup for follow-up'),(2,1,98.90,85,125,75,'Mild discomfort',5.90,152.00,'Post-surgery follow-up'),(3,1,98.70,78,118,68,'General fatigue',5.70,155.00,'Consultation checkup'),(4,1,99.10,90,130,80,'Severe pain',60.20,160.00,'Emergency response'),(6,1,98.50,82,122,72,'Mild symptoms',5.90,145.00,'Routine follow-up check'),(7,1,98.60,80,125,72,'No significant symptoms',5.80,160.50,'Routine follow-up with no abnormal findings'),(8,1,89.00,56,128,76,'her nose like runny and stuff',53.20,121.30,'I dunno maybe its cancer lol'),(9,1,89.00,56,128,76,'nothing he chillin',65.40,175.40,'blood prolly fine'),(10,1,0.00,0,0,0,NULL,0.00,0.00,NULL),(11,1,0.00,0,0,0,'',0.00,0.00,''),(12,1,0.00,0,0,0,NULL,0.00,0.00,NULL);
/*!40000 ALTER TABLE `checkup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `doctor_id` int NOT NULL AUTO_INCREMENT,
  `f_name` varchar(50) DEFAULT NULL,
  `l_name` varchar(50) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `specialty` varchar(50) DEFAULT NULL,
  `phone_number` char(10) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `address_2` varchar(100) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `zip` char(5) DEFAULT NULL,
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'John','Davis','1975-03-14','M','Cardiology','5551234567','123 Main St','','GA','30301'),(2,'Sarah','Smith','1980-07-22','F','Pediatrics','5552345678','456 Oak St','Apt 3','GA','30302'),(3,'James','Brown','1965-11-05','M','Neurology','5553456789','789 Pine St','','GA','30303'),(4,'Emily','Davis','1990-02-17','F','Dermatology','5554567890','101 Maple Ave','Suite B','GA','30304'),(5,'Michael','Johnson','1978-09-30','M','Orthopedics','5555678901','202 Cedar Ln','','GA','30305');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `labtest`
--

DROP TABLE IF EXISTS `labtest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `labtest` (
  `lab_test_id` int NOT NULL AUTO_INCREMENT,
  `doctor_id` int DEFAULT NULL,
  `patient_id` int DEFAULT NULL,
  `test_type` varchar(40) DEFAULT NULL,
  `low` decimal(9,2) DEFAULT NULL,
  `high` decimal(9,2) DEFAULT NULL,
  `unit_of_measurement` varchar(20) DEFAULT NULL,
  `results` text,
  `datetime` datetime DEFAULT NULL,
  `finalized` tinyint(1) DEFAULT '0',
  `normality` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`lab_test_id`),
  UNIQUE KEY `uq_labtest_doctor_patient_datetime_test_type` (`doctor_id`,`patient_id`,`datetime`,`test_type`),
  KEY `fk_labtest_patient_id` (`patient_id`),
  KEY `fk_labtest_test_type` (`test_type`),
  CONSTRAINT `fk_labtest_doctor_id` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `fk_labtest_patient_id` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `fk_labtest_test_type` FOREIGN KEY (`test_type`) REFERENCES `labtesttype` (`lab_test_type`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `labtest`
--

LOCK TABLES `labtest` WRITE;
/*!40000 ALTER TABLE `labtest` DISABLE KEYS */;
INSERT INTO `labtest` VALUES (10,4,13,'Calcium',23.00,1.00,'ml','Finalized test','2024-11-27 10:40:00',1,1),(11,4,13,'Calcium',2.00,1.00,'ml','abnormal','2024-11-19 10:40:00',1,0),(13,4,11,'Creatinine',2.00,1.00,'ml','good','2024-11-28 09:00:00',1,1),(14,1,13,'HbA1c',2.00,1.00,'ml','','2024-11-20 09:00:00',0,1),(16,3,10,'Electrolyte Panel',6.00,5.00,'cm','Ab normal 4.0','2024-11-29 09:40:00',0,1),(17,4,10,'Urinalysis',5.00,3.00,'oz','Normal 4.0','2024-11-20 09:00:00',1,1),(18,4,13,'C-Reactive Protein (CRP)',2.00,1.00,'ml','g','2024-12-19 10:20:00',0,NULL),(20,4,13,'C-Reactive Protein (CRP)',2.00,1.00,'ml','g','2024-12-19 10:40:00',0,NULL),(21,4,13,'Calcium',2.00,12.00,'ml','gas','2024-12-18 11:00:00',0,NULL),(22,3,13,'Coagulation Panel',2.00,1.00,'2','3','2024-12-25 10:00:00',1,0),(23,3,13,'Calcium',8.50,10.50,'mg/dL','g','2024-12-25 10:20:00',1,0),(24,4,13,'Electrolyte Panel',0.00,0.00,NULL,'good','2024-12-26 10:40:00',1,0),(25,4,11,'Coagulation Panel',0.00,0.00,NULL,'g','2024-12-25 10:00:00',1,0),(26,4,11,'Coagulation Panel',0.00,0.00,NULL,'','2024-12-17 09:40:00',0,0),(29,4,11,'Calcium',8.50,10.50,'mg/dL',NULL,'2024-12-17 10:00:00',0,0),(30,4,11,'Coagulation Panel',0.00,0.00,NULL,'g','2024-12-25 10:40:00',1,1),(31,4,11,'Vitamin D',20.00,50.00,'ng/mL','65	','2024-12-26 10:40:00',1,0);
/*!40000 ALTER TABLE `labtest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `labtesttype`
--

DROP TABLE IF EXISTS `labtesttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `labtesttype` (
  `lab_test_type` varchar(50) NOT NULL,
  `description` text,
  `low` decimal(10,2) DEFAULT NULL,
  `high` decimal(10,2) DEFAULT NULL,
  `units` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`lab_test_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `labtesttype`
--

LOCK TABLES `labtesttype` WRITE;
/*!40000 ALTER TABLE `labtesttype` DISABLE KEYS */;
INSERT INTO `labtesttype` VALUES ('Basic Metabolic Panel (BMP)','Tests for glucose, calcium, and electrolytes such as sodium, potassium, bicarbonate, and chloride.',8.00,20.00,'mg/dL'),('Blood Urea Nitrogen (BUN)','Evaluates kidney function by measuring the amount of urea nitrogen in the blood.',7.00,25.00,'mg/dL'),('C-Reactive Protein (CRP)','Measures CRP levels to detect inflammation in the body.',0.00,10.00,'mg/L'),('Calcium','Measures calcium levels in the blood, important for bones, muscles, and nerves.',8.50,10.50,'mg/dL'),('Coagulation Panel','Includes tests like PT and INR to evaluate blood clotting.',NULL,NULL,NULL),('Complete Blood Count (CBC)','Measures red and white blood cells, hemoglobin, hematocrit, and platelets.',NULL,NULL,NULL),('Creatinine','Tests for creatinine levels to assess kidney function.',0.60,1.20,'mg/dL'),('Electrolyte Panel','Measures levels of essential electrolytes including sodium, potassium, chloride, and bicarbonate.',NULL,NULL,NULL),('HbA1c','Provides an average blood glucose level over the past 2-3 months.',4.00,5.60,'%'),('Iron Studies','Measures iron, ferritin, transferrin, and total iron binding capacity (TIBC) to assess iron levels.',50.00,170.00,'mcg/dL'),('Lipid Panel','Measures cholesterol, triglycerides, HDL, and LDL levels.',NULL,NULL,NULL),('Liver Function Tests (LFT)','Tests for enzymes such as ALT, AST, ALP, and bilirubin to assess liver health.',NULL,NULL,NULL),('Prostate-Specific Antigen (PSA)','Measures PSA levels to help screen for prostate issues.',0.00,4.00,'ng/mL'),('Thyroid Stimulating Hormone (TSH)','Measures the level of TSH to evaluate thyroid function.',0.40,4.00,'mIU/L'),('Urinalysis','Tests for components such as pH, protein, glucose, and white blood cells in urine.',NULL,NULL,NULL),('Vitamin D','Tests for levels of vitamin D in the blood, important for bone health.',20.00,50.00,'ng/mL');
/*!40000 ALTER TABLE `labtesttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_test`
--

DROP TABLE IF EXISTS `login_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_test` (
  `username` varchar(25) NOT NULL,
  `password` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_test`
--

LOCK TABLES `login_test` WRITE;
/*!40000 ALTER TABLE `login_test` DISABLE KEYS */;
INSERT INTO `login_test` VALUES ('john','pass'),('susan','pass2');
/*!40000 ALTER TABLE `login_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nurse`
--

DROP TABLE IF EXISTS `nurse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nurse` (
  `nurse_id` int NOT NULL AUTO_INCREMENT,
  `f_name` varchar(50) DEFAULT NULL,
  `l_name` varchar(50) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `phone_number` char(10) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `address_2` varchar(100) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `zip` char(5) DEFAULT NULL,
  PRIMARY KEY (`nurse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nurse`
--

LOCK TABLES `nurse` WRITE;
/*!40000 ALTER TABLE `nurse` DISABLE KEYS */;
INSERT INTO `nurse` VALUES (1,'Alice','Johnson','1985-06-12','F','1234567890','123 Main St','Apt 2','California','90210'),(2,'Bob','Smith','1990-02-25','M','0987654321','456 Elm St',NULL,'New York','10001'),(3,'Alice','Johnson','1985-06-12','F','1234567890','123 Main St','Apt 2','California','90210'),(4,'Bob','Smith','1990-02-25','M','0987654321','456 Elm St',NULL,'New York','10001');
/*!40000 ALTER TABLE `nurse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nurseaccount`
--

DROP TABLE IF EXISTS `nurseaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nurseaccount` (
  `user_id` int NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `user_logs` text,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_nurse_user` FOREIGN KEY (`user_id`) REFERENCES `nurse` (`nurse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nurseaccount`
--

LOCK TABLES `nurseaccount` WRITE;
/*!40000 ALTER TABLE `nurseaccount` DISABLE KEYS */;
INSERT INTO `nurseaccount` VALUES (1,'password','Login logs for Alice'),(2,'password2','Login logs for Bob');
/*!40000 ALTER TABLE `nurseaccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `f_name` varchar(50) DEFAULT NULL,
  `l_name` varchar(50) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `phone_number` char(10) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `address_2` varchar(100) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `zip` char(5) DEFAULT NULL,
  `active_status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (6,'James','Dean','1997-05-02','M','5555555555','555 Abc St',NULL,'FL','12345',0),(7,'Bob','Sagget','1990-06-30','M','4444444444','444 Abc St',NULL,'GA','12345',0),(8,'Tim','Green','1993-01-02','M','3333333333','111 ABC st',NULL,'AZ','11111',NULL),(9,'Frank','Franklinson','2001-01-01','M','2222222222','222 Abc St',NULL,'LA','44444',NULL),(10,'busan','Franklinson','2001-01-07','F','2314444444','222 Abc St',NULL,'LA','44444',1),(11,'Mary','Lo','2021-10-28','F','1234567888','2 gasga','','FL','22222',1),(12,'Active','Patient','2024-10-29','F','1234567891','213','','AK','21345',0),(13,'Tommy','Lamont','1999-07-03','M','1234567890','123 st','','CT','12345',1);
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `useraccount` (
  `accounttype` varchar(20) NOT NULL,
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_logs` text,
  `password` varchar(255) NOT NULL,
  `f_name` varchar(50) DEFAULT NULL,
  `l_name` varchar(50) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`accounttype`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES ('admin',1,'Login logs for Alice','testpassword1','Bob','Johnson','admin1'),('admin',2,'Login logs for Bob','testpassword2','Jane','Smith','admin2'),('Admin',6,NULL,'$2a$10$XpQBFVaLlrXouWYwNurFN.R07LuiEb.HEVPA6I0AYKVpXYIYiehWW','Dillon','Emmons','demmons2'),('nurse',1,'Login logs for Alice','password','Alice','Johnson','nurse1'),('nurse',2,'Login logs for Bob','password2','Bob','Smith','nurse2'),('Nurse',4,NULL,'$2a$10$IoYpD0RNgNeO0oa9kv2yUe5ZgwxEGukrFt3G8aicOLJpKYG.GYGrW','Dillon','Emmons','demmons1');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-01 22:57:33
