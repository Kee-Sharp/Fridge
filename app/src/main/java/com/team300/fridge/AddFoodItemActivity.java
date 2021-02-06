package com.team300.fridge;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class AddFoodItemActivity extends AppCompatActivity implements FoodItemDialog.FoodItemDialogListener {
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);
        // set up views
        RecyclerView recyclerView = findViewById(R.id.items);
        Model model = Model.getInstance();
        List<FoodItem> items = model.getAllFoodItems();
        AllItemsAdapter mAdapter = new AllItemsAdapter(items);
        recyclerView.setAdapter(mAdapter);

        Button buttonBack = findViewById(R.id.button_second);
        buttonBack.setOnClickListener(v -> {
            Intent result = new Intent(AddFoodItemActivity.this, MainActivity.class);
            setResult(Activity.RESULT_CANCELED, result);
            finish();
        });

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //only filters on submit
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    mAdapter.filter(newText);
                    return true;
                }
                return false;
            }
        });
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    buttonBack.setVisibility(View.GONE);
                } else {
                    buttonBack.setVisibility(View.VISIBLE);
                }
            }
        });

        Button button = findViewById(R.id.searchButton);
        button.setOnClickListener((view)->{
            searchView.setQuery(searchView.getQuery(), true);
        });
    }

    @Override
    public void saveInformation(String name, String quantity, String dateString) {
        searchView.setQuery("", true);
        String[] separated = dateString.split("/");
        int month = Integer.parseInt(separated[0]);
        int day = Integer.parseInt(separated[1]);
        int year = Integer.parseInt(separated[2]);
        Date date = java.util.Date.from(LocalDate.of(year, month, day).atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        Log.d("date", date.toString());
        Intent result = new Intent(AddFoodItemActivity.this, MainActivity.class);
        result.putExtra("name", name);
        result.putExtra("quantity", Integer.parseInt(quantity));
        result.putExtra("date", date);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}