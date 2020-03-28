package com.cs411databases.databasefinalproject.objects;

import java.sql.Date;

//Transactions (TransactionID, ProductOfferingID, TransactionDate, TransactionPrice, IsReturn)
public class Transaction {
    private String TransactionID;
    private String ProductOfferingID;
    private Date TransactionDate;
    private Double TransactionPrice;
    private boolean IsReturn;

    public Transaction(String transactionID, String productOfferingID, Date transactionDate, Double transactionPrice, boolean isReturn) {
        TransactionID = transactionID;
        ProductOfferingID = productOfferingID;
        TransactionDate = transactionDate;
        TransactionPrice = transactionPrice;
        IsReturn = isReturn;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public String getProductOfferingID() {
        return ProductOfferingID;
    }

    public void setProductOfferingID(String productOfferingID) {
        ProductOfferingID = productOfferingID;
    }

    public Date getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        TransactionDate = transactionDate;
    }

    public Double getTransactionPrice() {
        return TransactionPrice;
    }

    public void setTransactionPrice(Double transactionPrice) {
        TransactionPrice = transactionPrice;
    }

    public boolean isReturn() {
        return IsReturn;
    }

    public void setReturn(boolean aReturn) {
        IsReturn = aReturn;
    }

    @Override
    public String toString() {
        return "TransactionID: " + TransactionID +
                "\nProductOfferingID: " + ProductOfferingID +
                "\nTransactionDate: " + TransactionDate +
                "\nTransactionPrice: " + TransactionPrice +
                "\nIsReturn=" + IsReturn;
    }
}
