package com.cs411databases.databasefinalproject.objects;

//Retailers (RetailerID, RetailerName, RetailerCategory)
public class Retailer {
    private long RetailerID;
    private String RetailerName;
    private String RetailerCategory;

    public long getRetailerID() {
        return RetailerID;
    }

    public void setRetailerID(long retailerID) {
        RetailerID = retailerID;
    }

    public String getRetailerName() {
        return RetailerName;
    }

    public void setRetailerName(String retailerName) {
        RetailerName = retailerName;
    }

    public String getRetailerCategory() {
        return RetailerCategory;
    }

    public void setRetailerCategory(String retailerCategory) {
        RetailerCategory = retailerCategory;
    }

    @Override
    public String toString() {
        return "Retailer{" +
                "RetailerID=" + RetailerID +
                ", RetailerName='" + RetailerName + '\'' +
                ", RetailerCategory='" + RetailerCategory + '\'' +
                '}';
    }
}
