package edu.cs408.vormund;

import java.sql.*;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;

public class DBHelpers {
	//Where the user key will be stored upon successful login to the system. Used by functions to pass key to decryption algorithm
	private String key;
	private Database dbObj;
	private int user_id; //Setting this manually for now, but will have to be accessed from somewhere in the future

	public DBHelpers()
	{
		dbObj = new Database();
		if(!dbObj.hasConnection() || !dbObj.hasStatement())
			System.exit(1);
	}

	//Creates new user for the system and returns the generated userID. Returns -1 if user already in system
	public int newUser(String userName, String password) throws SQLException, NoSuchAlgorithmException {
		//Check to see if userName is already taken
		ResultSet userNameCheck = dbObj.query("SELECT * FROM user_data WHERE user_name='" + userName + "'");
		//If the result set is not empty
		if(userNameCheck.next())
		{
			return -1;
		}

		//Perform the insert

		String userPassword = Encryption.encryptHashString(password);

		user_id = dbObj.insertQuery("INSERT INTO user_data (user_name, password) VALUES ('" + userName + "', '" + userPassword + "')");


		return user_id;
	}

	//Creates new entry for bank information. Will return -1 if account number already exists or upon other error, and 0 if successful
	public int newBank(String name, String accountNumber, String routingNumber, String bankAddress, String accountType) throws SQLException {
		//Check to see if accountNumber is already taken
		boolean accountExists = false;

		ResultSet bankEntries = dbObj.query("SELECT data_id FROM encrypted_data WHERE category LIKE 'Bank Account' AND user_id=" + user_id);
		if(bankEntries.next())
		{
			//I think that this will work as a way to loop through
			while(!bankEntries.isAfterLast())
			{
				int data_id = bankEntries.getInt(bankEntries.findColumn("data_id"));
				BankInfo tmpBank = getBank(data_id);
				if(tmpBank.getAccountNumber().equals(accountNumber))
				{
					accountExists = true;
					break;
				}
				bankEntries.next();
			}
		}

		if(accountExists)
			return -1;

		//Have now shown accountNumber to not already exist in database and can proceed with the insert
		String dataString = accountNumber + ";" + routingNumber + ";" + bankAddress + ";" + accountType;
		int insertStatus = dbObj.insertBLOB(user_id, "Bank Account", name, dataString, key);

		if(insertStatus == -1)
			return -1;

		return 0;
	}

	//Creates new entry for web account. Returns -1 if name and email pair is already taken or upon other error, and will return 0 otherwise
	public int newWeb(String name, String url, String email, String userName, String password, String[][] securityQAPairs) throws SQLException {
		//Check to see if name email pair is already taken
		boolean accountExists = false;

		ResultSet webEntries = dbObj.query("SELECT data_id, note FROM encrypted_data WHERE category LIKE 'Web Account' AND user_id='" + user_id + "'");
		if(webEntries.next())
		{
			while(!webEntries.isAfterLast())
			{
				int data_id = webEntries.getInt(webEntries.findColumn("data_id"));
				WebInfo tmpWeb = getWeb(data_id);
				if(tmpWeb.getEmail().equals(email) && name.equals(webEntries.getString(2)))
				{
					accountExists = true;
					break;
				}
				webEntries.next();
			}
		}

		if(accountExists)
			return -1;

		//Need to build out and insert new web blob. Start by building the csv style delimited data string
		String dataString = url + ";" + email + ";" + userName + ";" + password;

		//Dynamically add the security QA pairs into the dataString for encryption
		for(int i = 0; i < securityQAPairs.length; i++)
		{
			dataString += ";";
			dataString += securityQAPairs[i][0];
			dataString += ";";
			dataString += securityQAPairs[i][1];
		}

		int insertStatus = dbObj.insertBLOB(user_id, "Web Account", name, dataString, key);

		if(insertStatus == -1)
			return -1;

		return 0;
	}

	//Creates new note
	public int newNote(String name, String text) {
		//Notes should be able to occur more than once with the same name (i.e. grocery list), so jump right into the insert

		int insertStatus = dbObj.insertBLOB(user_id, "Note", name, text, key);

		if(insertStatus == -1)
			return -1;

		return 0;
	}

	//Creates new entry for SSN and returns ID of data table entry. Is the user allowed to store more than one SSN for themselves? I guess they can... hmmm
	public int newSocial(String name, String ssn) throws SQLException {
		//Check to see if SSN is already taken
		boolean ssnExists = false;

		ResultSet ssnEntries = dbObj.query("SELECT data_id FROM encrypted_data WHERE category LIKE 'SSN' AND user_id='" + user_id + "'");
		if(ssnEntries.next())
		{
			while(!ssnEntries.isAfterLast())
			{
				int data_id = ssnEntries.getInt(ssnEntries.findColumn("data_id"));
				String tmpSSN = getSocial(data_id).getSSN();
				if(tmpSSN.equals(ssn))
				{
					ssnExists = true;
					break;
				}
				ssnEntries.next();
			}
		}

		if(ssnExists)
			return -1;

		//Have now shown ssn to not already exist in database and can proceed with the insert
    String data_string = name + ";" + ssn;
		int insertStatus = dbObj.insertBLOB(user_id, "SSN", name, data_string, key);

		if(insertStatus == -1)
			return -1;

		return 0;
	}

