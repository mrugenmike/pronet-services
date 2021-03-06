-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: 127.0.0.1    Database: pronet
-- ------------------------------------------------------
-- Server version	5.6.23

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
-- Table structure for table `feeds`
--

DROP TABLE IF EXISTS `feeds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feeds` (
  `feed_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `feed_title` varchar(200) DEFAULT NULL,
  `feed_description` varchar(500) DEFAULT NULL,
  `feed_role` varchar(45) DEFAULT NULL,
  `user_name` VARCHAR (200) DEFAULT NULL,
  `user_img` VARCHAR(200) DEFAULT NULL
  PRIMARY KEY (`feed_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follow` (
  `followerID` varchar(255) DEFAULT NULL,
  `followeeID` varchar(255) DEFAULT NULL,
  `followeeImgURL` varchar(255) DEFAULT NULL,
  `followeeName` varchar(255) DEFAULT NULL,
  `followeeRole` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_apps`
--

DROP TABLE IF EXISTS `job_apps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_apps` (
  `job_app_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `job_id` int(11) DEFAULT NULL,
  `job_title` varchar(200) DEFAULT NULL,
  `company_name` varchar(200) DEFAULT NULL,
  `user_name` varchar(200) DEFAULT NULL,
  `user_email` varchar(200) DEFAULT NULL,
  `app_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`job_app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
ALTER TABLE job_apps
MODIFY COLUMN app_date date
--
-- Dumping data for table `job_apps`
--

LOCK TABLES `job_apps` WRITE;
/*!40000 ALTER TABLE `job_apps` DISABLE KEYS */;
INSERT INTO `job_apps` VALUES (1,2,1,1113,'SE','TCS',NULL,NULL,'2015-04-05 23:00:53'),(2,2,1,1113,'SE TEST','TCS',NULL,NULL,'2015-04-05 23:01:12'),(3,2,1,1113,'NEW TEST','TCS','kukday','kukday@gmail.com','2015-04-05 23:24:34');
/*!40000 ALTER TABLE `job_apps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_login`
--

DROP TABLE IF EXISTS `user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_login` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(25) DEFAULT NULL,
  `role` varchar(2) DEFAULT NULL,
  `last_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login`
--

LOCK TABLES `user_login` WRITE;
/*!40000 ALTER TABLE `user_login` DISABLE KEYS */;
INSERT INTO `user_login` VALUES (1,'kukday','kukday@gmail.com','test','U','2015-04-05 04:07:11'),(2,'TCS','tcs@gmail.com','test','C','2015-04-05 04:07:21'),(3,'kukday','nkukday@abc.com','test','U','2015-04-05 20:26:33');
/*!40000 ALTER TABLE `user_login` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-05 17:06:38





--Varuna Table


DROP TABLE IF EXISTS `user_login`;
CREATE TABLE `user_login` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(25) DEFAULT NULL,
  `role` varchar(2) DEFAULT NULL,
  `last_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
);


DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `followerID` varchar(255) DEFAULT NULL,
  `followeeID` varchar(255) DEFAULT NULL,
  `followeeImgURL` varchar(255) DEFAULT NULL,
  `followeeName` varchar(255) DEFAULT NULL,
  `followeeRole` varchar(255) DEFAULT NULL
);



DROP TABLE IF EXISTS `feeds`;
CREATE TABLE `feeds` (
  `feed_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `feed_title` varchar(200) DEFAULT NULL,
  `feed_description` varchar(500) DEFAULT NULL,
  `feed_role` varchar(45) DEFAULT NULL,
  `user_name` VARCHAR (200) DEFAULT NULL,
  `user_img` VARCHAR(200) DEFAULT NULL,
  PRIMARY KEY (`feed_id`)
);

DROP TABLE IF EXISTS `connections`;
CREATE TABLE `connections` (
  `user_id` int(11) ,
  `item_id` int(11) ,
  `preference` int(11) DEFAULT NULL
);


DROP TABLE IF EXISTS `skills`;
CREATE TABLE `skills` (
  `user_id` int(11) ,
  `item_id` int(11) ,
  `preference` int(11) DEFAULT NULL
);


DROP TABLE IF EXISTS `skillsPref`;
CREATE TABLE `skillsPref` (
  `user_id` int(11) ,
  `item_id` int(11) ,
  `preference` int(11) DEFAULT NULL
);


DROP TABLE IF EXISTS `skillsMapping`;
CREATE TABLE `skillsMapping` (
  `skillID` int(11) NOT NULL AUTO_INCREMENT,
  `skillName` varchar(50),
PRIMARY KEY (`skillID`)
);

INSERT INTO `skillsMapping` VALUES (1,'java'),(2,'c'),(3,'elasticsearch'),(4,'c++'),(5,'c#'),(6,'python'),(7,'scala'),(8,'ruby'),(9,'javascript'),(10,'nodejs'),(11,'groovy'),(12,'php'),(13,'sql'),(14,'objective-c'),(15,'.net'),(16,'perl'),(17,'r-language'),(18,'big-data'),(19,'lisp'),(20,'mahout'),(21,'mongodb'),(22,'dynamodb'),(23,'oracle'),(24,'postgresql'),(25,'redis'),(26,'cassendra'),(27,'neo4j'),(28,'photography'),(29,'ms-office'),(30,'angularjs'),(31,'bootstrap'),(32,'html'),(33,'css'),(34,'xml'),(35,'jsp'),(36,'jsf'),(37,'jquery'),(38,'spring'),(39,'springboot'),(40,'verilog'),(41,'viapi'),(42,'data-mining'),(43,'pig'),(44,'hive'),(45,'hadoop'),(46,'zookeeper'),(47,'data-analysis'),(48,'android'),(49,'ios'),(50,'swift'),(51,'machine-learning'),(52,'spark')



