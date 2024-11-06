-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: thejavengers
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `excursiones`
--

DROP TABLE IF EXISTS `excursiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `excursiones` (
  `codigo_excursion` varchar(10) NOT NULL,
  `descripcion` text NOT NULL,
  `fecha` date NOT NULL,
  `numero_dias` int NOT NULL,
  `precio_inscripcion` decimal(10,2) NOT NULL,
  PRIMARY KEY (`codigo_excursion`),
  UNIQUE KEY `descripcion` (`descripcion`(255),`fecha`),
  CONSTRAINT `CHK_precio_pos` CHECK ((`precio_inscripcion` >= 0)),
  CONSTRAINT `excursiones_chk_1` CHECK ((`numero_dias` > 0)),
  CONSTRAINT `excursiones_chk_2` CHECK ((`precio_inscripcion` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `excursiones`
--

LOCK TABLES `excursiones` WRITE;
/*!40000 ALTER TABLE `excursiones` DISABLE KEYS */;
/*!40000 ALTER TABLE `excursiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `federaciones`
--

DROP TABLE IF EXISTS `federaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `federaciones` (
  `codigo_federacion` varchar(10) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`codigo_federacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `federaciones`
--

LOCK TABLES `federaciones` WRITE;
/*!40000 ALTER TABLE `federaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `federaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inscripciones`
--

DROP TABLE IF EXISTS `inscripciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inscripciones` (
  `numero_inscripcion` int NOT NULL AUTO_INCREMENT,
  `numero_socio` int NOT NULL,
  `codigo_excursion` varchar(10) NOT NULL,
  `fecha_inscripcion` date NOT NULL,
  PRIMARY KEY (`numero_inscripcion`),
  KEY `numero_socio` (`numero_socio`),
  KEY `codigo_excursion` (`codigo_excursion`),
  CONSTRAINT `inscripciones_ibfk_1` FOREIGN KEY (`numero_socio`) REFERENCES `socios` (`numero_socio`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `inscripciones_ibfk_2` FOREIGN KEY (`codigo_excursion`) REFERENCES `excursiones` (`codigo_excursion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inscripciones`
--

LOCK TABLES `inscripciones` WRITE;
/*!40000 ALTER TABLE `inscripciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `inscripciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seguros`
--

DROP TABLE IF EXISTS `seguros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seguros` (
  `tipo_seguro` enum('basico','completo') NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  PRIMARY KEY (`tipo_seguro`),
  CONSTRAINT `seguros_chk_1` CHECK ((`precio` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seguros`
--

LOCK TABLES `seguros` WRITE;
/*!40000 ALTER TABLE `seguros` DISABLE KEYS */;
/*!40000 ALTER TABLE `seguros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socios`
--

DROP TABLE IF EXISTS `socios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `socios` (
  `numero_socio` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `nif` varchar(15) DEFAULT NULL,
  `tipo_socio` enum('estandar','federado','infantil') NOT NULL,
  `seguro` varchar(255) DEFAULT NULL,
  `tutor` int DEFAULT NULL,
  `federacion` varchar(10) DEFAULT NULL,
  `cuota_mensual` decimal(10,2) NOT NULL DEFAULT '10.00',
  `apellidos` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`numero_socio`),
  KEY `socios_ibfk_2` (`federacion`),
  KEY `socios_ibfk_1` (`tutor`),
  CONSTRAINT `socios_ibfk_1` FOREIGN KEY (`tutor`) REFERENCES `socios` (`numero_socio`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `socios_ibfk_2` FOREIGN KEY (`federacion`) REFERENCES `federaciones` (`codigo_federacion`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socios`
--

LOCK TABLES `socios` WRITE;
/*!40000 ALTER TABLE `socios` DISABLE KEYS */;
INSERT INTO `socios` VALUES (2,'DIEGO','asfasfsf','estandar','COMPLETO',NULL,NULL,10.00,'DIEGO'),(4,'Marina','102','estandar','COMPLETO',NULL,NULL,10.00,'MArina'),(5,'ramon','1230','estandar','BASICO',NULL,NULL,10.00,'ramirez');
/*!40000 ALTER TABLE `socios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-03 19:23:05
