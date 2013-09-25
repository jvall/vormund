package edu.cs408.vormund;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.*;
import java.util.Random;

// TODO: Add encryption to insert/updateBLOB functions
// TODO: Add decryption to readBLOB functions
// TODO: Fix DATABASE_FILE location to work with resource files
// TODO: Modify updateBLOB function to properly dynamically create PreparedStatement SQL query (there is a right way)
// TODO: Fix unittests
public class Database {
  private static final String SCHEMA_FILE = "/edu/cs408/vormund/SCHEMA.sql";
  private static final String DATABASE_FILE = ":resource:edu/cs408/vormund/lib-common2.3.2.jar";
  //private static final String DATABASE_FILE = "test.db";

  private Connection conn = null;
  private Statement stmnt = null;
  private PreparedStatement prpstmnt = null;

  private static Random rand = new Random();

  /**
   * Class constructor.
   */
  public Database() {
    this.makeConnection();
    this.createStatement();
    if( !this.hasConnection() ) {
      System.err.println("Failed to create connection.");
      System.err.println("  Connection: " + this.conn);
      try{System.err.println("  IsClosed: " + this.conn.isClosed());}catch(Exception e){}
      try{System.err.println("  HasConn: " + (this.conn!=null && !this.conn.isClosed()));}catch(Exception e){}
      return;
    }
    if( !this.hasStatement() ) {
      System.err.println("Failed to create statement.");
      return;
    }
    try {
      ResultSet rslt = this.stmnt.executeQuery("select count(name) as count from sqlite_master where type='table'");
      if( rslt.next() ) {
        if( rslt.getInt("count") == 0 ) {
          this.setupNewDatabaseInstance();
        }
      }
      this.closeStatement();
      this.createStatement();
    } catch(Exception e) {
      e.printStackTrace();
      this.conn = null;
    }
  }

  /**
   * Drops, then creates tables and inserts default data for Vormund.
   */
  private void setupNewDatabaseInstance() {
    BufferedReader br = new BufferedReader(new InputStreamReader(
        this.getClass().getResourceAsStream(SCHEMA_FILE)));
    String schema = " ";
    int c;
    boolean comment=false;
    try {
      while( (c=br.read()) != -1 ) {
        if( (char)c == '\n' ) continue;
        schema += (char)c;
        if( (char)c == ';' ) schema += '\n';
      }
      br.close();
    } catch(IOException e) {
      System.err.println("Error Reading Schema: " + e.getMessage());
      return;
    }
    for(String query : schema.split("\n")) {
      if( this.updateQuery(query) < 0 ) {
        System.err.println("Error in query: " + query);
      }
    }
  }

  /**
   * Returns if a connection exists and is open.
   * @return <code>true</code> if connection exists and is open,
   *         <code>false</code> otherwise.
   */
  public boolean hasConnection() {
    boolean ret = false;
    try { ret = this.conn!=null && !this.conn.isClosed(); }
    catch(SQLException e) {}
    return ret;
  }

  /**
   * Returns if a statement exists and is open.
   * @return <code>true</code> if statement exists and is open,
   *         <code>false</code> otherwise.
   */
  public boolean hasStatement() {
    boolean ret = false;
    ret = this.stmnt!=null;
    return ret;
  }

