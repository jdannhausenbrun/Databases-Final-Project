package com.cs411databases.databasefinalproject.objects;

//Products (ProductID, BrandID, ProductName, ProductType)
public class Product {
    private long ProductID;
    private long BrandID;
    private String ProductName;
    private String ProductType;

    public long getProductID() {
        return ProductID;
    }

    public void setProductID(long productID) {
        ProductID = productID;
    }

    public long getBrandID() {
        return BrandID;
    }

    public void setBrandID(long brandID) {
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
        return "Product{" +
                "ProductID=" + ProductID +
                ", BrandID=" + BrandID +
                ", ProductName='" + ProductName + '\'' +
                ", ProductType='" + ProductType + '\'' +
                '}';
    }
}
