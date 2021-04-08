package com.team300.fridge.Tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.team300.fridge.AddFoodItemActivity;
import com.team300.fridge.MainActivity;
import com.team300.fridge.POJOs.FoodItem;
import com.team300.fridge.FridgeListAdapter;
import com.team300.fridge.POJOs.Model;
import com.team300.fridge.R;
import com.team300.fridge.POJOs.User;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FridgeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FridgeFragment extends Fragment {

    protected FridgeListAdapter mAdapter;
    private static final int ADD_FOOD_ITEM_REQUEST = 1;


    public FridgeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FridgeFragment.
     */
    public static FridgeFragment newInstance() {
        return new FridgeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fridge, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        assert view != null;

        RecyclerView recyclerView = view.findViewById(R.id.items);
        Model model = Model.getInstance();
        List<FoodItem> items = model.getCurrentUser().getFoodItems();
        //We use our own custom adapter to convert a list of food items into a viewable state
        mAdapter = new FridgeListAdapter(items);
        recyclerView.setAdapter(mAdapter);

        //starts the AddFoodItemActivity, expecting data for a FoodItem in return
        Button addButton = view.findViewById(R.id.button_first);
        addButton.setOnClickListener((v)->{
            Intent intent = new Intent(getActivity(), AddFoodItemActivity.class);
            startActivityForResult(intent, ADD_FOOD_ITEM_REQUEST);
        });

        //only for demonstrating different users while login/registration arent implemented
        Button switchUserButton = view.findViewById(R.id.button_switch_user);
        switchUserButton.setOnClickListener((v)->{
            //switch user out and refresh main activity
//            model.switchUser();
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.switchOutUser();
        });

    }

    // gets the data from the AddFoodItemActivity, converts it to a FoodItem and adds it to the list
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                    User currentUser = Model.getInstance().getCurrentUser();
                    List<FoodItem> items = currentUser.getFoodItems();
                    List<FoodItem> newList = FoodItem.addFoodItemToList(items, newItem);
                    currentUser.setFoodItems(newList);
                    mAdapter.setFoodItems(newList);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}