/*CREATE DATABASE springbootdb;*/

/*CREATE DATABASE springbootdb_second;*/

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(25) DEFAULT NULL COMMENT '用户名称',
  `description` varchar(25) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ;

CREATE TABLE `school` (
  `id` bigint(20) NOT NULL,
  `school_name` varchar(64) DEFAULT NULL COMMENT '学校名',
  `school_describe` varchar(128) DEFAULT NULL COMMENT '学校描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into user ( `user_name`, `description`) values ( 'shuai', 'so handsome');

insert into school ( `id`, `school_name`, `school_describe`) values ( '1', '清华', '自强不息，厚德载物');