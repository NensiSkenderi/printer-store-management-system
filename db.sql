CREATE DATABASE  IF NOT EXISTS `toner` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `toner`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: toner
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `arketuar`
--

DROP TABLE IF EXISTS `arketuar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `arketuar` (
  `id` int NOT NULL AUTO_INCREMENT,
  `menyra` varchar(16) NOT NULL,
  `created_date` date DEFAULT (curdate()),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arketuar`
--

LOCK TABLES `arketuar` WRITE;
/*!40000 ALTER TABLE `arketuar` DISABLE KEYS */;
INSERT INTO `arketuar` VALUES (1,'Kesh','2020-09-27');
/*!40000 ALTER TABLE `arketuar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bojra`
--

DROP TABLE IF EXISTS `bojra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bojra` (
  `id` int NOT NULL AUTO_INCREMENT,
  `emri` text NOT NULL,
  `created_date` date DEFAULT (curdate()),
  `lloji_bojes_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_bojra__idx` (`lloji_bojes_id`),
  CONSTRAINT `bojra_ibfk_1` FOREIGN KEY (`lloji_bojes_id`) REFERENCES `lloji_bojra` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bojra`
--

LOCK TABLES `bojra` WRITE;
/*!40000 ALTER TABLE `bojra` DISABLE KEYS */;
INSERT INTO `bojra` VALUES (1,'HP-302 BK/COL','2020-09-27',2),(2,'HP-651 BK/COL','2020-09-27',2),(3,'MLT-111','2020-09-27',1),(4,'ergi','2020-09-27',2);
/*!40000 ALTER TABLE `bojra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `furnizim`
--

DROP TABLE IF EXISTS `furnizim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `furnizim` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bojra_id` int NOT NULL,
  `sasia` double NOT NULL,
  `cmimi` double NOT NULL,
  `created_date` date DEFAULT (curdate()),
  PRIMARY KEY (`id`),
  KEY `bojra_id` (`bojra_id`),
  CONSTRAINT `furnizim_ibfk_1` FOREIGN KEY (`bojra_id`) REFERENCES `bojra` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `furnizim`
--

LOCK TABLES `furnizim` WRITE;
/*!40000 ALTER TABLE `furnizim` DISABLE KEYS */;
/*!40000 ALTER TABLE `furnizim` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventari`
--

DROP TABLE IF EXISTS `inventari`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventari` (
  `bojra_id` int NOT NULL,
  `gjendja` double NOT NULL,
  KEY `bojra_id` (`bojra_id`),
  CONSTRAINT `inventari_ibfk_1` FOREIGN KEY (`bojra_id`) REFERENCES `bojra` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventari`
--

LOCK TABLES `inventari` WRITE;
/*!40000 ALTER TABLE `inventari` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventari` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `klient`
--

DROP TABLE IF EXISTS `klient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `klient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `klienti` text NOT NULL,
  `nipt` varchar(16) DEFAULT NULL,
  `created_date` date DEFAULT (curdate()),
  `kontakt` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `klient`
--

LOCK TABLES `klient` WRITE;
/*!40000 ALTER TABLE `klient` DISABLE KEYS */;
INSERT INTO `klient` VALUES (1,'Nensi','A12345678A','2020-09-27',NULL),(2,'Ergi','A12345678B','2020-09-27','lknk'),(3,'test','tert','2020-09-27','069');
/*!40000 ALTER TABLE `klient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lloji_bojra`
--

DROP TABLE IF EXISTS `lloji_bojra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lloji_bojra` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lloji_bojes` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lloji_bojra`
--

LOCK TABLES `lloji_bojra` WRITE;
/*!40000 ALTER TABLE `lloji_bojra` DISABLE KEYS */;
INSERT INTO `lloji_bojra` VALUES (1,'Cartridge laser'),(2,'Mbushje inkjet');
/*!40000 ALTER TABLE `lloji_bojra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shitje`
--

DROP TABLE IF EXISTS `shitje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shitje` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lloji_fatures` varchar(32) NOT NULL,
  `klient_id` int NOT NULL,
  `created_date` date DEFAULT (curdate()),
  `arketim_id` int DEFAULT NULL,
  `date_likujduar` date DEFAULT NULL,
  `bojra_id` int NOT NULL,
  `sasia` double DEFAULT NULL,
  `cmimi` double DEFAULT NULL,
  `deleted` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `klient_id` (`klient_id`),
  KEY `arketim_id` (`arketim_id`),
  CONSTRAINT `shitje_ibfk_1` FOREIGN KEY (`klient_id`) REFERENCES `klient` (`id`),
  CONSTRAINT `shitje_ibfk_2` FOREIGN KEY (`arketim_id`) REFERENCES `arketuar` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shitje`
--

LOCK TABLES `shitje` WRITE;
/*!40000 ALTER TABLE `shitje` DISABLE KEYS */;
/*!40000 ALTER TABLE `shitje` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-30 19:18:55
