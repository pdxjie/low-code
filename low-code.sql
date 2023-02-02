/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : low-code

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 24/01/2023 01:35:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lc_data_source
-- ----------------------------
DROP TABLE IF EXISTS `lc_data_source`;
CREATE TABLE `lc_data_source`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `source_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源IP',
  `source_cover` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源封面',
  `source_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源名称',
  `source_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源账户名称',
  `source_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源密码',
  `connect_num` int NULL DEFAULT NULL COMMENT '连接次数',
  `database_num` int NULL DEFAULT NULL COMMENT '数据库数量',
  `source_port` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源端口号',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '自定义数据源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lc_data_source
-- ----------------------------
INSERT INTO `lc_data_source` VALUES ('1616775181043253250', '1616724924829872129', '127.0.0.1', 'https://edu-2330.oss-cn-beijing.aliyuncs.com/icon-images/1.jpg', '个人博客', 'root', 'bGaCYr6mn5Y', 12, 28, '3306', '2023-01-21 20:30:16', '2023-01-21 20:30:16');
INSERT INTO `lc_data_source` VALUES ('1616825805831266305', '1616723100278231041', '127.0.0.1', 'https://edu-2330.oss-cn-beijing.aliyuncs.com/icon-images/19.jpg', '低代码', 'root', 'bGaCYr6mn5Y', 8, 28, '3306', '2023-01-21 23:51:26', '2023-01-21 23:51:26');

-- ----------------------------
-- Table structure for lc_tags
-- ----------------------------
DROP TABLE IF EXISTS `lc_tags`;
CREATE TABLE `lc_tags`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `tags_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lc_tags
-- ----------------------------

-- ----------------------------
-- Table structure for lc_user
-- ----------------------------
DROP TABLE IF EXISTS `lc_user`;
CREATE TABLE `lc_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ邮箱',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `salt` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密盐',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户描述',
  `tags_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户标签',
  `register_time` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `role` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lc_user
-- ----------------------------
INSERT INTO `lc_user` VALUES ('1616723100278231041', '派大星', 'admin@qq.com', '3c18471f5593fb109c8f1e702c742713', '8d32ede833c04ec58ba8', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQz3_FLGXahKII9UDgYPvo9irhGN_ORt9ej5Q&usqp=CAU', '只因派大星太美', NULL, '2023-01-21 17:03:19', '2023-01-21 17:03:19', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
