package com.team300.fridge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.team300.fridge.POJOs.FoodItem;
import com.team300.fridge.POJOs.Model;
import com.team300.fridge.POJOs.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This inner class is our custom adapter.  It takes our basic model information and
 * converts it to the correct layout for this view.
 *
 * In this case, we are just mapping the name and quantity of a FoodItem object to text fields.
 */
public class FridgeListAdapter
        extends RecyclerView.Adapter<FridgeListAdapter.ViewHolder> {

    /**
     * Collection of the items to be shown in this list.
     */
    private List<FoodItem> mFoodItems;

    /**
     * This inner class represents a ViewHolder which provides us a way to cache information
     * about the binding between the model element (in this case a FoodItem) and the widgets in
     * the list view (in this case the two TextViews)
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
    public FridgeListAdapter(List<FoodItem> items) {
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
        final User user = Model.getInstance().getCurrentUser();
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
                            case R.id.eat:
                                //indicate app will delete one
                                List<FoodItem> newList1 = delete(holder.getAdapterPosition(), v, false);
                                user.setFoodItems(newList1);
                                return true;
                            case R.id.eat_all:
//                                Snackbar.make(v, "Delete all " + holder.mFoodItem.getName() + " selected", Snackbar.LENGTH_LONG)
//                                        .setAction("Action", null).show();
                                List<FoodItem> newList2 = deleteAll(holder.getAdapterPosition(), v, false);
                                user.setFoodItems(newList2);
                                return true;
                            case R.id.throw_one:
                                List<FoodItem> newList3 = delete(holder.getAdapterPosition(), v, true);
                                user.setFoodItems(newList3);
                                return true;
                            case R.id.throw_all:
                                List<FoodItem> newList4 = deleteAll(holder.getAdapterPosition(), v, true);
                                user.setFoodItems(newList4);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
    }
        //delete 1 from the quantity from the clicked on item
    public List<FoodItem> delete(int position, View v, boolean thrown){
        FoodItem food = mFoodItems.get(position);
        //TODO: uncomment and add proper call to get food cost
//        if(thrown){
//            float cost = food.getCost();
//            updateBarChart(FinanceTrackerActivity.getCurrMonth(), cost);
//        }
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
        return mFoodItems;
    }

    public List<FoodItem> deleteAll(int position, View v, boolean thrown){
        FoodItem food = mFoodItems.get(position);
        //TODO: uncomment and add proper call to get food cost
//        if(thrown){
//            float cost = food.getCost() * food.getQuantity();
//            updateBarChart(FinanceTrackerActivity.getCurrMonth(), cost);
//        }
        //for good measure
        food.setQuantity(0);
        //remove from list bc there are none in fridge
        mFoodItems.remove(food);
        this.notifyItemRemoved(position);
        return mFoodItems;
    }

    //Update monthly total data & bar chart when you throw away something from your fridge
    public void updateBarChart(String month, float newWaste){
        float oldTotal = 0;
        ArrayList<Float> monthTotals = FinanceTrackerActivity.getMonthTotals();
        switch(month) {
            case "Jan":
                oldTotal = monthTotals.get(0);
                monthTotals.set(0, oldTotal + newWaste);
            case "Feb":
                oldTotal = monthTotals.get(1);
                monthTotals.set(0, oldTotal + newWaste);
            case "Mar":
                oldTotal = monthTotals.get(2);
                monthTotals.set(0, oldTotal + newWaste);
            case "Apr":
                oldTotal = monthTotals.get(3);
                monthTotals.set(0, oldTotal + newWaste);
            case "May":
                oldTotal = monthTotals.get(4);
                monthTotals.set(0, oldTotal + newWaste);
            case "June":
                oldTotal = monthTotals.get(5);
                monthTotals.set(0, oldTotal + newWaste);
            case "July":
                oldTotal = monthTotals.get(6);
                monthTotals.set(0, oldTotal + newWaste);
            case "Aug":
                oldTotal = monthTotals.get(7);
                monthTotals.set(0, oldTotal + newWaste);
            case "Sep":
                oldTotal = monthTotals.get(8);
                monthTotals.set(0, oldTotal + newWaste);
            case "Oct":
                oldTotal = monthTotals.get(9);
                monthTotals.set(0, oldTotal + newWaste);
            case "Nov":
                oldTotal = monthTotals.get(10);
                monthTotals.set(0, oldTotal + newWaste);
            case "Dec":
                oldTotal = monthTotals.get(2);
                monthTotals.set(0, oldTotal + newWaste);
        }
        FinanceTrackerActivity.setMonthTotals(monthTotals);
    }

    @Override
    public int getItemCount() {
        return mFoodItems.size();
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.mFoodItems = foodItems;
    }
}