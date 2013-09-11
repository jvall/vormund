package edu.cs408.vormund;

import java.sql.*;
import java.util.ArrayList;

public class DBHelpers {
	//Where the user key will be stored upon successful login to the system. Used by functions to pass key to decryption algorithm
	private String key;
	private Database dbObj;
	private int user_id = 1; //Setting this manually for now, but will have to be accessed from somewhere in the future
	
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
	public int newBank(String name, String accountNumber, String routingNumber, String bankAddress, String type) throws SQLException {
		//Check to see if accountNumber is already taken
		boolean accountExists = false;
		
		//get the type_id of Bank Account
		ResultSet dataTypeQuery = dbObj.query("SELECT type_id FROM data_type WHERE type_name='Bank Account'");
		dataTypeQuery.first();
		int bankType = dataTypeQuery.getInt(1);
		
		ResultSet bankEntries = dbObj.query("SELECT data_id FROM encryped_data WHERE type_id='" + bankType + "' AND user_id='" + user_id + "'");
		if(bankEntries.first())
		{
			while(!bankEntries.isAfterLast())
			{
				int data_id = bankEntries.getInt(1);
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
		byte[] encryptedBankData = Encryption.encryptBlob(key, accountNumber + ", " + routingNumber + ", " + bankAddress + ", " + type + "'");
		
		//The user_id should be stored and accessible somewhere
		dbObj.updateQuery("INSERT INTO encrypted_data (user_id, type_id, encrypted_data, note) VALUES ('" + user_id + "', '" + bankType + "', '" + encryptedBankData + "', '" + name + "')");
		
		//Get the data_id of the inserted row somehow?
		
		
		return 0;
	}

	//Creates new entry for web account information and returns the ID of the generated data table entry
	public int newWeb(String name, String url, String email, String userName, String password, String[][] securityQAPairs) throws SQLException {
		//Check to see if name email pair is already taken
		boolean accountExists = false;
		
		//get the type_id of Bank Account
		ResultSet dataTypeQuery = dbObj.query("SELECT type_id FROM data_type WHERE type_name='Web Account'");
		dataTypeQuery.first();
		int webType = dataTypeQuery.getInt(1);
		
		ResultSet webEntries = dbObj.query("SELECT data_id, note FROM encryped_data WHERE type_id='" + webType + "' AND user_id='" + user_id + "'");
		if(webEntries.first())
		{
			while(!webEntries.isAfterLast())
			{
				int data_id = webEntries.getInt(1);
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
		
		//Need to build out and insert new web blob
		
		return 0;
	}

	//Creates new note and returns the ID of the generated data table entry
	public int newNote(String name, String text) {
		return 0;
	}

	//Creates new entry for SSN and returns ID of data table entry. Is the user allowed to store more than one SSN for themselves? I guess they can... hmmm
	public int newSocial(String name, String ssn) throws SQLException {
		//Check to see if SSN is already taken
		boolean ssnExists = false;
		
		//get the type_id of SSN
		ResultSet dataTypeQuery = dbObj.query("SELECT type_id FROM data_type WHERE type_name='Social'");
		dataTypeQuery.first();
		int ssnType = dataTypeQuery.getInt(1);
		
		ResultSet ssnEntries = dbObj.query("SELECT data_id FROM encryped_data WHERE type_id='" + ssnType + "' AND user_id='" + user_id + "'");
		if(ssnEntries.first())
		{
			while(!ssnEntries.isAfterLast())
			{
				/* WORK IN PROGRESS
				int data_id = ssnEntries.getInt(1);
				SSNInfo tmpSSN = getSocial(data_id);
				if(tmpSSN.getSSN().equals(ssn))
				{
					ssnExists = true;
					break;
				}
				ssnEntries.next();
				*/
			}
		}
		
		if(ssnExists)
			return -1;
		
		//Have now shown ssn to not already exist in database and can proceed with the insert
		
		
		return 0;
	}

	//Returns the userID if valid login, 0 otherwise
	public int checkLogin(String userName, String password) {
		return 0;
	}

	//Return a listing of all data entries of type bank including their name/label and ID
	public ArrayList<BankInfo> getBanks(int userID) {
		//get the type_id of Bank Account
		ArrayList<BankInfo> banks = new ArrayList<BankInfo>();

		try {
			ResultSet entries = dbObj.query("SELECT encryped_data FROM encryped_data WHERE type_id='" + getRecordType("Bank Account") + "' AND user_id='" + userID + "'");
			if(entries.first())
			{
				while(!entries.isAfterLast())
				{
					byte bank_info[] = entries.getBytes("encrypted_data");
					String decrypt = Encryption.decryptBlob(key, bank_info);
					banks.add(BankInfo.serializeCSVDump(decrypt));
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
	public BankInfo getBank(int bankID) {
		// I have no idea how SQLite data is returned in Java
        ResultSet data = dbObj.query("SELECT * FROM encrypted_data WHERE data_id='" + bankID + "'");
        // key should be an instance variable of this class
        //data = Encryption.decryptBlob(key, data);
        // data should now be plaintext CSV
        return new BankInfo(""); //tmp
	}

	//Returns a listing of all data entries of type web account including their name/label and ID
	public void getWebs(int userID) {

	}

	//Used to retrieve the encryped data of a web account entry
	public WebInfo getWeb(int webID) {
		return new WebInfo("");	//tmp
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
	public String getSocial(int socialID) {
		return null;
	}

	//Will overwrite data previously written for entry with given userID
	public void updateUser(int userID) {

	}

	//Will overwrite data previously written for entry with given bankID
	public void updateBank(int bankID, String name, String accountNumber, String routingNumber, String bankAddress, String type) {
		//We can make the assumption that the user is editing a pre-existing entry and can go directly to the update function
		byte encryptedBankData[] = Encryption.encryptBlob(key, accountNumber + ", " + routingNumber + ", " + bankAddress + ", " + type + "'");
		
		//The user_id should be stored and accessible somewhere
		dbObj.updateQuery("UPDATE encrypted_data SET encrypted_data = '" + encryptedBankData + "', note='" + name + "' WHERE data_id='" + bankID + "'");
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

	/*********************
	*	Helper Methods   *
	*********************/

	private int getRecordType(String type) {
		int numType = -1;
		try {
			ResultSet dataTypeQuery = dbObj.query("SELECT type_id FROM data_type WHERE type_name='" + type + "'");
			dataTypeQuery.first();
			numType = dataTypeQuery.getInt(1);
		} catch (SQLException e) {
			System.err.println("Database error: " + e);
		}
		return numType;
	}
}
