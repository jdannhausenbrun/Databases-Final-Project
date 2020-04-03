package com.cs411databases.databasefinalproject.objects;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

//Transactions (TransactionID, ProductOfferingID, TransactionDate, TransactionPrice, IsReturn)
public class Transaction implements DatabaseObject {
    private int TransactionID;
    private int ProductOfferingID;
    private Date TransactionDate;
    private Double TransactionPrice;
    private boolean IsReturn;
    private static List<String> attributeNames = Arrays.asList("ProductOfferingID", "TransactionDate", "TransactionPrice", "IsReturn");

    public Transaction(int transactionID, int productOfferingID, Date transactionDate, Double transactionPrice, boolean isReturn) {
        TransactionID = transactionID;
        ProductOfferingID = productOfferingID;
        TransactionDate = transactionDate;
        TransactionPrice = transactionPrice;
        IsReturn = isReturn;
    }

    public int getID() {
        return TransactionID;
    }

    public String getIDColumnName() {
        return "TransactionID";
    }

    public String getAttributeName(int index) {
        return attributeNames.get(index - 1);
    }

    public int getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(int transactionID) {
        TransactionID = transactionID;
    }

    public int getProductOfferingID() {
        return ProductOfferingID;
    }

    public void setProductOfferingID(int productOfferingID) {
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
        String isReturnText = "";
        if(IsReturn) {
            isReturnText = "Yes";
        } else {
            isReturnText = "No";
        }
        return "TransactionID: " + TransactionID +
                "\nProductOfferingID: " + ProductOfferingID +
                "\nTransactionDate: " + TransactionDate +
                "\nTransactionPrice: " + TransactionPrice +
                "\nIsReturn: " + isReturnText;
    }
}
