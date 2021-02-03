package com.team300.fridge;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This inner class is our custom adapter.  It takes our basic model information and
 * converts it to the correct layout for this view.
 *
 * In this case, we are just mapping the toString of the Course object to a text field.
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
                Snackbar.make(v, "Clicked on " + holder.mFoodItem.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

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

    @Override
    public int getItemCount() {
        return mFoodItems.size();
    }


}