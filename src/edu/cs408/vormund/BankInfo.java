package edu.cs408.vormund;

public class BankInfo {
    private int accountNumber;
    private int routingNumber;
    private String bankName;
    private String bankLocation;
    private String type;

    public BankInfo(BankInfo b) {
        this.accountNumber = b.accountNumber;
        this.routingNumber = b.routingNumber;
        this.bankName = b.bankName;
        this.bankLocation = b.bankLocation;
        this.type = b.type;
    }

    public BankInfo(int actNum, int rtNum, String bkNm,
                    String bkLoc, String t) {
        this.accountNumber = actNum;
        this.routingNumber = rtNum;
        this.bankName = bkNm;
        this.bankLocation = bkLoc;
        this.type = t;
    }

    public BankInfo(String csvBankVals) {
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public int getRoutingNumber() {
        return this.routingNumber;
    }

    public String getBankName() {
        return this.bankName;
    }

    public String getBankLocation() {
        return this.bankLocation;
    }

    public String getAccountType() {
        return this.type;
    }

    public static BankInfo serializeCSVDump(String csvBankVals) {
        // MUST be in same order as above constructor
        String vals[] = csvBankVals.split(",");
        int actNum = -1, rtNum = -1;
        try {
            actNum = Integer.parseInt(vals[0]);
            rtNum = Integer.parseInt(vals[1]);
        } catch (Exception e) {
            System.err.println("Formatting exception occurred: " + e);
        }

        return new BankInfo(actNum, rtNum, vals[2], vals[3], vals[4]);
    }
}

