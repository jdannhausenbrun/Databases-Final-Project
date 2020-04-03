package com.cs411databases.databasefinalproject.objects;

import java.util.Arrays;
import java.util.List;

//Products (ProductID, BrandID, ProductName, ProductType)
public class Product implements DatabaseObject {
    private int ProductID;
    private int BrandID;
    private String ProductName;
    private String ProductType;
    private static List<String> attributeNames = Arrays.asList("BrandID", "ProductName", "ProductType");

    public Product(int productID, int brandID, String productName, String productType) {
        ProductID = productID;
        BrandID = brandID;
        ProductName = productName;
        ProductType = productType;
    }

    public int getID() {
        return ProductID;
    }

    public String getIDColumnName() {
        return "ProductID";
    }

    public String getAttributeName(int index) {
        return attributeNames.get(index - 1);
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getBrandID() {
        return BrandID;
    }

    public void setBrandID(int brandID) {
        BrandID = brandID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    @Override
    public String toString() {
        return "ProductID: " + ProductID +
                "\nBrandID: " + BrandID +
                "\nProductName: " + ProductName +
                "\nProductType: " + ProductType;
    }
}
