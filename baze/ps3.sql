-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem3
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
-- Table structure for table `gledanje`
--

DROP TABLE IF EXISTS `gledanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gledanje` (
  `IDGled` int NOT NULL AUTO_INCREMENT,
  `IDKor` int NOT NULL,
  `IDSnim` int NOT NULL,
  `datumGled` datetime NOT NULL,
  `sekPoc` int NOT NULL,
  `sekOdgl` int NOT NULL,
  PRIMARY KEY (`IDGled`),
  KEY `gledanje_ibfk_1_idx` (`IDKor`),
  KEY `pretplata_ibfk_2_idx` (`IDSnim`),
  CONSTRAINT `gledanje_ibfk_1` FOREIGN KEY (`IDKor`) REFERENCES `korisnik3` (`IDKor`),
  CONSTRAINT `gledanje_ibfk_2` FOREIGN KEY (`IDSnim`) REFERENCES `snimak3` (`IDSnim`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gledanje`
--

LOCK TABLES `gledanje` WRITE;
/*!40000 ALTER TABLE `gledanje` DISABLE KEYS */;
INSERT INTO `gledanje` VALUES (1,1,1,'2022-02-10 05:37:35',10,200),(2,3,2,'2021-05-03 09:44:33',200,200),(3,3,2,'2021-05-03 09:44:33',100,100),(4,14,2,'2020-07-21 22:22:00',80,80),(5,5,2,'2018-12-10 01:22:49',40,40),(6,16,2,'2022-10-11 21:02:49',130,130),(7,7,2,'2018-12-10 08:56:12',50,50),(8,9,2,'2020-07-21 22:22:00',65,65),(9,8,2,'2018-12-10 19:32:13',44,44),(10,13,2,'2021-01-23 19:32:13',33,33),(11,7,1,'2023-04-28 08:36:17',240,120),(12,7,8,'2022-03-09 05:37:35',0,0),(13,8,8,'2021-07-01 06:01:24',35,35),(14,4,3,'2020-07-21 01:22:49',60,60),(15,9,1,'2023-06-17 03:23:42',70,70),(16,13,1,'2023-06-19 03:55:33',0,50);
/*!40000 ALTER TABLE `gledanje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik3`
--

DROP TABLE IF EXISTS `korisnik3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik3` (
  `IDKor` int NOT NULL,
  `imeKor` varchar(45) NOT NULL,
  PRIMARY KEY (`IDKor`),
  UNIQUE KEY `imeKor_UNIQUE` (`imeKor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik3`
--

LOCK TABLES `korisnik3` WRITE;
/*!40000 ALTER TABLE `korisnik3` DISABLE KEYS */;
INSERT INTO `korisnik3` VALUES (4,'Ema'),(12,'Istvan'),(9,'Ivana'),(17,'Jana'),(5,'Janko'),(11,'Jovan'),(13,'Katarina'),(3,'Luka'),(14,'Marija'),(15,'Matija'),(6,'Mia'),(1,'Mihajlo'),(8,'Milena'),(2,'Ognjen'),(10,'Sofija'),(16,'Tadija'),(7,'Zoe');
/*!40000 ALTER TABLE `korisnik3` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocena`
--

DROP TABLE IF EXISTS `ocena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ocena` (
  `IDOc` int NOT NULL AUTO_INCREMENT,
  `IDKor` int NOT NULL,
  `IDSnim` int NOT NULL,
  `ocena` int NOT NULL,
  `datumOcene` datetime NOT NULL,
  PRIMARY KEY (`IDOc`),
  KEY `ocena_ibfk_1_idx` (`IDKor`),
  KEY `ocena_ibfk_2_idx` (`IDSnim`),
  CONSTRAINT `ocena_ibfk_1` FOREIGN KEY (`IDKor`) REFERENCES `korisnik3` (`IDKor`),
  CONSTRAINT `ocena_ibfk_2` FOREIGN KEY (`IDSnim`) REFERENCES `snimak3` (`IDSnim`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocena`
--

LOCK TABLES `ocena` WRITE;
/*!40000 ALTER TABLE `ocena` DISABLE KEYS */;
INSERT INTO `ocena` VALUES (1,1,1,5,'2021-05-03 09:44:33'),(2,3,1,3,'2022-03-11 05:20:35'),(3,16,1,4,'2023-04-25 08:22:33'),(4,9,8,3,'2023-04-25 01:22:49'),(5,16,2,3,'2022-10-11 19:32:13'),(6,3,2,5,'2022-10-11 08:36:17'),(7,11,1,1,'2021-08-27 06:01:24'),(8,10,1,2,'2022-02-10 09:44:33'),(9,17,2,5,'2021-05-17 19:32:13'),(10,1,4,3,'2021-08-27 01:22:49'),(11,7,7,2,'2023-04-25 01:12:24');
/*!40000 ALTER TABLE `ocena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paket`
--

DROP TABLE IF EXISTS `paket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paket` (
  `IDPak` int NOT NULL AUTO_INCREMENT,
  `imePak` varchar(45) NOT NULL,
  `cenaPak` int NOT NULL,
  PRIMARY KEY (`IDPak`),
  UNIQUE KEY `imePak_UNIQUE` (`imePak`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paket`
--

LOCK TABLES `paket` WRITE;
/*!40000 ALTER TABLE `paket` DISABLE KEYS */;
INSERT INTO `paket` VALUES (1,'platinum',2000),(2,'gold',1500),(3,'silver',1000),(4,'bronze',500),(5,'ryzen',250),(6,'intel',2500),(7,'kingston',3200),(8,'ultra',4000),(9,'skill',750);
/*!40000 ALTER TABLE `paket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pretplata`
--

DROP TABLE IF EXISTS `pretplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pretplata` (
  `IDPret` int NOT NULL AUTO_INCREMENT,
  `IDKor` int NOT NULL,
  `datumPret` datetime NOT NULL,
  `cena` int NOT NULL,
  `IDPak` int NOT NULL,
  PRIMARY KEY (`IDPret`),
  KEY `pretplata_ibfk_1_idx` (`IDPak`),
  KEY `pretplata_ibfk_2_idx` (`IDKor`),
  CONSTRAINT `pretplata_ibfk_1` FOREIGN KEY (`IDPak`) REFERENCES `paket` (`IDPak`),
  CONSTRAINT `pretplata_ibfk_2` FOREIGN KEY (`IDKor`) REFERENCES `korisnik3` (`IDKor`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pretplata`
--

LOCK TABLES `pretplata` WRITE;
/*!40000 ALTER TABLE `pretplata` DISABLE KEYS */;
INSERT INTO `pretplata` VALUES (1,1,'2022-02-10 05:37:35',1500,2),(2,1,'2022-03-11 05:20:35',1500,2),(3,1,'2022-04-11 06:01:22',250,5),(4,1,'2022-05-11 06:01:23',2000,1),(5,1,'2022-06-11 06:01:24',3000,7),(6,3,'2020-12-23 19:32:13',1500,2),(7,3,'2021-01-23 19:32:13',1500,2),(8,14,'2023-04-25 19:32:13',2000,1),(9,11,'2020-07-21 22:22:00',500,4),(10,16,'2018-12-10 17:32:01',1250,6),(11,1,'2022-07-17 06:01:24',3200,7);
/*!40000 ALTER TABLE `pretplata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `snimak3`
--

DROP TABLE IF EXISTS `snimak3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `snimak3` (
  `IDSnim` int NOT NULL,
  `imeSnim` varchar(45) NOT NULL,
  PRIMARY KEY (`IDSnim`),
  UNIQUE KEY `imeSnim_UNIQUE` (`imeSnim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `snimak3`
--

LOCK TABLES `snimak3` WRITE;
/*!40000 ALTER TABLE `snimak3` DISABLE KEYS */;
INSERT INTO `snimak3` VALUES (3,'Djokovic-Federer 2022'),(1,'Finale kupa 2021'),(8,'Olimpijske igre 2016'),(2,'Olimpijske igre 2020'),(4,'Spa trka 2020'),(7,'Srbija-Italija 2022'),(6,'Srbija-SAD 2021'),(5,'Zvezda-Partizan 2019');
/*!40000 ALTER TABLE `snimak3` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-11 14:04:09
