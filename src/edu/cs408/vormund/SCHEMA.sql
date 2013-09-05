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

CREATE TABLE user_data (
  user_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  user_name TEXT NOT NULL UNIQUE,
  name TEXT NOT NULL
); /** I am uncertain the values that we need to store, since we'll store only one row **/

CREATE TABLE data_type (
  type_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  type_name TEXT NOT NULL UNIQUE,
  type_value TEXT NOT NULL DEFAULT "text"
);

CREATE TABLE encrypted_data (
  data_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  category TEXT NOT NULL,
  type_id INTEGER NOT NULL,
  encrypted_data BLOB NOT NULL,
  note TEXT NOT NULL,
  UNIQUE(category, type_id),
  FOREIGN KEY(type_id) REFERENCES data_type(type_id)
);

INSERT INTO data_type(type_name, type_value) VALUES('username', 'text');
INSERT INTO data_type(type_name, type_value) VALUES('password', 'text');
INSERT INTO data_type(type_name, type_value) VALUES('file', 'file');
INSERT INTO data_type(type_name, type_value) VALUES('routing_number', 'integer');
INSERT INTO data_type(type_name, type_value) VALUES('account_number', 'number');
