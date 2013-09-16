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
  password TEXT NOT NULL
);

CREATE TABLE encrypted_data (
  data_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER NOT NULL,
  category TEXT NOT NULL,
  name TEXT NOT NULL,
  encrypted_data BLOB NOT NULL,
  UNIQUE(user_id, category, name),
  FOREIGN KEY(user_id) REFERENCES user_data(user_id)
);

/* INPUT DATA FOR TESTING PURPOSES */
/* Password: test */
INSERT INTO user_data(user_name, password) VALUES ('testie', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08');
INSERT INTO user_data(user_name, password) VALUES ('tester', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08');

INSERT INTO encrypted_data(user_id, category, name, encrypted_data) VALUES (1, 'Bank Account', 'Purdue Federal Credit Union:Checking', 'VK9vb3AjYWiNGZtJa6ePpRLT6SR8+XedR+Uff0vqQDC2GSMPC3KPUJjTI9PmaXu0jfrCdiAKiNWR82JM47xjkUirSlnP4eHVEQkNiT+Mrdk=');
INSERT INTO encrypted_data(user_id, category, name, encrypted_data) VALUES (1, 'Bank Account', 'Teachers Credit Union', 'bgAXGazNPIxFEG9FrrEZ3BBqllAD3n26bpMnLntFQJdni4s1HgMHo9yiAc1k2KCYzxFYtdwYz37HnBwHeYsC8cvbboQhc+cVYxAMyL2O5fA=');
INSERT INTO encrypted_data(user_id, category, name, encrypted_data) VALUES (1, 'Bank Account', 'Purdue Federal Credit Union:Savings','bgAXGazNPIxFEG9FrrEZ3BBqllAD3n26bpMnLntFQJdni4s1HgMHo9yiAc1k2KCYzxFYtdwYz37HnBwHeYsC8cvbboQhc+cVYxAMyL2O5fA=');
/*INSERT INTO encrypted_data(user_id, category, name, encrypted_data) VALUES (1, 'Website', 'Facebook', '');
INSERT INTO encrypted_data(user_id, category, name, encrypted_data) VALUES (1, 'Website', 'Google', '');
INSERT INTO encrypted_data(user_id, category, name, encrypted_data) VALUES (1, 'Website', 'Reddit', '');
INSERT INTO encrypted_data(user_id, category, name, encrypted_data) VALUES (1, 'Website', 'Purdue Career', '');*/
