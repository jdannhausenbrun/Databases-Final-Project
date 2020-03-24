package com.cs411databases.databasefinalproject.objects;

//Brands (BrandID, BrandName)
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

    @Override
    public String toString() {
        return "Brand{" +
                "BrandID=" + BrandID +
                ", BrandName='" + BrandName + '\'' +
                '}';
    }
}
