-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: englishdb
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Noun'),(2,'Verb'),(3,'Adjective'),(4,'Adverb'),(5,'Tag Question');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `choice`
--

DROP TABLE IF EXISTS `choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `choice` (
  `id` varchar(100) NOT NULL,
  `content` varchar(255) NOT NULL,
  `question_id` varchar(100) DEFAULT NULL,
  `is_correct` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_choice_question_idx` (`question_id`),
  CONSTRAINT `fk_choice_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choice`
--

LOCK TABLES `choice` WRITE;
/*!40000 ALTER TABLE `choice` DISABLE KEYS */;
INSERT INTO `choice` VALUES ('1923e291-4320-4bd6-99c5-0b19cace49fd','is','1eea6354-03ea-4d40-a085-1f50e3ddc8c1',''),('28b1d6e0-3fdf-4d65-98c9-84678b2dd47c','mine','57fa1066-85b2-403c-88cf-6005d22c0107','\0'),('4042e4a8-23ac-44e9-85b2-198f0162a057','are','1eea6354-03ea-4d40-a085-1f50e3ddc8c1','\0'),('425f6e5f-3336-4268-a97c-035eee7924a7','were','1eea6354-03ea-4d40-a085-1f50e3ddc8c1','\0'),('7cc012e6-b425-4d7b-aa01-90ddc858f570','my','57fa1066-85b2-403c-88cf-6005d22c0107',''),('d0ea0722-0647-4b6c-8db6-111ab2f162fc','me','57fa1066-85b2-403c-88cf-6005d22c0107','\0'),('e4cc37d1-13ff-4a62-9b8f-7fe0d56365ce','myself','57fa1066-85b2-403c-88cf-6005d22c0107','\0'),('e88d78e8-3801-497a-b147-66dd2fe02403','was','1eea6354-03ea-4d40-a085-1f50e3ddc8c1','\0');
/*!40000 ALTER TABLE `choice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` varchar(100) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_question_cate_idx` (`category_id`),
  CONSTRAINT `fk_question_cate` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES ('1eea6354-03ea-4d40-a085-1f50e3ddc8c1','This ... a book',2),('57fa1066-85b2-403c-88cf-6005d22c0107','this is ... book',4);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-10 16:53:24
