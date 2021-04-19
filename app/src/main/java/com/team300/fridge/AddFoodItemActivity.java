package com.team300.fridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.team300.fridge.POJOs.Model;
import com.team300.fridge.POJOs.Product;
import com.team300.fridge.POJOs.FoodItem;

import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AddFoodItemActivity extends AppCompatActivity implements FoodItemDialog.FoodItemDialogListener {
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);
        MainActivity.setContext(this);
        // set up views
        RecyclerView recyclerView = findViewById(R.id.items);
        Model model = Model.getInstance();
        List<Product> items = model.get_products();
        AllItemsAdapter mAdapter = new AllItemsAdapter(items);
        recyclerView.setAdapter(mAdapter);

        //return to MainActivity with no new food item
        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //only filters on submit or when text is empty
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
        //TODO: doesn't work right now
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

        //submits the text to filter
        Button button = findViewById(R.id.searchButton);
        button.setOnClickListener((view)->{
            searchView.setQuery(searchView.getQuery(), true);
        });
    }

    //pass information about the new FoodItem back to the MainActivity
    @Override
    public void saveInformation(String name,  int _id, String quantity, String dateString) {
        //reset searchView before going back
        searchView.setQuery("", true);
        String[] separated = dateString.split("/");
        int month = Integer.parseInt(separated[0]);
        int day = Integer.parseInt(separated[1]);
        int year = Integer.parseInt(separated[2]);
        Date date = Model.toDate(LocalDate.of(year, month, day));
        Product p = Model.getInstance().get_products().get(_id);
        MainActivity.getUiThreadRealm().executeTransaction(r-> {
            FoodItem f = r.createObject(FoodItem.class, new ObjectId());
            f.setName(name);
            f.setProductId(p);
            f.setQuantity(Integer.parseInt(quantity));
            f.setPurchaseDate(date);
        });
        Intent result = new Intent(AddFoodItemActivity.this, MainActivity.class);
        result.putExtra("name", name);
        result.putExtra("quantity", Integer.parseInt(quantity));
        result.putExtra("date", date);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}