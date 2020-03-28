package com.cs411databases.databasefinalproject.objects;

//Products (ProductID, BrandID, ProductName, ProductType)
public class Product {
    private String ProductID;
    private String BrandID;
    private String ProductName;
    private String ProductType;

    public Product(String productID, String brandID, String productName, String productType) {
        ProductID = productID;
        BrandID = brandID;
        ProductName = productName;
        ProductType = productType;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getBrandID() {
        return BrandID;
    }

    public void setBrandID(String brandID) {
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
