# yoshua-otp

# DDL
-- yoshua_sqe.otp definition

CREATE TABLE `otp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `otp_code` varchar(6) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `updator` varchar(255) DEFAULT NULL,
  `expiry_time` bigint(20) DEFAULT NULL,
  `otp_status` tinyint(4) DEFAULT NULL COMMENT '1-CREATED, 2-VALIDATED, 3-EXPIRED',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;