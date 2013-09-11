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
DROP TABLE IF EXISTS user_data;

CREATE TABLE user_data (
  user_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  user_name TEXT NOT NULL UNIQUE,
  password TEXT NOT NULL,
  name TEXT NOT NULL
);

CREATE TABLE encrypted_data (
  data_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER NOT NULL,
  category TEXT NOT NULL,
  encrypted_data BLOB NOT NULL,
  note TEXT,
  UNIQUE(user_id, category),
  FOREIGN KEY(user_id) REFERENCES user_data(user_id)
);
