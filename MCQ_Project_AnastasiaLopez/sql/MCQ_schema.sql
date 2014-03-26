CREATE DATABASE  IF NOT EXISTS `MCQ_schema` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `MCQ_schema`;
-- MySQL dump 10.13  Distrib 5.6.13, for osx10.6 (i386)
--
-- Host: 127.0.0.1    Database: MCQ_schema
-- ------------------------------------------------------
-- Server version	5.6.15

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
-- Table structure for table `Answers`
--

DROP TABLE IF EXISTS `Answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Answers` (
  `answ_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`answ_id`)
) ENGINE=InnoDB AUTO_INCREMENT=264 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Answers`
--

LOCK TABLES `Answers` WRITE;
/*!40000 ALTER TABLE `Answers` DISABLE KEYS */;
INSERT INTO `Answers` VALUES (84,'2'),(85,'3'),(86,'4'),(87,'5'),(88,'10'),(89,'20'),(90,'15'),(91,'hello'),(92,'good bye'),(93,'thank you'),(94,'no'),(103,'obese'),(104,'skinny'),(105,'thin'),(106,'ugly'),(107,'adios'),(108,'chao'),(109,'hola'),(110,'feo'),(111,'Gato'),(112,'Loro'),(113,'Perro'),(114,'Casa'),(115,'25'),(116,'London'),(117,'Buenos Aires'),(118,'Caracas'),(119,'New York'),(120,'Barcelona'),(121,'Rome'),(122,'Vatican City'),(123,'Milan'),(124,'Meaow'),(125,'Croak'),(126,'Woof'),(127,'Rindindinding'),(128,'America'),(129,'Europe'),(130,'Africa'),(131,'Asia'),(132,'viscosity of ink'),(133,'capillary action phenomenon'),(134,'diffusion of ink through the blotting'),(135,'siphon action'),(136,'Fermi'),(137,'angstrom'),(138,'newton'),(139,'tesla'),(140,'time'),(141,'distance'),(142,'light'),(143,'intensity of light'),(144,'2 minutes'),(145,'4 minutes'),(146,'8 minutes'),(147,'16 minutes'),(148,'all stars move from east to west'),(149,'the earth rotates from west to east'),(150,'the earth rotates from east to west'),(151,'the background of the stars moves from west to east'),(152,'2/5'),(153,'4/7'),(154,'4/9'),(155,'5/11'),(156,'6/13'),(157,'thrust'),(158,'pressure'),(159,'frequency'),(160,'conductivity'),(161,'they contain free electrons'),(162,'the atoms are lightly packed'),(163,'they have high melting point'),(164,'All of the above'),(165,'velocity'),(166,'momentum'),(167,'acceleration'),(168,'kinetic energy'),(169,'force'),(170,'Electrons'),(171,'Electromagnetic radiations'),(172,'Alpha particles'),(173,'Neutrons'),(174,'transverse'),(175,'longitudinal'),(176,'electromagnetic'),(177,'polarised'),(178,'minimum'),(179,'maximum'),(180,'zero'),(181,'minimum or maximum'),(182,'amplitude'),(183,'wavelength'),(184,'dispersion'),(185,'interference'),(186,'diffraction'),(187,'polarization'),(188,'move towards A'),(189,'move towards B'),(190,'move at right angles to the line joining A and B'),(191,'remain at rest'),(192,'surface tension'),(193,'viscosity'),(194,'specific gravity'),(195,'elasticity'),(196,'audio sounds'),(197,'infrasonic'),(198,'ultrasonic'),(199,'supersonics'),(202,'vector'),(203,'scalar'),(204,'phasor'),(205,'tensor'),(206,'Rainbow'),(207,'Earthshine'),(208,'Halo'),(209,'Mirage'),(210,'Report'),(211,'Field'),(212,'Record'),(213,'File'),(214,'To decrease the current'),(215,'To increase the current'),(216,'To decrease the voltage momentarily'),(217,'To increase the voltage momentarily'),(218,'Alan Turing'),(219,'Jeff Bezos'),(220,'George Boole'),(221,'Charles Babbage'),(222,'Choke'),(223,'Resistor'),(224,'Capacitor'),(225,'100 kHz'),(226,'1 GHz'),(227,'30 to 300 MHz'),(228,'3 to 30 MHz'),(229,'Pumping'),(230,'Exciting'),(231,'Priming'),(232,'Raising'),(233,'R = s2'),(234,'R = s'),(235,'R > s'),(236,'R = 1/s'),(237,'Programmable Lift Computer'),(238,'Program List Control'),(239,'Programmable Logic Controller'),(240,'Piezo Lamp Connector'),(241,'Blackbody radiation'),(242,'Stimulated emission'),(243,'Planck\'s radiation'),(244,'Einstein oscillation'),(245,'Power Poles'),(246,'Power Skirting'),(247,'Flush Floor Ducting'),(248,'Extension Cords'),(249,'Inductor'),(250,'Transistor'),(251,'Relay'),(252,'1976'),(253,'1972'),(254,'1980'),(255,'1984'),(256,'Potassium chloride'),(257,'Potassium carbonate'),(258,'Potassium hydroxide'),(259,'Sodium bicarbonate'),(260,'transfer of pollen from anther to stigma'),(261,'germination of pollen grains'),(262,'growth of pollen tube in ovule'),(263,'visiting flowers by insects');
/*!40000 ALTER TABLE `Answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Question_answers`
--

DROP TABLE IF EXISTS `Question_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Question_answers` (
  `question_id` int(11) NOT NULL,
  `answer_id` int(11) NOT NULL,
  PRIMARY KEY (`question_id`,`answer_id`),
  KEY `question_id_idx` (`question_id`),
  KEY `answer_id_idx` (`answer_id`),
  CONSTRAINT `answer_id` FOREIGN KEY (`answer_id`) REFERENCES `Answers` (`answ_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `question_id` FOREIGN KEY (`question_id`) REFERENCES `Questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Question_answers`
--

LOCK TABLES `Question_answers` WRITE;
/*!40000 ALTER TABLE `Question_answers` DISABLE KEYS */;
INSERT INTO `Question_answers` VALUES (35,107),(35,108),(35,109),(35,110),(36,111),(36,112),(36,113),(36,114),(38,88),(38,89),(38,90),(38,115),(39,116),(39,117),(39,118),(39,119),(40,120),(40,121),(40,122),(40,123),(41,124),(41,125),(41,126),(41,127),(42,128),(42,129),(42,130),(42,131),(43,132),(43,133),(43,134),(43,135),(44,136),(44,137),(44,138),(44,139),(45,140),(45,141),(45,142),(45,143),(46,144),(46,145),(46,146),(46,147),(47,148),(47,149),(47,150),(47,151),(49,161),(49,162),(49,163),(49,164),(50,158),(50,165),(50,167),(50,169),(51,170),(51,171),(51,172),(51,173),(52,174),(52,175),(52,176),(52,177),(53,178),(53,179),(53,180),(53,181),(54,159),(54,165),(54,182),(54,183),(55,188),(55,189),(55,190),(55,191),(56,192),(56,193),(56,194),(56,195),(85,196),(85,197),(85,198),(85,199),(86,202),(86,203),(86,204),(86,205),(88,210),(88,211),(88,212),(88,213),(90,218),(90,219),(90,220),(90,221),(94,229),(94,230),(94,231),(94,232),(95,233),(95,234),(95,235),(95,236),(96,237),(96,238),(96,239),(96,240),(97,241),(97,242),(97,243),(97,244),(98,245),(98,246),(98,247),(98,248),(99,224),(99,249),(99,250),(99,251),(100,252),(100,253),(100,254),(100,255),(106,120),(106,121),(109,252),(109,253),(109,254),(109,255),(110,252),(110,253),(110,254),(110,255),(111,252),(111,254),(111,255),(118,256),(118,257),(118,258),(118,259),(119,260),(119,261),(119,262),(119,263),(120,136),(120,170),(120,171),(120,172),(121,136),(121,137),(121,138),(121,182),(122,87),(122,88),(122,90),(122,115);
/*!40000 ALTER TABLE `Question_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Question_topics`
--

DROP TABLE IF EXISTS `Question_topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Question_topics` (
  `question_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  PRIMARY KEY (`question_id`,`topic_id`),
  KEY `answer_idx` (`topic_id`),
  CONSTRAINT `question_t` FOREIGN KEY (`question_id`) REFERENCES `Questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `topic` FOREIGN KEY (`topic_id`) REFERENCES `Topics` (`topic_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Question_topics`
--

LOCK TABLES `Question_topics` WRITE;
/*!40000 ALTER TABLE `Question_topics` DISABLE KEYS */;
INSERT INTO `Question_topics` VALUES (36,7),(39,17),(42,17),(41,19),(100,19),(109,19),(110,19),(111,19),(43,22),(44,22),(45,22),(46,22),(47,22),(49,22),(50,22),(51,22),(52,22),(53,22),(54,22),(55,22),(56,22),(85,22),(120,22),(121,22),(49,25),(49,26),(51,28),(120,28),(52,29),(85,29),(53,30),(54,31),(56,33),(96,37),(98,37),(100,37),(109,37),(110,37),(111,37),(118,38),(119,38),(122,39);
/*!40000 ALTER TABLE `Question_topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Questions`
--

DROP TABLE IF EXISTS `Questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) CHARACTER SET utf8mb4 NOT NULL COMMENT 'Enunciado de la pregunta',
  `correct_answer` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `correct_answer_idx` (`correct_answer`),
  CONSTRAINT `correct_answer` FOREIGN KEY (`correct_answer`) REFERENCES `Answers` (`answ_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Questions`
--

LOCK TABLES `Questions` WRITE;
/*!40000 ALTER TABLE `Questions` DISABLE KEYS */;
INSERT INTO `Questions` VALUES (35,'Hello in spanish is',109),(36,'Dog in spanish is',113),(38,'5x5',115),(39,'The capital of Venezuela is:',118),(40,'The capital of Italy is:',121),(41,'What does the fox say',127),(42,'In what continent is Peru in',128),(43,'The absorption of ink by blotting paper involves',133),(44,'Nuclear sizes are expressed in a unit named',136),(45,'Light year is a unit of',141),(46,'Light from the Sun reaches us in nearly',146),(47,'Stars appears to move from east to west because',149),(48,'Pa(Pascal) is the unit for',158),(49,'Metals are good conductors of electricity because',161),(50,'Pick out the scalar quantity',158),(51,'Out of the following, which is not emitted by radioactive substance?',173),(52,'Sound waves in air are',175),(53,'Magnetism at the centre of a bar magnet is',180),(54,'Of the following properties of a wave, the one that is independent of the other is its',182),(55,'Point A is at a lower electrical potential than point B. An electron between them on the line joining them will',189),(56,'Materials for rain-proof coats and tents owe their water-proof properties to',192),(85,'Sound of frequency below 20 Hz is called',197),(86,'Moment of inertia is',205),(87,'Of the following natural phenomena, tell which one known in Sanskrit as \'deer\'s thirst\'?',209),(88,'What is part of a database that holds only one type of information?',211),(90,'Who is largely responsible for breaking the German Enigma codes, created a test that provided a foundation for artificial intelligence?',218),(92,'Made from a variety of materials, such as carbon, which inhibits the flow of current...',223),(93,'What frequency range is the High Frequency band?',228),(94,'The first step to getting output from a laser is to excite an active medium. What is this process called?',229),(95,'What is the relationship between resistivity r and conductivity s',236),(96,'What does the term PLC stand for?',239),(97,'After the first photons of light are produced, which process is responsible for amplification of the light?',242),(98,'Which is NOT an acceptable method of distributing small power outlets throughout an open plan office area?',248),(99,'Which consists of two plates separated by a dielectric and can store a charge?',224),(100,'In what year was the \"@\" chosen for its use in e-mail addresses?',253),(106,'In which city the language catalan is spoken',120),(109,'In what year did the volcano Mauna Loa erupt',255),(110,'In what year was Ronald Reagan elected president',254),(111,'In what year was the Godfather movie released',253),(118,'Ordinary table salt is sodium chloride. What is baking soda?',259),(119,'Pollination is best defined as',260),(120,'Out of the following, which is not emitted by radioactive substance?',170),(121,'Nuclear sizes are expressed in a unit named',136),(122,'5x5',115);
/*!40000 ALTER TABLE `Questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Test`
--

DROP TABLE IF EXISTS `Test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Test` (
  `module_code` varchar(10) NOT NULL,
  `date` varchar(10) NOT NULL,
  `test_question_id` int(11) NOT NULL,
  PRIMARY KEY (`module_code`,`date`,`test_question_id`),
  KEY `question_id_idx` (`test_question_id`),
  CONSTRAINT `test_question_id` FOREIGN KEY (`test_question_id`) REFERENCES `Questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Test`
--

LOCK TABLES `Test` WRITE;
/*!40000 ALTER TABLE `Test` DISABLE KEYS */;
INSERT INTO `Test` VALUES ('ce207','2014-04-06',36),('ce207','2014-05-01',36),('ce212','2014-03-20',39),('ce218','2014-03-20',39),('ce212','2014-03-20',40),('ce218','2014-03-25',40),('ce212','2014-03-20',41),('ce212','2014-03-20',42),('ce218','2014-03-25',42),('ce212','2014-03-20',43),('ce218','2014-03-20',43),('ce522','2014-03-27',43),('ce111','2014-03-27',44),('ce111','2014-03-12',45),('ce111','2014-03-27',45),('ce111','2014-03-20',46),('ce111','2014-03-27',46),('ce111','2014-03-12',47),('ce111','2014-03-27',47),('ce212','2014-03-20',47),('ce218','2014-03-20',47),('ce522','2014-03-27',47),('ce111','2014-03-27',49),('ce218','2014-03-25',49),('ce522','2014-03-27',49),('ce111','2014-03-12',51),('ce111','2014-03-20',51),('ce522','2014-03-27',52),('ce111','2014-03-20',53),('ce111','2014-03-20',54);
/*!40000 ALTER TABLE `Test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Topics`
--

DROP TABLE IF EXISTS `Topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Topics` (
  `topic_id` int(11) NOT NULL AUTO_INCREMENT,
  `topic` varchar(50) NOT NULL,
  PRIMARY KEY (`topic_id`),
  UNIQUE KEY `topic_UNIQUE` (`topic`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Topics`
--

LOCK TABLES `Topics` WRITE;
/*!40000 ALTER TABLE `Topics` DISABLE KEYS */;
INSERT INTO `Topics` VALUES (19,'animals'),(38,'biology'),(26,'conductors'),(36,'database'),(5,'easy'),(32,'electricity'),(6,'english'),(17,'geography'),(30,'magnetism'),(33,'materials'),(39,'math'),(25,'metal'),(27,'movement'),(22,'physics'),(9,'prueba'),(28,'radioactivity'),(29,'sound'),(7,'spanish'),(37,'technology'),(8,'test'),(31,'wave');
/*!40000 ALTER TABLE `Topics` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-03-18 18:12:45
