/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.7.12-log : Database - change_srms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`change_srms` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `change_srms`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `adminName` varchar(20) NOT NULL COMMENT '用户名',
  `adminNickname` varchar(12) NOT NULL COMMENT '管理员姓名',
  `adminPassword` varchar(20) NOT NULL COMMENT '密码',
  `role` varchar(20) NOT NULL COMMENT '角色，general普通管理员，super超级管理员',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `isDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '伪删除',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`adminName`,`adminNickname`,`adminPassword`,`role`,`version`,`isDelete`,`createTime`,`updateTime`) values (826528681663922176,'super','超级管理员','123456','super',1,0,'2021-03-30 18:49:53','2021-03-30 18:49:53');
insert  into `admin`(`id`,`adminName`,`adminNickname`,`adminPassword`,`role`,`version`,`isDelete`,`createTime`,`updateTime`) values (829033714808782848,'general','普通管理员测试','123456','general',1,0,'2021-04-06 16:44:00','2021-04-06 16:44:00');

/*Table structure for table `history` */

DROP TABLE IF EXISTS `history`;

CREATE TABLE `history` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `siteId` bigint(20) NOT NULL COMMENT '场地id',
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `reason` text NOT NULL COMMENT '借用场地的原因',
  `beginTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '预约借出时间',
  `endTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '预约归还时间',
  `generalAdminId` bigint(20) DEFAULT NULL COMMENT '普通管理员id',
  `superAdminId` bigint(20) DEFAULT NULL COMMENT '超级管理员id',
  `loanKeyId` bigint(20) DEFAULT NULL COMMENT '借出钥匙的管理员id',
  `returnKeyId` bigint(20) DEFAULT NULL COMMENT '归还钥匙的管理员id',
  `loanState` int(11) NOT NULL DEFAULT '0' COMMENT '审核状态，0未审核，1审核中，2审核通过，3审核不通过',
  `keyState` int(11) NOT NULL DEFAULT '0' COMMENT '钥匙状态，0未借出，1已经借出，2已经归还，3不需要钥匙',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `isDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '伪删除',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `site` */

DROP TABLE IF EXISTS `site`;

CREATE TABLE `site` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `siteName` varchar(20) NOT NULL COMMENT '场地名称',
  `location` text COMMENT '场地的具体位置',
  `seat` int(11) NOT NULL COMMENT '场地能容纳的人员数量',
  `hasKeys` tinyint(1) NOT NULL COMMENT '是否有钥匙',
  `isLent` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否正在被预约借用',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `isDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '伪删除',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `userName` varchar(20) NOT NULL COMMENT '用户名',
  `userNickname` varchar(12) NOT NULL COMMENT '用户姓名',
  `userPassword` varchar(20) NOT NULL COMMENT '密码',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `isDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '伪删除',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`userName`,`userNickname`,`userPassword`,`version`,`isDelete`,`createTime`,`updateTime`) values (822161231610642432,'test@test.com','用户测试','123456',1,0,'2021-03-18 17:35:12','2021-03-18 17:35:12');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
