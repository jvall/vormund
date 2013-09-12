package edu.cs408.vormund;

public class BankInfo {
    private String bankName;
    private String accountNumber;
    private String routingNumber;
    private String bankAddress;
    private String type; //Used to denote checking vs savings. A standard for this will need to be set

    public BankInfo(BankInfo b) {
        this.accountNumber = b.accountNumber;
        this.routingNumber = b.routingNumber;
        this.bankName = b.bankName;
        this.bankAddress = b.bankAddress;
        this.type = b.type;
    }

    public BankInfo(String accountNumber, String routingNumber, String bankName, String bankAddress, String type) {
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.bankName = bankName;
        this.bankAddress = bankAddress;
        this.type = type;
    }

    public BankInfo(String csvBankVals) {
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getRoutingNumber() {
        return this.routingNumber;
    }

    public String getBankName() {
        return this.bankName;
    }

    public String getBankAddress() {
        return this.bankAddress;
    }

    public String getAccountType() {
        return this.type;
    }

    public static BankInfo serializeCSVDump(String csvBankVals) {
        // MUST be in same order as above constructor
        String vals[] = csvBankVals.split(";");

        return new BankInfo(vals[0], vals[1], vals[2], vals[3], vals[4]);
    }
}

