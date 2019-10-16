CREATE DATABASE IF NOT EXISTS `kino`;
USE `kino`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: kino
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `usertype_id` int(11) unsigned NOT NULL,
  `salt` varchar(11) NOT NULL,
  `login_attempts` int(11) NOT NULL DEFAULT '0',
  `last_login_attempt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `name`, `password`, `usertype_id`, `salt`, `login_attempts`, `last_login_attempt`) VALUES (33,'admin','2dd3ad3ec024487cb165dc6b487ef028bbbc2177fca0404d269a8ecf6aee258b8e3ead20e10d95a23a86ba6cc7c0f40585f4349b38fbdecd1f56248ae2869285',1,'_1033043588',0,'2019-10-08 02:32:17'),(34,'employee','e4853aec4befd0d2fec1c416d5db75634c7b7c1e662783938c4b691aef295f377bf0db203f05a42b278ff2dfb1f5e2073eab459c487aa79571be958513ea411d',2,'_-602472355',0,'2019-10-08 02:32:43');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'kino'
--
/*!50003 DROP PROCEDURE IF EXISTS `createUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `createUser`(IN name varchar(50), IN password varchar(250), IN salt varchar(11), IN usertype_id int)
BEGIN
	INSERT INTO `users` (`name`, `password`, `salt`, `usertype_id`) VALUES (name, sha2(password, 512), salt, usertype_id);
    SELECT * FROM `users` ORDER BY id DESC LIMIT 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `validateUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `validateUser`(IN cname VARCHAR(50), IN cpassword VARCHAR(250))
BEGIN
DECLARE rid int;
DECLARE rlogin_attempts INT;
DECLARE rlast_login_attempt INT;

IF CHAR_LENGTH(cname) > 0 AND CHAR_LENGTH(cpassword) > 0 THEN
	
	
	
	SET @recCount = (SELECT count(*) FROM `users` WHERE `name` = cname AND `password` = sha2(CONCAT(`salt`, cpassword), 512) AND (`login_attempts` < 10 OR (`login_attempts` >= 10 AND NOW() > ADDTIME(`last_login_attempt`, '0:2:0'))));

	IF @recCount = 1 THEN
		
		UPDATE `users` SET `login_attempts` = 0 WHERE `name` = cname AND `password` = sha2(cpassword, 512);
		SELECT `id`,`name`,`salt`, `usertype_id` FROM `users` WHERE `name` = cname AND `password` = sha2(CONCAT(`salt`, cpassword), 512);
	ELSE
		
		SET @uCount = (SELECT count(*) FROM `users` WHERE `name` = cname);
		IF @uCount = 1 THEN
			
			SELECT `id`, `login_attempts` FROM `users` WHERE `name` = cname INTO rid, rlogin_attempts;
			UPDATE `users` SET `login_attempts` = (rlogin_attempts+1), `last_login_attempt` = NOW() WHERE `id` = rid;
		END IF;
		
		SELECT NULL LIMIT 0;
	END IF;
ELSE
	SELECT NULL LIMIT 0;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Movies
CREATE PROCEDURE `createMovie`(IN name varchar(50), IN genre varchar(50), IN length int)
BEGIN
	INSERT INTO `movies` (`name`, `genre`, `length`) VALUES (name, genre, length);
    SELECT * FROM `movies` ORDER BY id DESC LIMIT 1;
END

CREATE TABLE `movies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `genre` varchar(50) DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

-- Dump completed on 2019-10-08  2:33:18
CREATE USER IF NOT EXISTS 'kinodml'@'localhost';
ALTER USER 'kinodml'@'localhost' IDENTIFIED BY 'KinoKano!';
GRANT DELETE, INSERT, SELECT, UPDATE, EXECUTE ON `kino`.* TO 'kinodml'@'localhost';
FLUSH PRIVILEGES;