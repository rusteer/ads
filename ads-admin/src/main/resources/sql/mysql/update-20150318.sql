ALTER TABLE `bbpay`.`bbpay_feedback` DROP COLUMN `enabled` , DROP COLUMN `report_failure` , DROP COLUMN `report_success` , DROP COLUMN `report_block` , DROP COLUMN `timeout` , DROP COLUMN `expire_time` , DROP COLUMN `fee_id` , CHANGE COLUMN `block_port` `block_port` VARCHAR(30) NOT NULL  AFTER `id` , CHANGE COLUMN `block_content` `block_content` VARCHAR(30) NULL  AFTER `block_port` , CHANGE COLUMN `port` `reply_port` VARCHAR(30) NULL DEFAULT NULL  , CHANGE COLUMN `content` `reply_content` VARCHAR(30) NULL DEFAULT NULL  
, DROP INDEX `fee_id` ;


ALTER TABLE `bbpay`.`bbpay_block` DROP COLUMN `enabled` , DROP COLUMN `is_confirm` , DROP COLUMN `report_block` , DROP COLUMN `expire_time` , DROP COLUMN `fee_id` , CHANGE COLUMN `port` `block_port` VARCHAR(100) NOT NULL  , CHANGE COLUMN `content` `block_content` VARCHAR(100) NULL DEFAULT NULL  
, DROP INDEX `fee_id` ;


ALTER TABLE `bbpay`.`bbpay_feedback` CHANGE COLUMN `block_port` `block_port` VARCHAR(100) NOT NULL  , CHANGE COLUMN `block_content` `block_content` VARCHAR(100) NULL  , CHANGE COLUMN `reply_port` `reply_port` VARCHAR(100) NULL DEFAULT NULL  , CHANGE COLUMN `reply_content` `reply_content` VARCHAR(100) NULL DEFAULT NULL  ;


ALTER TABLE `bbpay`.`bbpay_biz` ADD COLUMN `block_ids` VARCHAR(100) NULL  AFTER `group_id` , ADD COLUMN `feedback_ids` VARCHAR(100) NULL  AFTER `block_ids` ;

ALTER TABLE `bbpay`.`bbpay_feedback` CHANGE COLUMN `type` `reply_type` INT(11) NULL DEFAULT '0'  ;

ALTER TABLE `bbpay`.`bbpay_biz` DROP COLUMN `feedback_ids` ;

drop table `bbpay`.`bbpay_block`;
ALTER TABLE `bbpay`.`bbpay_feedback` RENAME TO  `bbpay`.`bbpay_block` ;
