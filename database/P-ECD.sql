CREATE DATABASE  IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mydb`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 80.57.4.176    Database: mydb
-- ------------------------------------------------------
-- Server version	5.5.49-0ubuntu0.14.04.1

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `id` mediumint(8) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'henk','alleskits',1),(2,'piet','yes',0),(3,'kaas','no',0),(4,'jan','alles',0),(5,'klaas','kits',0),(6,'pan','poep',0),(7,'koek','kees',1),(8,'klop','knop',0),(9,'water','lop',1),(10,'mobiel','ui',0),(11,'user','test',1);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activities`
--

DROP TABLE IF EXISTS `activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` mediumint(8) NOT NULL,
  `client_id` mediumint(8) NOT NULL,
  `subjective` longtext,
  `objective` longtext,
  `evaluation` longtext,
  `plan` longtext,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_activities_accounts1_idx` (`account_id`),
  KEY `fk_activities_client1_idx` (`client_id`),
  CONSTRAINT `fk_activities_accounts1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activities`
--

LOCK TABLES `activities` WRITE;
/*!40000 ALTER TABLE `activities` DISABLE KEYS */;
INSERT INTO `activities` VALUES (1,4,70,'Oorpijn','Rood loopoor','Bacteriële buitensooronsteking','Antibiotica','2016-06-07 14:39:59'),(2,5,70,'Kiespijn','Rotte kies','Kieus Rottius','Tandjetrek','2016-06-07 14:39:56');
/*!40000 ALTER TABLE `activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `phonenumber` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `room` mediumint(9) NOT NULL,
  `martial` varchar(255) NOT NULL,
  `weight` mediumint(9) NOT NULL,
  `extra` varchar(45) DEFAULT 'Allergieën: -',
  `sex` tinyint(1) NOT NULL DEFAULT '1',
  `birthdate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Willa','Sears','0878 867 8863','Maecenas.libero@urnajusto.co.uk',131,'Single',87,'Allergieën: -',1,'2012-03-03'),(2,'Thane','Goodwin','0800 001510','Fusce.aliquam@tempus.co.uk',104,'Divorced',57,'Allergieën: -',1,'2012-03-03'),(3,'Jordan','Spears','055 8465 2035','pretium.neque.Morbi@mauris.org',144,'Married',100,'Allergieën: -',1,'2012-03-03'),(4,'Aurelia','Brewer','07624 641583','non.dui.nec@mollisnon.com',39,'Married',95,'Allergieën: -',1,'2012-03-03'),(5,'Walter','Weaver','(01927) 28062','Sed.auctor@lacus.com',150,'Married',79,'Allergieën: -',1,'2012-03-03'),(6,'Teagan','Craft','070 9839 2727','nec.quam.Curabitur@ligulaDonec.com',13,'Divorced',105,'Allergieën: -',1,'2012-03-03'),(7,'Roth','Tran','(0171) 986 1338','laoreet.lectus@duiFuscealiquam.ca',108,'Common-Law',79,'Allergieën: -',1,'2012-03-03'),(8,'Demetria','Herring','07763 868194','quis.accumsan.convallis@estNunc.org',85,'Married',93,'Allergieën: -',1,'2012-03-03'),(9,'Isaac','Cobb','0500 918541','vestibulum.massa.rutrum@Integereu.net',103,'Single',112,'Allergieën: -',1,'2012-03-03'),(10,'Sopoline','Holder','0800 1111','faucibus.Morbi.vehicula@dictum.net',58,'Single',113,'Allergieën: -',1,'2012-03-03'),(11,'Blair','Welch','(01940) 00391','volutpat@Aliquamadipiscing.edu',110,'Single',104,'Allergieën: -',1,'2012-03-03'),(12,'Kylynn','Hester','(021) 5431 0308','montes.nascetur.ridiculus@faucibuslectusa.ca',96,'Common-Law',85,'Allergieën: -',1,'2012-03-03'),(13,'Bernard','Beard','(012605) 35977','diam.at@enim.org',15,'Divorced',89,'Allergieën: -',1,'2012-03-03'),(14,'Quintessa','Mcfadden','076 5428 0571','est.ac@elitdictumeu.net',72,'Divorced',57,'Allergieën: -',1,'2012-03-03'),(15,'Garth','Cooke','0845 46 43','amet@Vivamus.net',101,'Single',99,'Allergieën: -',1,'2012-03-03'),(16,'Lyle','Wilder','(01811) 245269','commodo.ipsum@imperdietnec.ca',31,'Single',82,'Allergieën: -',1,'2012-03-03'),(17,'Dieter','Cummings','0800 771910','ac@velsapien.net',69,'Divorced',70,'Allergieën: -',1,'2012-03-03'),(18,'Fuller','Burgess','(016977) 0213','egestas.Aliquam@velnisl.edu',105,'Divorced',56,'Allergieën: -',1,'2012-03-03'),(19,'Felix','Howe','(0171) 208 8591','quis.arcu@anteVivamus.ca',82,'Single',96,'Allergieën: -',1,'2012-03-03'),(20,'Lois','Hensley','(015123) 34186','non@ligulaAeneaneuismod.ca',13,'Common-Law',50,'Allergieën: -',1,'2012-03-03'),(21,'Hadley','Hodge','0800 1111','odio.a.purus@magna.net',75,'Divorced',106,'Allergieën: -',1,'2012-03-03'),(22,'Adena','Collier','0500 157775','ipsum@liberoProinsed.com',134,'Single',118,'Allergieën: -',1,'2012-03-03'),(23,'Addison','Barnett','0845 46 47','Donec@CuraeDonectincidunt.com',63,'Divorced',80,'Allergieën: -',1,'2012-03-03'),(24,'Griffith','Sykes','(017524) 99903','elit.erat.vitae@Phaselluselit.com',114,'Divorced',97,'Allergieën: -',1,'2012-03-03'),(25,'Graiden','Kerr','055 5729 2723','pede.Praesent.eu@Suspendissedui.net',146,'Married',69,'Allergieën: -',1,'2012-03-03'),(26,'Candice','Aguilar','(016977) 0784','non.arcu@turpisegestas.com',60,'Divorced',94,'Allergieën: -',1,'2012-03-03'),(27,'Jacob','Alvarez','0832 518 7009','scelerisque@blanditNamnulla.edu',42,'Married',76,'Allergieën: -',1,'2012-03-03'),(28,'Rhoda','Malone','(016977) 0479','urna@magnaDuis.net',78,'Common-Law',95,'Allergieën: -',1,'2012-03-03'),(29,'Chandler','Delgado','0800 204893','et.netus@orci.co.uk',146,'Common-Law',60,'Allergieën: -',1,'2012-03-03'),(30,'Adria','Acosta','0800 1111','mauris.elit.dictum@malesuadaaugueut.net',62,'Divorced',52,'Allergieën: -',1,'2012-03-03'),(31,'Callum','Wilcox','(016977) 4560','mi.lorem.vehicula@lacinia.edu',83,'Married',65,'Allergieën: -',1,'2012-03-03'),(32,'Summer','Hester','(0118) 452 7637','amet.lorem.semper@justo.net',73,'Common-Law',54,'Allergieën: -',1,'2012-03-03'),(33,'Kamal','Crane','0800 768217','Nullam.lobortis@CuraeDonec.net',92,'Married',64,'Allergieën: -',1,'2012-03-03'),(34,'Clare','Juarez','056 1198 1987','vel.lectus.Cum@semperauctor.co.uk',42,'Single',51,'Allergieën: -',1,'2012-03-03'),(35,'Slade','Gutierrez','0959 423 1622','venenatis.vel@Quisque.co.uk',18,'Married',59,'Allergieën: -',1,'2012-03-03'),(36,'Felicia','Jordan','(01773) 67484','mauris.ut@nibhvulputate.org',33,'Married',52,'Allergieën: -',1,'2012-03-03'),(37,'Demetria','Glenn','(0111) 606 1132','ad@molestiesodales.com',49,'Single',89,'Allergieën: -',1,'2012-03-03'),(38,'Ian','Jenkins','0500 909780','neque.venenatis@liberonec.net',138,'Common-Law',74,'Allergieën: -',1,'2012-03-03'),(39,'Dale','Sloan','0800 196 4051','sed.est.Nunc@anteNunc.net',62,'Single',105,'Allergieën: -',1,'2012-03-03'),(40,'Adena','Rivers','0500 405943','blandit.viverra@at.co.uk',61,'Common-Law',70,'Allergieën: -',1,'2012-03-03'),(41,'Nina','Nielsen','07739 113261','eu.erat.semper@leoinlobortis.org',98,'Divorced',111,'Allergieën: -',1,'2012-03-03'),(42,'Ferris','Ruiz','07417 609088','lacinia@nonummyac.edu',49,'Divorced',115,'Allergieën: -',1,'2012-03-03'),(43,'Aurelia','Case','076 8914 2421','massa.lobortis@placeratorcilacus.net',143,'Divorced',112,'Allergieën: -',1,'2012-03-03'),(44,'Magee','Mckee','(019690) 77353','sed.tortor.Integer@Sedneque.ca',114,'Married',80,'Allergieën: -',1,'2012-03-03'),(45,'Chancellor','Velazquez','056 1580 8377','elit@nulla.com',45,'Common-Law',51,'Allergieën: -Allergieën: -',1,'2012-03-03'),(46,'Graiden','Russo','0322 626 8292','neque@tincidunt.edu',19,'Common-Law',112,'Allergieën: -',1,'2012-03-03'),(47,'Aileen','Santos','0500 454006','dictum.eu@sedtortor.com',65,'Married',113,'Allergieën: -',1,'2012-03-03'),(48,'Kirby','Skinner','076 3585 9399','Sed@bibendumsedest.ca',124,'Common-Law',77,'Allergieën: -',1,'2012-03-03'),(49,'Coby','Alvarez','0800 289 8062','primis.in.faucibus@consequatnecmollis.edu',16,'Common-Law',83,'Allergieën: -',1,'2012-03-03'),(50,'Ahmed','Blanchard','0981 446 9446','sed.pede.Cum@adipiscingligula.co.uk',49,'Single',116,'Allergieën: -',1,'2012-03-03'),(51,'Rama','Jenkins','(01282) 515258','Sed.diam.lorem@duinec.org',126,'Divorced',90,'Allergieën: -',1,'2012-03-03'),(52,'Rose','Wagner','0994 569 1778','Phasellus@Vestibulum.edu',100,'Single',115,'Allergieën: -',1,'2012-03-03'),(53,'Georgia','Blanchard','0846 197 0838','a@id.ca',109,'Single',62,'Allergieën: -',1,'2012-03-03'),(54,'Lael','Woodard','(0161) 713 2217','purus.Nullam@Pellentesqueut.co.uk',112,'Single',104,'Allergieën: -',1,'2012-03-03'),(55,'Quintessa','Moreno','056 3776 3573','interdum.Sed@Vivamusnibhdolor.com',94,'Married',86,'Allergieën: -',1,'2012-03-03'),(56,'Hadassah','Estes','0800 990 9219','turpis.vitae.purus@Vivamus.edu',53,'Common-Law',103,'Allergieën: -',1,'2012-03-03'),(57,'Alexander','Mcneil','07417 159586','eu.nulla.at@semper.co.uk',61,'Divorced',55,'Allergieën: -',1,'2012-03-03'),(58,'Fritz','Flores','(016977) 8966','Phasellus.elit@necenim.ca',133,'Divorced',89,'Allergieën: -',1,'2012-03-03'),(59,'Clayton','Hoffman','(022) 2531 6537','lacus@placeratorcilacus.edu',46,'Divorced',102,'Allergieën: -',1,'2012-03-03'),(60,'Kiayada','Mueller','(01782) 42805','bibendum.fermentum.metus@cursusdiam.co.uk',51,'Divorced',94,'Allergieën: -',1,'2012-03-03'),(61,'Wyatt','Bowers','0840 921 6449','Aliquam.gravida@ante.co.uk',135,'Common-Law',117,'Allergieën: -',1,'2012-03-03'),(62,'Jaden','Strickland','(015258) 83595','dui@scelerisqueneque.net',144,'Divorced',80,'Allergieën: -',1,'2012-03-03'),(63,'Kevyn','Reilly','07624 653051','porttitor.scelerisque@odiotristiquepharetra.ca',54,'Single',119,'Allergieën: -',1,'2012-03-03'),(64,'Asher','William','0800 1111','dolor@velvenenatis.edu',93,'Married',97,'Allergieën: -',1,'2012-03-03'),(65,'Kirestin','Townsend','07987 275811','mauris.Suspendisse@atarcu.ca',28,'Married',85,'Allergieën: -',1,'2012-03-03'),(66,'Barry','Garrison','0800 198 4762','varius@Namac.net',41,'Married',84,'Allergieën: -',1,'2012-03-03'),(67,'Giselle','Mcgee','07721 114521','eget.mollis.lectus@elementumsemvitae.ca',105,'Single',99,'Allergieën: -',1,'2012-03-03'),(68,'Gretchen','Gomez','0500 407470','ultrices.posuere@dictum.org',123,'Common-Law',84,'Allergieën: -',1,'2012-03-03'),(69,'Lars','Henry','(016050) 94803','eu.enim.Etiam@fermentumarcuVestibulum.co.uk',19,'Divorced',84,'Allergieën: -',1,'2012-03-03'),(70,'Priscilla','Bauer','055 6634 9739','tincidunt@eleifendCrassed.com',114,'Single',116,'Allergieën: -',1,'2012-03-03'),(71,'Ginger','Mathis','070 6595 9812','Suspendisse@Donecluctusaliquet.ca',91,'Common-Law',85,'Allergieën: -',1,'2012-03-03'),(72,'Cameron','Maddox','(01269) 95804','laoreet.posuere@blandit.co.uk',146,'Divorced',108,'Allergieën: -',1,'2012-03-03'),(73,'Dara','Herrera','0800 192512','dictum@massa.org',140,'Married',53,'Allergieën: -',1,'2012-03-03'),(74,'Brenna','Barker','0845 46 45','consectetuer.euismod.est@nibhDonecest.edu',133,'Divorced',120,'Allergieën: -',1,'2012-03-03'),(75,'Laura','Doyle','070 0961 5319','hendrerit.Donec.porttitor@at.co.uk',120,'Divorced',55,'Allergieën: -',1,'2012-03-03'),(76,'Audrey','Flowers','055 2694 1290','et@tellusfaucibus.net',114,'Common-Law',103,'Allergieën: -',1,'2012-03-03'),(77,'Zeus','Banks','070 7438 6574','congue.In@imperdietornareIn.ca',23,'Divorced',90,'Allergieën: -',1,'2012-03-03'),(78,'Aubrey','Rivers','(021) 5771 8332','orci.Ut@Aliquamfringilla.org',57,'Common-Law',62,'Allergieën: -',1,'2012-03-03'),(79,'Naida','Quinn','(0111) 952 3978','Nullam@Suspendissesagittis.org',103,'Single',89,'Allergieën: -',1,'2012-03-03'),(80,'Alfonso','Reid','(01523) 00631','ornare@nequeMorbiquis.edu',115,'Single',97,'Allergieën: -',1,'2012-03-03'),(81,'Neil','Wilder','0800 1111','erat@blandit.net',49,'Common-Law',57,'Allergieën: -',1,'2012-03-03'),(82,'Tamekah','Lindsay','0868 203 4910','Phasellus.at.augue@quamelementum.edu',118,'Single',85,'Allergieën: -',1,'2012-03-03'),(83,'James','Parsons','0957 293 0875','Donec.fringilla.Donec@Duismienim.org',76,'Single',51,'Allergieën: -',1,'2012-03-03'),(84,'Selma','Aguilar','070 0685 4787','elit@risus.co.uk',56,'Single',81,'Allergieën: -',1,'2012-03-03'),(85,'Michael','Cantrell','(022) 1745 2049','nisi.dictum.augue@nonegestasa.edu',118,'Single',51,'Allergieën: -',1,'2012-03-03'),(86,'Anthony','Mckee','(020) 4194 4653','natoque@variuset.edu',74,'Common-Law',74,'Allergieën: -',1,'2012-03-03'),(87,'Martha','Leblanc','(014567) 00132','In.nec@adipiscingnonluctus.edu',92,'Divorced',79,'Allergieën: -',1,'2012-03-03'),(88,'Emery','Guy','0350 272 0756','Nulla.semper.tellus@acfacilisisfacilisis.net',16,'Divorced',77,'Allergieën: -',1,'2012-03-03'),(89,'Dustin','Turner','(019351) 32796','sit.amet@lectus.com',47,'Divorced',102,'Allergieën: -',1,'2012-03-03'),(90,'Todd','Harvey','(029) 9760 5362','lorem.auctor.quis@doloregestas.com',35,'Married',75,'Allergieën: -',1,'2012-03-03'),(91,'Beck','Carlson','(016977) 6941','nisi@Etiamligulatortor.com',35,'Common-Law',103,'Allergieën: -',1,'2012-03-03'),(92,'Zachery','Conway','(016977) 9352','suscipit@facilisisvitaeorci.co.uk',92,'Common-Law',62,'Allergieën: -',1,'2012-03-03'),(93,'Unity','Saunders','(016977) 1220','cubilia@dictumeu.edu',148,'Common-Law',70,'Allergieën: -',1,'2012-03-03'),(94,'Alika','Sharp','076 2574 0963','mi.enim.condimentum@est.org',117,'Single',72,'Allergieën: -',1,'2012-03-03'),(95,'Ishmael','Pierce','055 6685 5651','amet.consectetuer@necluctus.ca',38,'Common-Law',50,'Allergieën: -',1,'2012-03-03'),(96,'Lillian','Robertson','07624 382168','nulla@luctuslobortis.edu',147,'Single',72,'Allergieën: -',1,'2012-03-03'),(97,'Rhoda','Mason','0800 1111','volutpat.nunc@Sed.com',114,'Common-Law',80,'Allergieën: -',1,'2012-03-03'),(98,'Chantale','Douglas','0994 394 6219','Cras.eu.tellus@hendreritconsectetuer.org',94,'Common-Law',115,'Allergieën: -',1,'2012-03-03'),(99,'Madeson','Lang','0315 740 5854','ac.facilisis@mauris.net',108,'Divorced',52,'Allergieën: -',1,'2012-03-03'),(100,'Lareina','Phelps','076 3855 5127','nunc@eulacusQuisque.org',110,'Married',65,'Allergieën: -',1,'2012-03-03');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `phonenumber` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `birthdate` date NOT NULL,
  `city` varchar(255) NOT NULL,
  `postal` varchar(10) NOT NULL,
  `country` varchar(100) NOT NULL,
  `account_id` mediumint(8) NOT NULL,
  PRIMARY KEY (`id`,`account_id`),
  KEY `fk_employees_accounts_idx` (`account_id`),
  CONSTRAINT `fk_employees_accounts` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Amos','Hodge','0800 1111','justo.sit.amet@turpisvitaepurus.com','2016-06-07','Tielrode','7183','United States Minor Outlying Islands',7),(2,'Oleg','Little','055 5875 3482','cursus.in.hendrerit@urnaVivamusmolestie.ca','2016-06-07','Kumluca','488134','Czech Republic',2),(3,'Cheryl','Lindsey','0366 102 4136','erat.Etiam@egestasrhoncus.co.uk','2016-06-07','Kapolei','57836','Equatorial Guinea',3),(4,'Chaney','Ball','(0191) 825 1070','Duis.risus.odio@porttitor.ca','2016-06-07','Sankt Johann im Pongau','ZF3L 7RJ','Liechtenstein',4),(5,'Eden','Frazier','(016977) 9292','nulla.Donec.non@Sednuncest.com','2016-06-07','Schriek','02129','Egypt',5),(6,'Lisandra','Tillman','0845 46 49','arcu.Sed@portaelita.org','2016-06-07','Edinburgh','6803','Mayotte',6),(7,'Idona','Page','0845 46 45','Vivamus.nisi.Mauris@ametnullaDonec.com','2016-06-07','Stourbridge','05674','Turkey',1),(8,'Arsenio','Goodman','(016608) 82429','eget.mollis@velitjustonec.edu','2016-06-07','Falerone','66229','Niger',8),(9,'Wynter','Sellers','(01581) 66984','hendrerit.consectetuer@malesuadavel.net','2016-06-07','Burlington','73334','Curaçao',9),(10,'Daquan','Olsen','(014745) 36992','diam.eu.dolor@Morbimetus.net','2016-06-07','Montreuil','54-407','Qatar',10),(11,'Test','Test','123456789','test@test.test','2016-06-07','Testytown','30TEST','Republic of Test',11);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `episodes`
--

DROP TABLE IF EXISTS `episodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `episodes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `client_id` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_episodes_clients_idx` (`client_id`),
  CONSTRAINT `fk_episodes_clients` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `episodes`
--

LOCK TABLES `episodes` WRITE;
/*!40000 ALTER TABLE `episodes` DISABLE KEYS */;
INSERT INTO `episodes` VALUES (1,'aids',70),(2,'cancer',70);
/*!40000 ALTER TABLE `episodes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-20 13:06:31
