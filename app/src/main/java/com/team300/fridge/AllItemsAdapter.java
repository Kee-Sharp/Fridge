package com.team300.fridge;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AllItemsAdapter extends RecyclerView.Adapter<AllItemsAdapter.ViewHolder> {
    /**
     * Collection of the items to be shown in this list.
     */
    private List<FoodItem> mAllItems;
    private List<FoodItem> mAllItemsCopy;

    /**
     * This inner class represents a ViewHolder which provides us a way to cache information
     * about the binding between the model element (in this case a FoodItem) and the widgets in
     * the list view (in this case the one TextView)
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public FoodItem mFoodItem;

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
     * @param items the list of items to be displayed in the recycler view
     */
    public AllItemsAdapter(List<FoodItem> items) {
        mAllItems = items;
        mAllItemsCopy = new ArrayList<>();
        mAllItemsCopy.addAll(items);
    }

    @Override
    public AllItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*

          This sets up the view for each individual item in the recycler display
          To edit the actual layout, we would look at: res/layout/text_row_item.xml
          If you look at the example file, you will see it currently just 2 TextView elements
         */
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_food_item, parent, false);
        return new AllItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AllItemsAdapter.ViewHolder holder, int position) {
        /*
        This is where we have to bind each data element in the list (given by position parameter)
        to an element in the view (which is the TextView widget
         */
        //start by getting the element at the correct position
        FoodItem item = mAllItems.get(position);
        holder.mFoodItem = item;
        /*
          Now we bind the data to the widgets.  In this case, pretty simple, put the name in a
          textview
        */
        holder.mNameView.setText(item.getName());

        /*
         * set up a listener to handle if the user clicks on this list item, what should happen?
         */
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v, item);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mAllItems.size();
    }

    public void filter(String text) {
        mAllItems.clear();
        if(text.isEmpty()){
            mAllItems.addAll(mAllItemsCopy);
        } else{
            text = text.toLowerCase();
            for(FoodItem item: mAllItemsCopy){
                if(item.getName().toLowerCase().contains(text)){
                    mAllItems.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void openDialog(View v, FoodItem item) {
        FoodItemDialog dialog = new FoodItemDialog();
        Bundle args = new Bundle();
        args.putString("Food_Item_Name", item.getName());
        dialog.setArguments(args);
        AddFoodItemActivity a = (AddFoodItemActivity) v.getContext();
        dialog.show(a.getSupportFragmentManager(), "food item dialog");
    }
}
