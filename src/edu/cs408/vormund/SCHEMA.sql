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
  password TEXT NOT NULL,
  name TEXT NOT NULL
); /** I am uncertain the values that we need to store, since we'll store only one row **/

CREATE TABLE data_type (
  type_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  type_name TEXT NOT NULL UNIQUE, /* SSN, Bank Number, Routing Number, File, Note, etc. */
  type_value TEXT NOT NULL DEFAULT "text" /* Or file, text, number, etc */
);

CREATE TABLE encrypted_data (
  data_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER NOT NULL,
  category TEXT NOT NULL,
  type_id INTEGER NOT NULL,
  encrypted_data BLOB NOT NULL,
  note TEXT,
  UNIQUE(user_id, category, type_id),
  FOREIGN KEY(user_id) REFERENCES user_data(user_id),
  FOREIGN KEY(type_id) REFERENCES data_type(type_id)
);

INSERT INTO data_type(type_name, type_value) VALUES('Username', 'text');
INSERT INTO data_type(type_name, type_value) VALUES('Password', 'text');
INSERT INTO data_type(type_name, type_value) VALUES('File', 'file');
INSERT INTO data_type(type_name, type_value) VALUES('Routing Number', 'integer');
INSERT INTO data_type(type_name, type_value) VALUES('Account Number', 'number');
