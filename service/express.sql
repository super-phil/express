CREATE TABLE `express` (
  `id`          BIGINT(20)                 AUTO_INCREMENT
  COMMENT '主键',
  `user_id`     BIGINT(20)                 DEFAULT NULL
  COMMENT '用户ID',
  `number`      VARCHAR(50)
                COLLATE utf8mb4_general_ci DEFAULT NULL
  COMMENT '快递单号',
  `price`       INT(11)                    DEFAULT 0
  COMMENT '价格',
  `type`        VARCHAR(4)
                COLLATE utf8mb4_general_ci DEFAULT NULL
  COMMENT '类型',
  `url`         VARCHAR(256)
                COLLATE utf8mb4_general_ci DEFAULT NULL
  COMMENT 'url',
  `desc`        VARCHAR(128)
                COLLATE utf8mb4_general_ci DEFAULT NULL
  COMMENT '备注',
  `create_time` TIMESTAMP                  DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time` TIMESTAMP                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`number`),
  KEY (`number`),
  KEY (`type`)
)
  ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '快递';