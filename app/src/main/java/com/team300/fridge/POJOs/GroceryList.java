package com.team300.fridge.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class GroceryList extends RealmObject implements Parcelable, Cloneable<GroceryList> {
    @Required
    private String object_id;

    @PrimaryKey
    private int _id;
    private String name;
    private Date createdOn;
    private RealmList<FoodItem> items;

    public GroceryList(String object_id, String name, RealmList<FoodItem> items) {
        if (name == null) {
            throw new InvalidParameterException("Name must not be null");
        } else if (items.size() == 0) {
            throw new InvalidParameterException("Items must not be empty");
        } else {
            this.object_id = object_id;
            this.name = name;
            this.createdOn = new Date();
            this.items = items;
        }
    }

    public GroceryList(String name, RealmList<FoodItem> items) {
        Model model = Model.getInstance();
        if (name == null) {
            throw new InvalidParameterException("Name must not be null");
        } else if (items.size() == 0) {
            throw new InvalidParameterException("Items must not be empty");
        } else {
            this.object_id = model.getCurrentUser().getEmail();
            this.name = name;
            this.createdOn = new Date();
            this.items = items;
        }
    }

    public GroceryList(String name, List<FoodItem> items) {
        Model model = Model.getInstance();
        if (name == null) {
            throw new InvalidParameterException("Name must not be null");
        } else if (items.size() == 0) {
            throw new InvalidParameterException("Items must not be empty");
        } else {
            this.object_id = model.getCurrentUser().getEmail();
            this.name = name;
            this.createdOn = new Date();
            this.items = new RealmList<FoodItem>();
            ListIterator<FoodItem> iterator = items.listIterator();

            while(iterator.hasNext()) {
                this.items.add(iterator.next());
            }
        }
    }

    public GroceryList(String object_id, String name, List<FoodItem> items) {
        if (name == null) {
            throw new InvalidParameterException("Name must not be null");
        } else if (items.size() == 0) {
            throw new InvalidParameterException("Items must not be empty");
        } else {
            this.object_id = object_id;
            this.name = name;
            this.createdOn = new Date();
            this.items = new RealmList<FoodItem>();
            ListIterator<FoodItem> iterator = items.listIterator();

            while(iterator.hasNext()) {
                this.items.add(iterator.next());
            }
        }
    }


    public GroceryList(){this("default", new RealmList<FoodItem>());}

    //create FoodItem from parcel information
    private GroceryList(Parcel in){
        this.object_id = in.readString();
        this.name = in.readString();
        this.createdOn = (java.util.Date)in.readSerializable();
        items = new RealmList<FoodItem>();
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
        out.writeString(object_id);
        out.writeString(name);
        out.writeSerializable(createdOn);
        out.writeTypedList(items);
    }
    //need creator field for new items
    public static final Parcelable.Creator<GroceryList> CREATOR
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

    public void setItems(RealmList<FoodItem> items) {
        this.items = items;
    }

    public void addItem(FoodItem item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "GroceryList{" +
                "name='" + name + '\'' +
                ", createdOn=" + createdOn +
                ", items=" + items +
                '}';
    }

    @Override
    public GroceryList clone() {
        List<FoodItem> newItems = new ArrayList<>();
        for (FoodItem item: items) {
            newItems.add(item.clone());
        }
        GroceryList ret = new GroceryList(object_id, name, newItems);
        ret.setCreatedOn(createdOn);
        return ret;
    }
}
