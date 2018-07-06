/*
Navicat MySQL Data Transfer

Source Server         : local_dev
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : common

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-07-06 10:40:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `status` tinyint(2) NOT NULL COMMENT '状态',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `longitude` double(12,6) NOT NULL COMMENT '经度',
  `latitude` double(12,6) NOT NULL COMMENT '纬度',
  `geo_hash` varchar(15) NOT NULL COMMENT 'geo hash',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户表';
