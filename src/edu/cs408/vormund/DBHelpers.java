package edu.cs408.vormund;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelpers {
	//Where the user key will be stored upon successful login to the system. Used by functions to pass key to decryption algorithm
	String key;
	Database dbObj;
	
	public DBHelpers()
	{
		dbObj = new Database();
	}
	
	//Creates new user for the system and returns the generated userID. Returns -1 if user already in system
	public int newUser(String userName, String password, String name) throws SQLException {
		//Check to see if userName is already taken
		ResultSet userNameCheck = dbObj.query("SELECT * FROM user_data WHERE user_name='" + userName + "'");
		//If the result set is not empty
		if(userNameCheck.first())
		{
			return -1;
		}
		
		//Perform the insert
		dbObj.updateQuery("INSERT INTO user_data (user_name, password, name) VALUES ('" + userName + "', '" + password + "', '" + name + "')");
		
		//Get the user_id of the just inserted row
		userNameCheck = dbObj.query("SELECT * FROM user_data WHERE user_name='" + userName + "'");
		//Move cursor to first row
		userNameCheck.first();
		return userNameCheck.getInt(1);
	}

	//Creates new entry for bank information and returns the ID of the generated data table entry. Will return -1 if account number already exists
	public int newBank(String name, String accountNumber, String routingNumber, String bankAddress, boolean isCheckingAccount, boolean isSavingsAccount) throws SQLException {
		//Check to see if accountNumber is already taken
		boolean accountExists = false;
		
		//get the type_id of Bank Account
		ResultSet dataTypeQuery = dbObj.query("SELECT type_id FROM data_type WHERE type_name='Bank Account'");
		dataTypeQuery.first();
		int bankType = dataTypeQuery.getInt(1);
		
		ResultSet bankEntries = dbObj.query("SELECT data_id FROM encryped_data WHERE type_id='" + bankType + "'");
		if(bankEntries.first())
		{
			while(!bankEntries.isAfterLast())
			{
				int data_id = bankEntries.getInt(1);
				BankInfo tmpBank = getBank(data_id);
				if(tmpBank.getAccountNumber() == Integer.parseInt(accountNumber))
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
		String encryptedBankData = Encryption.encryptBlob(key, accountNumber + ", " + routingNumber + ", " + bankAddress + ", " + isCheckingAccount + ", " + isSavingsAccount);
		
		//The user_id should be stored and accessible somewhere
		dbObj.updateQuery("INSERT INTO encrypted_data (user_id, type_id, encrypted_data) VALUES ('" + user_id + "', '" + bankType + "', '" + encryptedBankData + "')");
		
		//Get the data_id of the inserted row somehow?
		
		
		return 0;
	}

	//Creates new entry for web account information and returns the ID of the generated data table entry
	public int newWeb(String name, String url, String email, String userName, String password, String[][] securityQAPairs) {
		return 0;
	}

	//Creates new note and returns the ID of the generated data table entry
	public int newNote(String name, String text) {
		return 0;
	}

	//Creates new entry for SSN and returns ID of data table entry
	public int newSocial(String name, String ssn) {
		return 0;
	}

	//Returns the userID if valid login, 0 otherwise
	public int checkLogin(String userName, String password) {
		return 0;
	}

	//Return a listing of all data entries of type bank including their name/label and ID
	public void getBanks(int userID) {

	}

	//Used to retrieve the encrypted data of a bank entry
	public BankInfo getBank(int bankID) {
		// I have no idea how SQLite data is returned in Java
        ResultSet data = dbObj.query("SELECT * FROM encrypted_data WHERE data_id='" + bankID + "'");
        // key should be an instance variable of this class
        data = Encryption.decryptBlog(key, data);
        // data should now be plaintext CSV
        return new BankInfo(data);
	}

	//Returns a listing of all data entries of type web account including their name/label and ID
	public void getWebs(int userID) {

	}

	//Used to retrieve the encryped data of a web account entry
	public void getWeb(int webID) {

	}

	//Returns a listing of all notes stored by the user including their name and ID
	public void getNotes(int userID) {

	}

	//Used to retrieve the contents of a note
	public void getNote(int noteID) {

	}

	//Used to retrieve a listing of SSNs stored including their ID and the name (person they are associated with)
	public void getSocials(int userID) {

	}

	//Used to retrieve a specific SSN
	public int getSocial(int socialID) {
		return 0;
	}

	//Will overwrite data previously written for entry with given userID
	public void updateUser(int userID) {

	}

	//Will overwrite data previously written for entry with given bankID
	public void updateBank(int bankID) {

	}

	//Will overwrite data previously written for entry with given webID
	public void updateWeb(int webID) {

	}

	//Will overwrite data previously written for entry with given noteID
	public void updateNote(int noteID) {

	}

	//Will overwrite data previously written for entry with given socialID
	public void updateSocial(int socialID) {

	}

	//Will remove entry with given userID
	public void deleteUser(int userID) {

	}

	//Will remove entry with given bankID
	public void deleteBank(int bankID) {

	}

	//Will remove entry with given webID
	public void deleteWeb(int webID) {

	}

	//Will remove entry with given noteID
	public void deleteNote(int noteID) {

	}

	//Will remove entry with given socialID
	public void deleteSocial(int socialID) {

	}
}
