/*
SQLyog Ultimate v8.55 
MySQL - 5.1.73 : Database - djTalk
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`djTalk` /*!40100 DEFAULT CHARACTER SET euckr */;

USE `djTalk`;

/*Table structure for table `tblAdmin` */

DROP TABLE IF EXISTS `tblAdmin`;

CREATE TABLE `tblAdmin` (
  `no` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PrimaryKey',
  `userId` varchar(128) NOT NULL COMMENT 'user Account',
  `userPw` varchar(128) NOT NULL COMMENT 'user Account Password',
  `name` varchar(128) NOT NULL COMMENT 'user Name',
  `phone` varchar(32) NOT NULL COMMENT 'user Phone number',
  `email` varchar(32) NOT NULL COMMENT 'user email Account',
  `birth` datetime NOT NULL COMMENT 'birth date',
  `regDate` datetime NOT NULL COMMENT 'Registration date',
  PRIMARY KEY (`no`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

/*Table structure for table `tblChatHistory` */

DROP TABLE IF EXISTS `tblChatHistory`;

CREATE TABLE `tblChatHistory` (
  `no` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `roomId` varchar(512) NOT NULL,
  `data` varchar(4096) NOT NULL,
  `mtime` varchar(128) NOT NULL,
  `regDate` datetime NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=MyISAM AUTO_INCREMENT=744 DEFAULT CHARSET=euckr;

/*Table structure for table `tblChatMember` */

DROP TABLE IF EXISTS `tblChatMember`;

CREATE TABLE `tblChatMember` (
  `device_f` varchar(128) NOT NULL COMMENT 'device ForeignKey',
  `room_f` int(11) NOT NULL COMMENT 'room ForeignKey',
  `regDate` datetime NOT NULL COMMENT 'entrance date',
  PRIMARY KEY (`device_f`,`room_f`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

/*Table structure for table `tblChatRoom` */

DROP TABLE IF EXISTS `tblChatRoom`;

CREATE TABLE `tblChatRoom` (
  `no` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PrimaryKey',
  `desc` varchar(128) NOT NULL COMMENT 'Chatting Room Title Or Description',
  `roomId` varchar(128) NOT NULL COMMENT 'Solution Room ID',
  `type` varchar(4) NOT NULL COMMENT 'A type of room - CO : Counsellings / DP : Departments / DS : Department Sections / CL : Classes',
  `pref_dept` varchar(10) DEFAULT NULL COMMENT 'Preference for type DP - dept. number',
  `pref_section` varchar(10) DEFAULT NULL COMMENT 'Preference for type DS - dept. section number',
  `pref_class` varchar(10) DEFAULT NULL COMMENT 'Preference for type CL - class number',
  `pref_daynight` varchar(10) DEFAULT NULL COMMENT 'Preference for type DS - / 주 : 주간, 야 : 야간',
  `pref_grade` tinyint(4) DEFAULT NULL COMMENT 'Preference for type DS - grade',
  `profile_f` int(11) DEFAULT NULL COMMENT 'Profile Image Path (Optional)',
  `announce_f` int(11) DEFAULT NULL COMMENT 'Announced Message ForeignKey from tblChat(Optional)',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'A status of room - 1 : Active / 2 : Disabled / 3 : Restricted',
  `regDate` datetime NOT NULL COMMENT 'registration date',
  PRIMARY KEY (`no`)
) ENGINE=MyISAM AUTO_INCREMENT=6401 DEFAULT CHARSET=euckr;

/*Table structure for table `tblDevice` */

DROP TABLE IF EXISTS `tblDevice`;

CREATE TABLE `tblDevice` (
  `deviceId` varchar(128) NOT NULL COMMENT 'Unique ID of each device',
  `deviceType` tinyint(4) NOT NULL COMMENT 'A type of device - 1 : Android / 2 : iOS',
  `allowPush` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'A variable for user to be sent push - 1 : Allowed / 2 : Not Allowed',
  `regKey` varchar(512) DEFAULT NULL COMMENT 'GCM Token serial',
  `prevRegKey` varchar(512) DEFAULT NULL COMMENT 'Previous GCM Token serial - For force logout of previous device',
  `vibration` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 : Always / 1 : Only mannerMode / 2 : Off',
  `notifyWithSound` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 : On / 1 : Off',
  `notificationSound` varchar(128) DEFAULT 'default' COMMENT 'No rules',
  `noDisturb` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'A controlling variable for user to not be disturbed - 1 : off / 2 : on',
  `disturbStart` time NOT NULL DEFAULT '00:00:00' COMMENT 'A start time of no disturb mode',
  `disturbEnd` time NOT NULL DEFAULT '06:00:00' COMMENT 'A end time of no disturb mode',
  `profile_f` int(11) DEFAULT NULL COMMENT 'Profile Image ForeignKey',
  `nickname` varchar(128) DEFAULT NULL COMMENT 'Nickname of user',
  `no` varchar(128) NOT NULL COMMENT 'Primary Key and Member foreign key of View table',
  `update` datetime DEFAULT NULL COMMENT 'updated date',
  `appVersion` varchar(32) DEFAULT NULL COMMENT 'A version name of mobile app',
  `lastLogin` datetime DEFAULT NULL COMMENT 'last login time',
  `lastIp` varchar(16) DEFAULT NULL COMMENT 'Last Logged IP Address',
  `regDate` datetime DEFAULT NULL COMMENT 'registration date',
  PRIMARY KEY (`no`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

/*Table structure for table `tblMultimedia` */

DROP TABLE IF EXISTS `tblMultimedia`;

CREATE TABLE `tblMultimedia` (
  `no` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PrimaryKey',
  `device_f` varchar(128) DEFAULT NULL COMMENT 'Device ForeignKey',
  `room_f` varchar(512) DEFAULT NULL COMMENT 'Solution Room ID',
  `mediaPath` varchar(512) DEFAULT NULL COMMENT 'Multimedia Physical Path',
  `originalName` varchar(128) DEFAULT NULL COMMENT 'Multimedia Original Name',
  `extension` varchar(64) DEFAULT NULL COMMENT 'Multimedia Extension Name',
  `size` int(11) DEFAULT NULL COMMENT 'A size of multimedia file',
  `type` varchar(4) DEFAULT NULL COMMENT 'A type of multimedia file - IM : Image&Video / LO : Location / VR : Voice Record',
  `hide` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Flag for displaying image - 0 : Displayed / 1 : Restricted',
  `longitude` double DEFAULT NULL COMMENT 'For location type - longitude',
  `latitude` double DEFAULT NULL COMMENT 'For location type - latitude',
  `regDate` datetime NOT NULL COMMENT 'registration Date',
  PRIMARY KEY (`no`)
) ENGINE=MyISAM AUTO_INCREMENT=345 DEFAULT CHARSET=euckr;

/*Table structure for table `tblPublic` */

DROP TABLE IF EXISTS `tblPublic`;

CREATE TABLE `tblPublic` (
  `no` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PrimaryKey',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'A Message Type / 0 : 일반, 1 : 알림, 2 : 공지',
  `multi_f` int(11) DEFAULT NULL COMMENT 'A multimedia number ForeignKey',
  `title` varchar(512) DEFAULT NULL COMMENT 'A title of message',
  `message` varchar(2048) DEFAULT NULL COMMENT 'A message contents',
  `filterType` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Message Filter Selector - 0 : No filter / 1 : Only Assistant(Filtered with class_f) / 2 : Only Professor(class_f) / 3 : Assistant and Professor(class_f) / 4 : Only Class Member including Professor and assistant(class_f) / 5 : Private(filter_f)',
  `filter_f` varchar(128) DEFAULT NULL COMMENT 'A filter for private user - be used for private Message like birthday cellebration',
  `class_f` varchar(128) DEFAULT NULL COMMENT 'A filter as class identifier',
  `hyperlink` varchar(512) DEFAULT NULL COMMENT 'An internal hyperlink of a message',
  `mType` int(11) NOT NULL COMMENT 'Message Type',
  `regDate` datetime NOT NULL COMMENT 'registration date',
  PRIMARY KEY (`no`)
) ENGINE=MyISAM AUTO_INCREMENT=49 DEFAULT CHARSET=euckr;

/*Table structure for table `temp` */

DROP TABLE IF EXISTS `temp`;

CREATE TABLE `temp` (
  `memberId` varchar(128) NOT NULL,
  `userAccount` varchar(128) NOT NULL,
  `userPassword` varchar(128) NOT NULL,
  `grade` tinyint(4) NOT NULL,
  `department` varchar(10) NOT NULL,
  `class` varchar(10) NOT NULL,
  `daynight` varchar(10) NOT NULL,
  `phone` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `access` varchar(4) NOT NULL COMMENT '교/ 직/ 학/ 조',
  PRIMARY KEY (`memberId`)
) ENGINE=MyISAM AUTO_INCREMENT=258 DEFAULT CHARSET=euckr;

/*Table structure for table `tempClass` */

DROP TABLE IF EXISTS `tempClass`;

CREATE TABLE `tempClass` (
  `classcode` varchar(10) NOT NULL,
  `className` varchar(128) NOT NULL,
  `classEngName` varchar(128) NOT NULL,
  PRIMARY KEY (`classcode`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=euckr;

/*Table structure for table `tempDept` */

DROP TABLE IF EXISTS `tempDept`;

CREATE TABLE `tempDept` (
  `no` varchar(10) NOT NULL,
  `hangleName` varchar(128) NOT NULL,
  `engName` varchar(128) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=euckr;

/*Table structure for table `tempTaking` */

DROP TABLE IF EXISTS `tempTaking`;

CREATE TABLE `tempTaking` (
  `device_f` varchar(128) NOT NULL,
  `class_f` varchar(128) NOT NULL,
  PRIMARY KEY (`device_f`,`class_f`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
