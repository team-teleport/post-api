-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: sungan-db-0.cmsebdllgluu.ap-northeast-2.rds.amazonaws.com    Database: sungan-post-service
-- ------------------------------------------------------
-- Server version	5.7.34-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `sungan_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_profile_img_url` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `FKg6q2teypuqhmwxri02ig8anl3` (`sungan_id`),
  CONSTRAINT `FKg6q2teypuqhmwxri02ig8anl3` FOREIGN KEY (`sungan_id`) REFERENCES `sungan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment_like`
--

DROP TABLE IF EXISTS `comment_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `comment_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqlv8phl1ibeh0efv4dbn3720p` (`comment_id`),
  CONSTRAINT `FKqlv8phl1ibeh0efv4dbn3720p` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `detail_hash_tag`
--

DROP TABLE IF EXISTS `detail_hash_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detail_hash_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_egfmcsowpyudjqov24utpo8r9` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hotplace`
--

DROP TABLE IF EXISTS `hotplace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotplace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `line2_station_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_profile_img_url` varchar(255) DEFAULT NULL,
  `emoji` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `FKl7q4nh8287b1xkepqqa618fay` (`line2_station_id`),
  CONSTRAINT `FKl7q4nh8287b1xkepqqa618fay` FOREIGN KEY (`line2_station_id`) REFERENCES `line2station` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hotplace_comment`
--

DROP TABLE IF EXISTS `hotplace_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotplace_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `hotplace_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_profile_img_url` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `FKcx6riaera06a26ciit3t54xy9` (`hotplace_id`),
  CONSTRAINT `FKcx6riaera06a26ciit3t54xy9` FOREIGN KEY (`hotplace_id`) REFERENCES `hotplace` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hotplace_comment_like`
--

DROP TABLE IF EXISTS `hotplace_comment_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotplace_comment_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `hotplace_comment_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb6iblqe2atpcpdx57s9no490m` (`hotplace_comment_id`),
  CONSTRAINT `FKb6iblqe2atpcpdx57s9no490m` FOREIGN KEY (`hotplace_comment_id`) REFERENCES `hotplace_comment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hotplace_like`
--

DROP TABLE IF EXISTS `hotplace_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotplace_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `hotplace_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmlasau1xlfqwp903vm020fxuv` (`hotplace_id`),
  CONSTRAINT `FKmlasau1xlfqwp903vm020fxuv` FOREIGN KEY (`hotplace_id`) REFERENCES `hotplace` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hotplace_nested_comment`
--

DROP TABLE IF EXISTS `hotplace_nested_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotplace_nested_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `hotplace_comment_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_profile_img_url` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `FKaf40vd84lifd2vdd3ywpmkw40` (`hotplace_comment_id`),
  CONSTRAINT `FKaf40vd84lifd2vdd3ywpmkw40` FOREIGN KEY (`hotplace_comment_id`) REFERENCES `hotplace_comment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `line2station`
--

DROP TABLE IF EXISTS `line2station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `line2station` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `line2type`
--

DROP TABLE IF EXISTS `line2type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `line2type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `line_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nested_comment`
--

DROP TABLE IF EXISTS `nested_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nested_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `comment_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_profile_img_url` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `FKih12p9f558eidr7xujmvslyhb` (`comment_id`),
  CONSTRAINT `FKih12p9f558eidr7xujmvslyhb` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `detail` varchar(255) DEFAULT NULL,
  `like_cnt` bigint(20) DEFAULT '0',
  `read_cnt` bigint(20) DEFAULT '0',
  `should_be_uploaded` bit(1) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `line2_station_id` bigint(20) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `car_num` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_profile_img_url` varchar(255) DEFAULT NULL,
  `vehicle_num` varchar(255) DEFAULT NULL,
  `report_type_id` bigint(20) DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `FK25omi6lpqb33dalmye76e0kwq` (`line2_station_id`),
  KEY `FK989tfinp1jxv79fvmju0cfpj3` (`report_type_id`),
  CONSTRAINT `FK25omi6lpqb33dalmye76e0kwq` FOREIGN KEY (`line2_station_id`) REFERENCES `line2station` (`id`),
  CONSTRAINT `FK989tfinp1jxv79fvmju0cfpj3` FOREIGN KEY (`report_type_id`) REFERENCES `report_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_comment`
