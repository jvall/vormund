package edu.cs408.vormund;

public class SSNInfo {
    private String name;
    private String ssn;
    private int recordID;

    public SSNInfo(SSNInfo s) {
        this.name = s.name;
        this.ssn = s.ssn;
        this.recordID = s.recordID;
    }

    public SSNInfo(String name, String ssn, int recordID) {
    	this.name = name;
        this.ssn = ssn;
        this.recordID = recordID;
    }

    public SSNInfo(String csvSSNVals) {
    }

    public int getRecordID() {
      return this.recordID;
    }

    public String getName() {
    	return this.name;
    }

    public String getSSN() {
    	return this.ssn;
    }

    public String toString() {
        return name + ";" + ssn;
    }

    public static SSNInfo serializeCSVDump(String csvSSNVals, int recordID) {
        // MUST be in same order as above constructor
        String vals[] = csvSSNVals.split(";");
        return new SSNInfo(vals[0], vals[1], recordID);
    }
}

