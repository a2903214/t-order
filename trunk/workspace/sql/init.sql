drop database torder;
create database torder ;
use torder;

CREATE TABLE `torder`.`t_gift_ticket` (
  `c_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `c_number` VARCHAR(300) NOT NULL,
  `c_passwd` VARCHAR(300) NOT NULL,
  `c_spec` VARCHAR(1024),
  PRIMARY KEY (`c_id`),
  INDEX `idx_gt_number`(`c_number`)
)
ENGINE = InnoDB;

CREATE TABLE `torder`.`t_customer` (
  `c_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(45) NOT NULL,
  `c_mobile` VARCHAR(45) NOT NULL,
  `c_addr` VARCHAR(1024) NOT NULL,
  PRIMARY KEY (`c_id`),
  INDEX `idx_cm_name`(`c_name`),
  INDEX `idx_cm_mobile`(`c_mobile`)
)
ENGINE = InnoDB;


CREATE TABLE `torder`.`t_order` (
  `c_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `c_custom_id` INTEGER UNSIGNED NOT NULL,
  `c_gt_id` INTEGER UNSIGNED NOT NULL,
  `c_delivery_time` DATETIME,
  `c_create_time` DATETIME,
  `c_status` INTEGER UNSIGNED NOT NULL,
  `c_desc` VARCHAR(1024),
  PRIMARY KEY (`c_id`),
  INDEX `idx_order_cid`(`c_custom_id`),
  INDEX `idx_order_gt_id`(`c_gt_id`),
  INDEX `idx_order_dtime`(`c_delivery_time`)
)
ENGINE = InnoDB;
