package edu.cs408.vormund;

public class DBHelpers {
	//Creates new user for the system and returns the generated userID
	public static int newUser() {
		return 0;
	}

	//Creates new entry for bank information and returns the ID of the generated data table entry
	public static int newBank() {
		return 0;
	}

	//Creates new entry for web account information and returns the ID of the generated data table entry
	public static int newWeb() {
		return 0;
	}

	//Creates new note and returns the ID of the generated data table entry
	public static int newNote() {
		return 0;
	}

	//Creates new entry for SSN and returns ID of data table entry
	public static int newSocial() {
		return 0;
	}

	//Returns the userID if valid login, 0 otherwise
	public static int checkLogin(String userName, String password) {
		return 0;
	}

	//Return a listing of all data entries of type bank including their name/label and ID
	public  void getBanks(int userID) {

	}

	//Used to retrieve the encrypted data of a bank entry
	public BankInfo getBank(int bankID) {
		// I have no idea how SQLite data is returned in Java
        ResultSet data = dbObj.query("SELECT * FROM encrypted_data WHERE data_id='" + userID + "'");
        // key should be an instance variable of this class
        data = Encryption.decryptBlog(key, data);
        // data should now be plaintext CSV
        return new BankInfo(data);
	}

	//Returns a listing of all data entries of type web account including their name/label and ID
	public static void getWebs(int userID) {

	}

	//Used to retrieve the encryped data of a web account entry
	public static void getWeb(int webID) {

	}

	//Returns a listing of all notes stored by the user including their name and ID
	public static void getNotes(int userID) {

	}

	//Used to retrieve the contents of a note
	public static void getNote(int noteID) {

	}

	//Used to retrieve a listing of SSNs stored including their ID and the name (person they are associated with)
	public static void getSocials(int userID) {

	}

	//Used to retrieve a specific SSN
	public static int getSocial(int socialID) {
		return 0;
	}

	//Will overwrite data previously written for entry with given userID
	public static void updateUser(int userID) {

	}

	//Will overwrite data previously written for entry with given bankID
	public static void updateBank(int bankID) {

	}

	//Will overwrite data previously written for entry with given webID
	public static void updateWeb(int webID) {

	}

	//Will overwrite data previously written for entry with given noteID
	public static void updateNote(int noteID) {

	}

	//Will overwrite data previously written for entry with given socialID
	public static void updateSocial(int socialID) {

	}

	//Will remove entry with given userID
	public static void deleteUser(int userID) {

	}

	//Will remove entry with given bankID
	public static void deleteBank(int bankID) {

	}

	//Will remove entry with given webID
	public static void deleteWeb(int webID) {

	}

	//Will remove entry with given noteID
	public static void deleteNote(int noteID) {

	}

	//Will remove entry with given socialID
	public static void deleteSocial(int socialID) {

	}
}
