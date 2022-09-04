/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : jim

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2022-09-03 23:32:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `tips` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '1', '0', '超级管理员', '24', 'administrator', null);
INSERT INTO `sys_role` VALUES ('5', '2', '1', '临时', '26', 'temp', null);
INSERT INTO `sys_role` VALUES ('178', '4', '0', 'bbbbb', null, 'bb', null);

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=442 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '123456', 'jim', null);
INSERT INTO `sys_user` VALUES ('45', '123456', '老板', null);
INSERT INTO `sys_user` VALUES ('46', '123456', '经理', null);
INSERT INTO `sys_user` VALUES ('425', '111', '小明', '2020-12-02 14:52:12');
INSERT INTO `sys_user` VALUES ('439', '111111', '小白', '2020-12-02 15:00:12');
INSERT INTO `sys_user` VALUES ('440', '123456', 'jim', null);
INSERT INTO `sys_user` VALUES ('441', '123456', 'jim', null);
