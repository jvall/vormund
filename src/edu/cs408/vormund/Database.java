package edu.cs408.vormund;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.*;

public class Database {
  private static final String SCHEMA_FILE = "/edu/cs408/vormund/SCHEMA.sql";
  private static final String DATABASE_FILE = ":resource:/edu/cs408/vormund/lib-common2.3.2.jar";
  //private static final String DATABASE_FILE = "test.db";

  private Connection conn = null;
  private Statement stmnt = null;

  public Database() {
    try {
      Class.forName("org.sqlite.JDBC");
      this.conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);
      this.stmnt = this.conn.createStatement();
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
      if( this.executeUpdate(query) < 0 ) {
        System.out.println("Error in query: " + query);
      }
    }
  }

  public boolean hasConnection() { return this.conn==null; }

  public int executeUpdate(String query) {
    int result=-1;
    try {
      if( this.stmnt==null ) this.stmnt = this.conn.createStatement();
      result = this.stmnt.executeUpdate(query);
    } catch(SQLException e) {
      System.out.println("ERROR: " + e.getMessage());
      result=-1;
    }
    return result;
  }

  public void close() {
    if( this.conn != null ) {
      try {
        this.conn.close();
      } catch(Exception e) {
      } finally {
        this.conn = null;
      }
    }
  }

  /*public static void main(String[] args) {
    Database db = new Database();
    db.close();
  }*/
}
