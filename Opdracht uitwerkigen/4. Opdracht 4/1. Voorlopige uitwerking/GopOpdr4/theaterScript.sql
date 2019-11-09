-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.51b-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema theater
--

CREATE DATABASE IF NOT EXISTS theater;
USE theater;

--
-- Definition of table `klant`
--

DROP TABLE IF EXISTS `klant`;
CREATE TABLE `klant` (
  `klantnummer` int(10) unsigned NOT NULL,
  `naam` varchar(45) NOT NULL,
  `telefoon` varchar(45) NOT NULL,
  PRIMARY KEY  (`klantnummer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `klant`
--

/*!40000 ALTER TABLE `klant` DISABLE KEYS */;
INSERT INTO `klant` (`klantnummer`,`naam`,`telefoon`) VALUES 
 (1,'Sint','030-7492885'),
 (2,'Pootjes','020-5644387'),
 (3,'Klas2d','015-6772391'),
 (4,'Jansen','06-22230487'),
 (5,'van der Gaag','020-6823422');
/*!40000 ALTER TABLE `klant` ENABLE KEYS */;


--
-- Definition of table `bezetting`
--

DROP TABLE IF EXISTS `bezetting`;
CREATE TABLE `bezetting` (
  `resnummer` int(10) unsigned NOT NULL auto_increment,
  `voorstelling` date NOT NULL,
  `rijnummer` int(10) unsigned NOT NULL,
  `stoelnummer` int(10) unsigned NOT NULL,
  `klant` int(10) unsigned NOT NULL,
  PRIMARY KEY  USING BTREE (`resnummer`),
  KEY `FK_bezetting_1` (`voorstelling`),
  KEY `FK_bezetting_2` (`klant`),
  UNIQUE INDEX `U_bezetting_1` (`voorstelling` ASC, `rijnummer` ASC, `stoelnummer` ASC) ,
  CONSTRAINT `FK_bezetting_1` FOREIGN KEY (`voorstelling`) REFERENCES `voorstelling` (`datum`),
  CONSTRAINT `FK_bezetting_2` FOREIGN KEY (`klant`) REFERENCES `klant` (`klantnummer`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bezetting`
--

/*!40000 ALTER TABLE `bezetting` DISABLE KEYS */;
INSERT INTO `bezetting` (`resnummer`,`voorstelling`,`rijnummer`,`stoelnummer`,`klant`) VALUES 
 (14,'2015-09-12',2,4,1),
 (15,'2015-09-12',2,5,1),
 (16,'2015-09-12',2,6,1),
 (17,'2015-09-12',3,4,2),
 (18,'2015-09-12',3,3,2),
 (19,'2015-09-12',3,2,2),
 (20,'2015-09-12',3,5,2),
 (21,'2015-09-12',4,5,2),
 (22,'2015-09-12',4,4,2),
 (23,'2015-09-12',4,3,2),
 (24,'2015-09-12',4,2,2),
 (25,'2015-09-12',5,4,3),
 (26,'2015-09-12',5,5,3),
 (27,'2015-09-12',5,6,3),
 (28,'2015-09-12',6,6,3),
 (29,'2015-09-12',6,5,3),
 (30,'2015-09-12',6,4,3),
 (31,'2015-09-12',6,3,3),
 (32,'2015-09-12',5,3,3),
 (33,'2015-09-12',7,3,3),
 (34,'2015-09-12',7,4,3),
 (35,'2015-09-12',7,5,3),
 (36,'2015-09-12',7,6,3),
 (37,'2015-09-12',7,7,3),
 (38,'2015-09-12',8,7,3),
 (39,'2015-09-12',8,6,3),
 (40,'2015-09-12',8,5,3),
 (41,'2015-09-12',8,4,3),
 (42,'2015-09-12',8,3,3),
 (43,'2015-09-12',8,2,3),
 (44,'2015-09-12',7,2,3),
 (45,'2015-09-12',6,2,3),
 (46,'2015-09-12',5,2,3),
 (47,'2015-09-12',5,1,3),
 (48,'2015-09-12',6,1,3),
 (49,'2015-09-12',7,1,3),
 (50,'2015-09-12',8,1,3),
 (51,'2015-09-15',3,2,4),
 (52,'2015-09-15',3,3,4),
 (53,'2015-09-15',3,4,4),
 (54,'2015-10-19',3,4,5),
 (55,'2015-10-19',3,5,5);
/*!40000 ALTER TABLE `bezetting` ENABLE KEYS */;


--
-- Definition of table `voorstelling`
--

DROP TABLE IF EXISTS `voorstelling`;
CREATE TABLE `voorstelling` (
  `datum` date NOT NULL,
  `naam` varchar(45) NOT NULL,
  PRIMARY KEY  (`datum`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `voorstelling`
--

/*!40000 ALTER TABLE `voorstelling` DISABLE KEYS */;
INSERT INTO `voorstelling` (`datum`,`naam`) VALUES 
 ('2015-09-12','Carmen'),
 ('2015-09-15','Het temmen van de feeks'),
 ('2015-10-19','Bang zijn. Toch doen.'),
 ('2015-11-01','De A van Abeltje');
/*!40000 ALTER TABLE `voorstelling` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
