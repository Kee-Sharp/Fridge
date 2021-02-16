package com.team300.fridge;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by robertwaters on 1/5/17.
 *
 * This is our facade to the Model.  We are using a Singleton design pattern to allow
 * access to the model from each controller.
 *
 *
 */


public class Model {
    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    /** holds the list of the users food items */
    private List<FoodItem> _foodItems;
    /** holds the list of all the food items */
    private List<FoodItem> _allFoodItems;


    /**
     * make a new model
     */
    private Model() {
        _foodItems = new ArrayList<>();
        _allFoodItems = new ArrayList<>();
        //comment this out after full app developed
        loadDummyData();

    }

    /**
     * populate the model with some dummy data.  The full app would not require this.
     * comment out when adding new courses functionality is present.
     */
    private void loadDummyData() {
        Date today = new Date();
        _foodItems.add(new FoodItem("Apple", 1, 5, today));
        _foodItems.add(new FoodItem( "Cheesecake", 7, 1, today));
        _foodItems.add(new FoodItem("Romaine Lettuce", 19, 1, today));
        _foodItems.add(new FoodItem("Eggs", 10, 12, today));
        List<String> names = new ArrayList<>();
        names.add("Apple");
        names.add("Banana");
        names.add("Cheese");
        names.add("Bread");
        names.add("Broccoli");
        names.add("Garlic");
        names.add("Eggs");
        names.add("Chicken");
        names.add("Coffee");
        names.add("Cheesecake");
        names.add("Romaine Lettuce");
        names.add("Ice Cream");
        names.add("Ham");
        names.add("Bacon");
        names.add("Milk");
        names.add("Orange Juice");
        names.add("Lemonade");
        names.add("Grape Fanta");
        names.add("Ketchup");
        names.add("Tomato");
        Collections.sort(names);
        int id = 1;
        for (String n: names) {
            _allFoodItems.add(new FoodItem(n, id++, 1, today));
        }
    }

    /**
     * get the food items
     * @return a list of the food items in the app
     */
    public List<FoodItem> getFoodItems() { return _foodItems; }

    public void setFoodItems(List<FoodItem> foodItems) {
        this._foodItems = foodItems;
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
        for (FoodItem f : _foodItems ) {
            if (f.equals(foodItem)) return false;
        }
        _foodItems.add(foodItem);
        return true;
    }

    /**
     * get the food items
     * @return a list of the food items in the app
     */
    public List<FoodItem> getAllFoodItems() { return _allFoodItems; }

    // method to convert LocalDate into java.util.Date
    public static Date toDate(LocalDate date) {
        return java.util.Date.from(date.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

}
