package com.team300.fridge.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.InvalidParameterException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User implements Parcelable {

    private String name;
    private String email;
    private int passhash;
    private List<DayOfWeek> notificationDates;
    private List<FoodItem> foodItems;
    private List<GroceryList> groceryLists;

    public User(String name, String email, String password) {
        if (name == null) {
            throw new InvalidParameterException("Name must not be null");
        } else if (email == null || !email.matches("^.+@.+\\..+")) {
            throw new InvalidParameterException("Invalid email");
        } else {
            this.name = name;
            this.email = email;
            this.passhash = hash(password);
            this.notificationDates = new ArrayList<>();
        }
    }

    public User(String name, String email, String password, List<FoodItem> foodItems, List<GroceryList> groceryLists) {
        this(name, email, password);
        this.foodItems = foodItems;
        this.groceryLists = groceryLists;
    }

    //create User from Parcel information
    private User(Parcel in) {
        name = in.readString();
        email = in.readString();
        notificationDates = new ArrayList<>();
        String[] dates = (String[]) in.readArray(String.class.getClassLoader());
        for (String date : dates) {
            notificationDates.add(DayOfWeek.valueOf(date));
        }
        foodItems = new ArrayList<>();
        in.readTypedList(foodItems, FoodItem.CREATOR);
        groceryLists = new ArrayList<>();
        in.readTypedList(groceryLists, GroceryList.CREATOR);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        List<String> dates = new ArrayList<>();
        for (DayOfWeek day : notificationDates) {
            dates.add(day.toString());
        }
        dest.writeArray(dates.toArray());
        dest.writeTypedList(foodItems);
        dest.writeTypedList(groceryLists);
    }

    //need creator field for new items
    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>(){
        @Override
        public User createFromParcel(Parcel in){
            return new User(in);
        }
        @Override
        public User[] newArray(int size){
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPasshash() {
        return passhash;
    }

    public static int hash(String password) {
        int[] primes = {17, 41, 13, 53, 29};
        int result = 0;
        for (int i = 0; i < password.length(); i++) {
            result += password.charAt(i) * primes[i % primes.length];
        }
        return result;
    }

    public List<DayOfWeek> getNotificationDates() {
        return notificationDates;
    }

    //returns true if the new day was added
    public boolean addDate(DayOfWeek dow) {
        if (notificationDates.contains(dow)) {
            return false;
        } else {
            notificationDates.add(dow);
            Collections.sort(notificationDates);
            return true;
        }
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    /**
     * add a food item to the app.  checks if the food item is already entered
     *
     * uses O(n) linear search for food item
     *
     * @param foodItem  the food item to be added
     * @return true if added, false if a duplicate
     */
    public boolean addFoodItem(FoodItem foodItem) {
        for (FoodItem f : foodItems ) {
            if (f.equals(foodItem)) return false;
        }
        foodItems.add(foodItem);
        return true;
    }

    public List<GroceryList> getGroceryLists() {
        return groceryLists;
    }

    public void setGroceryLists(List<GroceryList> groceryLists) {
        this.groceryLists = groceryLists;
    }

    public void addGroceryList(GroceryList newGroceryList) {
        groceryLists.add(newGroceryList);
    }

    public boolean removeDate(DayOfWeek dow) {
        return notificationDates.remove(dow);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", notificationDates=" + notificationDates +
                ", foodItems=" + foodItems +
                ", groceryLists=" + groceryLists +
                '}';
    }
}
