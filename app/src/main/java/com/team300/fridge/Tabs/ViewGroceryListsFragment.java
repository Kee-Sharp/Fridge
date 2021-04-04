package com.team300.fridge.Tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.team300.fridge.AddGroceryListActivity;
import com.team300.fridge.POJOs.FoodItem;
import com.team300.fridge.POJOs.GroceryList;
import com.team300.fridge.GroceryListAdapter;
import com.team300.fridge.POJOs.Model;
import com.team300.fridge.POJOs.User;
import com.team300.fridge.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewGroceryListsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewGroceryListsFragment extends Fragment {

    protected GroceryListAdapter mAdapter;
    private static final int ADD_GROCERY_LIST_REQUEST = 1;

    public ViewGroceryListsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ViewGroceryListsFragment.
     */
    public static ViewGroceryListsFragment newInstance() {
        return new ViewGroceryListsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_grocery_lists, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        assert view != null;

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        User user = Model.getInstance().getCurrentUser();
        List<GroceryList> groceryLists = user.getGroceryLists();
        mAdapter = new GroceryListAdapter(groceryLists);
        recyclerView.setAdapter(mAdapter);

        TextView noGroceryLists = view.findViewById(R.id.no_grocery_lists);
        if (groceryLists.size() == 0) {
            //Replace recyclerView with text saying no items
            noGroceryLists.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        Button addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), AddGroceryListActivity.class);
            startActivityForResult(intent, ADD_GROCERY_LIST_REQUEST);
        });
    }

    //adds the new GroceryList into the main list
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_GROCERY_LIST_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    GroceryList newGroceryList = bundle.getParcelable("new_list");
                    User user = Model.getInstance().getCurrentUser();
                    user.addGroceryList(newGroceryList);
                    mAdapter.setItems(user.getGroceryLists());
                }
            }
        }
    }
}