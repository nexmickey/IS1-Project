-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem1
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
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `IDKor` int NOT NULL AUTO_INCREMENT,
  `imeKor` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `godiste` int NOT NULL,
  `pol` varchar(1) NOT NULL,
  `IDMesto` int NOT NULL,
  PRIMARY KEY (`IDKor`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `imeKor_UNIQUE` (`imeKor`),
  KEY `sdf_idx` (`IDMesto`),
  CONSTRAINT `korisnik_ibfk_1` FOREIGN KEY (`IDMesto`) REFERENCES `mesto` (`IDMesto`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'Mihajlo','mihajlo@gmail.com',2002,'M',1),(2,'Ognjen','ogisa@gmail.com',2004,'M',4),(3,'Luka','luka98@gmail.com',1998,'M',6),(4,'Ema','emicaaa@gmail.com',1991,'Z',5),(5,'Janko','jankojov@gmail.com',2002,'M',2),(6,'Mia','mia453@gmail.com',1985,'Z',1),(7,'Zoe','zoe2001@gmail.com',2001,'Z',7),(8,'Milena','mikica02@gmail.com',2000,'Z',8),(9,'Ivana','ivanaaa@gmail.com',1995,'Z',1),(10,'Sofija','sofimarso@gmail.com',2000,'Z',4),(11,'Jovan','jovakovac@gmail.com',1995,'M',3),(12,'Istvan','istvadone@gmail.com',1998,'M',7),(13,'Katarina','kaca1989@gmail.com',1989,'Z',5),(14,'Marija','maka1998@gmail.com',1997,'Z',3),(15,'Matija','promata@gmail.com',2001,'M',3),(16,'Tadija','tadija@gmail.com',2002,'M',14),(17,'Jana','jana@gmail.com',2000,'Z',12);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesto`
--

DROP TABLE IF EXISTS `mesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesto` (
  `IDMesto` int NOT NULL AUTO_INCREMENT,
  `imeMes` varchar(45) NOT NULL,
  PRIMARY KEY (`IDMesto`),
  UNIQUE KEY `imeMes_UNIQUE` (`imeMes`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesto`
--

LOCK TABLES `mesto` WRITE;
/*!40000 ALTER TABLE `mesto` DISABLE KEYS */;
INSERT INTO `mesto` VALUES (1,'Beograd'),(5,'Kragujevac'),(14,'Kraljevo'),(8,'Loznica'),(2,'Nis'),(3,'Novi Sad'),(11,'Prijepolje'),(12,'Sombor'),(13,'Stara Pazova'),(7,'Subotica'),(4,'Vranje'),(6,'Zajecar');
/*!40000 ALTER TABLE `mesto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-11 14:04:22
