/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : box_practice

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2022-09-03 11:21:30
*/

SET FOREIGN_KEY_CHECKS=0;
CREATE DATABASE IF NOT EXISTS box_practice;
USE box_practice;
-- ----------------------------
-- Table structure for `t_arrange`
-- ----------------------------
DROP TABLE IF EXISTS `t_arrange`;
CREATE TABLE `t_arrange` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `task_id` int(10) NOT NULL COMMENT 'the id of task',
  `employee_id` int(10) NOT NULL COMMENT 'the id of employee',
  `create_at` datetime DEFAULT NULL COMMENT 'the datetime of creating',
  `update_at` datetime DEFAULT NULL COMMENT 'the date time of updating',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_arrange
-- ----------------------------
INSERT INTO `t_arrange` VALUES ('15', '16', '8', '2022-09-02 21:30:37', '2022-09-02 21:30:37');
INSERT INTO `t_arrange` VALUES ('16', '17', '9', '2022-09-02 21:30:49', '2022-09-02 21:30:49');
INSERT INTO `t_arrange` VALUES ('17', '18', '8', '2022-09-02 21:31:01', '2022-09-02 21:31:01');
INSERT INTO `t_arrange` VALUES ('18', '15', '10', '2022-09-02 21:32:23', '2022-09-02 21:32:23');
INSERT INTO `t_arrange` VALUES ('19', '7', '10', '2022-09-02 21:36:54', '2022-09-02 21:36:54');
INSERT INTO `t_arrange` VALUES ('20', '19', '8', '2022-09-02 21:42:31', '2022-09-02 21:42:31');
INSERT INTO `t_arrange` VALUES ('21', '17', '10', '2022-09-02 21:44:09', '2022-09-02 21:44:09');
INSERT INTO `t_arrange` VALUES ('22', '17', '9', '2022-09-02 21:44:14', '2022-09-02 21:44:14');

-- ----------------------------
-- Table structure for `t_employee`
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `name` varchar(100) NOT NULL COMMENT 'the name of employee',
  `hospital_id` int(10) NOT NULL COMMENT 'the id of hospital',
  `create_at` datetime DEFAULT NULL COMMENT 'the datetime of creating',
  `update_at` datetime DEFAULT NULL COMMENT 'the date time of updating',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES ('8', 'tom', '10', '2022-09-02 21:27:41', '2022-09-02 21:27:41');
INSERT INTO `t_employee` VALUES ('9', 'jim', '10', '2022-09-02 21:27:48', '2022-09-02 21:27:48');
INSERT INTO `t_employee` VALUES ('10', 'rose', '9', '2022-09-02 21:28:12', '2022-09-02 21:28:12');

-- ----------------------------
-- Table structure for `t_hospital`
-- ----------------------------
DROP TABLE IF EXISTS `t_hospital`;
CREATE TABLE `t_hospital` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `name` varchar(100) NOT NULL COMMENT 'the name of hospitals',
  `create_at` datetime DEFAULT NULL COMMENT 'the datetime of creating',
  `update_at` datetime DEFAULT NULL COMMENT 'the date time of updating',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_hospital
-- ----------------------------
INSERT INTO `t_hospital` VALUES ('9', 'zhe2', '2022-09-02 21:27:25', '2022-09-02 21:27:25');
INSERT INTO `t_hospital` VALUES ('10', 'zhe1', '2022-09-02 21:27:28', '2022-09-02 21:27:28');
INSERT INTO `t_hospital` VALUES ('11', 'tttttttt11', '2022-09-03 09:23:59', '2022-09-03 09:23:59');

-- ----------------------------
-- Table structure for `t_task`
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `title` varchar(100) NOT NULL COMMENT 'the name of employee',
  `description` varchar(100) DEFAULT NULL COMMENT 'the description of employee',
  `priority` varchar(20) NOT NULL COMMENT 'the priority of task',
  `status` varchar(20) NOT NULL COMMENT 'the status of task',
  `employee_id` int(10) NOT NULL COMMENT 'the id of employee',
  `create_at` datetime DEFAULT NULL COMMENT 'the datetime of creating',
  `update_at` datetime DEFAULT NULL COMMENT 'the date time of updating',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_task
-- ----------------------------
INSERT INTO `t_task` VALUES ('16', 't68', '9999', 'URGENT', 'OPEN', '8', '2022-09-02 21:30:37', '2022-09-02 21:43:38');
INSERT INTO `t_task` VALUES ('17', 't3', 'd6', 'URGENT', 'OPEN', '9', '2022-09-02 21:30:49', '2022-09-02 21:44:14');
INSERT INTO `t_task` VALUES ('18', 't1', 'd1', 'URGENT', 'OPEN', '8', '2022-09-02 21:31:01', '2022-09-02 21:31:01');
INSERT INTO `t_task` VALUES ('19', 't11', 'd11', 'URGENT', 'OPEN', '8', '2022-09-02 21:42:31', '2022-09-02 21:42:31');
