package com.team300.fridge;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener((view)-> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });

        Button finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener((view) -> {
            //error handling
            boolean error = false;
            if (groceryListName.getText().toString().length() == 0) {
                groceryListName.setError("Must have a name");
                error = true;
            }
            if (mAdapter.getSelectedItems().size() == 0) {
                Toast toast = Toast.makeText(getApplicationContext(), "No items selected", Toast.LENGTH_SHORT);
                toast.show();
                error = true;
            }
            if (!error) {
                //return to ViewGroceryListsFragment with information
                Intent result = new Intent(AddGroceryListActivity.this, MainActivity.class);
                GroceryList newGroceryList = new GroceryList(groceryListName.getText().toString(), mAdapter.getSelectedItems());
                result.putExtra("new_list", newGroceryList);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

    }
}