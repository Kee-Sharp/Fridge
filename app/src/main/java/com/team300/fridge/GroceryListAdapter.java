package com.team300.fridge;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class GroceryListAdapter
        extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {


    private List<GroceryList> mGroceryLists;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }

    }
    /**
     * set the items to be used by the adapter
     * @param groceryLists the list of grocery lists to be displayed in the recycler view
     */
    public GroceryListAdapter(List<GroceryList> groceryLists) {
        mGroceryLists = groceryLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
