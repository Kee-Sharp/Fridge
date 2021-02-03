package com.team300.fridge;

import java.util.ArrayList;
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

    /** holds the list of all the food items */
    private List<FoodItem> _foodItems;


    /**
     * make a new model
     */
    private Model() {
        _foodItems = new ArrayList<>();

        //comment this out after full app developed -- for homework leave in
        loadDummyData();

    }

    /**
     * populate the model with some dummy data.  The full app would not require this.
     * comment out when adding new courses functionality is present.
     */
    private void loadDummyData() {
        Date today = new Date();
        _foodItems.add(new FoodItem("Apple", 001, 5, today));
        _foodItems.add(new FoodItem( "Cheesecake", 002, 1, today));
        _foodItems.add(new FoodItem("Romaine Lettuce", 003, 1, today));
        _foodItems.add(new FoodItem("Eggs", 004, 12, today));
    }

    /**
     * get the food items
     * @return a list of the food items in the app
     */
    public List<FoodItem> getFoodItems() { return _foodItems; }

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

}
