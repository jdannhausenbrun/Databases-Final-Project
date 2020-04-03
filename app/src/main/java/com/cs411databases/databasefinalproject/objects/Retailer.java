package com.cs411databases.databasefinalproject.objects;

import java.util.Arrays;
import java.util.List;

//Retailers (RetailerID, RetailerName, RetailerCategory)
public class Retailer implements DatabaseObject {
    private int RetailerID;
    private String RetailerName;
    private String RetailerCategory;
    private static List<String> attributeNames = Arrays.asList("RetailerName", "RetailerCategory");

    public Retailer(int retailerID, String retailerName, String retailerCategory) {
        RetailerID = retailerID;
        RetailerName = retailerName;
        RetailerCategory = retailerCategory;
    }

    public int getID() {
        return RetailerID;
    }

    public String getIDColumnName() {
        return "RetailerID";
    }

    public String getAttributeName(int index) {
        return attributeNames.get(index - 1);
    }

    public int getRetailerID() {
        return RetailerID;
    }

    public void setRetailerID(int retailerID) {
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
