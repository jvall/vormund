package edu.cs408.vormund;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.*;

public class Database {
  private static final String SCHEMA_FILE = "/edu/cs408/vormund/SCHEMA.sql";
  //private static final String DATABASE_FILE = ":resource:/edu/cs408/vormund/lib-common2.3.2.jar";
  private static final String DATABASE_FILE = "test.db";

  private Connection conn = null;
  private Statement stmnt = null;
  private PreparedStatement prpstmnt = null;

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
      System.err.println("  HasConn: " + this.hasConnection());
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
      return;
    }
    for(String query : schema.split("\n")) {
      if( this.updateQuery(query) < 0 ) {
        System.out.println("Error in query: " + query);
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
    } catch(ClassNotFoundException | SQLException e) {
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
    } catch(SQLException e) {
      System.out.println("MySQL Insert Error: " + e.getMessage());
      ret=-1;
    } catch(SQLFeatureNotSupportedException e) {
      System.err.println("Error: Get Generated Keys feature is not supported.");
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
      ret = null;
    }
    return ret;
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
   * @param category Category of the data being stored (ie. Facebook, Purdue Federal Bank, etc.)
   * @param type_id ID of the type of data being stored
   * @param data Unencrypted data that will be stored in the database
   * @param encryption_key Key that will be used to encrypt the data
   * @return Number of rows affected. <code>-1</code> if the query was unsuccessful
   */
  public int insertBLOB(int user_id, String category, int type_id, String note, byte[] data, String encryption_key) {
    int ret = -1;
    byte enc_data[] = data; // Needs to run through encryption process
    ByteArrayInputStream bais = new ByteArrayInputStream(enc_data);
    try {
      this.prpstmnt = this.conn.prepareStatement("INSERT INTO " +
          "encrypted_data(user_id, category, type_id, note, encrypted_data) " +
          "VALUES(?, ?, ?, ?, ?)");
      this.prpstmnt.setInt(1, user_id);
      this.prpstmnt.setString(2, category);
      this.prpstmnt.setInt(3, type_id);
      this.prpstmnt.setString(4, note);
      this.prpstmnt.setBlob(5, bais, enc_data.length);
      ret = this.prpstmnt.executeUpdate();
      this.prpstmnt.close();
    } catch(SQLException e) {
      System.out.println("Error inserting BLOB to encrypted_data table:\n\t" + e.getMessage());
      ret = -1;
    } finally {
      this.prpstmnt = null;
      return ret;
    }
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

    assert db.insertQuery("INSERT INTO data_type(type_name, type_value) VALUES('SSN', 'text')")==1;
    ResultSet result = db.query("SELECT * FROM data_type WHERE type_name LIKE 'SSN'");
    assert result.next();
    assert result.getInt("type_id")==6;
    assert result.getString("type_name").compareTo("SSN")==0;
    assert result.getString("type_value").compareTo("text")==0;
    assert !result.next();
    result.close();
    assert result.isClosed();

    assert db.updateQuery("DELETE FROM data_type WHERE type_id=6")==1;
    result = db.query("SELECT * FROM data_type WHERE type_name LIKE 'SSN'");
    assert !result.next();
    result.close();
    assert result.isClosed();

    assert db.insertQuery("INSERT INTO user_data(user_name, password, name) VALUES('test_user', 'test', 'Test McTester')") == 1;
    byte test_array[] = "Hello World".getBytes();
    assert db.insertBLOB(1, "Facebook", 1, "Facebook Username", test_array, "test_pass") == 1;
    result = db.query("SELECT * FROM encrypted_data");
    assert result.next();
    assert result.getInt("data_id")==1;
    assert result.getInt("user_id")==1;
    assert result.getString("category").compareTo("Facebook") == 0;
    assert result.getInt("type_id")==1;
    assert result.getString("note").compareTo("Facebook Username") == 0;
    Blob test_blob = result.getBlob("encrypted_data");
    assert (new String(test_blob.getBytes((long)0, (int)test_blob.length()))).compareTo("Hello World") == 0;
    test_blob.free();
    assert !result.next();
    result.close();
    assert result.isClosed();

    result = db.query("SELECT * FROM user_data INNER JOIN encrypted_data ON " +
        "user_data.user_id=encrypted_data.user_id INNER JOIN data_type ON " +
        "encrypted_data.type_id=data_type.type_id");
    assert result.next();
    assert result.getInt("user_data.user_id") == 1;
    assert result.getString("user_data.user_name").compareTo("test_user") == 0;
    assert result.getInt("encrypted_data.data_id") == 1;
    assert result.getString("encrypted_data.category").compareTo("Facebook") == 0;
    assert result.getInt("encrypted_data.type_id") == 1;
    assert result.getString("data_type.type_name").compareTo("Username") == 0;
    test_blob = result.getBlob("encrypted_data");
    assert (new String(test_blob.getBytes((long)0, (int)test_blob.length()))).compareTo("Hello World") == 0;
    test_blob.free();
    result.close();
    assert result.isClosed();
  }
}
