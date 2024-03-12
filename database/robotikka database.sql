-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.29 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for robotikka
CREATE DATABASE IF NOT EXISTS `robotikka` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `robotikka`;

-- Dumping structure for table robotikka.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `email` varchar(100) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table robotikka.loyalty_card
CREATE TABLE IF NOT EXISTS `loyalty_card` (
  `code` int NOT NULL,
  `type` enum('GOLD','PLATINUM','SILVER') DEFAULT NULL,
  `barcode` longblob,
  `customer_email` varchar(100) NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `customer_email_UNIQUE` (`customer_email`),
  KEY `fk_loyalty_card_customer_idx` (`customer_email`),
  CONSTRAINT `fk_loyalty_card_customer` FOREIGN KEY (`customer_email`) REFERENCES `customer` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table robotikka.order_detail
CREATE TABLE IF NOT EXISTS `order_detail` (
  `code` int NOT NULL,
  `issued_date` date DEFAULT NULL,
  `total_cost` double DEFAULT NULL,
  `customer_email` varchar(100) NOT NULL,
  `discount_amount` double DEFAULT NULL,
  `user_email` varchar(100) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `fk_order_detail_customer1_idx` (`customer_email`),
  KEY `fk_order_detail_user1_idx` (`user_email`),
  CONSTRAINT `fk_order_detail_customer1` FOREIGN KEY (`customer_email`) REFERENCES `customer` (`email`),
  CONSTRAINT `fk_order_detail_user1` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table robotikka.product
CREATE TABLE IF NOT EXISTS `product` (
  `code` int NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table robotikka.product_details
CREATE TABLE IF NOT EXISTS `product_details` (
  `code` varchar(80) NOT NULL,
  `barcode` longblob,
  `qty_on_hand` int DEFAULT NULL,
  `selling_price` double DEFAULT NULL,
  `buying_price` double DEFAULT NULL,
  `discount_availability` tinyint DEFAULT NULL,
  `show_price` double DEFAULT NULL,
  `product_code` int NOT NULL,
  PRIMARY KEY (`code`),
  KEY `fk_product_details_product1_idx` (`product_code`),
  CONSTRAINT `fk_product_details_product1` FOREIGN KEY (`product_code`) REFERENCES `product` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table robotikka.product_details_has_order_detail
CREATE TABLE IF NOT EXISTS `product_details_has_order_detail` (
  `order_detail_code` int NOT NULL,
  `product_details_code` varchar(80) NOT NULL,
  `product_qty` int DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `amount` double DEFAULT NULL,
  PRIMARY KEY (`order_detail_code`,`product_details_code`),
  KEY `fk_product_details_has_order_detail_order_detail1_idx` (`order_detail_code`),
  KEY `fk_product_details_has_order_detail_product_details1_idx` (`product_details_code`),
  CONSTRAINT `fk_product_details_has_order_detail_order_detail1` FOREIGN KEY (`order_detail_code`) REFERENCES `order_detail` (`code`),
  CONSTRAINT `fk_product_details_has_order_detail_product_details1` FOREIGN KEY (`product_details_code`) REFERENCES `product_details` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

-- Dumping structure for table robotikka.user
CREATE TABLE IF NOT EXISTS `user` (
  `email` varchar(100) NOT NULL,
  `password` varchar(750) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