	//Returns true if the user exists
	public boolean checkUserExist(String userName) {
		ResultSet entries = null;
		try {
			String query = "SELECT * FROM user_data WHERE user_name LIKE '" + userName + "'";
			entries = dbObj.query(query);
			boolean status = entries.next();
			return status;
		} catch (SQLException e) {
			System.err.println("Check login error: " + e);
		}

		return false;
	}

	//Returns the userID if valid login, false otherwise
	public boolean checkLogin(String userName, String password) {
		ResultSet entries = null;
		try {
			String encrypt = Encryption.encryptHashString(password);
			entries = dbObj.query("SELECT * FROM user_data WHERE user_name='" + userName + "' AND password='" + encrypt + "'");
			if (entries.next()) {
				user_id = entries.getInt("user_id");
				key = password;
				int length = key.length();
				if(length < 16)
				{
					for(int i  = 0; i < 16-length; i++)
					{
						key += '0';
					}
				}
				else if(length > 16)
				{
					key = key.substring(0,  16);
				}

			}
			else
				user_id = -1;
		} catch (NoSuchAlgorithmException e) {
			System.err.println("No algorithm found: " + e);
		} catch (SQLException e) {
			System.err.println("Check login error: " + e);
		}

		return user_id != -1;
	}

    //Return a listing of all data entries of type bank including their name/label and ID
    public ArrayList<BankInfo> getBanks() {
        //get the type_id of Bank Account
        ArrayList<BankInfo> banks = new ArrayList<BankInfo>();

        try {
            ResultSet entries = dbObj.query("SELECT encrypted_data, data_id, name FROM encrypted_data WHERE category LIKE 'Bank Account' AND user_id='" + user_id + "'");
            if(entries.next())
            {
                while(!entries.isAfterLast())
                {
                    String decrypt = dbObj.readFromBLOB(entries, "encrypted_data", key);
                    banks.add(BankInfo.serializeCSVDump(decrypt, entries.getInt("data_id"), entries.getString("name")));
                    entries.next();
                }
            }
        } catch (SQLException e) {
            //TODO: replace with error logging
            System.err.println("Database error: " + e);
        }

        return banks;
    }

    //Used to retrieve the encrypted data of a bank entry
    public BankInfo getBank(int bankEntryID) {
        BankInfo bank = null;
        try {
            ResultSet entries = dbObj.query("SELECT * FROM encrypted_data WHERE data_id='" + bankEntryID + "' AND user_id='" + user_id + "'");
            entries.next();
            String decrypt = dbObj.readFromBLOB(entries, "encrypted_data", key);
            bank = BankInfo.serializeCSVDump(decrypt, entries.getInt("data_id"), entries.getString("name"));
        } catch (SQLException e) {
            //TODO: replace with error logging
            System.err.println("Database error: " + e);
        }

        return bank;
    }

    //Returns a listing of all data entries of type web account including their name/label and ID
    public ArrayList<WebInfo> getWebs() {
        ArrayList<WebInfo> webs = new ArrayList<WebInfo>();

        try {
            ResultSet entries = dbObj.query("SELECT encrypted_data FROM encrypted_data WHERE category LIKE 'Web Account' AND user_id='" + user_id + "'");
            if(entries.next())
            {
                while(!entries.isAfterLast())
                {
                    String decrypt = dbObj.readFromBLOB(entries, "encrypted_data", key);
                    webs.add(WebInfo.serializeCSVDump(decrypt, entries.getInt("data_id")));
                    entries.next();
                }
            }
        } catch (SQLException e) {
            //TODO: replace with error logging
            System.err.println("Database error: " + e);
        }

        return webs;
    }

    //Used to retrieve the encryped data of a web account entry
    public WebInfo getWeb(int webID) {
        WebInfo web = null;
        try {
            ResultSet entries = dbObj.query("SELECT * FROM encrypted_data WHERE data_id='" + webID + "' AND user_id='" + user_id + "'");
            entries.next();
            String decrypt = dbObj.readFromBLOB(entries, "encrypted_data", key);
            web = WebInfo.serializeCSVDump(decrypt, entries.getInt("data_id"));
        } catch (SQLException e) {
            //TODO: replace with error logging
            System.err.println("Database error: " + e);
        }

        return web;
    }

    //Returns a listing of all notes stored by the user including their name and ID
    public ArrayList<NoteInfo> getNotes() {
        ArrayList<NoteInfo> notes = new ArrayList<NoteInfo>();

        try {
            ResultSet entries = dbObj.query("SELECT * FROM encrypted_data WHERE category LIKE 'Note' AND user_id='" + user_id + "'");
            if(entries.next())
            {
                while(!entries.isAfterLast())
                {
                    String decrypt = dbObj.readFromBLOB(entries, "encrypted_data", key);
                    notes.add(new NoteInfo(entries.getString("name"), decrypt, entries.getInt("data_id")));
                    entries.next();
                }
            }
        } catch (SQLException e) {
            //TODO: replace with error logging
            System.err.println("Database error: " + e);
        }

        return notes;
    }

