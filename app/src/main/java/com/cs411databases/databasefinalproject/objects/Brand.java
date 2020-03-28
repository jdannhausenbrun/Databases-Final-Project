package com.cs411databases.databasefinalproject.objects;

//Brands (BrandID, BrandName)
public class Brand {
    private String BrandID;
    private String BrandName;

    public Brand(String brandID, String brandName) {
        BrandID = brandID;
        BrandName = brandName;
    }

    public String getBrandID() {
        return BrandID;
    }

    public void setBrandID(String brandID) {
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
