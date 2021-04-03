package com.team300.fridge.POJOs;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodItem implements Parcelable, Cloneable<FoodItem> {
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
    //create FoodItem from parcel information
    private FoodItem(Parcel in){
        name = in.readString();
        productId = in.readInt();
        quantity = in.readInt();
        location = in.readString();
        purchaseDate = (java.util.Date)in.readSerializable();
    }
//------------------------------------Parcelable //TODO: ensure correct data is passed in/out
    //rarely need this for anything
    @Override
    public int describeContents(){
        return this.hashCode();
    }
    //write necessary data
    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(name);
        out.writeInt(productId);
        out.writeInt(quantity);
        out.writeString(location);
        out.writeSerializable(purchaseDate);
    }
    //need creator field for new items
    public static final Parcelable.Creator<FoodItem> CREATOR
            = new Parcelable.Creator<FoodItem>(){
        @Override
        public FoodItem createFromParcel(Parcel in){
            return new FoodItem(in);
        }
        @Override
        public FoodItem[] newArray(int size){
            return new FoodItem[size];
        }
    };
//------------------------------------------------
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

    @Override
    public String toString() {
        return "FoodItem{" +
                "name='" + name + '\'' +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", location='" + location + '\'' +
                ", purchaseDate=" + purchaseDate +
                '}';
    }

    public static List<FoodItem> addFoodItemToList(List<FoodItem> items, FoodItem newItem) {
        List<FoodItem> newList = new ArrayList<>();
        boolean duplicateFound = false;
        // if already in list update its' quantity, else just add it
        for (FoodItem f: items) {
            if (newItem.getName().equals(f.getName())) {
                duplicateFound = true;
                f.setQuantity(f.getQuantity() + newItem.getQuantity());
            }
            newList.add(f);
        }
        if (!duplicateFound) {
            newList.add(newItem);
        }
        return newList;
    }

    @Override
    public FoodItem clone() {
        return new FoodItem(name, productId, quantity, purchaseDate);
    }
}

