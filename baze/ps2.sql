-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem2
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `IDKat` int NOT NULL AUTO_INCREMENT,
  `imeKat` varchar(45) NOT NULL,
  PRIMARY KEY (`IDKat`),
  UNIQUE KEY `imeKat_UNIQUE` (`imeKat`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (6,'atletika'),(8,'formula 1'),(1,'fudbal'),(10,'golf'),(2,'kosarka'),(4,'odbojka'),(11,'plivanje'),(5,'rukomet'),(12,'skill'),(9,'stoni tenis'),(3,'tenis'),(7,'vaterpolo');
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik2`
--

DROP TABLE IF EXISTS `korisnik2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik2` (
  `IDKor` int NOT NULL,
  `imeKor` varchar(45) NOT NULL,
  PRIMARY KEY (`IDKor`),
  UNIQUE KEY `imeKor_UNIQUE` (`imeKor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik2`
--

LOCK TABLES `korisnik2` WRITE;
/*!40000 ALTER TABLE `korisnik2` DISABLE KEYS */;
INSERT INTO `korisnik2` VALUES (4,'Ema'),(12,'Istvan'),(9,'Ivana'),(17,'Jana'),(5,'Janko'),(11,'Jovan'),(13,'Katarina'),(3,'Luka'),(14,'Marija'),(15,'Matija'),(6,'Mia'),(1,'Mihajlo'),(8,'Milena'),(2,'Ognjen'),(10,'Sofija'),(16,'Tadija'),(7,'Zoe');
/*!40000 ALTER TABLE `korisnik2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sn_kat`
--

DROP TABLE IF EXISTS `sn_kat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sn_kat` (
  `IDSnim` int NOT NULL,
  `IDKat` int NOT NULL,
  PRIMARY KEY (`IDSnim`,`IDKat`),
  KEY `sn_kat_ibfk_2_idx` (`IDKat`),
  CONSTRAINT `sn_kat_ibfk_1` FOREIGN KEY (`IDSnim`) REFERENCES `snimak` (`IDSnim`),
  CONSTRAINT `sn_kat_ibfk_2` FOREIGN KEY (`IDKat`) REFERENCES `kategorija` (`IDKat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sn_kat`
--

LOCK TABLES `sn_kat` WRITE;
/*!40000 ALTER TABLE `sn_kat` DISABLE KEYS */;
INSERT INTO `sn_kat` VALUES (1,1),(2,1),(2,2),(5,2),(2,3),(3,3),(7,4),(8,5),(2,6),(6,7),(4,8),(8,9),(8,10),(8,11);
/*!40000 ALTER TABLE `sn_kat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `snimak`
--

DROP TABLE IF EXISTS `snimak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `snimak` (
  `IDSnim` int NOT NULL AUTO_INCREMENT,
  `imeSnim` varchar(45) NOT NULL,
  `trajanje` int NOT NULL,
  `IDKor` int NOT NULL,
  `datumSnim` datetime NOT NULL,
  PRIMARY KEY (`IDSnim`),
  UNIQUE KEY `imeSnim_UNIQUE` (`imeSnim`),
  KEY `snimak_ibfk_1_idx` (`IDKor`),
  CONSTRAINT `snimak_ibfk_1` FOREIGN KEY (`IDKor`) REFERENCES `korisnik2` (`IDKor`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `snimak`
--

LOCK TABLES `snimak` WRITE;
/*!40000 ALTER TABLE `snimak` DISABLE KEYS */;
INSERT INTO `snimak` VALUES (1,'Finale kupa 2021',80,1,'2022-02-10 05:37:35'),(2,'Olimpijske igre 2020',200,11,'2022-02-10 17:32:01'),(3,'Djokovic-Federer 2022',120,3,'2022-03-11 05:20:35'),(4,'Spa trka 2020',150,1,'2021-07-01 01:12:24'),(5,'Zvezda-Partizan 2019',180,14,'2021-05-03 09:44:33'),(6,'Srbija-SAD 2021',75,3,'2020-07-21 22:22:00'),(7,'Srbija-Italija 2022',90,14,'2023-02-01 05:37:35'),(8,'Olimpijske igre 2016',90,3,'2022-10-11 19:32:13');
/*!40000 ALTER TABLE `snimak` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-11 14:03:57
