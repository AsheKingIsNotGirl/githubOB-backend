/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : github

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 21/02/2022 23:53:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for github_contributor
-- ----------------------------
DROP TABLE IF EXISTS `github_contributor`;
CREATE TABLE `github_contributor`  (
  `contributor_sn` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '协作者序列号',
  `repo_id` bigint(11) NULL DEFAULT NULL COMMENT '所属仓库id',
  `contributor_id` bigint(11) NULL DEFAULT NULL COMMENT '协作者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`contributor_sn`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