    //Used to retrieve the contents of a note
    public NoteInfo getNote(int noteID) {
        NoteInfo note = null;
        try {
            ResultSet entries = dbObj.query("SELECT * FROM encrypted_data WHERE data_id='" + noteID + "' AND user_id='" + user_id + "'");
            entries.next();
            String decrypt = dbObj.readFromBLOB(entries, "encrypted_data", key);
            note = new NoteInfo(entries.getString("name"), decrypt, entries.getInt("data_id"));
        } catch (SQLException e) {
            //TODO: replace with error logging
            System.err.println("Database error: " + e);
        }

        return note;
    }

    //Used to retrieve a listing of SSNs stored including their ID and the name (person they are associated with)
    public ArrayList<SSNInfo> getSocials() {
        ArrayList<SSNInfo> ssns = new ArrayList<SSNInfo>();

        try {
            ResultSet entries = dbObj.query("SELECT encrypted_data, data_id FROM encrypted_data WHERE category LIKE 'SSN' AND user_id='" + user_id + "'");
            while(entries.next()) {
              String decrypt = dbObj.readFromBLOB(entries, "encrypted_data", key);
              int id = entries.getInt("data_id");
              ssns.add(SSNInfo.serializeCSVDump(decrypt, id));
            }
        } catch (SQLException e) {
            //TODO: replace with error logging
            System.err.println("Database error: " + e);
        }
        return ssns;
    }

    //Used to retrieve a specific SSN
    public SSNInfo getSocial(int socialID) {
        SSNInfo ssn = null;
        try {
            ResultSet entries = dbObj.query("SELECT * FROM encrypted_data WHERE data_id='" + socialID + "' AND user_id='" + user_id + "'");
            entries.next();
            int id = entries.getInt("data_id");
            String decrypt = dbObj.readFromBLOB(entries, "encrypted_data", key);
            ssn = SSNInfo.serializeCSVDump(decrypt, id);
        } catch (SQLException e) {
            //TODO: replace with error logging
            System.err.println("Database error: " + e);
        }

        return ssn;
    }

	//Will overwrite data previously written for entry with given userID
  	public int updateUser(int userID, String userName, String password, String name) {
    		if(userID != 0 && userName != null && password != null && name != null){
      			String query = "UPDATE user_data SET user_name='" + userName + "', password='" + password + "', name='" + name + "' WHERE user_id='" + userID + "'";
      			return dbObj.updateQuery(query);
    		}
    		
    		return -1;
  	}

	//Will overwrite data previously written for entry with given bankID
  	public int updateBank(int bankID, String name, String accountNumber, String routingNumber, String bankAddress, String accountType) {
    		if(bankID != 0 && name != null && accountNumber != null && routingNumber != null && bankAddress != null && accountType != null){
      			String dataString = accountNumber + ";" + routingNumber + ";" + bankAddress + ";" + accountType;
      			return dbObj.updateBLOB(bankID, name, dataString, key);
    		}
    		
    		return -1;
  	}

	//Will overwrite data previously written for entry with given webID
  	public int updateWeb(int webID, String name, String url, String email, String userName, String password, String[][] securityQAPairs) {
    		if(webID != 0 && name != null && url != null && email != null && userName != null && password != null && securityQAPairs != null){
      			String dataString = url + ";" + email + ";" + userName + ";" + password;
      
      			//Dynamically add the security QA pairs into the dataString for encryption
      			for(int i = 0; i < securityQAPairs.length; i++)
      			{
        			dataString += ";";
        			dataString += securityQAPairs[i][0];
        			dataString += ";";
        			dataString += securityQAPairs[i][1];
      			}
      
      			return dbObj.updateBLOB(webID, name, dataString, key);
    		}
    		
    		return -1;
  	}

	//Will overwrite data previously written for entry with given noteID
  	public int updateNote(int noteID, String name, String text) {
		if(noteID != 0 && name != null && text != null){
			return dbObj.updateBLOB(noteID, name, text, key);
		}
		
		return -1;
  	}

	//Will overwrite data previously written for entry with given socialID
  	public int updateSocial(int socialID, String name, String ssn) {
		if(socialID != 0 && name != null && ssn != null){
  			String data_string = name + ";" + ssn;
  			return dbObj.updateBLOB(socialID, null, data_string, key);
		}
		
		return -1;
  	}

	//Will remove entry with given ID
	public void delete(int id) {
    		if(id != 0){
    			String query = "DELETE FROM encrypted_data WHERE data_id='" + id + "'";
      			dbObj.updateQuery(query);
    		}
  	}
	/*********************
	*	Helper Methods   *
	*********************/

    private int resultsCount(ResultSet r) {
      int numRows = 0;
      try {
          r.last();
          numRows = r.getRow();
          r.beforeFirst();
      } catch (SQLException e) {
          System.err.println("Database error: " + e);
      }
      return numRows;
  }

	public void close() {
		dbObj.close();
	}
}
