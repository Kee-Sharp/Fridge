package com.team300.fridge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class AddGroceryListActivity extends AppCompatActivity {

    protected EditText groceryListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery_list);

        groceryListName = findViewById(R.id.groceryListName);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Model model = Model.getInstance();
        List<FoodItem> items = model.getAllFoodItems();
        GroceryListFoodAdapter mAdapter = new GroceryListFoodAdapter(items);
        recyclerView.setAdapter(mAdapter);

        Button finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener((view) -> {
            //return to ViewGroceryListsActivity with information
        });

    }
}