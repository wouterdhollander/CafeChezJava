/*
 Navicat Premium Data Transfer

 Source Server         : MySql
 Source Server Type    : MySQL
 Source Server Version : 50141
 Source Host           : localhost
 Source Database       : cafe

 Target Server Type    : MySQL
 Target Server Version : 50141
 File Encoding         : utf-8

 Date: 01/10/2010 19:31:00 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tblliquids`
-- ----------------------------
DROP TABLE IF EXISTS `tblliquids`;
CREATE TABLE `tblliquids` (
  `idLiquid` int(11) NOT NULL AUTO_INCREMENT,
  `liquidName` varchar(50) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`idLiquid`),
  UNIQUE KEY `liquidName` (`liquidName`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `tblliquids`
-- ----------------------------
INSERT INTO `tblliquids` VALUES ('1', 'Cola', '1.50'), ('2', 'Leffe', '2.20'), ('3', 'Koffie', '2.00'), ('4', 'Cola-Light', '1.50'), ('5', 'Whisky', '4.50'), ('6', 'Thee', '2.00'), ('7', 'Spa', '1.50'), ('8', 'Westmalle', '2.20'), ('9', 'Hoegaarden', '2.00'), ('10', 'Duvel', '2.50'), ('11', 'Stella', '1.85'), ('12', 'Chocomelk', '1.50'), ('13', 'Tonic', '1.85');

-- ----------------------------
--  Table structure for `tblober`
-- ----------------------------
DROP TABLE IF EXISTS `tblober`;
CREATE TABLE `tblober` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lastName` varchar(50) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `tblober`
-- ----------------------------
INSERT INTO `tblober` VALUES ('1', 'Peters', 'Wout', 'password'), ('2', 'Segers', 'Nathalie', 'password'), ('3', 'Vandenbroeck', 'Ilse', 'password'), ('4', 'Desmet', 'Patrick', 'password');

-- ----------------------------
--  Table structure for `tblorders`
-- ----------------------------
DROP TABLE IF EXISTS `tblorders`;
CREATE TABLE `tblorders` (
  `idOrder` int(11) NOT NULL AUTO_INCREMENT,
  `idLiquid` int(11) NOT NULL DEFAULT '0',
  `qty` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `idOber` int(11) DEFAULT NULL,
  PRIMARY KEY (`idOrder`,`idLiquid`),
  KEY `idLiquid` (`idLiquid`),
  KEY `FK_tblorders` (`idOber`),
  CONSTRAINT `FK_tblorders` FOREIGN KEY (`idOber`) REFERENCES `tblober` (`id`),
  CONSTRAINT `tblorders_ibfk_1` FOREIGN KEY (`idLiquid`) REFERENCES `tblliquids` (`idLiquid`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `tblorders`
-- ----------------------------
INSERT INTO `tblorders` VALUES ('1', '2', '10', '2009-12-25', '4'), ('2', '2', '9', '2009-12-25', '4'), ('3', '8', '10', '2009-12-25', '1'), ('4', '10', '4', '2009-12-26', '1'), ('5', '4', '10', '2009-12-26', '1'), ('6', '9', '13', '2009-12-26', '1'), ('7', '12', '10', '2009-12-26', '1'), ('8', '12', '10', '2009-12-26', '1'), ('9', '5', '4', '2009-12-26', '1'), ('10', '10', '1', '2009-12-29', '3'), ('11', '12', '12', '2009-12-29', '3'), ('12', '8', '4', '2009-12-29', '3'), ('13', '5', '10', '2009-12-29', '2'), ('14', '11', '6', '2009-12-29', '2'), ('15', '3', '4', '2009-12-29', '2'), ('16', '7', '2', '2009-12-29', '2'), ('17', '1', '1', '2009-12-29', '2'), ('18', '12', '1', '2009-12-30', '1'), ('19', '10', '5', '2010-01-01', '4'), ('20', '4', '14', '2010-01-01', '4'), ('21', '12', '1', '2010-01-01', '4'), ('22', '2', '1', '2010-01-01', '4'), ('57', '4', '5', '2010-01-07', '2'), ('58', '4', '5', '2010-01-07', '2'), ('59', '12', '6', '2010-01-07', '2'), ('60', '9', '8', '2010-01-07', '2'), ('61', '12', '6', '2010-01-07', '2'), ('62', '12', '14', '2010-01-07', '2'), ('63', '12', '5', '2010-01-07', '2'), ('64', '12', '14', '2010-01-07', '2'), ('65', '12', '5', '2010-01-07', '2'), ('66', '10', '7', '2010-01-07', '2'), ('67', '9', '12', '2010-01-08', '4'), ('68', '2', '9', '2010-01-08', '4'), ('69', '9', '12', '2010-01-08', '4'), ('70', '9', '9', '2010-01-08', '4');
