/*==============================================================================
**                                                                            **
**    Vormund Schema File                                                     **
**    Version 1.0                                                             **
**    Last Modified on 9/2/2010                                               **
**                                                                            **
**    Purdue University CS40800 Team 6                                        **
**    Members:                                                                **
**      Margarita Lee Li                                                      **
**      Seth Cook                                                             **
**      John Vall                                                             **
**      Mitchell Stendal                                                      **
**      Adam Rice                                                             **
**      Samuel Marshall                                                       **
**                                                                            **
==============================================================================*/

DROP TABLE IF EXISTS encrypted_data;
DROP TABLE IF EXISTS data_type;
DROP TABLE IF EXISTS user_data;

CREATE TABLE `user_data` (
  `user_id` INTEGER NOT NULL AUTOINCREMENT,
  `user_name` TEXT NOT NULL,
  `password` TEXT NOT NULL,
  PRIMARY KEY(`user_id`),
  UNIQUE(`user_name`)
);

CREATE TABLE `data_type` (
  `type_id` INTEGER NOT NULL AUTOINCREMENT,
  `type_name` TEXT NOT NULL,
  `type_value` TEXT NOT NULL DEFAULT "text",
  PRIMARY KEY(`type_id`),
  UNIQUE(`type_name`)
);

CREATE TABLE `encrypted_data` (
  `data_id` INTEGER NOT NULL AUTOINCREMENT,
  `category` TEXT NOT NULL,
  `type_id` INTEGER NOT NULL,
  `encrypted_data` BLOB NOT NULL,
  `note` TEXT NOT NULL,
  PRIMARY KEY(`data_id`),
  UNIQUE(`category`, `type_id`),
  FOREIGN KEY `type_id` REFERENCES `data_type`(`type_id`)
);

INSERT INTO `data_type`(`type_name`, `type_value`) VALUES('username', 'text');
INSERT INTO `data_type`(`type_name`, `type_value`) VALUES('password', 'text');
INSERT INTO `data_type`(`type_name`, `type_value`) VALUES('file', 'file');
INSERT INTO `data_type`(`type_name`, `type_value`) VALUES('routing_number', 'integer');
INSERT INTO `data_type`(`type_name`, `type_value`) VALUES('account_number', 'number');
