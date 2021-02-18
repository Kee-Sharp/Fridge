package com.team300.fridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected FridgeListAdapter mAdapter;
    private static final int ADD_FOOD_ITEM_REQUEST = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //populate each view with the appropriate view from the activity_main layout file
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.items);
        Model model = Model.getInstance();
        List<FoodItem> items = model.getFoodItems();
        //We use our own custom adapter to convert a list of food items into a viewable state
        mAdapter = new FridgeListAdapter(items);
        recyclerView.setAdapter(mAdapter);

        //starts the AddFoodItemActivity, expecting data for a FoodItem in return
        Button addButton = findViewById(R.id.button_first);
        addButton.setOnClickListener((view)->{
            Intent intent = new Intent(this, AddFoodItemActivity.class);
            startActivityForResult(intent, ADD_FOOD_ITEM_REQUEST);
        });

        //starts the GroceryListsActivity
        Button groceryListButton = findViewById(R.id.button_second);
        groceryListButton.setOnClickListener((view)->{
            Intent intent = new Intent(this, ViewGroceryListsActivity.class);
            startActivity(intent);
        });
    }

    // gets the data from the AddFoodItemActivity, converts it to a FoodItem and adds it to the list
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_FOOD_ITEM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    String name = bundle.getString("name");
                    int productId = 100; //reverse lookup name to get actual product id later
                    int quantity = bundle.getInt("quantity");
                    Date date = (Date) bundle.getSerializable("date");
                    FoodItem newItem = new FoodItem(name, productId, quantity, date);
                    Model model = Model.getInstance();
                    List<FoodItem> items = model.getFoodItems();
                    List<FoodItem> newList = FoodItem.addFoodItemToList(items, newItem);
                    model.setFoodItems(newList);
                    mAdapter.setFoodItems(newList);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}