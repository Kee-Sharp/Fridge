package com.team300.fridge;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
            Intent result = new Intent(AddGroceryListActivity.this, ViewGroceryListsActivity.class);
            GroceryList newGroceryList = new GroceryList(groceryListName.getText().toString(), mAdapter.getSelectedItems());
            Log.d("grocery list", newGroceryList.toString());
            result.putExtra("new list", newGroceryList);
            setResult(Activity.RESULT_OK, result);
            finish();
        });

    }
}