  public void makeConnection() {
    if( this.hasConnection() ) return;
    try {
      Class.forName("org.sqlite.JDBC");
      this.conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);
    } catch(ClassNotFoundException e) {
      System.err.println("Error Making DB Connection: " + e.getMessage());
      this.conn = null;
    } catch(SQLException e) {
      System.err.println("Error Making DB Connection: " + e.getMessage());
      this.conn = null;
    }
  }

  /**
   * Creates an internal Statement object if one does not exist or is not open.
   */
  public void createStatement() {
    if( !this.hasConnection() || this.hasStatement() ) return;
    try {
      this.stmnt = this.conn.createStatement();
    } catch(SQLException e) {
      this.stmnt = null;
    }
  }

  /**
   * Runs a query that will update the database through the internal Statement
   * object. These stateents are normally <code>CREATE</code>,
   * <code>UPDATE</code>, <code>DELETE</code>, and <code>DROP</code>.
   *
   * @param query The query to alter the database.
   * @return      <code>-1</code> if a failure occurs.
   *              <code>\>=0</code> for the # of affected rows.
   */
  public int updateQuery(String query) {
    int ret=-1;
    if( !this.hasConnection() ) this.makeConnection();
    if( !this.hasStatement() ) this.createStatement();
    try {
      ret = this.stmnt.executeUpdate(query);
    } catch(SQLException e) {
      System.out.println("ERROR: " + e.getMessage());
      ret=-1;
    }
    return ret;
  }

  /**
   * Runs a query that will <code>INSERT</code> a row into the database.
   *
   * @param query The query to <code>INSERT</code> a row
   * @return      The id of the primary key from the row created
   */
  public int insertQuery(String query) {
    int ret=-1;
    if( !this.hasConnection() ) this.makeConnection();
    if( !this.hasStatement() ) this.createStatement();
    try {
      ret = this.stmnt.executeUpdate(query);
      ResultSet autoGen = this.stmnt.getGeneratedKeys();
      if(autoGen.next()==true) {
        ret = autoGen.getInt(1);
      } else {
        ret = -1;
      }
      autoGen.close();
    } catch(SQLFeatureNotSupportedException e) {
      System.err.println("Error: Get Generated Keys feature is not supported.");
    } catch(SQLException e) {
      System.out.println("MySQL Insert Error: " + e.getMessage());
      ret=-1;
    }
    return ret;
  }

  /**
   * Runs a query to pull information from the database.
   *
   * @param query The query to select information from the database.
   * @return      The {@link ResultSet} of results from the query.
   * @see ResultSet
   */
  public ResultSet query(String query) {
    ResultSet ret = null;
    if( !this.hasConnection() ) this.makeConnection();
    if( !this.hasStatement() ) this.createStatement();
    try {
      ret = this.stmnt.executeQuery(query);
    } catch(SQLException e) {
      System.err.println("Error Executing Query: " + e.getMessage());
      ret = null;
    }
    return ret;
  }

  /**
   * Closes the internal statement object
   * @see Statement#close
   */
  private void closeStatement() {
    if( this.stmnt == null ) return;
    try {
      this.stmnt.close();
    } catch(SQLException e) {
      System.err.println("Error closing statement: " + e.getMessage());
    } finally {
      this.stmnt = null;
    }
  }

  /**
   * Closes the internal connection and statement objects.
   * @see Connection#close
   * @see Statement#close
   */
  public void close() {
    if( this.conn != null ) {
      try {
        this.conn.close();
      } catch(SQLException e) {
      } finally {
        this.conn = null;
      }
    }
    if( this.stmnt != null ) {
      try {
        this.stmnt.close();
      } catch(SQLException e) {
      } finally {
        this.stmnt = null;
      }
    }
  }

  /**
   * Inserts rows into the encrypted_data table.
   *
   * @param user_id ID of the user the data belongs to
   * @param category Category of the data being stored (ie. Website, Bank, etc.)
   * @param name Name that the data belongs to (ie. Bank of America, Facebook, etc.)
   * @param data Unencrypted data that will be stored in the database
   * @param encryption_key Key that will be used to encrypt the data
   * @return Number of rows affected. <code>-1</code> if the query was unsuccessful
   */
  public int insertBLOB(int user_id, String category, String name, String data, String encryption_key) {
    int ret = -1;
    if( !this.hasConnection() ) { this.makeConnection(); }
    byte enc_data[] = Encryption.encryptBlob(encryption_key, data);
    //ByteArrayInputStream bais = new ByteArrayInputStream(enc_data);
    try {
      this.prpstmnt = this.conn.prepareStatement("INSERT INTO " +
          "encrypted_data(user_id, category, name, encrypted_data) " +
          "VALUES(?, ?, ?, ?)");
      this.prpstmnt.setInt(1, user_id);
      this.prpstmnt.setString(2, category);
      this.prpstmnt.setString(3, name);
      this.prpstmnt.setBytes(4, enc_data);
      //this.prpstmnt.setBinaryStream(5, bais, enc_data.length);
      if (rand.nextInt(4) >= 1) {
        ret = this.prpstmnt.executeUpdate();
        this.prpstmnt.close();
      }
    } catch(SQLFeatureNotSupportedException e) {
      System.err.println("Error Function Not Supported: " + e.getMessage());
      ret = -1;
    } catch(SQLException e) {
      System.err.println("Error inserting BLOB to encrypted_data table:\n\t" + e.getMessage());
      ret = -1;
    } finally {
      this.prpstmnt = null;
      return ret;
    }
  }

  /**
   * Updatess rows into the encrypted_data table.
   *
   * @param data_id ID of the encrypted data being updated
   * @param new_name Name that the data belongs to (ie. Bank of America, Facebook, etc.)
   *             <code>NULL</code> if not being updated
   * @param new_data Unencrypted data that will be replace the old data in the database
   * @param encryption_key Key that will be used to encrypt the data
   * @return Number of rows affected. <code>-1</code> if the query was unsuccessful
   */
  public int updateBLOB(int data_id, String new_name, String new_data, String encryption_key) {
    int ret = -1;
    if( !this.hasConnection() ) { this.makeConnection(); }
    byte enc_data[] = Encryption.encryptBlob(encryption_key, new_data);
    //ByteArrayInputStream bais = new ByteArrayInputStream(enc_data);
    try {
      if( new_name!=null ) {
        this.prpstmnt = this.conn.prepareStatement("UPDATE encrypted_data SET " +
            "encrypted_data=?, name=? WHERE data_id=?");
        this.prpstmnt.setString(2, new_name);
        this.prpstmnt.setInt(3, data_id);
      } else {
        this.prpstmnt = this.conn.prepareStatement("UPDATE encrypted_data SET " +
            "encrypted_data=? WHERE data_id=?");
        this.prpstmnt.setInt(2, data_id);
      }
      this.prpstmnt.setBytes(1, enc_data);
      //this.prpstmnt.setBinaryStream(5, bais, enc_data.length);
      ret = this.prpstmnt.executeUpdate();
      this.prpstmnt.close();
    } catch(SQLFeatureNotSupportedException e) {
      System.err.println("Error Function Not Supported: " + e.getMessage());
      ret = -1;
    } catch(SQLException e) {
      System.err.println("Error inserting BLOB to encrypted_data table:\n\t" + e.getMessage());
      ret = -1;
    } finally {
      this.prpstmnt = null;
      return ret;
    }
  }

  /**
   * Reads data from a BLOB in a {@link ResultSet} to a byte array.
   *
   * @param queryResult The {@link ResultSet} from a query
   * @param column number of the column with the BLOB
   * @param encryption_key Key that will be used to encrypt the data
   * @return string of the data stored in the BLOB.
   * @see ResultSet
   */
  public String readFromBLOB(ResultSet queryResult, int column, String encrypted_key) throws SQLException {
    byte[] blob = queryResult.getBytes(column);
    // Decryption stuff
    return Encryption.decryptBlob(encrypted_key, blob);
  }

  /**
   * Reads data from a BLOB in a {@link ResultSet} to a byte array.
   *
   * @param queryResult The {@link ResultSet} from a query
   * @param column Name of the column of the BLOB
   * @param encryption_key Key that will be used to encrypt the data
   * @return string of the data stored in the BLOB.
   * @see ResultSet
   */
  public String readFromBLOB(ResultSet queryResult, String column, String encrypted_key) throws SQLException {
    return readFromBLOB(queryResult, queryResult.findColumn(column), encrypted_key);
  }

  public static void main(String[] args) throws SQLException {
    Database db = new Database();
    assert db.hasConnection();
    assert db.hasStatement();
    db.close();
    assert !db.hasConnection();
    assert !db.hasStatement();

    db.makeConnection();
    assert db.hasConnection();

    db.createStatement();
    assert db.hasStatement();

    ResultSet result = null;
    assert db.insertQuery("INSERT INTO user_data(user_name, password) VALUES ('cookie', 'tester')") == 1;
    assert db.insertQuery("INSERT INTO user_data(user_name, password) VALUES ('mstendall', 'tester')")==2;
    result = db.query("SELECT * FROM user_data");
    assert result.next();
    System.out.println(result.getString("user_name"));
    assert result.getString("user_name").compareTo("cookie")==0;
    assert result.getString("password").compareTo("tester")==0;
    assert result.getInt("user_id") == 1;

    assert result.next();
    System.out.println(result.getString("user_name"));
    assert result.getString("user_name").compareTo("mstendall")==0;
    assert result.getString("password").compareTo("tester")==0;
    assert result.getInt("user_id") == 2;

    assert !result.next();
  }
}
