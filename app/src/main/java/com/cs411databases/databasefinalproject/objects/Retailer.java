package com.cs411databases.databasefinalproject.objects;

//Retailers (RetailerID, RetailerName, RetailerCategory)
public class Retailer {
    private String RetailerID;
    private String RetailerName;
    private String RetailerCategory;

    public Retailer(String retailerID, String retailerName, String retailerCategory) {
        RetailerID = retailerID;
        RetailerName = retailerName;
        RetailerCategory = retailerCategory;
    }

    public String getRetailerID() {
        return RetailerID;
    }

    public void setRetailerID(String retailerID) {
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
        return "RetailerID: " + RetailerID +
                "\nRetailerName: " + RetailerName +
                "\nRetailerCategory: " + RetailerCategory;
    }
}
