package edu.cs408.vormund;

public class WebInfo {
    private String name;
    private String url;
    private String email;
    private String userName;
    private String password;
    private String [][] securityQAPairs;

    public WebInfo(WebInfo w) {
        this.name = w.name;
        this.url = w.url;
        this.email = w.email;
        this.userName = w.userName;
        this.password = w.password;
        this.securityQAPairs = w.securityQAPairs;
    }

    public WebInfo(String name, String url, String email, String userName, String password, String[][] securityQAPairs) {
    	this.name = name;
        this.url = url;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.securityQAPairs = securityQAPairs;
    }

    public WebInfo(String csvWebVals) {
    }

    public String getName() {
    	return this.name;
    }

    public String getUrl() {
    	return this.url;
    }

    public String getEmail() {
    	return this.email;
    }

    public String getUserName() {
    	return this.userName;
    }

    public String getPassword() {
    	return this.password;
    }

    public String[][] getSecurityQs() {
    	return this.securityQAPairs;
    }

    public static WebInfo serializeCSVDump(String csvWebVals) {
        // MUST be in same order as above constructor
        String vals[] = csvWebVals.split(";");

        int numQAs = (vals.length - 5) / 2;

        String [][] QAPairs = new String [numQAs][2];

        //This NEEDS testing.
        for(int i = 0; i < numQAs; i++)
        {
        	QAPairs[i][0] = vals[i*2+5];
        	QAPairs[i][1] = vals[i*2+6];
        }

        return new WebInfo(vals[0], vals[1], vals[2], vals[3], vals[4], QAPairs);
    }
}

