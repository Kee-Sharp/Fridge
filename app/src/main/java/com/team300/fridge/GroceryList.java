package com.team300.fridge;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class GroceryList {
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
}
