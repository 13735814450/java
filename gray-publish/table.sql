CREATE DATABASE gray;
use gray;

CREATE TABLE `gray_service` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务类型',
  `ip` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务ip',
  `clientIp` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端ip，用分号分割',
  `port` int(10) NOT NULL COMMENT '端口',
  PRIMARY KEY (`id`),
  UNIQUE KEY `s_unique` (`type`,`ip`,`port`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


