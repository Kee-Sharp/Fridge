package com.team300.fridge;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team300.fridge.POJOs.FoodItem;
import com.team300.fridge.POJOs.Model;
import com.team300.fridge.POJOs.User;

import java.util.ArrayList;
import java.util.List;

public class GroceryListFoodAdapter
        extends RecyclerView.Adapter<GroceryListFoodAdapter.ViewHolder>{

    private List<FoodItem> mItems;
    private List<FoodItem> mCurrentUserItems;
    private List<Integer> selectedPositions;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
    /**
     * set the items to be used by the adapter
     * @param items the list of food items to be displayed in the recycler view
     */
    public GroceryListFoodAdapter(List<FoodItem> items, List<FoodItem> currentUserItems) {
        mItems = items;
        mCurrentUserItems = currentUserItems;
        selectedPositions = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.search_food_item, viewGroup, false);
        return new GroceryListFoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        FoodItem item = mItems.get(position);
        viewHolder.mNameView.setText(item.getName());
        viewHolder.mView.setSelected(selectedPositions.contains(position));
        //highlight the items that are currently in fridge
        boolean contains = false;
        for (FoodItem f: mCurrentUserItems) {
            if (item.getName().equals(f.getName())) contains = true;
        }
        if (contains) {
            viewHolder.mNameView.setTextColor(Color.GRAY);
        } else {
            viewHolder.mNameView.setTextColor(Color.BLACK);
        }
        viewHolder.mView.setOnClickListener((view) -> {
            //TODO: we could potentially just use the position parameter but this is how an example
            // did this, look into later
            int viewPosition = viewHolder.getLayoutPosition();
            if (selectedPositions.contains(viewPosition)) {
                //since we're using integers, List.remove(int) is removing the index of int so use
                // removeAll instead to remove the instance of the int value
                List<Integer> p = new ArrayList<>();
                p.add(viewPosition);
                selectedPositions.removeAll(p);
            } else {
                selectedPositions.add(viewPosition);
            }
            notifyItemChanged(viewPosition);
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public List<FoodItem> getSelectedItems() {
        List<FoodItem> selectedItems = new ArrayList<>();
        for (int i: selectedPositions) {
            selectedItems.add(mItems.get(i));
        }
        return selectedItems;
    }
}
