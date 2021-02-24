package com.team300.fridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ViewGroceryListsActivity extends AppCompatActivity {

    protected GroceryListAdapter mAdapter;
    private static final int ADD_GROCERY_LIST_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_lists);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Model model = Model.getInstance();
        List<FoodItem> items = model.getFoodItems();
        List<GroceryList> groceryLists = model.getGroceryLists();
        mAdapter = new GroceryListAdapter(groceryLists);
        recyclerView.setAdapter(mAdapter);

        TextView noGroceryLists = findViewById(R.id.no_grocery_lists);
        if (items.size() == 0) {
            //Replace recyclerView with text saying no items
            noGroceryLists.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener((view) -> {
            Intent intent = new Intent(this, AddGroceryListActivity.class);
            startActivityForResult(intent, ADD_GROCERY_LIST_REQUEST);
        });

    }
}