-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: MORCMS
-- ------------------------------------------------------
-- Server version	8.0.13

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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `name`, `password`, `usertype_id`, `salt`, `login_attempts`, `last_login_attempt`) VALUES (18,'mor','18fd8d6f9a9505d3320f90215dab0e7892255bf16414f8c887f132285374945cfa60944927636b9d72b67bb950c2f74fdc1ae23c22b08aa5b581a938fdbab885',1,'___44302935',8,'2019-05-27 10:41:10'),(23,'g4','de3d3ddef187a28a8463487d7c6d97abdea96d1e80d509c7ad319b8872eb4208e7a7f462e14fe183d09f009ed172b6ec05e97a059ac5750d5fdbda674df0a581',4,'_1385574766',0,'2019-10-04 10:27:21');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'MORCMS'
--
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `createUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `createUser`(IN name varchar(50), IN password varchar(250), IN salt varchar(11), IN usertype_id int)
BEGIN
	INSERT INTO `MORCMS`.`users` (`name`, `password`, `salt`, `usertype_id`) VALUES (name, sha2(password, 512), salt, usertype_id);
    SELECT * FROM `MORCMS`.`users` ORDER BY id DESC LIMIT 1;
END ;;

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
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `validateUser`(IN cname VARCHAR(50), IN cpassword VARCHAR(250))
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

-- Dump completed on 2019-10-04 10:35:58
