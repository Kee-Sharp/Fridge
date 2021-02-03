package com.team300.fridge;


import java.util.Date;

public class FoodItem {
    private String name;
    private int productId; //based on FoodKeeper Data
    private int quantity; //unit quantity of item
    private String location; //TODO: turn location into enum with "Fridge", "Pantry", "Freezer"
    private Date purchaseDate; //date of purchase

    public FoodItem(String name, int productId, int quantity, Date purchaseDate) {
        if (name != null && productId > 0 && quantity > 0) {
            this.name = name;
            this.productId = productId;
            this.quantity = quantity;
            this.purchaseDate = purchaseDate;
        }
    }

    private Date findBestByDate() {
        return null;
    }
    public String getName() {
        return name;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}

