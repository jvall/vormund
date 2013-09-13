package edu.cs408.vormund;

public class SSNInfo {
    private String name;
    private String ssn;

    public SSNInfo(SSNInfo s) {
        this.name = s.name;
        this.ssn = s.ssn;
    }

    public SSNInfo(String name, String ssn) {
    	this.name = name;
        this.ssn = ssn;
    }

    public SSNInfo(String csvSSNVals) {
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

    public static SSNInfo serializeCSVDump(String csvSSNVals) {
        // MUST be in same order as above constructor
        String vals[] = csvSSNVals.split(";");

        return new SSNInfo(vals[0], vals[1]);
    }
}

