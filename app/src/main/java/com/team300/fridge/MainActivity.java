package com.team300.fridge;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected SimpleFoodItemRecyclerViewAdapter mAdapter;
    protected static int newItemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //populate each view with the appropriate view from the activity_main layout file
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.items);
        Model model = Model.getInstance();
        List<FoodItem> items = model.getFoodItems();
        //We use our own custom adapter to convert a list of food items into a viewable state
        mAdapter = new SimpleFoodItemRecyclerViewAdapter(items);
        recyclerView.setAdapter(mAdapter);

        Button addButton = findViewById(R.id.button_first);
        //adds the same 4 static items to the list, will be replaced with the addFoodItem activity
        addButton.setOnClickListener((view)->{
            List<FoodItem> newItems = new ArrayList<>();
            Date today = new Date();
            newItems.add(new FoodItem("Banana", 5, 3, today));
            newItems.add(new FoodItem("Bacon", 6, 8, today));
            newItems.add(new FoodItem("Ketchup", 7, 1, today));
            newItems.add(new FoodItem("Grape Fanta", 8, 2, today));
            model.addFoodItem(newItems.get(newItemCount++ % 4));
            mAdapter.setFoodItems(model.getFoodItems());
            mAdapter.notifyItemInserted(model.getFoodItems().size() - 1);
        });
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