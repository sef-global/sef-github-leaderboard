CREATE DATABASE  IF NOT EXISTS `sef_giraffe` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sef_giraffe`;
-- MySQL dump 10.13  Distrib 8.0.14, for macos10.14 (x86_64)
--
-- Host: localhost    Database: sef_giraffe
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `board` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `title` varchar(100) NOT NULL,
                         `image` varchar(250) NOT NULL,
                         `description` text,
                         `status` varchar(10) NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entity`
--

DROP TABLE IF EXISTS `entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `entity` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `name` varchar(100) NOT NULL,
                          `image` varchar(250) NOT NULL,
                          `board_id` int(11) NOT NULL,
                          `status` varchar(10) NOT NULL,
                          PRIMARY KEY (`id`),
                          KEY `fk_entity_board_idx` (`board_id`),
                          CONSTRAINT `fk_entity_board` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `score` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `entity_id` int(11) NOT NULL,
                         `description` varchar(250) DEFAULT NULL,
                         `points` int(11) NOT NULL,
                         `created_at` bigint(20) NOT NULL,
                         `status` varchar(10) NOT NULL,
                         PRIMARY KEY (`id`),
                         KEY `fk_score_entity1_idx` (`entity_id`),
                         CONSTRAINT `fk_score_entity1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-10 19:43:48
