USE `express`;
DROP TABLE IF EXISTS `express`;
CREATE TABLE `express` (
  `id`          BIGINT(20)   AUTO_INCREMENT
  COMMENT '主键',
  `user_id`     BIGINT(20)   DEFAULT NULL
  COMMENT '用户ID',
  `number`      VARCHAR(50)  DEFAULT NULL
  COMMENT '快递单号',
  `price`       INT(11)      DEFAULT 0
  COMMENT '价格',
  `status`      INT(11)      DEFAULT 0
  COMMENT '状态[0:未上交,1:已上交]',
  `mark`      INT(11)      DEFAULT 0
  COMMENT '状态[0:正常,1:异常]',
  `type`        VARCHAR(4)   DEFAULT NULL
  COMMENT '类型',
  `url`         VARCHAR(256) DEFAULT NULL
  COMMENT 'url',
  `desc`        VARCHAR(128) DEFAULT NULL
  COMMENT '备注',
  `create_time` TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time` TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`number`),
  KEY (`number`),
  KEY (`type`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '快递';
DROP TABLE IF EXISTS `income`;
CREATE TABLE `income` (
  `id`          BIGINT(20) AUTO_INCREMENT
  COMMENT '主键',
  `x`           INT(11)    DEFAULT 0
  COMMENT '现金',
  `w`           INT(11)    DEFAULT 0
  COMMENT '微信',
  `q`           INT(11)    DEFAULT 0
  COMMENT '欠款',
  `d`           INT(11)    DEFAULT 0
  COMMENT '代收',
  `y`           INT(11)    DEFAULT 0
  COMMENT '月结',
  `create_time` TIMESTAMP  DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time` TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '收入';
DROP TABLE IF EXISTS `remark`;
CREATE TABLE `remark` (
  `id`          BIGINT(20)   AUTO_INCREMENT
  COMMENT '主键',
  `text`        VARCHAR(150) DEFAULT NULL
  COMMENT '备注',
  `create_time` TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time` TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '备注';
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`          BIGINT(20)   AUTO_INCREMENT
  COMMENT '主键',
  `username`    VARCHAR(150) DEFAULT NULL
  COMMENT '用户名',
  `password`    VARCHAR(150) DEFAULT NULL
  COMMENT '密码',
  `create_time` TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time` TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '用户';