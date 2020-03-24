package com.cs411databases.databasefinalproject.objects;

//ProductsForSale (ProductOfferingID, RetailerID, ProductID, Price, DiscountPrice)
public class ProductForSale {
    private long ProductOfferingID;
    private long RetailerID;
    private long ProductID;
    private float Price;
    private float DiscountPrice;

    public long getProductOfferingID() {
        return ProductOfferingID;
    }

    public void setProductOfferingID(long productOfferingID) {
        ProductOfferingID = productOfferingID;
    }

    public long getRetailerID() {
        return RetailerID;
    }

    public void setRetailerID(long retailerID) {
        RetailerID = retailerID;
    }

    public long getProductID() {
        return ProductID;
    }

    public void setProductID(long productID) {
        ProductID = productID;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public float getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(float discountPrice) {
        DiscountPrice = discountPrice;
    }

    @Override
    public String toString() {
        return "ProductForSale{" +
                "ProductOfferingID=" + ProductOfferingID +
                ", RetailerID=" + RetailerID +
                ", ProductID=" + ProductID +
                ", Price=" + Price +
                ", DiscountPrice=" + DiscountPrice +
                '}';
    }
}
