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
-- Dumping data for table `line2station`
--

INSERT INTO `line2station` (`id`, `name`) VALUES (1,'시청'),(2,'을지로입구(IBK기업은행)'),(3,'을지로3가'),(4,'을지로4가'),(5,'동대문역사문화공원(DDP)'),(6,'신당'),(7,'상왕십리'),(8,'왕십리(성동구청)'),(9,'한양대'),(10,'뚝섬'),(11,'성수'),(12,'건대입구'),(13,'구의(광진구청)'),(14,'강변(동서울터미널)'),(15,'잠실나루'),(16,'잠실(송파구청)'),(17,'잠실새내'),(18,'종합운동장'),(19,'삼성(무역센터)'),(20,'선릉'),(21,'역삼'),(22,'강남'),(23,'교대(법원·검찰청)'),(24,'서초'),(25,'방배(백석예술대)'),(26,'사당(대향병원)'),(27,'낙성대(강감찬)'),(28,'서울대입구(관악구청)'),(29,'봉천'),(30,'신림(양지병원)'),(31,'신대방'),(32,'구로디지털단지(원광디지털대)'),(33,'대림(구로구청)'),(34,'신도림'),(35,'문래'),(36,'영등포구청'),(37,'당산'),(38,'합정(홀트아동복지회)'),(39,'홍대입구'),(40,'신촌'),(41,'이대'),(42,'아현(추계예술대)'),(43,'충정로(경기대입구)'),(44,'용답'),(45,'신답'),(46,'용두(동대문구청)'),(47,'신설동'),(48,'도림천'),(49,'양천구청'),(50,'신정네거리'),(51,'까치산');

--
-- Dumping data for table `line2type`
--

INSERT INTO `line2type` (`id`, `line_type`) VALUES (1,'을지로순환선(본선)'),(2,'성수지선'),(3,'신정지선');

--
-- Dumping data for table `report_type`
--

INSERT INTO `report_type` (`id`, `label`, `category`) VALUES (11,'냉방/난방','REQUEST'),(12,'안내방송','REQUEST'),(13,'소음','REQUEST'),(14,'요청사항','REQUEST'),(15,'취객','REPORT'),(16,'추행','REPORT'),(17,'몰카','REPORT'),(18,'소란','REPORT'),(19,'행상인','REPORT'),(20,'신고사항','REPORT');

--
-- Dumping data for table `station_has_types`
--

INSERT INTO `station_has_types` (`id`, `line2_station_id`, `line2_type_id`) VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,5,1),(6,6,1),(7,7,1),(8,8,1),(9,9,1),(10,10,1),(11,11,1),(12,12,1),(13,13,1),(14,14,1),(15,15,1),(16,16,1),(17,17,1),(18,18,1),(19,19,1),(20,20,1),(21,21,1),(22,22,1),(23,23,1),(24,24,1),(25,25,1),(26,26,1),(27,27,1),(28,28,1),(29,29,1),(30,30,1),(31,31,1),(32,32,1),(33,33,1),(34,34,1),(35,35,1),(36,36,1),(37,37,1),(38,38,1),(39,39,1),(40,40,1),(41,41,1),(42,42,1),(43,43,1),(44,44,2),(45,45,2),(46,46,2),(47,47,2),(48,48,3),(49,49,3),(50,50,3),(51,51,3);

--
-- Dumping data for table `sungan_channel`
--

INSERT INTO `sungan_channel` (`id`, `name`) VALUES (1,'음악'),(2,'시사'),(3,'스포츠'),(4,'게임'),(5,'잡담');
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
