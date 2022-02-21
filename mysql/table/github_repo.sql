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

 Date: 21/02/2022 23:53:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for github_repo
-- ----------------------------
DROP TABLE IF EXISTS `github_repo`;
CREATE TABLE `github_repo`  (
  `repo_sn` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '仓库序号',
  `repo_id` bigint(11) NOT NULL COMMENT 'github仓库id',
  `repo_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库名',
  `repo_full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库全名',
  `user_id` bigint(11) NULL DEFAULT NULL COMMENT 'github用户id',
  `repo_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'github用户备注',
  `is_fork` int(1) NULL DEFAULT NULL COMMENT '仓库是否是fork仓库',
  `repo_star_count` int(11) NULL DEFAULT NULL COMMENT 'github star数量',
  `repo_fork_count` int(11) NULL DEFAULT NULL COMMENT 'github fork数量',
  `repo_watch_count` int(11) NULL DEFAULT NULL COMMENT 'github watch数量',
  `repo_license` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'github参考license',
  `repo_topics` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'github仓库标签',
  `repo_languages` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'github仓库语言',
  `etag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`repo_sn`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
