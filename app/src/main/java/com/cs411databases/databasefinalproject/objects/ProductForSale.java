package com.cs411databases.databasefinalproject.objects;

//ProductsForSale (ProductOfferingID, RetailerID, ProductID, Price, DiscountPrice)
public class ProductForSale {
    private String ProductOfferingID;
    private String RetailerID;
    private String ProductID;
    private Double Price;
    private Double DiscountPrice;

    public ProductForSale(String productOfferingID, String retailerID, String productID, Double price, Double discountPrice) {
        ProductOfferingID = productOfferingID;
        RetailerID = retailerID;
        ProductID = productID;
        Price = price;
        DiscountPrice = discountPrice;
    }

    public String getProductOfferingID() {
        return ProductOfferingID;
    }

    public void setProductOfferingID(String productOfferingID) {
        ProductOfferingID = productOfferingID;
    }

    public String getRetailerID() {
        return RetailerID;
    }

    public void setRetailerID(String retailerID) {
        RetailerID = retailerID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Double getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        DiscountPrice = discountPrice;
    }

    @Override
    public String toString() {
        return "ProductOfferingID: " + ProductOfferingID +
                "\nRetailerID: " + RetailerID +
                "\nProductID: " + ProductID +
                "\nPrice: " + Price +
                "\nDiscountPrice: " + DiscountPrice;
    }
}
