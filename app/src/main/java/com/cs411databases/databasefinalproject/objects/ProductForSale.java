package com.cs411databases.databasefinalproject.objects;

import java.util.Arrays;
import java.util.List;

//ProductsForSale (ProductOfferingID, RetailerID, ProductID, Price, DiscountPrice)
public class ProductForSale implements DatabaseObject {
    private int ProductOfferingID;
    private int RetailerID;
    private int ProductID;
    private Double Price;
    private Double DiscountPrice;
    private static List<String> attributeNames = Arrays.asList("RetailerID", "ProductID", "Price", "DiscountPrice");

    public ProductForSale(int productOfferingID, int retailerID, int productID, Double price, Double discountPrice) {
        ProductOfferingID = productOfferingID;
        RetailerID = retailerID;
        ProductID = productID;
        Price = price;
        DiscountPrice = discountPrice;
    }

    public int getID() {
        return ProductOfferingID;
    }

    public String getIDColumnName() {
        return "ProductOfferingID";
    }

    public String getAttributeName(int index) {
        return attributeNames.get(index - 1);
    }

    public int getProductOfferingID() {
        return ProductOfferingID;
    }

    public void setProductOfferingID(int productOfferingID) {
        ProductOfferingID = productOfferingID;
    }

    public int getRetailerID() {
        return RetailerID;
    }

    public void setRetailerID(int retailerID) {
        RetailerID = retailerID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
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
