package com.team300.fridge;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroceryList implements Parcelable {
    private String name;
    private Date createdOn;
    private List<FoodItem> items;

    public GroceryList(String name, List<FoodItem> items) {
        if (name == null) {
            throw new InvalidParameterException("Name must not be null");
        } else if (items.size() == 0) {
            throw new InvalidParameterException("Items must not be empty");
        } else {
            this.name = name;
            this.createdOn = new Date();
            this.items = items;
        }
    }
    //create FoodItem from parcel information
    private GroceryList(Parcel in){
        this.name = in.readString();
        this.createdOn = (java.util.Date)in.readSerializable();
        items = new ArrayList<>();
        in.readTypedList(items, FoodItem.CREATOR);
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
        out.writeSerializable(createdOn);
        out.writeTypedList(items);
    }
    //need creator field for new items
    private static final Parcelable.Creator<GroceryList> CREATOR
            = new Parcelable.Creator<GroceryList>(){
        @Override
        public GroceryList createFromParcel(Parcel in){
            return new GroceryList(in);
        }
        @Override
        public GroceryList[] newArray(int size){
            return new GroceryList[size];
        }
    };
//------------------------------------------------

    public String getName() {
        return name;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public List<FoodItem> getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedOn(Date createdOn) {
        //shouldnt actually be used, only added for dummy data
        this.createdOn = createdOn;
    }

    public void setItems(List<FoodItem> items) {
        this.items = items;
    }

    public void addItem(FoodItem item) {
        this.items = FoodItem.addFoodItemToList(items, item);
    }

    @Override
    public String toString() {
        return "GroceryList{" +
                "name='" + name + '\'' +
                ", createdOn=" + createdOn +
                ", items=" + items +
                '}';
    }
}
