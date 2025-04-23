/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : studyroom

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 21/04/2025 20:36:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel`  (
  `id` int NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `weight` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权重',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of carousel
-- ----------------------------

-- ----------------------------
-- Table structure for kehu
-- ----------------------------
DROP TABLE IF EXISTS `kehu`;
CREATE TABLE `kehu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sessionkey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `MBTI` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of kehu
-- ----------------------------
INSERT INTO `kehu` VALUES (1, '1', '1', '1', NULL, NULL, 'https://wx.qlogo.cn/mmhead/PVhOGPh6lDfYQDzJfyuWZe2qSJvju3AbME2y3DWKYRo/0', '2025-01-02 12:29:18', 'INFJ', '0', 133.00);
INSERT INTO `kehu` VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, '2025-01-02 12:29:14', NULL, '0', NULL);
INSERT INTO `kehu` VALUES (3, NULL, NULL, NULL, 'o7fUB5oxaNcZ1ZAhxUnchelyGBU4', 'ddOeTuR0g/0xV/XgYkOxaQ==', NULL, '2025-04-21 15:44:48', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for logbill
-- ----------------------------
DROP TABLE IF EXISTS `logbill`;
CREATE TABLE `logbill`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `kehuid` int NULL DEFAULT NULL,
  `kehuname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `roomid` int NULL DEFAULT NULL,
  `nick` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `roomfloor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `infotime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `seatsrow` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `seatcolunm` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `start_time` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `end_time` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `amount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of logbill
-- ----------------------------
INSERT INTO `logbill` VALUES (1, 1, '张三', 1, '501', '5', '自动退订', '2024-12-24 07:57:54', '1', '1', '16:00:00', '18:00:00', NULL);
INSERT INTO `logbill` VALUES (2, 1, '52', 2, '2', '2', '进行中', '2024-12-13 16:30:35', '2', '2', NULL, NULL, NULL);
INSERT INTO `logbill` VALUES (3, 1, '王五', 3, '211', '2', '已完成', '2024-12-13 16:30:42', '2', '2', NULL, NULL, NULL);
INSERT INTO `logbill` VALUES (4, 1, '1', 1, '101', '1', '结束', '2024-12-24 07:56:38', '1', '5', '16:00', '18:00', NULL);
INSERT INTO `logbill` VALUES (5, 1, '1', 1, '101', '1', '自动退订', '2024-12-24 07:54:49', '1', '1', '08:00', '10:00', NULL);
INSERT INTO `logbill` VALUES (6, 1, '1', 2, '201', '2', '未开始', '2024-12-24 08:22:05', '1', '2', '10:00', '12:00', '0');
INSERT INTO `logbill` VALUES (15, 1, '1', 3, '102', '1', '未开始', '2025-01-02 13:40:54', '4', '2', '14:00', '16:00', '0');
INSERT INTO `logbill` VALUES (16, 1, '1', 3, '102', '1', '手动退订', '2025-03-26 10:05:00', '1', '3', '12:00', '14:00', '0');
INSERT INTO `logbill` VALUES (17, 1, '1', 3, '102', '1', '未开始', '2025-03-26 10:48:41', '3', '3', '14:00', '16:00', '0');

-- ----------------------------
-- Table structure for loglogin
-- ----------------------------
DROP TABLE IF EXISTS `loglogin`;
CREATE TABLE `loglogin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `thistime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `node` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `IP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of loglogin
-- ----------------------------
INSERT INTO `loglogin` VALUES (1, '2024-12-15 16:45:11', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (2, '2024-12-15 18:11:02', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (3, '2024-12-15 18:17:23', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (4, '2024-12-16 10:18:27', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (5, '2024-12-16 10:22:47', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (6, '2024-12-16 10:23:38', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (7, '2024-12-16 10:25:10', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (8, '2024-12-16 10:26:00', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (9, '2024-12-16 10:29:04', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (10, '2024-12-16 10:30:55', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (11, '2024-12-16 10:34:35', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (12, '2024-12-16 10:34:55', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (13, '2024-12-16 10:36:28', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (14, '2024-12-16 10:37:47', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (15, '2024-12-24 07:34:12', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (16, '2024-12-24 07:40:31', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (17, '2024-12-24 07:49:39', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (18, '2024-12-24 07:50:45', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (19, '2024-12-24 07:51:46', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (20, '2024-12-24 07:52:29', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (21, '2024-12-24 07:53:18', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (22, '2024-12-24 07:54:43', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (23, '2024-12-24 07:54:54', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (24, '2024-12-24 07:55:16', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (25, '2024-12-24 07:57:46', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (26, '2024-12-24 07:58:17', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (27, '2024-12-24 08:19:18', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (28, '2024-12-24 08:21:54', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (29, '2025-01-02 12:01:06', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (30, '2025-01-02 12:02:12', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (31, '2025-01-02 12:10:18', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (32, '2025-01-02 12:30:00', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (33, '2025-01-02 12:30:38', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (34, '2025-01-02 12:43:36', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (35, '2025-01-02 12:44:02', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (36, '2025-01-02 12:45:06', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (37, '2025-01-02 12:53:23', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (38, '2025-01-02 12:56:34', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (39, '2025-01-02 12:57:12', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (40, '2025-01-02 12:59:48', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (41, '2025-01-02 13:10:15', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (42, '2025-01-02 13:10:22', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (43, '2025-01-02 13:12:04', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (44, '2025-01-02 13:13:17', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (45, '2025-01-02 13:15:56', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (46, '2025-01-02 13:17:33', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (47, '2025-01-02 13:26:18', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (48, '2025-01-02 13:35:37', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (49, '2025-01-02 13:39:02', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (50, '2025-03-26 10:03:02', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (51, '2025-03-26 10:04:44', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (52, '2025-03-26 10:09:45', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (53, '2025-03-26 10:32:51', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (54, '2025-03-26 10:48:20', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');
INSERT INTO `loglogin` VALUES (55, '2025-03-26 10:49:54', '1', 'SUCCESS', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for newbill
-- ----------------------------
DROP TABLE IF EXISTS `newbill`;
CREATE TABLE `newbill`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `time_id` int NULL DEFAULT NULL,
  `room_id` int NULL DEFAULT NULL,
  `kehu_id` int NULL DEFAULT NULL,
  `seats_id` int NULL DEFAULT NULL,
  `star` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `end` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `states` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `init_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `time` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of newbill
-- ----------------------------
INSERT INTO `newbill` VALUES (1, 1, 1, 1, 1, '8:00', '10:00', 'occupied', '2024-12-15 15:35:53', '2024-12-15');
INSERT INTO `newbill` VALUES (2, 1, 1, 1, 2, '8:00', '10:00', 'occupied', '2024-12-15 15:55:40', '2024-12-15');
INSERT INTO `newbill` VALUES (3, 2, 1, 1, 1, '10:00', '12:00', 'occupied', '2024-12-15 15:56:01', '2024-12-15');
INSERT INTO `newbill` VALUES (4, 1, 2, 1, 11, '8:00', '10:00', NULL, '2024-12-15 15:56:32', '2024-12-15');
INSERT INTO `newbill` VALUES (5, 5, 1, 1, 5, '16:00', '18:00', 'occupied', '2024-12-15 18:11:17', '2024-12-15');
INSERT INTO `newbill` VALUES (6, 1, 1, 1, 1, '8:00', '10:00', 'occupied', '2024-12-16 10:34:49', '2024-12-16');
INSERT INTO `newbill` VALUES (7, 2, 2, 1, 12, '10:00', '12:00', 'occupied', '2024-12-24 08:22:05', '2024-12-24');
INSERT INTO `newbill` VALUES (12, 4, 3, 1, 58, '14:00', '16:00', 'occupied', '2025-01-02 13:40:54', '2025-01-02');
INSERT INTO `newbill` VALUES (13, 3, 3, 1, 33, '12:00', '14:00', 'occupied', '2025-03-26 10:05:00', '2025-03-26');
INSERT INTO `newbill` VALUES (14, 4, 3, 1, 50, '14:00', '16:00', 'occupied', '2025-03-26 10:48:40', '2025-03-26');

-- ----------------------------
-- Table structure for root
-- ----------------------------
DROP TABLE IF EXISTS `root`;
CREATE TABLE `root`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `infotime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of root
-- ----------------------------
INSERT INTO `root` VALUES (1, 'root', 'root', '2024-12-16 11:09:41');

-- ----------------------------
-- Table structure for seats
-- ----------------------------
DROP TABLE IF EXISTS `seats`;
CREATE TABLE `seats`  (
  `seat_id` int NOT NULL AUTO_INCREMENT,
  `room_id` int NULL DEFAULT NULL,
  `Rrow` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '行',
  `Ccolumn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列',
  `is_available` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '表示座位是否可用，1 表示可用，0 表示已被预约',
  `number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` int NULL DEFAULT 0,
  PRIMARY KEY (`seat_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seats
-- ----------------------------
INSERT INTO `seats` VALUES (1, 1, '1', '1', 'available', '1', 100);
INSERT INTO `seats` VALUES (2, 1, '1', '2', 'available', '2', 10);
INSERT INTO `seats` VALUES (3, 1, '1', '3', 'available', '3', 0);
INSERT INTO `seats` VALUES (4, 1, '1', '4', 'available', '4', 0);
INSERT INTO `seats` VALUES (5, 1, '1', '5', 'available', '5', 0);
INSERT INTO `seats` VALUES (6, 1, '1', '6', 'available', '6', 0);
INSERT INTO `seats` VALUES (7, 1, '1', '7', 'available', '7', 0);
INSERT INTO `seats` VALUES (8, 1, '1', '8', 'available', '8', 0);
INSERT INTO `seats` VALUES (9, 1, '2', '1', 'available', '9', 0);
INSERT INTO `seats` VALUES (10, 1, '2', '2', 'available', '10', 0);
INSERT INTO `seats` VALUES (11, 2, '1', '1', 'available', '1', 0);
INSERT INTO `seats` VALUES (12, 2, '1', '2', 'available', '2', 0);
INSERT INTO `seats` VALUES (13, 2, '1', '3', 'available', '3', 0);
INSERT INTO `seats` VALUES (14, 2, '1', '4', 'available', '4', 0);
INSERT INTO `seats` VALUES (15, 2, '1', '5', 'available', '5', 0);
INSERT INTO `seats` VALUES (16, 2, '1', '6', 'available', '6', 0);
INSERT INTO `seats` VALUES (17, 2, '1', '7', 'available', '7', 0);
INSERT INTO `seats` VALUES (18, 2, '1', '8', 'available', '8', 0);
INSERT INTO `seats` VALUES (19, 2, '2', '1', 'available', '9', 0);
INSERT INTO `seats` VALUES (20, 2, '2', '2', 'available', '10', 0);
INSERT INTO `seats` VALUES (21, 2, '2', '3', 'available', '11', 0);
INSERT INTO `seats` VALUES (22, 2, '2', '4', 'available', '12', 0);
INSERT INTO `seats` VALUES (23, 2, '2', '5', 'available', '13', 0);
INSERT INTO `seats` VALUES (24, 2, '2', '6', 'available', '14', 0);
INSERT INTO `seats` VALUES (25, 2, '2', '7', 'available', '15', 0);
INSERT INTO `seats` VALUES (26, 2, '2', '8', 'available', '16', 0);
INSERT INTO `seats` VALUES (27, 2, '2', '9', 'available', '17', 0);
INSERT INTO `seats` VALUES (28, 2, '3', '1', 'available', '18', 0);
INSERT INTO `seats` VALUES (29, 2, '3', '2', 'available', '19', 0);
INSERT INTO `seats` VALUES (30, 2, '3', '3', 'available', '20', 0);
INSERT INTO `seats` VALUES (31, 3, '1', '1', 'available', '1', 0);
INSERT INTO `seats` VALUES (32, 3, '1', '2', 'available', '2', 0);
INSERT INTO `seats` VALUES (33, 3, '1', '3', 'available', '3', 0);
INSERT INTO `seats` VALUES (34, 3, '1', '4', 'available', '4', 0);
INSERT INTO `seats` VALUES (35, 3, '1', '5', 'available', '5', 0);
INSERT INTO `seats` VALUES (36, 3, '1', '6', 'available', '6', 0);
INSERT INTO `seats` VALUES (37, 3, '1', '7', 'available', '7', 0);
INSERT INTO `seats` VALUES (38, 3, '1', '8', 'available', '8', 0);
INSERT INTO `seats` VALUES (39, 3, '2', '1', 'available', '9', 0);
INSERT INTO `seats` VALUES (40, 3, '2', '2', 'available', '10', 0);
INSERT INTO `seats` VALUES (41, 3, '2', '3', 'available', '11', 0);
INSERT INTO `seats` VALUES (42, 3, '2', '4', 'available', '12', 0);
INSERT INTO `seats` VALUES (43, 3, '2', '5', 'available', '13', 0);
INSERT INTO `seats` VALUES (44, 3, '2', '6', 'available', '14', 0);
INSERT INTO `seats` VALUES (45, 3, '2', '7', 'available', '15', 0);
INSERT INTO `seats` VALUES (46, 3, '2', '8', 'available', '16', 0);
INSERT INTO `seats` VALUES (47, 3, '2', '9', 'available', '17', 0);
INSERT INTO `seats` VALUES (48, 3, '3', '1', 'available', '18', 0);
INSERT INTO `seats` VALUES (49, 3, '3', '2', 'available', '19', 0);
INSERT INTO `seats` VALUES (50, 3, '3', '3', 'available', '20', 0);
INSERT INTO `seats` VALUES (51, 3, '3', '4', 'available', '21', 0);
INSERT INTO `seats` VALUES (52, 3, '3', '5', 'available', '22', 0);
INSERT INTO `seats` VALUES (53, 3, '3', '6', 'available', '23', 0);
INSERT INTO `seats` VALUES (54, 3, '3', '7', 'available', '24', 0);
INSERT INTO `seats` VALUES (55, 3, '3', '8', 'available', '25', 0);
INSERT INTO `seats` VALUES (56, 3, '3', '9', 'available', '26', 0);
INSERT INTO `seats` VALUES (57, 3, '4', '1', 'available', '27', 0);
INSERT INTO `seats` VALUES (58, 3, '4', '2', 'available', '28', 0);
INSERT INTO `seats` VALUES (59, 3, '4', '3', 'available', '29', 0);
INSERT INTO `seats` VALUES (60, 3, '4', '4', 'available', '30', 0);
INSERT INTO `seats` VALUES (61, 4, '1', '1', 'available', '1', 0);
INSERT INTO `seats` VALUES (62, 4, '1', '2', 'available', '2', 0);
INSERT INTO `seats` VALUES (63, 4, '1', '3', 'available', '3', 0);
INSERT INTO `seats` VALUES (64, 4, '1', '4', 'available', '4', 0);
INSERT INTO `seats` VALUES (65, 4, '1', '5', 'available', '5', 0);
INSERT INTO `seats` VALUES (66, 4, '1', '6', 'available', '6', 0);
INSERT INTO `seats` VALUES (67, 4, '1', '7', 'available', '7', 0);
INSERT INTO `seats` VALUES (68, 4, '1', '8', 'available', '8', 0);
INSERT INTO `seats` VALUES (69, 4, '2', '1', 'available', '9', 0);
INSERT INTO `seats` VALUES (70, 4, '2', '2', 'available', '10', 0);
INSERT INTO `seats` VALUES (71, 4, '2', '3', 'available', '11', 0);
INSERT INTO `seats` VALUES (72, 4, '2', '4', 'available', '12', 0);
INSERT INTO `seats` VALUES (73, 4, '2', '5', 'available', '13', 0);
INSERT INTO `seats` VALUES (74, 4, '2', '6', 'available', '14', 0);
INSERT INTO `seats` VALUES (75, 4, '2', '7', 'available', '15', 0);
INSERT INTO `seats` VALUES (76, 4, '2', '8', 'available', '16', 0);
INSERT INTO `seats` VALUES (77, 4, '2', '9', 'available', '17', 0);
INSERT INTO `seats` VALUES (78, 4, '3', '1', 'available', '18', 0);
INSERT INTO `seats` VALUES (79, 4, '3', '2', 'available', '19', 0);
INSERT INTO `seats` VALUES (80, 4, '3', '3', 'available', '20', 0);

-- ----------------------------
-- Table structure for studyrooms
-- ----------------------------
DROP TABLE IF EXISTS `studyrooms`;
CREATE TABLE `studyrooms`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `room_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自习室所在位置，例如楼层、具体方位等信息',
  `root_floor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '例如楼层',
  `total_seats` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '人数',
  `root_time` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '开放时段段',
  `states` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '状态 0开放 1结束',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of studyrooms
-- ----------------------------
INSERT INTO `studyrooms` VALUES (1, '3', '101', '1', '11', '', '0');
INSERT INTO `studyrooms` VALUES (2, '21', '201', '2', '20', NULL, '0');
INSERT INTO `studyrooms` VALUES (3, '3', '102', '1', '30', NULL, '0');
INSERT INTO `studyrooms` VALUES (4, '4', '202', '2', '20', NULL, '0');
INSERT INTO `studyrooms` VALUES (5, '1', '105', '1', '10', NULL, '1');

-- ----------------------------
-- Table structure for timetype
-- ----------------------------
DROP TABLE IF EXISTS `timetype`;
CREATE TABLE `timetype`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `start` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `end` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `typename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of timetype
-- ----------------------------
INSERT INTO `timetype` VALUES (1, '08:00', '10:00', NULL);
INSERT INTO `timetype` VALUES (2, '10:00', '12:00', NULL);
INSERT INTO `timetype` VALUES (3, '12:00', '14:00', NULL);
INSERT INTO `timetype` VALUES (4, '14:00', '16:00', NULL);
INSERT INTO `timetype` VALUES (5, '16:00', '18:00', NULL);
INSERT INTO `timetype` VALUES (6, '18:00', '20:00', NULL);
INSERT INTO `timetype` VALUES (7, '20:00', '22:00', NULL);
INSERT INTO `timetype` VALUES (8, '22:00', '24:00', NULL);

-- ----------------------------
-- Table structure for wallet
-- ----------------------------
DROP TABLE IF EXISTS `wallet`;
CREATE TABLE `wallet`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `node` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `thistime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wallet
-- ----------------------------
INSERT INTO `wallet` VALUES (1, 1, 1.00, '+', '2025-01-02 13:13:09', '充值');
INSERT INTO `wallet` VALUES (2, 1, 2.00, '+', '2025-01-02 13:13:12', '充值');
INSERT INTO `wallet` VALUES (3, 1, 0.00, '-', '2025-01-02 13:40:54', '消费');
INSERT INTO `wallet` VALUES (4, 1, 0.00, '-', '2025-03-26 10:05:00', '消费');
INSERT INTO `wallet` VALUES (5, 1, 20.00, '-', '2025-03-26 10:05:18', '充值');
INSERT INTO `wallet` VALUES (6, 1, 0.00, '-', '2025-03-26 10:48:41', '消费');
INSERT INTO `wallet` VALUES (7, 1, 10.00, '-', '2025-03-26 10:48:59', '充值');

-- ----------------------------
-- Table structure for warranty
-- ----------------------------
DROP TABLE IF EXISTS `warranty`;
CREATE TABLE `warranty`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `roomid` int NULL DEFAULT NULL,
  `seatsid` int NULL DEFAULT NULL,
  `node` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `thistime` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `roomlocation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `seatnumber` int NULL DEFAULT NULL,
  `record` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warranty
-- ----------------------------
INSERT INTO `warranty` VALUES (1, 1, 1, '1', '2024-12-23 20:31:45', '完成', '11', 11, '11');
INSERT INTO `warranty` VALUES (2, 3, NULL, '321', NULL, '待处理', 'undefined+undefined', NULL, NULL);
INSERT INTO `warranty` VALUES (3, 3, NULL, '231', NULL, '待处理', '1+3', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
