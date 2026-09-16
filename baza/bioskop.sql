/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 8.0.43 : Database - bioskop
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bioskop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `bioskop`;

/*Table structure for table `bill` */

DROP TABLE IF EXISTS `bill`;

CREATE TABLE `bill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `saved_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `saved_by` (`saved_by`),
  CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`saved_by`) REFERENCES `employe` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bill` */

insert  into `bill`(`id`,`date_time`,`total_amount`,`saved_by`) values 
(1,'2025-10-07 23:18:22',168.00,NULL),
(2,'2025-10-08 15:04:08',480.00,NULL),
(3,'2025-10-08 15:06:25',480.00,NULL),
(4,'2025-10-08 15:45:05',420.00,NULL),
(5,'2025-10-08 16:02:56',240.00,NULL),
(6,'2025-10-08 16:03:43',120.00,NULL),
(7,'2025-10-08 16:04:49',120.00,NULL),
(8,'2025-10-08 16:13:59',120.00,NULL),
(9,'2025-10-08 16:17:11',120.00,NULL),
(10,'2025-10-08 16:19:23',120.00,NULL),
(11,'2025-10-08 16:22:30',333.00,NULL),
(12,'2025-10-14 00:06:41',555.00,NULL),
(13,'2025-10-14 02:10:20',333.00,NULL),
(14,'2025-10-15 19:40:37',444.00,NULL),
(15,'2025-10-16 00:54:15',192.00,NULL),
(16,'2025-10-16 15:32:49',288.00,NULL),
(17,'2025-10-16 18:29:36',444.00,NULL),
(19,'2025-10-17 00:13:49',444.00,NULL),
(20,'2025-10-17 00:14:49',444.00,NULL),
(21,'2025-10-17 23:47:14',110.00,NULL),
(22,'2025-10-17 23:47:58',55.00,NULL),
(23,'2025-10-17 23:48:10',33.00,NULL),
(24,'2025-10-17 23:48:54',22.00,NULL),
(25,'2025-10-17 23:49:31',11.00,NULL),
(26,'2025-10-18 00:03:07',11.00,NULL),
(27,'2025-10-18 18:30:35',444.00,NULL),
(35,'2025-10-18 21:15:18',333.00,NULL),
(36,'2025-10-18 21:35:18',333.00,NULL),
(37,'2025-10-18 21:38:39',888.00,NULL),
(38,'2025-10-18 22:26:01',444.00,NULL),
(39,'2025-10-19 01:48:21',555.00,NULL),
(40,'2025-10-19 01:49:32',444.00,NULL),
(41,'2025-10-19 02:05:31',333.00,NULL),
(42,'2025-10-19 02:06:22',2380.00,NULL),
(43,'2025-10-19 02:06:30',170.00,NULL),
(44,'2025-10-19 15:19:52',1332.00,1),
(45,'2025-10-20 02:34:32',1332.00,1),
(46,'2025-10-22 10:58:34',400.00,1),
(47,'2025-10-22 15:55:00',400.00,1),
(48,'2025-10-22 16:11:38',444.00,1),
(49,'2025-10-22 18:25:38',1221.00,1),
(50,'2025-10-22 19:03:50',6400.00,1),
(51,'2025-10-22 19:07:43',7000.00,1),
(52,'2025-10-22 19:20:38',2400.00,1),
(53,'2025-10-22 20:06:05',3000.00,1),
(54,'2025-10-22 20:57:34',300.00,1),
(55,'2025-10-22 21:19:31',300.00,1),
(56,'2025-10-22 21:23:20',3600.00,1);

/*Table structure for table `employe` */

DROP TABLE IF EXISTS `employe`;

CREATE TABLE `employe` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `employe` */

insert  into `employe`(`id`,`firstname`,`lastname`,`username`,`password`) values 
(1,'admin','admin','aaa','aaa');

/*Table structure for table `film` */

DROP TABLE IF EXISTS `film`;

CREATE TABLE `film` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `duration` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `film` */

insert  into `film`(`id`,`title`,`duration`,`description`,`release_date`) values 
(1,'Inception',120,'dfsdsfsdsd','2010-06-24'),
(2,'ASdredsa',120,'dfsdfssdfsdf','2010-07-09'),
(3,'dsasdaasd',111,'dsdssdf','2011-08-08'),
(4,'Fistik',120,'dfssdfsdffdssdf','2022-05-05'),
(5,'ERfio',111,'dsfsdfsdfds','2022-10-08'),
(6,'asddfs',111,'dsfdfssdfdsdsdfs','2010-06-06'),
(8,'qqqq',111,'ddfdffddffdfddfdfdfdf','1999-08-08'),
(9,'aaaaaaaaaaa',111,'fggddgffgfgfgfg','1999-09-09'),
(10,'Ocean eleven',122,'FIlmfilmfilm','2011-08-08');

/*Table structure for table `film_genre` */

DROP TABLE IF EXISTS `film_genre`;

CREATE TABLE `film_genre` (
  `film_id` bigint NOT NULL,
  `genre_id` bigint NOT NULL,
  PRIMARY KEY (`film_id`,`genre_id`),
  KEY `genre_id` (`genre_id`),
  CONSTRAINT `film_genre_ibfk_1` FOREIGN KEY (`film_id`) REFERENCES `film` (`id`) ON DELETE CASCADE,
  CONSTRAINT `film_genre_ibfk_2` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `film_genre` */

insert  into `film_genre`(`film_id`,`genre_id`) values 
(1,1),
(2,1),
(4,1),
(9,1),
(10,1),
(1,2),
(3,2),
(6,2),
(2,3),
(8,3),
(9,3),
(10,3),
(5,4);

/*Table structure for table `genre` */

DROP TABLE IF EXISTS `genre`;

CREATE TABLE `genre` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `genre` */

insert  into `genre`(`id`,`name`) values 
(1,'Drama'),
(2,'Komedija'),
(3,'Triler'),
(4,'Misterija'),
(5,'Romansa');

/*Table structure for table `hall` */

DROP TABLE IF EXISTS `hall`;

CREATE TABLE `hall` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `capacity` int NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `hall` */

insert  into `hall`(`id`,`capacity`,`name`) values 
(1,15,'sala 1'),
(2,500,'sala 2'),
(7,111,'SALA 444'),
(8,222,'sala kk'),
(9,222,'sasasa'),
(10,111,'fert'),
(11,111,'сдаасд');

/*Table structure for table `projection` */

DROP TABLE IF EXISTS `projection`;

CREATE TABLE `projection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `film_id` bigint NOT NULL,
  `hall_id` bigint NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `status` varchar(50) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `sold_tickets` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `film_id` (`film_id`),
  KEY `hall_id` (`hall_id`),
  CONSTRAINT `projection_ibfk_1` FOREIGN KEY (`film_id`) REFERENCES `film` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `projection_ibfk_2` FOREIGN KEY (`hall_id`) REFERENCES `hall` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `projection` */

