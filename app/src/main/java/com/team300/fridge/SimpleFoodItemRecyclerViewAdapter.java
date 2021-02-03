package com.team300.fridge;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

/**
 * This inner class is our custom adapter.  It takes our basic model information and
 * converts it to the correct layout for this view.
 *
 * In this case, we are just mapping the name and quantity of a FoodItem object to text fields.
 */
public class SimpleFoodItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleFoodItemRecyclerViewAdapter.ViewHolder> {

    /**
     * Collection of the items to be shown in this list.
     */
    private List<FoodItem> mFoodItems;

    /**
     * This inner class represents a ViewHolder which provides us a way to cache information
     * about the binding between the model element (in this case a FoodItem) and the widgets in
     * the list view (in this case the two TextView)
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNumberView;
        public final TextView mNameView;
        public FoodItem mFoodItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNumberView = (TextView) view.findViewById(R.id.number);
            mNameView = (TextView) view.findViewById(R.id.name);
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
    public SimpleFoodItemRecyclerViewAdapter(List<FoodItem> items) {
        mFoodItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*

          This sets up the view for each individual item in the recycler display
          To edit the actual layout, we would look at: res/layout/text_row_item.xml
          If you look at the example file, you will see it currently just 2 TextView elements
         */
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Model model = Model.getInstance();
        /*
        This is where we have to bind each data element in the list (given by position parameter)
        to an element in the view (which is one of our two TextView widgets
         */
        //start by getting the element at the correct position
        FoodItem item = mFoodItems.get(position);
        holder.mFoodItem = item;
        /*
          Now we bind the data to the widgets.  In this case, pretty simple, put the quantity in
          one textview and the name of a food item in the other.
         */
        holder.mNumberView.setText("" + item.getQuantity());
        holder.mNameView.setText(item.getName());

        /*
         * set up a listener to handle if the user clicks on this list item, what should happen?
         */
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //An example on click for the food items
                Snackbar.make(v, holder.mFoodItem.getName() + " selected", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //get view context
                Context context = v.getContext();
                //create popup with delete options
                PopupMenu popup = new PopupMenu(context, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.delete_menu, popup.getMenu());
                popup.show();
                //handle which option the user selects
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item){
                        switch(item.getItemId()){
                            case R.id.delete:
                                //indicate app will delete one
                                Snackbar.make(v, "Delete one " + holder.mFoodItem.getName() + " selected", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                delete(holder.getAdapterPosition(), v);
                                return true;
                            case R.id.delete_all:
                                Snackbar.make(v, "Delete all " + holder.mFoodItem.getName() + " selected", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                deleteAll(holder.getAdapterPosition(), v);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                // code from another app, shows how to move to another activity with the on click
//                Context context = v.getContext();
//                //create our new intent with the new screen (activity)
//                Intent intent = new Intent(context, CourseDetailActivity.class);
//                /*
//                    pass along the id of the course so we can retrieve the correct data in
//                    the next window
//                 */
//                intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, holder.mCourse.getId());
//
//                model.setCurrentCourse(holder.mCourse);
//
//                //now just display the new window
//                context.startActivity(intent);

            }
        });
    }
        //delete 1 from the quantity from the clicked on item
    public void delete(int position, View v){
        FoodItem food = mFoodItems.get(position);
        int oldQuantity = food.getQuantity();
        int newQuantity = oldQuantity;
        if(oldQuantity > 0) {
            newQuantity = oldQuantity - 1;
        }
        //if newQuantity is zero, remove item from list
        if(newQuantity <= 0){
            mFoodItems.remove(food);
            this.notifyItemRemoved(position);
        } else {
            food.setQuantity(newQuantity);
            //replace item with updated item
            mFoodItems.set(position, food);
            this.notifyItemChanged(position);
        }
        //test to make sure data updated
        Snackbar.make(v, mFoodItems.get(position).getName() + " new quantity is: " + mFoodItems.get(position).getQuantity(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void deleteAll(int position, View v){
        FoodItem food = mFoodItems.get(position);
        //for good measure
        food.setQuantity(0);
        //remove from list bc there are none in fridge
        mFoodItems.remove(food);
        this.notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mFoodItems.size();
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.mFoodItems = foodItems;
    }
}