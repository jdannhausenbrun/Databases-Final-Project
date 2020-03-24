package com.cs411databases.databasefinalproject.objects;

import java.sql.Date;

//Transactions (TransactionID, ProductOfferingID, TransactionDate, TransactionPrice, IsReturn)
public class Transaction {
    private long TransactionID;
    private long ProductOfferingID;
    private Date TransactionDate;
    private float TransactionPrice;
    private boolean IsReturn;

    public long getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(long transactionID) {
        TransactionID = transactionID;
    }

    public long getProductOfferingID() {
        return ProductOfferingID;
    }

    public void setProductOfferingID(long productOfferingID) {
        ProductOfferingID = productOfferingID;
    }

    public Date getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        TransactionDate = transactionDate;
    }

    public float getTransactionPrice() {
        return TransactionPrice;
    }

    public void setTransactionPrice(float transactionPrice) {
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
        return "Transaction{" +
                "TransactionID=" + TransactionID +
                ", ProductOfferingID=" + ProductOfferingID +
                ", TransactionDate=" + TransactionDate +
                ", TransactionPrice=" + TransactionPrice +
                ", IsReturn=" + IsReturn +
                '}';
    }
}
