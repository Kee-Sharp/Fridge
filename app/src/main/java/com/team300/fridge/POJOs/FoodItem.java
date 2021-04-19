package com.team300.fridge.POJOs;


import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class FoodItem extends RealmObject implements Parcelable, Cloneable<FoodItem> {
    @Required
    private String object_id = "user1";

    @PrimaryKey
    private ObjectId _id;
    private String name;
    private Product productId; //based on FoodKeeper Data
    private int quantity; //unit quantity of item
    private String location; //TODO: turn location into enum with "Fridge", "Pantry", "Freezer"
    private Date purchaseDate; //date of purchase
    private int status; //status of FoodItem, whether it is in grocery list (0), in stock(1), eaten(2), or thrown away(3)


    public static RealmList<com.team300.fridge.POJOs.FoodItem> addFoodItemToList(RealmList<com.team300.fridge.POJOs.FoodItem> items, com.team300.fridge.POJOs.FoodItem item) {
        items.add(item);
        return items;
    }
    public FoodItem(String name, Product productId, int quantity, Date purchaseDate) {
        if (name != null && productId != null && quantity > 0) {
            this._id = ObjectId.get();
            this.name = name;
            this.productId = productId;
            this.quantity = quantity;
            this.purchaseDate = purchaseDate;
            this.status = 1;
        }
    }

    public FoodItem(){
        this("default", new Product("Coffee creamer", "liquid refrigerated", "Dairy Products & Eggs", "Coffee creamer,Coffee, creamer,liquid refrigerated", -1, 21, -1, 2.0), 1, null);
    }

    //create FoodItem from parcel information
    private FoodItem(Parcel in){
        object_id = in.readString();
        _id = (ObjectId) in.readSerializable();
        name = in.readString();
        productId = (Product)in.readTypedObject(Product.CREATOR);
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
        out.writeString(object_id);
        out.writeSerializable(_id);
        out.writeString(name);
        out.writeValue((Object)productId);
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

    public String getObject_id(){return object_id;}

    public ObjectId get_id(){return _id;}

    public String getName() {
        return name;
    }

    public Product getProductId() {
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

    public void setProductId(Product productId) {
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

    public long getDaysTilExpiration() {
        LocalDate today = LocalDate.now();
        //This assumes the expiration date is 10 days after the purchase date
        //The actual date will be calculated from our database
        LocalDate expiration = Model.toLocalDate(purchaseDate).plusDays(10);
        return ChronoUnit.DAYS.between(today, expiration);
    }

    public String getExpiresInText() {
        long between = getDaysTilExpiration();
        if (between > 0) {
            if (between < 7) {
                return "" + between + (between > 1 ? " days" : " day");
            } else {
                int numWeeks = (int) (between / 7);
                return ""  + numWeeks + (numWeeks > 1 ? " weeks" : " week");
            }
        } else if (between == 0) {
            return "Today!";
        } else {
            return "" + between * -1 + (between < -1 ? " DAYS AGO" : " DAY AGO");
        }
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "object_id="+object_id+'\''+
                "_id="+_id.toString()+'\''+
                "name='" + name + '\'' +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", location='" + location + '\'' +
                ", purchaseDate=" + purchaseDate +
                '}';
    }

    public static RealmList<FoodItem> addFoodItemToList(List<FoodItem> items, FoodItem newItem) {
        RealmList<FoodItem> newList = new RealmList<FoodItem>();
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

