package com.cs411databases.databasefinalproject.objects;

public class Brand {
    private long BrandID;
    private String BrandName;

    public long getBrandID() {
        return BrandID;
    }

    public void setBrandID(long brandID) {
        BrandID = brandID;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }
}