insert  into `projection`(`id`,`film_id`,`hall_id`,`date`,`time`,`status`,`price`,`sold_tickets`) values 
(1,1,1,'2025-10-07','14:30:00','PAST',42.00,14),
(2,1,1,'2025-10-08','15:00:00','PAST',120.00,15),
(3,1,1,'2025-10-08','20:00:00','PAST',111.00,3),
(4,2,1,'2025-10-14','14:00:00','PAST',111.00,5),
(5,2,1,'2025-10-15','14:00:00','PAST',111.00,7),
(7,1,2,'2025-10-16','17:00:00','PAST',111.00,0),
(8,2,1,'2025-10-16','22:00:00','PAST',32.00,15),
(9,4,1,'2025-10-17','14:00:00','PAST',111.00,12),
(11,1,1,'2025-10-17','16:18:00','PAST',111.00,0),
(12,1,1,'2025-10-18','20:00:00','PAST',11.00,22),
(13,2,1,'2025-10-19','14:00:00','PAST',111.00,15),
(16,2,2,'2025-10-19','14:00:00','PAST',111.00,16),
(17,1,1,'2025-10-20','14:00:00','PAST',170.00,15),
(18,1,1,'2025-10-20','23:00:00','PAST',222.00,6),
(19,1,2,'2025-10-20','16:00:00','PAST',111.00,0),
(20,2,2,'2025-10-20','13:00:00','PAST',111.00,0),
(21,2,1,'2025-10-20','17:30:00','PAST',111.00,12),
(22,1,10,'2025-10-23','16:30:00','ACTIVE',100.00,8),
(24,1,1,'2025-10-22','22:00:00','PAST',111.00,15),
(25,1,10,'2025-10-22','18:00:00','PAST',111.00,0),
(26,1,2,'2025-10-22','23:00:00','ACTIVE',111.00,0),
(27,1,11,'2025-10-22','23:00:00','ACTIVE',111.00,0),
(28,1,1,'2025-10-24','20:00:00','SOLD_OUT',400.00,18),
(29,4,1,'2025-10-24','12:00:00','SOLD_OUT',500.00,23),
(30,1,1,'2025-10-25','11:00:00','ACTIVE',300.00,11),
(31,2,1,'2025-10-25','15:00:00','ACTIVE',300.00,11),
(32,1,1,'2025-10-26','14:00:00','ACTIVE',300.00,1),
(33,1,1,'2025-10-23','21:00:00','ACTIVE',400.00,9);

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `projection_id` bigint NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `bill_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `projection_id` (`projection_id`),
  KEY `bill_id` (`bill_id`),
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`projection_id`) REFERENCES `projection` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=328 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `ticket` */

insert  into `ticket`(`id`,`projection_id`,`price`,`bill_id`) values 
(1,1,42.00,1),
(2,1,42.00,1),
(3,1,42.00,1),
(4,1,42.00,1),
(5,2,120.00,2),
(6,2,120.00,2),
(7,2,120.00,2),
(8,2,120.00,2),
(9,2,120.00,3),
(10,2,120.00,3),
(11,2,120.00,3),
(12,2,120.00,3),
(13,1,42.00,4),
(14,1,42.00,4),
(15,1,42.00,4),
(16,1,42.00,4),
(17,1,42.00,4),
(18,1,42.00,4),
(19,1,42.00,4),
(20,1,42.00,4),
(21,1,42.00,4),
(22,1,42.00,4),
(23,2,120.00,5),
(24,2,120.00,5),
(25,2,120.00,6),
(26,2,120.00,7),
(27,2,120.00,8),
(28,2,120.00,9),
(29,2,120.00,10),
(30,3,111.00,11),
(31,3,111.00,11),
(32,3,111.00,11),
(33,4,111.00,12),
(34,4,111.00,12),
(35,4,111.00,12),
(36,4,111.00,12),
(37,4,111.00,12),
(38,5,111.00,13),
(39,5,111.00,13),
(40,5,111.00,13),
(41,5,111.00,14),
(42,5,111.00,14),
(43,5,111.00,14),
(44,5,111.00,14),
(45,8,32.00,15),
(46,8,32.00,15),
(47,8,32.00,15),
(48,8,32.00,15),
(49,8,32.00,15),
(50,8,32.00,15),
(51,8,32.00,16),
(52,8,32.00,16),
(53,8,32.00,16),
(54,8,32.00,16),
(55,8,32.00,16),
(56,8,32.00,16),
(57,8,32.00,16),
(58,8,32.00,16),
(59,8,32.00,16),
(60,9,111.00,17),
(61,9,111.00,17),
(62,9,111.00,17),
(63,9,111.00,17),
(64,9,111.00,19),
(65,9,111.00,19),
(66,9,111.00,19),
(67,9,111.00,19),
(68,9,111.00,20),
(69,9,111.00,20),
(70,9,111.00,20),
(71,9,111.00,20),
(72,12,11.00,21),
(73,12,11.00,21),
(74,12,11.00,21),
(75,12,11.00,21),
(76,12,11.00,21),
(77,12,11.00,21),
(78,12,11.00,21),
(79,12,11.00,21),
(80,12,11.00,21),
(81,12,11.00,21),
(82,12,11.00,22),
(83,12,11.00,22),
(84,12,11.00,22),
(85,12,11.00,22),
(86,12,11.00,22),
(87,12,11.00,23),
(88,12,11.00,23),
(89,12,11.00,23),
(90,12,11.00,24),
(91,12,11.00,24),
(92,12,11.00,25),
(93,12,11.00,26),
(94,13,111.00,27),
(95,13,111.00,27),
(96,13,111.00,27),
(97,13,111.00,27),
(122,13,111.00,36),
(123,13,111.00,36),
(124,13,111.00,36),
(125,13,111.00,37),
(126,13,111.00,37),
(127,13,111.00,37),
(128,13,111.00,37),
(129,13,111.00,37),
(130,13,111.00,37),
(131,13,111.00,37),
(132,13,111.00,37),
(133,16,111.00,38),
(134,16,111.00,38),
(135,16,111.00,38),
(136,16,111.00,38),
(137,16,111.00,39),
(138,16,111.00,39),
(139,16,111.00,39),
(140,16,111.00,39),
(141,16,111.00,39),
(142,16,111.00,40),
(143,16,111.00,40),
(144,16,111.00,40),
(145,16,111.00,40),
(146,16,111.00,41),
(147,16,111.00,41),
(148,16,111.00,41),
(149,17,170.00,42),
(150,17,170.00,42),
(151,17,170.00,42),
(152,17,170.00,42),
(153,17,170.00,42),
(154,17,170.00,42),
(155,17,170.00,42),
(156,17,170.00,42),
(157,17,170.00,42),
(158,17,170.00,42),
(159,17,170.00,42),
(160,17,170.00,42),
(161,17,170.00,42),
(162,17,170.00,42),
(163,17,170.00,43),
(164,18,222.00,44),
(165,18,222.00,44),
(166,18,222.00,44),
(167,18,222.00,44),
(168,18,222.00,44),
(169,18,222.00,44),
(170,21,111.00,45),
(171,21,111.00,45),
(172,21,111.00,45),
(173,21,111.00,45),
(174,22,100.00,46),
(175,22,100.00,46),
(176,22,100.00,46),
(177,22,100.00,46),
(178,22,100.00,47),
(179,22,100.00,47),
(180,22,100.00,47),
(181,22,100.00,47),
(182,24,111.00,48),
(183,24,111.00,48),
(184,24,111.00,48),
(185,24,111.00,48),
(186,24,111.00,49),
(187,24,111.00,49),
(188,24,111.00,49),
(189,24,111.00,49),
(190,28,400.00,50),
(191,28,400.00,50),
(192,28,400.00,50),
(194,28,400.00,50),
(195,28,400.00,50),
(196,28,400.00,50),
(197,28,400.00,50),
(198,28,400.00,50),
(199,28,400.00,50),
(200,28,400.00,50),
(201,28,400.00,50),
(202,28,400.00,50),
(203,28,400.00,50),
(204,28,400.00,50),
(205,28,400.00,50),
(206,28,400.00,50),
(207,28,400.00,50),
(208,28,400.00,50),
(209,29,500.00,51),
(210,29,500.00,51),
(211,29,500.00,51),
(212,29,500.00,51),
(213,29,500.00,51),
(214,29,500.00,51),
(215,29,500.00,51),
(216,29,500.00,51),
(217,29,500.00,51),
(218,29,500.00,51),
(219,29,500.00,51),
(220,29,500.00,51),
(221,29,500.00,51),
(222,29,500.00,51),
(223,29,500.00,51),
(224,29,500.00,51),
(225,29,500.00,51),
(226,29,500.00,51),
(227,29,500.00,51),
(228,29,500.00,51),
(229,29,500.00,51),
(230,29,500.00,51),
(231,29,500.00,51),
(232,30,300.00,52),
(233,30,300.00,52),
(234,30,300.00,52),
(235,30,300.00,52),
(236,30,300.00,52),
(237,30,300.00,52),
(238,30,300.00,52),
(239,30,300.00,52),
(240,30,300.00,52),
(241,30,300.00,52),
(242,30,300.00,52),
(243,24,111.00,49),
(246,24,111.00,49),
(247,24,111.00,49),
(248,24,111.00,49),
(249,24,111.00,49),
(250,24,111.00,49),
(251,24,111.00,49),
(260,31,300.00,53),
(261,31,300.00,53),
(262,31,300.00,53),
(263,31,300.00,53),
(264,31,300.00,53),
(265,31,300.00,53),
(266,31,300.00,53),
(267,31,300.00,53),
(268,31,300.00,53),
(269,31,300.00,53),
(270,31,300.00,54),
(271,32,300.00,55),
(297,33,400.00,56),
(298,33,400.00,56),
(305,33,400.00,56),
(306,33,400.00,56),
(307,33,400.00,56),
(308,33,400.00,56),
(309,33,400.00,56),
(310,33,400.00,56),
(311,33,400.00,56),
(318,21,111.00,45),
(319,21,111.00,45),
(320,21,111.00,45),
(321,21,111.00,45),
(322,21,111.00,45),
(323,21,111.00,45),
(324,21,111.00,45),
(325,21,111.00,45);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
