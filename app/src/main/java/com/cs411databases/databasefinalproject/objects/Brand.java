package com.cs411databases.databasefinalproject.objects;

import java.util.Arrays;
import java.util.List;

//Brands (BrandID, BrandName)
public class Brand implements DatabaseObject {
    private int BrandID;
    private String BrandName;
    private static List<String> attributeNames = Arrays.asList("BrandName");

    public Brand(int brandID, String brandName) {
        BrandID = brandID;
        BrandName = brandName;
    }

    public int getID() {
        return BrandID;
    }

    public String getIDColumnName() {
        return "BrandID";
    }

    public String getAttributeName(int index) {
        return attributeNames.get(index - 1);
    }

    public int getBrandID() {
        return BrandID;
    }

    public void setBrandID(int brandID) {
        BrandID = brandID;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    @Override
    public String toString() {
        return "BrandID: " + BrandID +
                "\nBrandName: " + BrandName;
    }
}
