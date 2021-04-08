package com.team300.fridge.POJOs;

import android.util.Log;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;


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
    private List<FoodItem> _allFoodItems;

    /** holds all of the app's users **/
    private List<User> _users;

    /** points to the current User **/
    private User currentUser;

    private final Random r = new Random(1);


    /**
     * make a new model
     */
    private Model() {
        _allFoodItems = new ArrayList<>();
        _users = new ArrayList<>();
        currentUser = null;
        //comment this out after full app developed
        loadDummyData();

    }

    /**
     * populate the model with some dummy data.  The full app would not require this.
     * comment out when adding new courses functionality is present.
     */
    private void loadDummyData() {
        //create list of "all" food items, using this list of names
        Date today = new Date();
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
        //create list of "all" grocery lists
        List<GroceryList> allGroceryLists = new ArrayList<>();
        GroceryList groceryList1 = new GroceryList("List 1", generateSubset(_allFoodItems));
        groceryList1.setCreatedOn(Model.toDate(LocalDate.of(2021, 1, 14)));
        allGroceryLists.add(groceryList1);
        GroceryList groceryList2 = new GroceryList("List 2", generateSubset(_allFoodItems));
        groceryList2.setCreatedOn(Model.toDate(LocalDate.of(2021, 2, 27)));
        allGroceryLists.add(groceryList2);
        GroceryList groceryList3 = new GroceryList("List 3", generateSubset(_allFoodItems));
        groceryList3.setCreatedOn(Model.toDate(LocalDate.of(2021, 3, 18)));
        allGroceryLists.add(groceryList3);
        GroceryList groceryList4 = new GroceryList("List 4", generateSubset(_allFoodItems));
        groceryList4.setCreatedOn(Model.toDate(LocalDate.of(2021, 4, 2)));
        allGroceryLists.add(groceryList4);

        //the food items and grocery lists that will be used for each of our 5 users
        List<FoodItem>[] foodItemLists = new ArrayList[5];
        List<GroceryList>[] groceryListLists = new ArrayList[5];

        //fill these in with random values
        for (int i = 0; i < 5; i++) {
            List<FoodItem> foodItemList = generateSubset(_allFoodItems);
            for (FoodItem item: foodItemList) {
                //mix up the quantity so it's not all 1
                item.setQuantity(r.nextInt(20) + 1);
            }
            foodItemLists[i] = foodItemList;
            groceryListLists[i] = generateSubset(allGroceryLists);
        }

        _users.add(new User("Abigail Gutierrez-Ray", "aagray@gatech.edu", "password1", foodItemLists[0], groceryListLists[0]));
        _users.add(new User("Deepti Vaidyanathan", "deeptivaidyanathan@gatech.edu", "password2", foodItemLists[1], groceryListLists[1]));
        _users.add(new User("Miranda Bisson", "mbisson3@gatech.edu", "password3", foodItemLists[2], groceryListLists[2]));
        _users.add(new User("Spencer Kee", "skee8@gatech.edu", "password4", foodItemLists[3], groceryListLists[3]));
        _users.add(new User("Tori Kraj", "victoria.kraj@gatech.edu", "password5", foodItemLists[4], groceryListLists[4]));
        for (User u: _users) {
            Log.d("user hash", u.getName() + " Hash: " + u.getPasshash());
        }
//        Log.d("aag food items", _users.get(0).getFoodItems().toString());
    }


    /**
     * get the food items
     * @return a list of the food items in the app
     */
    public List<FoodItem> getAllFoodItems() { return _allFoodItems; }


    public User getCurrentUser() {
        return currentUser;
    }

    public void switchUser(User user) {
        currentUser = user;
    }

    public List<User> getUsers() {
        return _users;
    }

    // method to convert LocalDate into java.util.Date
    public static Date toDate(LocalDate date) {
        return java.util.Date.from(date.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    //helper method for setting dummy data
    //generates random binary string of length n represented by a boolean array
    private boolean[] binaryString(int n) {
        boolean[] string = new boolean[n];
        for (int i = 0; i < n; i++) {
            string[i] = r.nextBoolean();
        }
        return string;
    }

    //helper method for setting dummy data
    //returns a random subset of the objects passed in
    private <T extends Cloneable<T>> List<T> generateSubset(List<T> objects) {
        List<T> ret = new ArrayList<>();
        int n = objects.size();
        boolean[] string = binaryString(n);
        for (int i = 0; i < n; i++) {
            if (string[i]) ret.add(objects.get(i).clone());
        }
        return ret;
    }

}