--

DROP TABLE IF EXISTS `report_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `report_id` bigint(20) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_profile_img_url` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `FK381h93wgbr0h9sd0nm0wee44c` (`report_id`),
  CONSTRAINT `FK381h93wgbr0h9sd0nm0wee44c` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_comment_like`
--

DROP TABLE IF EXISTS `report_comment_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_comment_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `report_comment_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6w9ev098vt0fxwvyus6fj27vu` (`report_comment_id`),
  CONSTRAINT `FK6w9ev098vt0fxwvyus6fj27vu` FOREIGN KEY (`report_comment_id`) REFERENCES `report_comment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_like`
--

DROP TABLE IF EXISTS `report_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcf94lymwmayjkogyeeo5g7oft` (`report_id`),
  CONSTRAINT `FKcf94lymwmayjkogyeeo5g7oft` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_nested_comment`
--

DROP TABLE IF EXISTS `report_nested_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_nested_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_profile_img_url` varchar(255) DEFAULT NULL,
  `report_comment_id` bigint(20) DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `FKfio0uot5953rd0hsbjtniyn3y` (`report_comment_id`),
  CONSTRAINT `FKfio0uot5953rd0hsbjtniyn3y` FOREIGN KEY (`report_comment_id`) REFERENCES `report_comment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_type`
--

DROP TABLE IF EXISTS `report_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) CHARACTER SET utf8 NOT NULL,
  `category` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `station_has_types`
--

DROP TABLE IF EXISTS `station_has_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station_has_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `line2_station_id` bigint(20) DEFAULT NULL,
  `line2_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhs16mdva62fmefey26m7qtn7m` (`line2_station_id`),
  KEY `FKawxlegpqxkn93y3mjr7hcs239` (`line2_type_id`),
  CONSTRAINT `FKawxlegpqxkn93y3mjr7hcs239` FOREIGN KEY (`line2_type_id`) REFERENCES `line2type` (`id`),
  CONSTRAINT `FKhs16mdva62fmefey26m7qtn7m` FOREIGN KEY (`line2_station_id`) REFERENCES `line2station` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sungan`
--

DROP TABLE IF EXISTS `sungan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sungan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `emoji` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `like_cnt` bigint(20) DEFAULT '0',
  `read_cnt` bigint(20) DEFAULT '0',
  `text` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `line2_station_id` bigint(20) DEFAULT NULL,
  `sungan_channel_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_profile_img_url` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `FKekl2nc97yvm8r95t5vpiu7ta5` (`line2_station_id`),
  KEY `FK9fkj94qfxfhv6uw687c0upe57` (`sungan_channel_id`),
  CONSTRAINT `FK9fkj94qfxfhv6uw687c0upe57` FOREIGN KEY (`sungan_channel_id`) REFERENCES `sungan_channel` (`id`),
  CONSTRAINT `FKekl2nc97yvm8r95t5vpiu7ta5` FOREIGN KEY (`line2_station_id`) REFERENCES `line2station` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sungan_channel`
--

DROP TABLE IF EXISTS `sungan_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sungan_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sungan_content`
--

DROP TABLE IF EXISTS `sungan_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sungan_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `sungan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKolsux0m5aa8vpj71r3jbosudx` (`sungan_id`),
  CONSTRAINT `FKolsux0m5aa8vpj71r3jbosudx` FOREIGN KEY (`sungan_id`) REFERENCES `sungan` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sungan_like`
--

DROP TABLE IF EXISTS `sungan_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sungan_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `sungan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjmo11sqnbg7u7krvyyghaotcu` (`sungan_id`),
  CONSTRAINT `FKjmo11sqnbg7u7krvyyghaotcu` FOREIGN KEY (`sungan_id`) REFERENCES `sungan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=513 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_viewed_sungan`
--

DROP TABLE IF EXISTS `user_viewed_sungan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_viewed_sungan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `sungan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhwj9qwvy3opcab14rav0qgemi` (`sungan_id`),
  CONSTRAINT `FKhwj9qwvy3opcab14rav0qgemi` FOREIGN KEY (`sungan_id`) REFERENCES `sungan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
