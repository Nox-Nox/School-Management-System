CREATE DATABASE  IF NOT EXISTS `school` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `school`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: school
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `adminID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`adminID`),
  UNIQUE KEY `adminID` (`adminID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `courseID` int NOT NULL AUTO_INCREMENT,
  `courseName` varchar(50) DEFAULT NULL,
  `courseCode` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`courseID`),
  UNIQUE KEY `courseID` (`courseID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `module` (
  `moduleID` int NOT NULL AUTO_INCREMENT,
  `moduleCode` varchar(20) DEFAULT NULL,
  `moduleName` varchar(50) DEFAULT NULL,
  `courseID` int DEFAULT NULL,
  PRIMARY KEY (`moduleID`),
  UNIQUE KEY `moduleID` (`moduleID`),
  KEY `courseID` (`courseID`),
  CONSTRAINT `module_ibfk_1` FOREIGN KEY (`courseID`) REFERENCES `course` (`courseID`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prof_course_junction`
--

DROP TABLE IF EXISTS `prof_course_junction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prof_course_junction` (
  `p_c_junction` int NOT NULL AUTO_INCREMENT,
  `professor_ID` int DEFAULT NULL,
  `courseID` int DEFAULT NULL,
  PRIMARY KEY (`p_c_junction`),
  UNIQUE KEY `p_c_junction` (`p_c_junction`),
  KEY `professor_ID` (`professor_ID`),
  KEY `courseID` (`courseID`),
  CONSTRAINT `prof_course_junction_ibfk_1` FOREIGN KEY (`professor_ID`) REFERENCES `professor` (`professor_ID`),
  CONSTRAINT `prof_course_junction_ibfk_2` FOREIGN KEY (`courseID`) REFERENCES `course` (`courseID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `professor`
--

DROP TABLE IF EXISTS `professor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professor` (
  `professor_ID` int NOT NULL AUTO_INCREMENT,
  `professorID` int DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `surname` varchar(30) DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `DoB` date DEFAULT NULL,
  PRIMARY KEY (`professor_ID`),
  UNIQUE KEY `professorID` (`professor_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `result`
--

DROP TABLE IF EXISTS `result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `result` (
  `resultID` int NOT NULL AUTO_INCREMENT,
  `mark` float DEFAULT NULL,
  `tTableID` int DEFAULT NULL,
  `student_ID` int DEFAULT NULL,
  `moduleID` int DEFAULT NULL,
  PRIMARY KEY (`resultID`),
  UNIQUE KEY `resultID` (`resultID`),
  KEY `tTableID` (`tTableID`),
  KEY `student_ID` (`student_ID`),
  KEY `moduleID` (`moduleID`),
  CONSTRAINT `result_ibfk_1` FOREIGN KEY (`tTableID`) REFERENCES `timetable` (`tTableID`),
  CONSTRAINT `result_ibfk_2` FOREIGN KEY (`student_ID`) REFERENCES `student` (`student_ID`),
  CONSTRAINT `result_ibfk_3` FOREIGN KEY (`moduleID`) REFERENCES `module` (`moduleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `student_ID` int NOT NULL AUTO_INCREMENT,
  `studentID` int DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `surname` varchar(30) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `DoB` date DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `courseID` int DEFAULT NULL,
  `tTableID` int DEFAULT NULL,
  PRIMARY KEY (`student_ID`),
  UNIQUE KEY `student_ID` (`student_ID`),
  KEY `courseID` (`courseID`),
  KEY `tTableID` (`tTableID`),
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`courseID`) REFERENCES `course` (`courseID`),
  CONSTRAINT `student_ibfk_3` FOREIGN KEY (`tTableID`) REFERENCES `timetable` (`tTableID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student_module_junction`
--

DROP TABLE IF EXISTS `student_module_junction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_module_junction` (
  `s_m_junction` int NOT NULL AUTO_INCREMENT,
  `student_ID` int DEFAULT NULL,
  `moduleID` int DEFAULT NULL,
  PRIMARY KEY (`s_m_junction`),
  UNIQUE KEY `s_m_junction` (`s_m_junction`),
  KEY `student_ID` (`student_ID`),
  KEY `moduleID` (`moduleID`),
  CONSTRAINT `student_module_junction_ibfk_1` FOREIGN KEY (`student_ID`) REFERENCES `student` (`student_ID`),
  CONSTRAINT `student_module_junction_ibfk_2` FOREIGN KEY (`moduleID`) REFERENCES `module` (`moduleID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timetable`
--

DROP TABLE IF EXISTS `timetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timetable` (
  `tTableID` int NOT NULL AUTO_INCREMENT,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `course` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`tTableID`),
  UNIQUE KEY `tTableID` (`tTableID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-27 23:01:21
