/*
Navicat MySQL Data Transfer

Source Server         : auto
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : friends

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2017-12-28 14:14:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `django_migrations`
-- ----------------------------
DROP TABLE IF EXISTS `django_migrations`;
CREATE TABLE `django_migrations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `applied` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of django_migrations
-- ----------------------------

-- ----------------------------
-- Table structure for `t_address`
-- ----------------------------
DROP TABLE IF EXISTS `t_address`;
CREATE TABLE `t_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `visited` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=216111 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_address
-- ----------------------------
INSERT INTO `t_address` VALUES ('1', 'http://www.sina.com.cn/', '0', '0');

-- ----------------------------
-- Table structure for `t_address_rule`
-- ----------------------------
DROP TABLE IF EXISTS `t_address_rule`;
CREATE TABLE `t_address_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_address_rule
-- ----------------------------

-- ----------------------------
-- Table structure for `t_base_data`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_data`;
CREATE TABLE `t_base_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_base_data
-- ----------------------------

-- ----------------------------
-- Table structure for `t_cell`
-- ----------------------------
DROP TABLE IF EXISTS `t_cell`;
CREATE TABLE `t_cell` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=879 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cell
-- ----------------------------

-- ----------------------------
-- Table structure for `t_cell_category`
-- ----------------------------
DROP TABLE IF EXISTS `t_cell_category`;
CREATE TABLE `t_cell_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cell_category
-- ----------------------------

-- ----------------------------
-- Table structure for `t_cell_relation`
-- ----------------------------
DROP TABLE IF EXISTS `t_cell_relation`;
CREATE TABLE `t_cell_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cellId` int(11) DEFAULT NULL,
  `relatedId` int(11) DEFAULT NULL,
  `weight` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `relatedId` (`relatedId`),
  CONSTRAINT `t_cell_relation_ibfk_1` FOREIGN KEY (`relatedId`) REFERENCES `t_sentence` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31075 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cell_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `t_friend`
-- ----------------------------
DROP TABLE IF EXISTS `t_friend`;
CREATE TABLE `t_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_friend
-- ----------------------------

-- ----------------------------
-- Table structure for `t_rule`
-- ----------------------------
DROP TABLE IF EXISTS `t_rule`;
CREATE TABLE `t_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_rule
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sence`
-- ----------------------------
DROP TABLE IF EXISTS `t_sence`;
CREATE TABLE `t_sence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sence
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sentence`
-- ----------------------------
DROP TABLE IF EXISTS `t_sentence`;
CREATE TABLE `t_sentence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68119 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sentence
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sentence_relation`
-- ----------------------------
DROP TABLE IF EXISTS `t_sentence_relation`;
CREATE TABLE `t_sentence_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sentenceId` int(11) DEFAULT NULL,
  `relatedId` int(11) DEFAULT NULL,
  `weight` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45168 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sentence_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `realName` varchar(50) DEFAULT 'jack',
  `password` char(64) DEFAULT NULL COMMENT '密码',
  `status` varchar(32) DEFAULT NULL COMMENT '状态',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `OnDuty` tinyint(1) DEFAULT NULL,
  `Emergency` tinyint(1) DEFAULT NULL,
  `Release` tinyint(1) DEFAULT NULL,
  `AttendTheMeeting` tinyint(1) DEFAULT NULL,
  `MeetingTheRecorder` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', null, '123456', null, '2014-07-17 12:59:08', null, null, null, null, null);

-- ----------------------------
-- Table structure for `t_word`
-- ----------------------------
DROP TABLE IF EXISTS `t_word`;
CREATE TABLE `t_word` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `唯一性` (`content`)
) ENGINE=InnoDB AUTO_INCREMENT=25621 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_word
-- ----------------------------

-- ----------------------------
-- Table structure for `t_word_category`
-- ----------------------------
DROP TABLE IF EXISTS `t_word_category`;
CREATE TABLE `t_word_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word_id` int(255) DEFAULT NULL,
  `base_data_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_word_category
-- ----------------------------

-- ----------------------------
-- Table structure for `t_word_relation`
-- ----------------------------
DROP TABLE IF EXISTS `t_word_relation`;
CREATE TABLE `t_word_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `formerRelatedId` bigint(20) DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `backRelatedId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=331606 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_word_relation
-- ----------------------------
