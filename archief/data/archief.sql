-- phpMyAdmin SQL Dump
-- version 3.5.8.1
-- http://www.phpmyadmin.net
--
-- Host: 10.246.16.137:3306
-- Generation Time: Nov 07, 2013 at 07:58 PM
-- Server version: 5.1.66-0+squeeze1
-- PHP Version: 5.3.3-7+squeeze15

TRUNCATE TABLE event;
TRUNCATE TABLE photo;
TRUNCATE TABLE user;

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `chiroelzestraat`
--

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `name` varchar(100) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `live` tinyint(4) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `live` (`live`),
  KEY `deleted` (`deleted`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`name`, `start`, `end`, `id`, `live`, `deleted`, `created`, `modified`,`event_type`) VALUES
('2009-2010 - Vaulx', '2010-07-15 00:00:00', '2010-07-30 00:00:00', 9, 1, 0, '2013-11-07 19:55:13', '2013-11-07 19:55:13','2'),
('2003-2004 - Han Sur Lesse', '2004-07-15 00:00:00', '2004-07-30 00:00:00', 2, 1, 0, '2013-11-02 14:37:28', '2013-11-02 14:37:28','2'),
('2004-2005 - La Geripont', '2005-07-15 00:00:00', '2005-07-30 00:00:00', 3, 1, 0, '2013-11-02 14:38:22', '2013-11-02 14:38:22','2'),
('2005-2006 - Bouillon', '2006-07-15 00:00:00', '2006-07-30 00:00:00', 4, 1, 0, '2013-11-02 14:38:51', '2013-11-02 14:38:51','2'),
('2006-2007 - Andler', '2007-07-15 00:00:00', '2007-07-30 00:00:00', 5, 1, 0, '2013-11-02 14:39:08', '2013-11-02 14:39:08','2'),
('2007-2008 - Froidville', '2008-07-15 00:00:00', '2008-07-30 00:00:00', 6, 1, 0, '2013-11-02 14:39:30', '2013-11-02 14:39:30','2'),
('2008-2009 - Le Mesnil', '2009-07-15 00:00:00', '2009-07-30 00:00:00', 7, 1, 0, '2013-11-02 14:39:51', '2013-11-02 14:39:51','2'),
('2010-2011 - Sart', '2011-07-15 00:00:00', '2011-07-30 00:00:00', 8, 1, 0, '2013-11-02 14:40:22', '2013-11-02 14:40:22','2'),
('2011-2012 - Hunningen', '2012-07-15 00:00:00', '2012-07-30 00:00:00', 10, 1, 0, '2013-11-07 19:55:47', '2013-11-07 19:55:47','2'),
('50 jaar Chiro Elzestraat', '2003-04-15 00:00:00', '2003-04-17 00:00:00', 11, 1, 0, '2013-11-07 19:56:26', '2013-11-07 19:56:26','3'),
('55 jaar Chiro Elzestraat', '2008-04-15 00:00:00', '2008-04-17 00:00:00', 12, 1, 0, '2013-11-07 19:56:52', '2013-11-07 19:56:52','3'),
('Oprichting Chirojongens Elzestraat', '1953-09-01 00:00:00', '1953-09-01 00:00:00', 13, 1, 0, '2013-11-07 19:57:56', '2013-11-07 19:57:56','4');

-- --------------------------------------------------------

--
-- Table structure for table `photo`
--

CREATE TABLE IF NOT EXISTS `photo` (
  `title` varchar(100) DEFAULT NULL,
  `directory` text,
  `event` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `live` tinyint(4) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `live` (`live`),
  KEY `deleted` (`deleted`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Groepsfotos 
--

INSERT INTO `photo`(`title`, `directory`,`event`) VALUES 
('50 jaar chiro','/50 jaar Chiro Elzestraat/IMG_5749.JPG','11'),
('55 jaar chiro','/55 jaar Chiro Elzestraat/P1000518.JPG','12'),
('55 jaar chiro','/2003-2004 - Han Sur Lesse/Kamp/Geert/geert 059.jpg','2'),
('55 jaar chiro','/2004-2005 - La Geripont/Groepsfotos/IMG_6459.JPG','3'),
('55 jaar chiro','/2005-2006 - Bouillon/Groepsfotos/Groepsfoto.jpg','4'),
('55 jaar chiro','/2006-2007 - Andler/0 (1).JPG','5'),
('55 jaar chiro','/2007-2008 - Froidville/DSC07731.JPG','6'),
('55 jaar chiro','/2008-2009 - Le Mesnil/1 THEMA/1.JPG','7'),
('55 jaar chiro','/2009-2010 - Vaulx/8thema/2.JPG','9'),
('55 jaar chiro','/2010-2011 - Sart/1 thema/eerste Dag/A1.JPG','8'),
('55 jaar chiro','/2011-2012 - Hunningen/Thema/2.JPG','10');

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `first` varchar(100) DEFAULT NULL,
  `last` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `live` tinyint(4) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `email` (`email`),
  KEY `password` (`password`),
  KEY `live` (`live`),
  KEY `deleted` (`deleted`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`first`, `last`, `email`, `password`, `id`, `live`, `deleted`, `created`, `modified`) VALUES
('Sander', 'Van Loock', 'lierserulez@hotmail.com', 'test', 1, 1, 0, '2013-10-13 17:37:29', '2013-10-13 17:37:29');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
