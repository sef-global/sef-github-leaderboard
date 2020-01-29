-- MySQL dump 10.13  Distrib 8.0.18, for Linux (x86_64)
--
-- Host: localhost    Database: sef_github_leaderboard
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `pr_url` varchar(250) DEFAULT NULL,
  `created_at` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_score_entity1_idx` (`user_id`),
  CONSTRAINT `fk_score_entity1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
INSERT INTO `score` VALUES (4,27498587,'https://github.com/sef-global/sef-site/pull/295',1577276341),(5,33048395,'https://github.com/sef-global/sef-github-leaderboad/pull/1',1577284954),(6,43912578,'https://github.com/sef-global/sef-site/pull/301',1577348493),(7,27498587,'https://github.com/sef-global/sef-site/pull/302',1577358588),(8,33048395,'https://github.com/sef-global/sef-github-leaderboad/pull/5',1577368081),(9,33048395,'https://github.com/sef-global/sef-github-leaderboad/pull/6',1577422683),(10,29551090,'https://github.com/sef-global/sef-site/pull/306',1577428194),(12,38850236,'https://github.com/sef-global/sef-github-leaderboad/pull/7',1577443703),(13,33048395,'https://github.com/sef-global/sef-webapps/pull/20',1577449990),(14,33048395,'https://github.com/sef-global/sef-webapps/pull/22',1577465257),(15,38850236,'https://github.com/sef-global/sef-github-leaderboad/pull/12',1577544771),(16,38850236,'https://github.com/sef-global/sef-github-leaderboad/pull/15',1577548303),(17,38850236,'https://github.com/sef-global/sef-site/pull/308',1577553841),(18,38850236,'https://github.com/sef-global/sef-site/pull/309',1577605093),(19,27498587,'https://github.com/sef-global/sef-site/pull/311',1577639484),(20,45477334,'https://github.com/sef-global/sef-github-leaderboad/pull/16',1577762815),(21,33048395,'https://github.com/sef-global/sef-site/pull/313',1577979813),(22,27498587,'https://github.com/sef-global/sef-site/pull/316',1577981854),(23,33048395,'https://github.com/sef-global/sef-site/pull/315',1577982123),(24,27498587,'https://github.com/sef-global/sef-site/pull/317',1578414765),(25,45477334,'https://github.com/sef-global/sef-site/pull/321',1578457153),(26,45477334,'https://github.com/sef-global/sef-site/pull/322',1578466779),(27,29551090,'https://github.com/sef-global/sef-site/pull/336',1578583812),(28,29551090,'https://github.com/sef-global/sef-site/pull/340',1578590603),(29,43912578,'https://github.com/sef-global/sef-site/pull/338',1578624865),(30,43912578,'https://github.com/sef-global/sef-site/pull/338',1578624865),(31,48247516,'https://github.com/sef-global/sef-site/pull/339',1578626073),(32,43912578,'https://github.com/sef-global/sef-site/pull/343',1578638184),(33,43912578,'https://github.com/sef-global/sef-site/pull/342',1578639388),(34,48247516,'https://github.com/sef-global/sef-site/pull/341',1578640086),(35,43912578,'https://github.com/sef-global/sef-site/pull/344',1578643042),(36,43912578,'https://github.com/sef-global/sef-site/pull/345',1578644695),(37,43912578,'https://github.com/sef-global/sef-site/pull/346',1578646285),(38,43912578,'https://github.com/sef-global/sef-site/pull/350',1578652562),(39,43912578,'https://github.com/sef-global/sef-site/pull/351',1578653887),(40,43912578,'https://github.com/sef-global/sef-site/pull/352',1578654059),(41,27498587,'https://github.com/sef-global/sef-site/pull/354',1578661028),(42,48247516,'https://github.com/sef-global/sef-site/pull/349',1578669028),(43,43912578,'https://github.com/sef-global/sef-site/pull/357',1578669189),(44,37865893,'https://github.com/sef-global/sef-site/pull/360',1578767110),(45,43912578,'https://github.com/sef-global/sef-site/pull/362',1578817246),(46,33048395,'https://github.com/sef-global/sef-site/pull/363',1578976924),(47,45477334,'https://github.com/sef-global/sef-site/pull/367',1578979547),(48,43912578,'https://github.com/sef-global/sef-site/pull/370',1579009542),(49,43912578,'https://github.com/sef-global/sef-site/pull/371',1579014638),(50,43912578,'https://github.com/sef-global/sef-site/pull/372',1579056314),(51,48247516,'https://github.com/sef-global/sef-site/pull/358',1579056657),(52,33048395,'https://github.com/sef-global/sef-site/pull/374',1579101401),(53,43912578,'https://github.com/sef-global/sef-site/pull/375',1579166398),(54,43912578,'https://github.com/sef-global/sef-site/pull/377',1579172173),(55,31344335,'https://github.com/sef-global/sef-site/pull/378',1579179755),(56,31344335,'https://github.com/sef-global/sef-site/pull/380',1579182275),(57,45477334,'https://github.com/sef-global/sef-site/pull/383',1579183414),(58,43912578,'https://github.com/sef-global/sef-site/pull/382',1579183669),(59,43912578,'https://github.com/sef-global/sef-site/pull/384',1579186204),(60,43912578,'https://github.com/sef-global/sef-site/pull/391',1579194255),(61,43912578,'https://github.com/sef-global/sef-site/pull/392',1579196710),(62,43912578,'https://github.com/sef-global/sef-site/pull/394',1579247944),(63,43912578,'https://github.com/sef-global/sef-site/pull/395',1579257832),(64,43912578,'https://github.com/sef-global/sef-site/pull/398',1579268081),(65,27498587,'https://github.com/sef-global/sef-site/pull/397',1579272961),(66,37865893,'https://github.com/sef-global/sef-site/pull/361',1579274160),(67,43912578,'https://github.com/sef-global/sef-site/pull/401',1579277500),(68,37865893,'https://github.com/sef-global/sef-site/pull/405',1579285417),(69,43912578,'https://github.com/sef-global/sef-site/pull/409',1579331767),(70,43912578,'https://github.com/sef-global/sef-site/pull/410',1579336542),(71,43912578,'https://github.com/sef-global/sef-site/pull/411',1579337621),(72,43912578,'https://github.com/sef-global/sef-site/pull/412',1579338450),(73,31344335,'https://github.com/sef-global/sef-site/pull/386',1579358842),(74,43912578,'https://github.com/sef-global/sef-site/pull/415',1579362928),(75,31344335,'https://github.com/sef-global/sef-site/pull/420',1579419117),(76,31344335,'https://github.com/sef-global/sef-site/pull/422',1579420808),(77,31344335,'https://github.com/sef-global/sef-site/pull/424',1579427623),(78,45477334,'https://github.com/sef-global/sef-site/pull/416',1579438088),(79,43912578,'https://github.com/sef-global/sef-site/pull/413',1579438410),(80,27498587,'https://github.com/sef-global/sef-site/pull/421',1579438516),(81,37865893,'https://github.com/sef-global/sef-site/pull/426',1579438617),(82,35697678,'https://github.com/sef-global/sef-github-leaderboad/pull/17',1579439102),(83,30489601,'https://github.com/sef-global/sef-site/pull/428',1579449308),(84,35697678,'https://github.com/sef-global/sef-github-leaderboad/pull/18',1579492751),(85,31344335,'https://github.com/sef-global/sef-site/pull/431',1579516007),(86,45477334,'https://github.com/sef-global/sef-site/pull/436',1579587813),(87,43912578,'https://github.com/sef-global/sef-site/pull/435',1579591498),(88,43912578,'https://github.com/sef-global/sef-site/pull/432',1579591926),(89,30489601,'https://github.com/sef-global/sef-site/pull/444',1579703043),(90,37865893,'https://github.com/sef-global/sef-site/pull/446',1579706281),(91,36418402,'https://github.com/sef-global/sef-site/pull/442',1579709169),(92,27630091,'https://github.com/sef-global/sef-site/pull/450',1579709294),(93,31344335,'https://github.com/sef-global/sef-site/pull/448',1579710819),(94,35697678,'https://github.com/sef-global/sef-site/pull/451',1579753139),(95,43912578,'https://github.com/sef-global/sef-site/pull/443',1579763826),(96,27630091,'https://github.com/sef-global/sef-github-leaderboad/pull/20',1579786447),(97,45477334,'https://github.com/sef-global/sef-site/pull/452',1579792066),(98,33048395,'https://github.com/sef-global/sef-site/pull/455',1579973996),(99,35697678,'https://github.com/sef-global/sef-site/pull/461',1580012639),(100,35697678,'https://github.com/sef-global/sef-site/pull/462',1580015354),(101,20089340,'https://github.com/sef-global/sef-site/pull/460',1580017791),(102,20089340,'https://github.com/sef-global/sef-site/pull/463',1580048648),(103,33048395,'https://github.com/sef-global/sef-github-leaderboad/pull/22',1580107775),(104,35697678,'https://github.com/sef-global/sef-github-leaderboad/pull/24',1580185314);
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `image` varchar(250) NOT NULL,
  `url` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (20089340,'yozaam','https://avatars3.githubusercontent.com/u/20089340?v=4','https://github.com/yozaam'),(27498587,'Piumal1999','https://avatars2.githubusercontent.com/u/27498587?v=4','https://github.com/Piumal1999'),(27630091,'kumuditha-udayanga','https://avatars2.githubusercontent.com/u/27630091?v=4','https://github.com/kumuditha-udayanga'),(29551090,'DilshanNaveen','https://avatars3.githubusercontent.com/u/29551090?v=4','https://github.com/DilshanNaveen'),(30489601,'osusara','https://avatars3.githubusercontent.com/u/30489601?v=4','https://github.com/osusara'),(31344335,'yohanym95','https://avatars2.githubusercontent.com/u/31344335?v=4','https://github.com/yohanym95'),(33048395,'jayasanka-sack','https://avatars1.githubusercontent.com/u/33048395?v=4','https://github.com/jayasanka-sack'),(35697678,'janethavi','https://avatars0.githubusercontent.com/u/35697678?v=4','https://github.com/janethavi'),(36418402,'BROjohnny','https://avatars0.githubusercontent.com/u/36418402?v=4','https://github.com/BROjohnny'),(37865893,'BhathiyaTK','https://avatars2.githubusercontent.com/u/37865893?v=4','https://github.com/BhathiyaTK'),(38850236,'Bawanthathilan','https://avatars1.githubusercontent.com/u/38850236?v=4','https://github.com/Bawanthathilan'),(43912578,'anjula-sack','https://avatars0.githubusercontent.com/u/43912578?v=4','https://github.com/anjula-sack'),(45477334,'Gravewalker666','https://avatars0.githubusercontent.com/u/45477334?v=4','https://github.com/Gravewalker666'),(48247516,'manojmaxma','https://avatars0.githubusercontent.com/u/48247516?v=4','https://github.com/manojmaxma');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-29  4:40:06
